package com.concurrency.beginner.p01;

/**
 * Problem 01 - Thread Basics
 *
 * Implement a Thread subclass that prints a message a given number of times.
 *
 * Requirements:
 *  - Extend Thread
 *  - Print `message` exactly `repeatCount` times when the thread runs
 *  - Store the name of the executing thread in `executingThreadName`
 */
public class PrintingThread extends Thread {

    private final String message;
    private final int repeatCount;

    /** Populated during run() with the name of the thread executing the task. */
    public volatile String executingThreadName;

    /**
     * @param threadName  a name for this thread (pass to super)
     * @param message     the string to print
     * @param repeatCount how many times to print it
     */
    public PrintingThread(String threadName, String message, int repeatCount) {
        super(threadName);
        this.message = message;
        this.repeatCount = repeatCount;
    }

    @Override
    public void run() {
        executingThreadName = Thread.currentThread().getName();
        for (int i = 0; i < repeatCount; i++) {
            System.out.println(message);
        }

    }
}
