package redis.lock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisLockService {

    private RedissonClient redissonClient;

    /**
     * Spring DI 안되는 외부라이브러리 -> 테스트 위해 생성자를 명시적으로 작성
     * 
     * @param redissonClient
     */
    public RedisLockService(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    /**
     * 분산락을 수행하며 waitTime과 leaseTime을 설정합니다.
     *
     * @param key       락의 key
     * @param waitTime  락을 기다리는 최대 시간
     * @param leaseTime 락을 점유하는 시간
     * @param unit  시간 단위
     * @return
     */
    public boolean doSomethingWithLock(String key, int waitTime, int leaseTime, TimeUnit unit)
            throws InterruptedException {
        RLock lock = redissonClient.getLock("lock:" + key);
        boolean acquired = lock.tryLock(waitTime, leaseTime, unit);
        if (!acquired) {
            return false;
        }
        try {
            // 실제 처리 로직
            System.out.println("🔒 Lock acquired by " + Thread.currentThread().getName());
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
                System.out.println("🔓 Lock released by " + Thread.currentThread().getName());
            }
        }
        return true;
    }
}