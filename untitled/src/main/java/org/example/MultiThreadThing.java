package org.example;

public class MultiThreadThing extends Thread {
    private int threadNumber;

    public MultiThreadThing (int threadNumber){
        this.threadNumber = threadNumber;
    }
    @Override
    public void run(){
        for(int i=0;i<5;i++){
            System.out.println(i);

            try{
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
