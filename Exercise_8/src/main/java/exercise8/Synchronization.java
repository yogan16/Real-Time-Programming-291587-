package exercise8;

public class Synchronization {
    public static void main(String[] args) throws InterruptedException {
        int numberOfThreads = 10;

        int[] normalCounter = new int[1];
        Thread[] normalThreads = new Thread[numberOfThreads];
        long start = System.currentTimeMillis();
        for (int i = 0; i < numberOfThreads; i++) {
            normalThreads[i] = new NormalThread(normalCounter);
            normalThreads[i].start();
        }
        for (Thread t : normalThreads) {
            t.join();
        }
        long end = System.currentTimeMillis();
        System.out.printf("Normal Thread Execution Time: %.3f seconds%n", (end - start) / 1000.0);

        Counter syncCounter = new Counter();
        Thread[] syncThreads = new Thread[numberOfThreads];
        start = System.currentTimeMillis();
        for (int i = 0; i < numberOfThreads; i++) {
            syncThreads[i] = new SynchronizedThread(syncCounter);
            syncThreads[i].start();
        }
        for (Thread t : syncThreads) {
            t.join();
        }
        end = System.currentTimeMillis();
        System.out.printf("Synchronized Thread Execution Time: %.3f seconds%n", (end - start) / 1000.0);
    }
}
