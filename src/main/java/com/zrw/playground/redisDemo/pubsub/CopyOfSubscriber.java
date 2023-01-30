package com.zrw.playground.redisDemo.pubsub;

import java.util.concurrent.CountDownLatch;

import redis.clients.jedis.JedisPubSub;

public class CopyOfSubscriber extends JedisPubSub {
	
	private CountDownLatch cdl;
	
	

	public CopyOfSubscriber(CountDownLatch cdl) {
		super();
		this.cdl = cdl;
	}



	// 取得订阅的消息后，通过cdl再次尝试加锁
    public void onMessage(String channel, String message) {
        cdl.countDown();
        System.out.println("=======1======");
    }



	public CountDownLatch getCdl() {
		return cdl;
	}



	public void setCdl(CountDownLatch cdl) {
		this.cdl = cdl;
	}
    
    

}
