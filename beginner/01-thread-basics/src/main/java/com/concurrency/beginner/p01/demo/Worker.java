package com.concurrency.beginner.p01.demo;



import java.util.concurrent.CountDownLatch;

public class Worker {

    private volatile boolean running = true;
    private volatile int count = 0;

    private final CountDownLatch started = new CountDownLatch(2);

    public void work() {
        started.countDown();

        while (running) {
            count++;
        }

        System.out.println(
                Thread.currentThread().getName()
                        + " stopped. Count = " + count
        );
    }

    public void stop() {
        running = false;
    }

    public int getCount() {
        return count;
    }

    public static void main(String[] args) throws InterruptedException {
        Worker worker = new Worker();

        Thread thread1 = new Thread(worker::work, "Worker-1");
        Thread thread2 = new Thread(worker::work, "Worker-2");

        thread1.start();
        thread2.start();

        worker.started.await();

        Thread.sleep(10);

        worker.stop();

        thread1.join();
        thread2.join();

        System.out.println("Final count: " + worker.getCount());
    }
}
