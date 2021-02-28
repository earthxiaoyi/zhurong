package cn.com.server.utils;

import java.util.concurrent.TimeUnit;

/**
 * @author jiaming
 */
public final class TimeUtil {

    private static volatile long currentTimeMillis;

    static {
        currentTimeMillis = System.currentTimeMillis();
        Thread daemon = new Thread(() -> {
            while (true) {
                currentTimeMillis = System.currentTimeMillis();
                try {
                    TimeUnit.MILLISECONDS.sleep(1);
                } catch (Throwable e) {

                }
            }
        });
        daemon.setDaemon(true);
        daemon.setName("sentinel-time-tick-thread");
        daemon.start();
    }

    public static long currentTimeMillis() {
        return currentTimeMillis;
    }
}
