package org.example;

public class MySleep extends Thread {
    public static void main(String[] args) {
        final int THREAD_COUNT = 20;

        for (int i = 1; i <= THREAD_COUNT; i++) {
            Thread t = new WorkerThread(i);
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private static class WorkerThread extends Thread {
        private final int threadNumber;

        public WorkerThread(int threadNumber) {
            this.threadNumber = threadNumber;
            setName("Worker-" + threadNumber);
        }
        @Override
        public void run() {
            try {
                print("ONE");
                Thread.sleep(2000);

                print("TWO");
                Thread.sleep(2000);

                print("THREE");
                Thread.sleep(2000);

                print("XXXXXXXXXX");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        private void print(String msg) {
            System.out.printf("Thread %2d | %s%n", threadNumber, msg);
        }
    }
}
