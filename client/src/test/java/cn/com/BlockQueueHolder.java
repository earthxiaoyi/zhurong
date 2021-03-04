package cn.com;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author jiaming
 */
public class BlockQueueHolder {

    protected static final LinkedBlockingQueue<Request> queue = new LinkedBlockingQueue<>();

}