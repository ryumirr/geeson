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
     * 분산락을 수행하며 waitTime과 leaseTime을 설정합니다.
     *
     * @param key       락의 key
     * @param waitTime  락을 기다리는 최대 시간
     * @param leaseTime 락을 점유하는 시간
     * @param timeUnit  시간 단위
     * @return
     */
    public boolean doSomethingWithLock(String key, long waitTime, long leaseTime, TimeUnit timeUnit) {
        RLock lock = redissonClient.getLock(key);
        boolean acquired = false;
        try {
            acquired = lock.tryLock(waitTime, leaseTime, timeUnit);
            if (acquired) {
                // TODO: 비즈니스 로직 수행
                log.info("Lock acquired for key: {}", key);
            } else {
                log.info("Failed to acquire lock for key: {}", key);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.info("Thread interrupted while trying to acquire lock for key: {}", key);
        } finally {
            if (acquired && lock.isHeldByCurrentThread()) {
                lock.unlock();
                log.info("Lock released for key: {}", key);
            }
        }
        return acquired;
    }
}