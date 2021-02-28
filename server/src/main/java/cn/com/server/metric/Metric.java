package cn.com.server.metric;

/**
 * @author jiaming
 */
public interface Metric {

    /**
     * 返回滑动窗口统计的数量
     *
     * @return
     */
    long sum();

    /**
     * 增加数量
     *
     * @param n
     */
    void add(int n);

}
