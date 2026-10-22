package com.concurrency.beginner.p06;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Problem 06 – Volatile Keyword: Status Board
 *
 * A shared notice board where one thread posts a status and many threads read it.
 * The `status` field must be volatile so readers always see the latest write.
 */
public class VolatileStatusBoard {

    // TODO: declare `status` as volatile
    private volatile String status = "INIT";

    private final AtomicLong readCount = new AtomicLong(0);

    /**
     * Posts a new status string. Called by a single writer thread.
     */
    public void postStatus(String status) {
        // TODO: assign the volatile field
        this.status = status;
    }

    /**
     * Reads the current status. May be called by many threads concurrently.
     * Must always reflect the most recently posted status.
     */
    public String getStatus() {
        // TODO: increment readCount, then return the volatile field
        readCount.incrementAndGet();
        return status;
    }

    /** Returns total number of getStatus() calls across all threads. */
    public long getReadCount() {
        return readCount.get();
    }
}
