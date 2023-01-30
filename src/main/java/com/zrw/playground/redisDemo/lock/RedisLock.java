package com.zrw.playground.redisDemo.lock;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;


/**
 * @author zrw
 */
@Service
public class RedisLock implements Lock {
	
//	@Resource
	private JedisConnectionFactory factory;
	
	private static final String LOCK = "lock";
	private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();
	
	@Override
	public void lock() {
		while (true) {
			// 1.尝试加锁
			if (tryLock()) {
				return;
			}
			// 2.加锁失败，当前任务休眠一段时间
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}

	@Override
	public boolean tryLock() {
		String uuid = UUID.randomUUID().toString();
		Jedis jedis = (Jedis) factory.getConnection().getNativeConnection();
		String ret = jedis.set(LOCK, uuid, SetParams.setParams().nx().px(30000));
		return "OK".equals(ret);
	}
	@Override
	public void unlock() {
		Jedis jedis = (Jedis) factory.getConnection().getNativeConnection();
		String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
		jedis.eval(script, Collections.singletonList(LOCK), Collections.singletonList(THREAD_LOCAL.get()));
		THREAD_LOCAL.remove();
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit)
			throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	
	@Override
	public void lockInterruptibly() throws InterruptedException {
		// TODO Auto-generated method stub

	}




	@Override
	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
	}



}
