package org.example;

import static java.lang.Thread.sleep;

public class MyThreadThread extends Thread {
    public static void main(String[] args) {
        new Thread(new MySleep()).start();
        new Thread(new MySleep()).start();
    }
    @Override
    public void run() {
        try {
            for (int x = 0; x < 1000; x++) {
                System.out.println(x);
                sleep(20009);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
