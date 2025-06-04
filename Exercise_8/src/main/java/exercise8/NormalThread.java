package exercise8;

public class NormalThread extends Thread {
    private int[] counter;
    public NormalThread(int[] counter) {
        this.counter = counter;
    }
    @Override
    public void run() {
        for (int i = 0; i < 1000000; i++) {
            counter[0]++;
        }
    }
}