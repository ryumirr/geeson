package redis.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
@Import(RedissonConfig.class)
public class RedisLockService {

    @Autowired
    private RedissonConfig config;

    private RedissonClient redissonClient;

    public RedisLockService(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    public boolean doSomethingWithLock(String lockKey, int waitTime, int leaseTime) throws IOException {
        if (redissonClient == null) {
            redissonClient = config.redissonClient();
        }
        RLock lock = redissonClient.getLock("lock:" + lockKey);
        boolean acquired = false;
        try {
            // 대기 최대 3초, 점유 5초
            acquired = lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
            System.out.print("Thread Id:" + Thread.currentThread().getId());
            if (acquired) {
                System.out.println(" Lock acquired!");
            } else {
                System.out.println("Lock not acquired.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            if (acquired) {
                lock.unlock();
                System.out.println("Thread Id:" + Thread.currentThread().getId() + 
                " Lock released! (after unlock status)\n");
            }
        }
        return acquired;
    }
}