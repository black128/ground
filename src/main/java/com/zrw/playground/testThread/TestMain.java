package com.zrw.playground.testThread;


import org.springframework.boot.configurationprocessor.json.JSONArray;

import java.util.ArrayList;
import java.util.concurrent.*;

public class TestMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        JSONArray jsonArray = new JSONArray(new ArrayList<>(new ArrayList<>()));
        new MyThread().start();
//
//        new Thread(() -> System.out.println("runnable")).start();
//
        MyCallable myCallable = new MyCallable();
        FutureTask<String> result = new FutureTask<String>(myCallable);
        new Thread(result).start();
        System.out.println(result.get());
//        ThreadFactory threadFactory = Thread::new;
//        ConcurrentLinkedQueue
//        ThreadPoolExecutor.AbortPolicy---丢弃任务并且抛出异常
//        ThreadPoolExecutor.DiscardPolicy---丢弃任务但是不抛出异常。如果线程队列已满，则后续提交的任务都会被丢弃，且是静默丢弃。
//        ThreadPoolExecutor.DiscardOldestPolicy---丢弃队列最前面的任务，然后重新提交被拒绝的任务
//        ThreadPoolExecutor.CallerRunsPolicy---由调用线程处理该任务，主线程执行被丢弃的任务
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1,
                10,
                300,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>(2),
                Thread::new,
                new ThreadPoolExecutor.AbortPolicy());

        for (int i = 0; i < 100; i++) {
            int finalI = i;
            try {
                threadPoolExecutor.execute(() -> {
                    System.out.println(Thread.currentThread().getId() + ":" + Thread.currentThread().getName() + "-----" + finalI);
                    try {
                        Thread.sleep(5000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            } catch (Exception e) {
                System.out.println(i);
            }
        }
//        threadPoolExecutor.shutdown();


    }
}

