package com.zrw.playground.testThread;

public class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("继承Thread类");
    }
}
