package com.zrw.playground.redisDemo.lock;

import com.zrw.playground.redisDemo.pubsub.Subscriber;
import com.zrw.playground.redisDemo.utils.FileUtils;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;


@Service
public class RedisImproveLock implements Lock {
	
	private static final String  KEY = "KEY";
	private static final String  CHANNEL_NAME = "KEY_CHANNEL";
	
	
//	@Resource
	private JedisConnectionFactory factory;
	
	private static ThreadLocal<String> local = new ThreadLocal<>();
	
	//定时任务的执行器
	ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);
	
	//存放每次加锁所创建的所有定时任务
	private static ConcurrentHashMap<String, Future>  futrues = new ConcurrentHashMap<String, Future>();
	
	
	// 阻塞式的加锁
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void lock() {
		while (true) {
			if (tryLock()) {
				return;
			}

			//创建消息订阅者
			CountDownLatch cdl = new CountDownLatch(1);//线程之间的协作机制类
			Subscriber subscriber = new Subscriber(cdl);//定义订阅对象
			Jedis jedis = (Jedis) factory.getConnection().getNativeConnection();
			//Jedis的subscribe操作是阻塞的，因此，我们另起一个线程来进行subscribe操作
			new Thread(new Runnable() {
				@Override
				public void run() {
					jedis.subscribe(subscriber, CHANNEL_NAME);
				}
			}).start();
			
			try {
				cdl.await(30,TimeUnit.SECONDS);//阻塞等待解锁
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cdl = null;
			subscriber.unsubscribe();//尝试加锁之前取消订阅
		}
	}



	@Override
	//非阻塞式加锁,使用setNx命令返回OK的加锁成功，生产随机值,并建立心跳检测
	@Transactional
	public boolean tryLock() {
		String uuid=local.get();
		if(local.get() == null){
			uuid = UUID.randomUUID().toString();
			local.set(uuid);
		}
		//获取redis的原始连接
		Jedis jedis = (Jedis) factory.getConnection().getNativeConnection();
		//使用setNx命令请求写值，并设置失效时间
		String ret = jedis.set(KEY, uuid, SetParams.setParams().nx().px(300000));
		//返回“ok”意味着加锁成功
		if("OK".equals(ret)){
			setHeartbeat(uuid);//加锁成功启动心跳检测，如有需要延长有效期
			jedis.close();
			return true;
		}
		//不是返回“ok”意味着加锁失败
		jedis.close();
		return false;
	}
	
	
	


	
	private void setHeartbeat(String uuid) {
		//如果已经有心跳检测直接返回
		if(futrues.contains(uuid)){
			return;
		}
//		Callable<V>
		//启动20秒一次的心跳检测
		Future futrue = scheduler.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				Jedis jedis = (Jedis) factory.getConnection().getNativeConnection();
				if(jedis.get(KEY).equals(uuid)){//如果当前任务的加锁节点存在，说明状态健康，但快到失效期因此延长有效期
					jedis.expire(uuid, 30);
				}else{//如果当前任务的加锁节点不存在，说明锁已经释放，将调度任务取消
					futrues.get(uuid).cancel(true);
					futrues.remove(uuid);
				}
				jedis.close();
			}
		}, 1, 20, TimeUnit.SECONDS);
		//调度启动之后将，当前节点的对应的任务存到map
		futrues.put(uuid, futrue);
		
	}



	@Override
	//解锁
	public void unlock() {
		//读取lua脚本
		String script = FileUtils.readFileByLines("E://workspaces//enjoy-demo//redis-lock//src//main//resources//unlock-new.lua");
		//获取redis的原始连接
		Jedis jedis = (Jedis) factory.getConnection().getNativeConnection();
		//通过原始连接连接redis执行lua脚本
		jedis.eval(script, Arrays.asList(KEY), Arrays.asList(local.get()));
		//解锁时，要将对应的心跳检测停止
		Future future = futrues.get(local.get());
		future.cancel(true);
		futrues.remove(local.get());
		//解锁之后，发送消息通知其他被阻塞的客户端
		jedis.publish(CHANNEL_NAME, "unlock");
		jedis.close();
		
		
	}
	
	//-----------------------------------------------

	@Override
	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
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

}
