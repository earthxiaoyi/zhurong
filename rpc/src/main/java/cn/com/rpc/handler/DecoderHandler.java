package cn.com.rpc.handler;

import cn.com.common.Request;
import cn.com.rpc.channel.Channel;
import cn.com.rpc.code.ZhuRongCodeC;
import cn.com.rpc.code.DecodeObject;

import java.io.IOException;

/**
 * @author jiaming
 */
public class DecoderHandler implements Handler {

    private Handler handler;

    public DecoderHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void connect(Channel channel, Object msg) {
        handler.connect(channel, msg);
    }

    @Override
    public void disconnect(Channel channel, Object msg) {
        handler.disconnect(channel, msg);
    }

    @Override
    public void recevied(Channel channel, Object msg){
        if (msg instanceof Request) {
            Request request = (Request) msg;
            Object data = request.getData();
            if (data instanceof DecodeObject) {
                try {
                    data = ZhuRongCodeC.decodeBody("kryo", (DecodeObject) data);
                    request.setData(data);
                    handler.recevied(channel, request);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            //TODO
        }
    }

    @Override
    public void sent(Channel channel, Object result) {
        handler.sent(channel, result);
    }

    @Override
    public void cause(Channel channel, Throwable cause) {
        handler.cause(channel, cause);
    }
}
