package exercise7;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Deadlock implements Runnable {
    private static final ReentrantLock lock1 = new ReentrantLock(true);
    private static final ReentrantLock lock2 = new ReentrantLock(true);
    private final Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        Thread myThread1 = new Thread(new Deadlock(), "thread-1");
        Thread myThread2 = new Thread(new Deadlock(), "thread-2");
        myThread1.start();
        myThread2.start();
    }

    public void run() {
        for (int i = 0; i < 10000; i++) {
            boolean b = random.nextBoolean();
            if (b) {
                System.out.println("[" + Thread.currentThread().getName() + "] Trying to lock resource 1");
                try {
                    if (lock1.tryLock(50, TimeUnit.MILLISECONDS)) {
                        try {
                            System.out.println("[" + Thread.currentThread().getName() + "] Locked resource 1");
                            System.out.println("[" + Thread.currentThread().getName() + "] Trying to lock resource 2");
                            if (lock2.tryLock(50, TimeUnit.MILLISECONDS)) {
                                try {
                                    System.out.println("[" + Thread.currentThread().getName() + "] Locked resource 2");
                                } finally {
                                    lock2.unlock();
                                    System.out.println("[" + Thread.currentThread().getName() + "] Released resource 2");
                                }
                            } else {
                                System.out.println("[" + Thread.currentThread().getName() + "] ➔ Could not lock resource 2, releasing resource 1 and retry");
                            }
                        } finally {
                            lock1.unlock();
                            System.out.println("[" + Thread.currentThread().getName() + "] Released resource 1");
                        }
                    } else {
                        System.out.println("[" + Thread.currentThread().getName() + "] ➔ Could not lock resource 1, will retry later");
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            } else {
                System.out.println("[" + Thread.currentThread().getName() + "] Trying to lock resource 2");
                try {
                    if (lock2.tryLock(50, TimeUnit.MILLISECONDS)) {
                        try {
                            System.out.println("[" + Thread.currentThread().getName() + "] Locked resource 2");
                            System.out.println("[" + Thread.currentThread().getName() + "] Trying to lock resource 1");
                            if (lock1.tryLock(50, TimeUnit.MILLISECONDS)) {
                                try {
                                    System.out.println("[" + Thread.currentThread().getName() + "] Locked resource 1");

                                } finally {
                                    lock1.unlock();
                                    System.out.println("[" + Thread.currentThread().getName() + "] Released resource 1");
                                }
                            } else {
                                System.out.println("[" + Thread.currentThread().getName() + "] ➔ Could not lock resource 1, releasing resource 2 and retry");
                            }
                        } finally {
                            lock2.unlock();
                            System.out.println("[" + Thread.currentThread().getName() + "] Released resource 2");
                        }
                    } else {
                        System.out.println("[" + Thread.currentThread().getName() + "] ➔ Could not lock resource 2, will retry later");
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            try {
                Thread.sleep(random.nextInt(50));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
