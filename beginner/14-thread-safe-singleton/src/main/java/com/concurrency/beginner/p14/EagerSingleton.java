package com.concurrency.beginner.p14;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Problem 14 – Thread-Safe Singleton: EagerSingleton
 * Approach: eager initialisation
 *
 * TODO: Implement getInstance() using the eager initialisation approach.
 * The constructor must increment creationCount exactly once.
 */
public class EagerSingleton {

    private static final AtomicInteger creationCount = new AtomicInteger(0);
    private final int id;

    // TODO: add the instance field (and Holder class if needed)
    private static final EagerSingleton instance = new EagerSingleton();

    private EagerSingleton() {
        // TODO: increment creationCount and assign a unique id
        id = creationCount.incrementAndGet();
    }

    /**
     * Returns the single instance of EagerSingleton.
     * Must be thread-safe.
     */
    public static EagerSingleton getInstance() {
        // TODO: implement the eager initialisation pattern
        return instance;
    }

    /** Returns the unique ID assigned to this instance at construction. */
    public int getId() { return id; }

    /** Returns how many times the constructor has been called (must be 1). */
    public static int getCreationCount() { return creationCount.get(); }

    /** Resets state for testing purposes — DO NOT use in production code. */
    static void resetForTesting() {
        creationCount.set(0);
        // instance = null; // Cannot reset a final field
        // TODO: if you hold a static instance field, reset it to null here
    }
}
