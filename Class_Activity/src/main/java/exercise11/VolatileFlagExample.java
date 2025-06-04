package exercise11;

public class VolatileFlagExample {

    private static volatile boolean runningVolatile = true;
    private static boolean runningSync = true;
    public static synchronized boolean isRunningSync() {
        return runningSync;
    }

    // Synchronized setter for the sync flag
    public static synchronized void stopSync() {
        runningSync = false;
    }

    public static void main(String[] args) throws InterruptedException {
        // —— Volatile worker ——
        Thread workerVolatile = new Thread(() -> {
            System.out.println("Volatile worker started...");
            while (runningVolatile) {
                // simulate work
            }
            System.out.println("Volatile worker stopped.");
        });

        // —— Synchronized worker ——
        Thread workerSync = new Thread(() -> {
            System.out.println("Synchronized worker started...");
            while (isRunningSync()) {
                // simulate work
            }
            System.out.println("Synchronized worker stopped.");
        });

        workerVolatile.start();
        workerSync.start();

        // Let both run for 2 seconds
        Thread.sleep(2000);

        // stop both
        runningVolatile = false;  // visibility via volatile
        stopSync();               // visibility via synchronized
    }
}
