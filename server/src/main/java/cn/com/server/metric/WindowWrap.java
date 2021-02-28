package cn.com.server.metric;

/**
 * @param <T>
 * @author jiaming
 */
public class WindowWrap<T> {

    /**
     * 一个窗口时段的时间长度（以毫秒为单位）
     */
    private final long windowLengthInMs;

    /**
     * 窗口的开始时间戳（以毫秒为单位）。
     */
    private long windowStart;

    /**
     * 数据
     */
    private T value;

    /**
     * @param windowLengthInMs a single window bucket's time length in milliseconds.
     * @param windowStart      the start timestamp of the window
     * @param value            statistic data
     */
    public WindowWrap(long windowLengthInMs, long windowStart, T value) {
        this.windowLengthInMs = windowLengthInMs;
        this.windowStart = windowStart;
        this.value = value;
    }

    public long windowLength() {
        return windowLengthInMs;
    }

    public long windowStart() {
        return windowStart;
    }

    public T value() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    /**
     * Reset start timestamp of current bucket to provided time.
     *
     * @param startTime valid start timestamp
     * @return bucket after reset
     */
    public WindowWrap<T> resetTo(long startTime) {
        this.windowStart = startTime;
        return this;
    }

    /**
     * Check whether given timestamp is in current bucket.
     *
     * @param timeMillis valid timestamp in ms
     * @return true if the given time is in current bucket, otherwise false
     * @since 1.5.0
     */
    public boolean isTimeInWindow(long timeMillis) {
        return windowStart <= timeMillis && timeMillis < windowStart + windowLengthInMs;
    }

    @Override
    public String toString() {
        return "WindowWrap{" +
                "windowLengthInMs=" + windowLengthInMs +
                ", windowStart=" + windowStart +
                ", value=" + value +
                '}';
    }
}