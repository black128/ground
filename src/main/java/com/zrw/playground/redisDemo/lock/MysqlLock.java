package com.zrw.playground.redisDemo.lock;

import com.zrw.playground.redisDemo.mapper.LockMapper;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

@Service
public class MysqlLock implements Lock {

	private static final int ID_NUM = 1;

//	@Resource
	private LockMapper mapper;
    //偏向锁   轻量级锁   重量级锁
	@Override
	// 阻塞式的加锁
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
	// 非阻塞式加锁,往数据库写入id为1的数据，能写成功的即加锁成功
	public boolean tryLock() {
		try {
			mapper.insert(ID_NUM);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	// 解锁
	public void unlock() {
		mapper.deleteByPrimaryKey(ID_NUM);
	}

	// -----------------------------------------------

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
