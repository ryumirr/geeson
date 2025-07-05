package redis.lock;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.awaitility.Awaitility.await;
import org.springframework.boot.test.context.SpringBootTest;
import org.redisson.api.RedissonClient;
import org.redisson.api.RLock;
import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

@SpringBootTest(classes = RedisLockService.class)
@ExtendWith(MockitoExtension.class)
public class RedisLockServiceTest {
    private RedissonClient mockClient;
    private RLock mockLock;
    private ExecutorService executor;

    @BeforeEach
    void setUp() {
        mockClient = mock(RedissonClient.class);
        mockLock = mock(RLock.class);
    }

    @AfterEach
    void shutdown() {
        if (executor != null) {
            executor.shutdown();
        }
    }

    /**
     * Lock이 잘 동작하나 확인
     * 
     * @throws Exception
     */
    @Test
    void testSingleLockConcurrency() throws Exception {
        when(mockClient.getLock("lock:testKey")).thenReturn(mockLock);
        when(mockLock.tryLock(3, 5, TimeUnit.SECONDS)).thenReturn(true);
        when(mockLock.isHeldByCurrentThread()).thenReturn(true);

        RedisLockService redisLockService = new RedisLockService();
        redisLockService.doSomethingWithLock("testKey", 3, 5, TimeUnit.SECONDS);

        // Lock풀렸나 확인
        verify(mockLock).unlock();
    }

    /**
     * 다중Lock 테스트 추가(쓰레드 n개 예정)
     * 대기를 n초, 점유 m초-> 대기시간 초과로 실패
     * 
     * @throws InterruptedException
     */
    @ParameterizedTest
    @MethodSource("testGetLockConcurrencyProvider")
    void testGetLockConcurrency(int threadCount, int waitTime, int leaseTime, boolean returnVal) throws InterruptedException {
        executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        RedisLockService redisLockService = new RedisLockService();
        // 여러 스레드 간의 공유 불변 상태 관리
        AtomicBoolean lockAcquired = new AtomicBoolean(false);
        
        when(mockClient.getLock("lock:testKey")).thenReturn(mockLock);
        when(mockLock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS)).thenReturn(returnVal);
        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    await()
                    .atMost(Duration.ofSeconds(waitTime))
                    .untilAsserted(() -> {
                            // lock얻었으면 break
                            if (lockAcquired.get()) {
                                verify(mockLock, atLeastOnce()).unlock();
                                return;
                            }
                            if (redisLockService.doSomethingWithLock("testKey", waitTime, leaseTime, TimeUnit.SECONDS)) {
                                lockAcquired.set(true);
                            }
                            System.out.println("TEST Thread Id:" + Thread.currentThread().getId() + " WaitTime:" + waitTime + " leaseTime:" + leaseTime);
                    });
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                } finally {
                    // 처리가 끝나면 latch 카운터를 1 줄인다
                    latch.countDown();
                }
            });
        }
        latch.await();
    }

    /**
     * @return Stream<Arguments>
     */
    static Stream<Arguments> testGetLockConcurrencyProvider() {
        return Stream.of(
            // waitTime < leaseTime
            Arguments.of(1, 2, 3, false),
            Arguments.of(2, 3, 5, false),
            Arguments.of(4, 5, 6, false),
            Arguments.of(5, 1, 5, false),
            // waitTime > leaseTime
            Arguments.of(2, 6, 4, true),
            Arguments.of(2, 5, 4, true),
            Arguments.of(2, 9, 4, true),
            Arguments.of(2, 4, 2, true));
    }
}