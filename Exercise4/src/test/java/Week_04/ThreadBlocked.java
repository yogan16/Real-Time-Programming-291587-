package Week_04;

public class ThreadBlocked {
    public static void main(String[] args) {

        final Shared s1 = new Shared();
        final Shared s2 = new Shared();
        Thread t1 = new Thread() {
            @Override
            public void run() {
                s1.methodOne(s2);
            }
        };
        Thread t2 = new Thread() {
            @Override
            public void run() {
                s2.methodTwo(s1);


            }
        };
        t1.start();
        t2.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("state t1 = " + t1.getState());
        System.out.println("state t2 = " + t2.getState());
    }
}

