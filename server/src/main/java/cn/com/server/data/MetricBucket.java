package cn.com.server.data;

import java.util.concurrent.atomic.LongAdder;

/**
 * @author jiaming
 */
public class MetricBucket {

    private final LongAdder counters;

    public MetricBucket() {
        this.counters = new LongAdder();
    }

    public MetricBucket reset() {
        counters.reset();
        return this;
    }

    public MetricBucket add(long n) {
        counters.add(n);
        return this;
    }

    public long getSum() {
        return counters.sum();
    }

}
