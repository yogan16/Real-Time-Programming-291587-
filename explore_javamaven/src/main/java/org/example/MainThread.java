package org.example;

public class MainThread {
    public static void main(String[] args){
        System.out.println(Thread.currentThread().getName());
        System.out.print(Thread.currentThread().getId());
    }

}

