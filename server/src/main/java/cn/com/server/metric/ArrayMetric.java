package cn.com.server.metric;

import cn.com.server.data.MetricBucket;

import java.util.List;

public class ArrayMetric implements Metric {

    private final SlideArray<MetricBucket> data;

    public ArrayMetric(SlideArray<MetricBucket> data) {
        this.data = data;
    }

    @Override
    public long sum() {
        //重置滑动窗口，防止拿到过期的滑动窗口数据
        data.currentWindow();
        //统计滑动窗口中的数量
        long sum = 0L;
        List<MetricBucket> values = data.values();
        for (MetricBucket bucket : values) {
            sum += bucket.getSum();
        }
        return sum;
    }

    @Override
    public void add(int n) {
        //获取当前滑动窗口
        WindowWrap<MetricBucket> windowWrap = data.currentWindow();
        //添加元素
        windowWrap.value().add(n);
    }

}