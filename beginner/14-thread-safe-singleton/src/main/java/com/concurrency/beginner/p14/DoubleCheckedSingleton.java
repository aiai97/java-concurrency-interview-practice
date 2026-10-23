package com.concurrency.beginner.p14;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Problem 14 – Thread-Safe Singleton: DoubleCheckedSingleton
 * Approach: double-checked locking (DCL) with volatile
 *
 * TODO: Implement getInstance() using the double-checked locking (DCL) with volatile approach.
 * The constructor must increment creationCount exactly once.
 */
public class DoubleCheckedSingleton {

    private static final AtomicInteger creationCount = new AtomicInteger(0);
    private final int id;
    private static volatile DoubleCheckedSingleton instance;

    // TODO: add the instance field (and Holder class if needed)

    private DoubleCheckedSingleton() {
        // TODO: increment creationCount and assign a unique id
        id = creationCount.incrementAndGet();
    }

    /**
     * Returns the single instance of DoubleCheckedSingleton.
     * Must be thread-safe.
     */
    public static DoubleCheckedSingleton getInstance() {
        // TODO: implement the double-checked locking (DCL) with volatile pattern
        if(instance == null) {
            synchronized(DoubleCheckedSingleton.class) {
                if(instance == null) {
                    instance = new DoubleCheckedSingleton();
                }
            }
        }
        return instance;
    }

    /** Returns the unique ID assigned to this instance at construction. */
    public int getId() { return id; }

    /** Returns how many times the constructor has been called (must be 1). */
    public static int getCreationCount() { return creationCount.get(); }

    /** Resets state for testing purposes — DO NOT use in production code. */
    static void resetForTesting() {
        creationCount.set(0);
        instance = null;
    }
}
