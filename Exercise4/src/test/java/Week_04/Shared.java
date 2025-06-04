package Week_04;

public class Shared {
    synchronized void methodOne(Shared s) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        s.methodTwo(this);
    }
    synchronized void methodTwo(Shared s) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        s.methodOne(this);
    }
}
