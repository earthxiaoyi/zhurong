package cn.com.rpc.handler;

import cn.com.rpc.channel.Channel;

public interface Handler {

    void connect(Channel channel, Object msg);

    void disconnect(Channel channel, Object msg);

    void recevied(Channel channel, Object msg);

    void sent(Channel channel, Object result);

    void cause(Channel channel, Throwable cause);

}