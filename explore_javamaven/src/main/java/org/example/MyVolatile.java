
package org.example;


import java.io.*;

public class MyVolatile {
    public static void main(String[] args) {
        MyThread t = new MyThread();
        t.setName("Yoganraj");
        t.start();

        System.out.println("Press ENTER to stop the thread...");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        t.shutdown();
    }
}
