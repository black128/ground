package com.zrw.playground.redisDemo;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestRedis {

    @Test
    public void test() {
        Jedis jedis = new Jedis("192.168.32.129", 6379, 10000);
        String set = jedis.set("8af36ded-7a47-471f-9362-398ee4b7ea8b", "value", SetParams.setParams().nx().px(1000000));
        StringBuilder sb = new StringBuilder();
        sb.append("if redis.call(\"get\",KEYS[1]) == ARGV[1] ");
        sb.append("then ");
        sb.append("    return redis.call(\"del\",KEYS[1]) ");
        sb.append("else ");
        sb.append("    return 0 ");
        sb.append("end ");
        String script = sb.toString();
        System.out.println(jedis.get("8af36ded-7a47-471f-9362-398ee4b7ea8b"));
        System.out.println(jedis.eval(script, Collections.singletonList("8af36ded-7a47-471f-9362-398ee4b7ea8b"), Collections.singletonList("value")));

    }

    public static Lock lock = new ReentrantLock();
    public int count = 100;
    private static final CountDownLatch LATCH = new CountDownLatch(100);
    public ThreadLocal<String> threadLocal = new ThreadLocal<>();
    public Jedis jedis = new Jedis("192.168.32.129", 6379, 1000000);
    JedisPool jedisPool = new JedisPool("192.168.32.129", 6379);
    public String key = "key";
    public Runnable runnable = () -> {
        do {
            String value = UUID.randomUUID().toString();
            Jedis resource = jedisPool.getResource();
            try {
                Boolean aBoolean = redisLock(resource, key, value, 10000);
                if(aBoolean){
                    Thread.sleep(5000);
                    System.out.println(Thread.currentThread().getName() + ":" + count);
                    count--;
                }else {
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if(!removeLock(resource, key, value)){
                    continue;
                }
                LATCH.countDown();
//                lock.unlock();
            }

        } while (count > 0);

    };

    @Test
    public void test2() throws InterruptedException {

        new Thread(runnable).start();
        new Thread(runnable).start();
        LATCH.await();


    }

    /**
     * 简单锁
     */
    public Boolean redisLock(Jedis jedis,String key,String value,int expireTime){
        String set = jedis.set(key, value, SetParams.setParams().nx().px(expireTime));
        if("OK".equals(set)){
            System.out.println("枷锁"+key);
            threadLocal.set(key);
            return true;
        }
        return false;
    }
    //解锁
    public Boolean removeLock(Jedis jedis,String key,String value){
//        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        System.out.println("开始解锁");
        StringBuilder sb = new StringBuilder();
        sb.append("if redis.call(\"get\",KEYS[1]) == ARGV[1] ");
        sb.append("then ");
        sb.append("    return redis.call(\"del\",KEYS[1]) ");
        sb.append("else ");
        sb.append("    return 0 ");
        sb.append("end ");
        String script = sb.toString();


        Object eval = null;
        if(value.equals(threadLocal.get())){
            eval = jedis.eval(script, Collections.singletonList(key), Collections.singletonList(value));
        }
        if(eval != null && "1".equals(eval.toString())){
            System.out.println("解锁成功");
            return true;
        }
        return false;
    }
}
