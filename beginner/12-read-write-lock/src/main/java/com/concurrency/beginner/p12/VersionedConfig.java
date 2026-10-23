package com.concurrency.beginner.p12;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Problem 12 – ReadWriteLock: Versioned Config with Lock Downgrading
 *
 * Demonstrates lock downgrading: write lock → acquire read lock →
 * release write lock → read under read lock → release read lock.
 */
public class VersionedConfig {

    private final Map<String, String> config = new HashMap<>();
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    /**
     * Updates the config entry and returns the value just written.
     *
     * Must use lock downgrading:
     *  1. Acquire write lock
     *  2. Write the value
     *  3. Acquire read lock (while still holding write lock)
     *  4. Release write lock
     *  5. Read and return the value (under read lock)
     *  6. Release read lock in finally
     */
    public String updateConfig(String key, String value) {
        // TODO: implement with lock downgrading
        rwLock.writeLock().lock();
        try {
            config.put(key, value);
            return config.get(key);
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    /**
     * Returns the value for the given key, or null if absent.
     * Uses the read lock.
     */
    public String getConfig(String key) {
        // TODO: readLock → return config.get → unlock(finally)
        rwLock.readLock().lock();
        try {
            return config.get(key);
        } finally {
            rwLock.readLock().unlock();
        }
    }

    /**
     * Returns a snapshot of all config keys.
     * Uses the read lock.
     */
    public Set<String> getAllKeys() {
        // TODO: readLock → return new HashSet<>(config.keySet()) → unlock(finally)
        rwLock.readLock().lock();
        try {
            return new HashSet<>(config.keySet());
        } finally {
            rwLock.readLock().unlock();
        }
    }
}
