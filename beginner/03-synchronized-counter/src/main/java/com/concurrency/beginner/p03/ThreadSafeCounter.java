package com.concurrency.beginner.p03;

/**
 * Problem 03 - Synchronized Counter (Thread-Safe version)
 *
 * Implement all methods so they are safe to call from multiple threads simultaneously.
 * Use the `synchronized` keyword (do NOT use AtomicInteger for this exercise).
 */
public class ThreadSafeCounter {

    private int count = 0;

    /** Increments the counter by 1. */
    public void increment() {
        synchronized (this){
            count++;
        }
    }

    /** Decrements the counter by 1. */
    public void decrement() {
        synchronized (this){
            count--;
        }
    }

    /** Returns the current count. Must reflect all completed increments/decrements. */
    public int getCount() {
        synchronized (this){
            return count;
        }
    }

    /** Resets the counter to 0. */
    public void reset() {
        synchronized (this){
            count = 0;
        }
    }

    /**
     * Increments the counter by `delta` as a single atomic operation.
     * @param delta the amount to add (can be negative)
     */
    public void incrementBy(int delta) {
        synchronized (this){
            count += delta;
        }
    }
}
