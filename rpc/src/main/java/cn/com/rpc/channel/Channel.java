package cn.com.rpc.channel;

import cn.com.rpc.exchange.Future;

import java.net.InetSocketAddress;

/**
 * @author jiaming
 */
public interface Channel {

    Future send(Object message, int timeout);

    void send(Object message, boolean sent);

    void close();

    boolean isConnected();

    void connect();

    InetSocketAddress getInetSocketAddress();

}
