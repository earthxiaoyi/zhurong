package cn.com;

import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author jiaming
 */
public class Producer implements Runnable {

    @Override
    public void run() {
        Random random = new Random();
        LinkedBlockingQueue<Request> queue = BlockQueueHolder.queue;
        while (true) {
            Request request = new Request();
            request.id = random.nextInt();
            request.requestData = "request test";
            queue.add(request);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
