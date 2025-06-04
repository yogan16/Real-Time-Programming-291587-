package org.example;

public class MultiplicationTask implements Runnable {
    private final int number;

    public MultiplicationTask(int number) {
        this.number = number;
    }
    @Override
    public void run() {
        for (int i = 1; i <= 3; i++) {
            System.out.println(Thread.currentThread().getName()
                    + ": " + number + " * " + i
                    + " = " + (number * i));
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    public static void main(String[] args) {
        Thread t0 = new Thread(new MultiplicationTask(1));
        Thread t1 = new Thread(new MultiplicationTask(2));
        Thread t2 = new Thread(new MultiplicationTask(3));

        t0.start();
        t2.start();
        t1.start();
    }
}


