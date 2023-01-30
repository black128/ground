package com.zrw.playground.redisDemo.pubsub;

import redis.clients.jedis.Jedis;

public class SubThread extends Thread {
    private  Subscriber subscriber;

    private  String channel;
    
    


	public SubThread( Subscriber subscriber, String channel) {
		this.subscriber = subscriber;
		this.channel = channel;
	}


	@Override
    public void run() {
        System.out.println(String.format("subscribe redis, channel %s, thread will be blocked", channel));
        Jedis jedis = null;
        try {
        	jedis = new Jedis("127.0.0.1");  //取出一个连接
        	jedis.subscribe(subscriber, channel);    //通过subscribe 的api去订阅，入参是订阅者和频道名
        } catch (Exception e) {
            System.out.println(String.format("subsrcibe channel error, %s", e));
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }


    public Subscriber getSubscriber() {
		return subscriber;
	}



	public void setSubscriber(Subscriber subscriber) {
		this.subscriber = subscriber;
	}



	public String getChannel() {
		return channel;
	}



	public void setChannel(String channel) {
		this.channel = channel;
	}



}
