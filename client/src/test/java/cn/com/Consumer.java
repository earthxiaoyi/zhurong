package cn.com;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author jiaming
 */
public class Consumer implements Runnable {

    /**
     * 时间周期
     */
    private static final long PERIOD_IN_MS = 500;

    /**
     * 请求阈值
     */
    private static final int REQUEST_THRESHOLD = 100;

    @Override
    public void run() {
        LinkedBlockingQueue<Request> queue = BlockQueueHolder.queue;
        while (true) {
            try {
                long startTime = System.currentTimeMillis();
                List<Request> requestList = new ArrayList<>(REQUEST_THRESHOLD);
                while (true) {
                    Request request = queue.take();
                    requestList.add(request);
                    long interval = System.currentTimeMillis() - startTime;
                    if (requestList.size() >= REQUEST_THRESHOLD || interval >= PERIOD_IN_MS) {
                        System.out.println("发送的数据：" + requestList.size());
                        requestList.clear();
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
