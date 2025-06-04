package exercise11;

public class ProducerConsumerDemo {

    static class SharedData {
        private boolean dataReady = false;
        private String data;

        public synchronized void produce() {
            try {
                System.out.println(Thread.currentThread().getName() + ": Preparing data...");
                Thread.sleep(1000);
                data = "Hello from producer";
                dataReady = true;
                System.out.println(Thread.currentThread().getName() + ": Data is ready.");
                notifyAll();  // wake up all waiting consumers
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        public synchronized void consume() {
            try {
                while (!dataReady) {
                    System.out.println(Thread.currentThread().getName() + ": Waiting for data...");
                    wait();
                }
                System.out.println(Thread.currentThread().getName() + ": Received -> " + data);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SharedData shared = new SharedData();

        // start two consumer threads
        Thread consumer1 = new Thread(shared::consume, "Consumer-1");
        Thread consumer2 = new Thread(shared::consume, "Consumer-2");
        consumer1.start();
        consumer2.start();

        // give consumers a moment to start and wait
        Thread.sleep(200);

        // start producer
        Thread producer = new Thread(shared::produce, "Producer");
        producer.start();
    }
}
