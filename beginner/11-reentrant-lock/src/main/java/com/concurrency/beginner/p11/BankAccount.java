package com.concurrency.beginner.p11;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Problem 11 – ReentrantLock: Thread-Safe Bank Account
 *
 * Use ReentrantLock (NOT synchronized) for all mutual exclusion.
 * The transfer() method must be deadlock-free under concurrent use.
 */
public class BankAccount {

    private static final AtomicLong ID_SEQ = new AtomicLong(0);

    public final long id;
    private double balance;
    private final ReentrantLock lock = new ReentrantLock();

    public BankAccount(double initialBalance) {
        this.id = ID_SEQ.incrementAndGet();
        this.balance = initialBalance;
    }

    /**
     * Deposits the given amount into this account.
     * @throws IllegalArgumentException if amount <= 0
     */
    public void deposit(double amount) {
        // TODO: validate, lock, add to balance, unlock (in finally)
        if(amount <= 0){
            throw new IllegalArgumentException("amount must be positive");
        }
        lock.lock();
        try {
            balance += amount;
        } finally {
            lock.unlock();
        }

    }

    /**
     * Withdraws the given amount from this account.
     * @throws IllegalArgumentException if amount <= 0
     * @throws IllegalStateException    if balance < amount
     */
    public void withdraw(double amount) {
        // TODO: validate, lock, check funds, subtract, unlock (in finally)
        if(amount <= 0){
            throw new IllegalArgumentException("amount must be positive");
        }

        lock.lock();
        try {
            if(amount > balance){
                throw new IllegalStateException("insufficient funds");
            }
            balance -= amount;
        } finally {
            lock.unlock();
        }
    }

    /** Returns the current balance. */
    public double getBalance() {
        // TODO: lock, read, unlock (in finally)
        lock.lock();
        try {
            return balance;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Transfers amount from this account to target atomically.
     * Must acquire both locks in a consistent order to prevent deadlock.
     * Strategy: always lock the account with the LOWER id first.
     *
     * @throws IllegalStateException if this account has insufficient funds
     */
    public void transfer(BankAccount target, double amount) {
        // TODO:
        //  1. Determine lock order using account ids
        //  2. Lock both accounts (first lock, then second) in a try block
        //  3. Perform withdraw from this, deposit to target
        //  4. Unlock both in reverse order in a finally block
        if (target == null) {
            throw new IllegalArgumentException(
                    "target must not be null");
        }

        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "amount must be positive");
        }

        if (this == target) {
            return;
        }

        // 始终先锁 id 较小的账户
        BankAccount first = this.id < target.id
                ? this : target;
        BankAccount second = this.id < target.id
                ? target : this;

        first.lock.lock();
        try{
            second.lock.lock();
            try {
                if (this.balance < amount) {
                    throw new IllegalStateException(
                            "insufficient funds");
                }
                this.balance -= amount;
                target.balance += amount;
            } finally {
                second.lock.unlock();
            }
        }finally {
            first.lock.unlock();
        }

    }

    // Package-private for testing lock-ordering
    ReentrantLock getLock() { return lock; }
}
