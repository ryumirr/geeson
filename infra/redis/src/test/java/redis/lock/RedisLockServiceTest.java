package redis.lock;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.redisson.api.RedissonClient;
import org.redisson.api.RLock;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

@SpringBootTest(classes = RedisLockService.class)
@ExtendWith(MockitoExtension.class)
public class RedisLockServiceTest {
    @Mock
    private RedissonClient mockClient;

    @Mock
    private RLock mockLock;

    private RedisLockService redisLockService;

    private ExecutorService executor;

    @BeforeEach
    void setUp() {
        // 테스트 간 상태 공유를 피하기 위해
        when(mockClient.getLock(anyString())).thenReturn(mockLock);
        redisLockService = new RedisLockService(mockClient);
    }

    @AfterEach
    void shutdown() {
        // memory leak 방지용
        if (executor != null)
            executor.shutdownNow();
    }

    @Test
    void testSingleLockConcurrency() throws Exception {
        when(mockClient.getLock("lock:testKey")).thenReturn(mockLock);
        when(mockLock.tryLock(3, 5, TimeUnit.SECONDS)).thenReturn(true);
        when(mockLock.isHeldByCurrentThread()).thenReturn(true);

        boolean result = redisLockService.doSomethingWithLock("testKey", 3, 5, TimeUnit.SECONDS);

        assert result;
        verify(mockLock).unlock();
    }

    @ParameterizedTest
    @MethodSource("testGetLockConcurrencyProvider")
    void testGetLockConcurrency(int threadCount, int waitTime, int leaseTime, boolean returnVal)
            throws InterruptedException {
        executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicBoolean lockAcquired = new AtomicBoolean(false);

        // returnVal값에 따라 조건부 이용
        if (returnVal) {
            lenient().when(mockLock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS)).thenReturn(true);
        } else {
            lenient().when(mockLock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS)).thenReturn(false);
        }

        lenient().when(mockLock.isHeldByCurrentThread()).thenReturn(true);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    boolean result = redisLockService.doSomethingWithLock("testKey", waitTime, leaseTime,
                            TimeUnit.SECONDS);
                    if (result) {
                        lockAcquired.set(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        System.out.println("✅ All threads completed");
    }

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