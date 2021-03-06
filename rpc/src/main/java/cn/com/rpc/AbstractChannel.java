package cn.com.rpc;

import cn.com.common.URI;
import cn.com.rpc.channel.Channel;
import cn.com.rpc.exchange.Future;

import java.net.InetSocketAddress;

public abstract class AbstractChannel implements Channel {

    private URI uri;

    public AbstractChannel(URI uri) {
        this.uri = uri;
    }

    @Override
    public Future send(Object obj, int timeout) {
        io.netty.channel.Channel channel = getChannel();
        NettyChannel nettyChannel = NettyChannel.getChannel(channel, uri);
        Future future = nettyChannel.send(obj, timeout);
        return future;
    }

    @Override
    public void send(Object message, boolean sent) {
        io.netty.channel.Channel channel = getChannel();
        NettyChannel nettyChannel = NettyChannel.getChannel(channel, uri);
        nettyChannel.send(message, sent);
    }

    public URI getUri() {
        return uri;
    }

    @Override
    public InetSocketAddress getInetSocketAddress() {
        return (InetSocketAddress) getChannel().localAddress();
    }

    abstract io.netty.channel.Channel getChannel();

}
