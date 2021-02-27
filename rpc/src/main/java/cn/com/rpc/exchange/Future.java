package cn.com.rpc.exchange;

public interface Future {

    Object get();

    Object get(long timeout);
}
