package assignment_2;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.Lock;

public class BankAccountWithLock {
    private double balance;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    public BankAccountWithLock(double initialBalance) {
        this.balance = initialBalance;
    }

    // Read balance (shared lock)
    public double getBalance() {
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " reads balance: " + balance);
            return balance;
        } finally {
            readLock.unlock();
        }
    }

    // Deposit money (exclusive lock)
    public void deposit(double amount) {
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " deposits: " + amount);
            balance += amount;
        } finally {
            writeLock.unlock();
        }
    }

    // Withdraw money (exclusive lock)
    public void withdraw(double amount) {
        writeLock.lock();
        try {
            if (balance >= amount) {
                System.out.println(Thread.currentThread().getName() + " withdraws: " + amount);
                balance -= amount;
            } else {
                System.out.println(Thread.currentThread().getName() + " insufficient funds for: " + amount);
            }
        } finally {
            writeLock.unlock();
        }
    }
    public static void main(String[] args) {
        BankAccountWithLock account = new BankAccountWithLock(1000.0);

        // Create threads to simulate concurrent access
        Thread reader1 = new Thread(() -> {
            System.out.println("Reader 1 balance: " + account.getBalance());
        }, "Reader-1");

        Thread reader2 = new Thread(() -> {
            System.out.println("Reader 2 balance: " + account.getBalance());
        }, "Reader-2");

        Thread writer1 = new Thread(() -> {
            account.deposit(500);
        }, "Writer-1");

        Thread writer2 = new Thread(() -> {
            account.withdraw(200);
        }, "Writer-2");

        // Start the threads
        reader1.start();
        writer1.start();
        reader2.start();
        writer2.start();
    }
}

