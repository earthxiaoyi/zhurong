package cn.com.common;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author jiaming
 */
public class Request implements Serializable {

    private static final long serialVersionUID = -1L;

    private static final AtomicLong ATOMIC_LONG = new AtomicLong();

    private Header header;
    private Object data;

    public long newId(){
        return ATOMIC_LONG.getAndIncrement();
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Request{" +
                "header=" + header +
                ", data=" + data +
                '}';
    }
}