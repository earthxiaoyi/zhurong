package cn.com.server.metric;

import cn.com.server.data.MetricBucket;

public class BucketSlideArray extends SlideArray<MetricBucket> {

    public BucketSlideArray(int sampleCount, int intervalInMs) {
        super(sampleCount, intervalInMs);
    }

    @Override
    public MetricBucket newEmptyBucket(long time) {
        return new MetricBucket();
    }

    @Override
    protected WindowWrap<MetricBucket> resetWindowTo(WindowWrap<MetricBucket> w, long startTime) {
        // 更新开始时间和重置值。
        w.resetTo(startTime);
        w.value().reset();
        return w;
    }
}