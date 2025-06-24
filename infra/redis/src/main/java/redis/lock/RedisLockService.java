package redis.lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@Import(RedissonConfig.class)
public class RedisLockService {

   private final RedissonConfig config;
   private RedissonClient redissonClient;

   public RedisLockService(RedissonConfig config, RedissonClient redissonClient) {
       this.config = config;
       this.redissonClient = redissonClient;
   }

   public boolean doSomethingWithLock(String lockKey, int waitTime, int leaseTime) throws IOException {
       if (redissonClient == null) {
           redissonClient = config.redissonClient();
       }
       
       RLock lock = redissonClient.getLock("lock:" + lockKey);
       boolean acquired = false;
       
       try {
           acquired = lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
           long threadId = Thread.currentThread().getId();
           
           if (acquired) {
               log.info("Thread Id: {} Lock acquired!", threadId);
           } else {
               log.warn("Thread Id: {} Lock not acquired.", threadId);
           }
       } catch (InterruptedException e) {
           Thread.currentThread().interrupt();
           log.error("Thread interrupted while trying to acquire lock", e);
       } finally {
            // 현재 쓰레드가 락 보유중일 때만 실행
           if (acquired && lock.isHeldByCurrentThread()) {
               lock.unlock();
               log.info("Thread Id: {} Lock released! (after unlock status)", 
                   Thread.currentThread().getId());
           }
       }
       return acquired;
   }
}