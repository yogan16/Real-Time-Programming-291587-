package casestudy2;

import java.util.concurrent.ConcurrentHashMap;

public class StudentLoginTracker {
    private static ConcurrentHashMap<String, Integer> loginMap = new ConcurrentHashMap<>();

    public static void incrementLogin(String studentId) {
        loginMap.compute(studentId, (key, val) -> (val == null) ? 1 : val + 1);
    }

    public static void main(String[] args) throws InterruptedException {
        Runnable task1 = () -> {
            for (int i = 0; i < 5; i++) {
                incrementLogin("student123");
            }
        };

        Runnable task2 = () -> {
            for (int i = 0; i < 5; i++) {
                incrementLogin("student456");
            }
        };

        Thread t1 = new Thread(task1);
        Thread t2 = new Thread(task2);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Final login count for student123: " + loginMap.get("student123"));
        System.out.println("Final login count for student456: " + loginMap.get("student456"));
    }
}
