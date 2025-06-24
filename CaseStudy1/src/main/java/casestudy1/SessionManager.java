package casestudy1;

import java.util.concurrent.*;

public class SessionManager {
    // Thread-safe storage of sessionId → lastActiveTimestamp
    private static final ConcurrentHashMap<String, Long> sessions = new ConcurrentHashMap<>();

    // Demo timeouts (ms)
    private static final long SESSION_TIMEOUT_MS    = 10_000; // 10 seconds
    private static final long CLEANUP_INTERVAL_MS  = 5_000;  // every 5 seconds
    private static final long MONITOR_INTERVAL_MS  = 7_000;  // every 7 seconds

    // Update (or create) a session’s last-active time
    public static void updateSession(String sessionId) {
        sessions.put(sessionId, System.currentTimeMillis());
        System.out.println("Updated session: " + sessionId);
    }

    // Periodically remove sessions inactive longer than SESSION_TIMEOUT_MS
    public static void startSessionCleanupTask(ScheduledExecutorService scheduler) {
        scheduler.scheduleAtFixedRate(() -> {
            long now = System.currentTimeMillis();
            sessions.forEach((id, lastActive) -> {
                if (now - lastActive > SESSION_TIMEOUT_MS) {
                    sessions.remove(id);
                    System.out.println("Removed expired session: " + id);
                }
            });
        }, CLEANUP_INTERVAL_MS, CLEANUP_INTERVAL_MS, TimeUnit.MILLISECONDS);
    }

    // Periodically print all active sessions for monitoring/logging
    public static void startSessionMonitorTask(ScheduledExecutorService scheduler) {
        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("Active Sessions:");
            sessions.forEach((id, lastActive) ->
                    System.out.println(" - " + id + " (Last active: " + lastActive + ")")
            );
        }, MONITOR_INTERVAL_MS, MONITOR_INTERVAL_MS, TimeUnit.MILLISECONDS);
    }

    // Simulate a user thread that “does work” and updates its session
    private static void simulateUser(String sessionId, int iterations, long delayMs) {
        for (int i = 0; i < iterations; i++) {
            updateSession(sessionId);
            try {
                Thread.sleep(delayMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // Scheduler for cleanup + monitoring
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        startSessionCleanupTask(scheduler);
        startSessionMonitorTask(scheduler);

        // Executor for simulating user-request threads
        ExecutorService userExecutor = Executors.newFixedThreadPool(2);
        userExecutor.submit(() -> simulateUser("user1", 5, 2_000)); // every 2s
        userExecutor.submit(() -> simulateUser("user2", 3, 4_000)); // every 4s

        userExecutor.shutdown();
        userExecutor.awaitTermination(1, TimeUnit.MINUTES);

        // Let cleanup/monitor run a bit longer for demo
        Thread.sleep(20_000);
        scheduler.shutdown();
    }
}
