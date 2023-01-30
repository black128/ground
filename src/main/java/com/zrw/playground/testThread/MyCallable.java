package com.zrw.playground.testThread;

import java.util.concurrent.Callable;

public class MyCallable implements Callable<String> {
    @Override
    public String call() throws Exception {
        System.out.println("实现callable接口");
        return "success";
    }
}
