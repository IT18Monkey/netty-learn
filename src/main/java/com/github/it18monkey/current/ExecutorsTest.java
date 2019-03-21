package com.github.it18monkey.current;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorsTest {
    public static void main(String[] args) {
        ExecutorService service = Executors.newScheduledThreadPool(1);
        service.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("线程1执行了");
                    TimeUnit.SECONDS.sleep(10);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        service.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("线程2执行了");
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
