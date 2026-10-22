package com.concurrency.beginner.p05;

/**
 * Problem 05 - Thread Join
 *
 * Use Thread.join() to collect results from parallel computations.
 */
public class ParallelMerge {

    /**
     * Computes the sum of all elements in `array` by:
     *  - Splitting the array in half
     *  - Summing each half on a separate thread
     *  - Joining both threads
     *  - Returning the combined total
     *
     * For arrays of length 0 or 1, handle the edge case directly.
     *
     * @param array the input array (non-null)
     * @return the total sum of all elements
     */
    public long sumArray(int[] array) throws InterruptedException {
        if(array.length == 0) return 0;
        if(array.length == 1) return array[0];
        int mid = array.length / 2;
        long[] sums = new long[2];
        Runnable task1 = () -> {
            int res = 0;
            for (int i = 0; i < mid; i++) {
                res += array[i];
            }
            sums[0] = res;
        };
        Runnable task2 = () -> {
            int res = 0;
            for (int i = mid; i < array.length; i++) {
                res += array[i];
            }
            sums[1] = res;
        };
        Thread leftThread = new Thread(task1);
        Thread rightThread = new Thread(task2);
        leftThread.start();
        rightThread.start();
        leftThread.join();
        rightThread.join();
        return sums[0] + sums[1];
    }

    /**
     * Finds the maximum element in `array` by:
     *  - Splitting the array in half
     *  - Finding the max of each half on a separate thread
     *  - Joining both threads
     *  - Returning the overall max
     *
     * @param array non-null, non-empty array
     * @return the maximum value in the array
     */
    public int findMax(int[] array) throws InterruptedException {
        int mid = array.length / 2;
        int[] maxes = new int[2];
        Runnable task1 = () -> {
            int max = array[0];
            for (int i = 0; i < mid; i++) {
                if (array[i] > max) {
                    max = array[i];
                }
            }
            maxes[0] = max;
        };
        Runnable task2 = () -> {
            int max = array[mid];
            for (int i = mid; i < array.length; i++) {
                if (array[i] > max) {
                    max = array[i];
                }
            }
            maxes[1] = max;
        };
        Thread leftThread = new Thread(task1);
        Thread rightThread = new Thread(task2);
        leftThread.start();
        rightThread.start();
        leftThread.join();
        rightThread.join();
        return Math.max(maxes[0], maxes[1]);
    }

    /**
     * Runs `task` on a new thread and waits up to `timeoutMillis` for it to complete.
     *
     * @param task          the task to run
     * @param timeoutMillis maximum wait time in milliseconds
     * @return true if the task completed within the timeout; false if it timed out
     */
    public boolean runWithTimeout(Runnable task, long timeoutMillis) throws InterruptedException {
        Thread thread = new Thread(task);
        thread.start();
        thread.join(timeoutMillis);
        return !thread.isAlive();
    }
}
