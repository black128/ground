package com.zrw.playground.redisDemo.pubsub;

import redis.clients.jedis.JedisPubSub;

import java.util.concurrent.CountDownLatch;

public class Subscriber extends JedisPubSub {
	
	private CountDownLatch cdl;
	
	

	public Subscriber(CountDownLatch cdl) {
		this.cdl = cdl;
	}



	// 取得订阅的消息后，通过cdl唤醒被阻塞的线程，再次尝试加锁
    public void onMessage(String channel, String message) {
    	if(cdl != null){
    		cdl.countDown();
    	}
    }





    

}
