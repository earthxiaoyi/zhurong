package cn.com.rpc.code;

import cn.com.common.*;
import cn.com.serialize.protobuf.ObjectInput;
import cn.com.serialize.protobuf.ObjectOutput;
import cn.com.serialize.protobuf.ProtostuffSerialization;
import cn.com.serialize.protobuf.Serialization;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author jiaming
 */
public class ZhuRongCodeC {

    private URI uri;

    public ZhuRongCodeC(URI uri) {
        this.uri = uri;
    }

    /**
     * 消息编码
     *
     * @param serial
     * @param byteBuf
     * @param msg
     */
    public void encoder(String serial, ByteBuf byteBuf, Object msg) throws IOException {
        Serialization serializer = getSerializer(serial);
        Header header;
        Object mData;
        if (msg instanceof Request) {
            Request request = (Request) msg;
            header = request.getHeader();
            mData = request.getData();
        } else if (msg instanceof Response) {
            Response response = (Response) msg;
            header = response.getHeader();
            mData = response.getData();
        } else {
            return;
        }
        //0-15     魔数字节MAGIC，0xca1100
        //byteBuf.writeShort(header.getMagic());
        //16-23    事件类型：0请求 1响应 2心跳
        byteBuf.writeByte(header.getEventType());
        //24-31
        byteBuf.writeByte(header.getStatus());
        //32-39    序列化协议：0 KryoSerialization
        byteBuf.writeByte(header.getSerialType());
        //40-104
        byteBuf.writeLong(header.getId());
        //105-136
        byteBuf.writeBytes(new byte[4]);
        //记录数据初始写入位置
        int saveWriterIndex = byteBuf.writerIndex();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutput objectOutput = serializer.serialize(outputStream);
        objectOutput.writeObject(mData);
        byteBuf.writeBytes(outputStream.toByteArray());
        //body 序列化后的长度
        int bodyLength = byteBuf.writerIndex() - saveWriterIndex;
        //bodylength+请求头17个字节=消息总长度
        int msgLength = bodyLength + Constant.HEADER_LENGTH;
        byteBuf.setInt(Constant.MSG_LENGTH_OFFSET, msgLength);
    }

    /**
     * 消息解码
     *
     * @param serial
     * @param byteBuf
     * @return
     */
    public Object decoder(String serial, ByteBuf byteBuf) throws IOException {
        short magic = byteBuf.readShort();
        if (Constant.MAGIC != magic) {
            return null;
        }
        byte eventType = byteBuf.readByte();
        byte status = byteBuf.readByte();
        byte searialType = byteBuf.readByte();
        long id = byteBuf.readLong();
        int msgLength = byteBuf.readInt();
        int readerIndex = byteBuf.readerIndex();
        int bodyLength = msgLength - Constant.HEADER_LENGTH;
        //判断是否在IO线程中解码消息
        boolean ioDecoder = uri.getParameter(Constant.IO_DECODER, Constant.IO_DECODER_DEFAULT_VALUE);
        Object data = null;
        if (eventType == Constant.RESPONSE) {
            ByteBuf msgByteBuf = byteBuf.slice(readerIndex, bodyLength);
            Serialization serializer = getSerializer(serial);
            byte[] bytes = new byte[msgByteBuf.readableBytes()];
            msgByteBuf.readBytes(bytes);
            InputStream inputStream = new ByteArrayInputStream(bytes);
            ObjectInput objectInput = serializer.deserialize(inputStream);
            data = objectInput.readObject();
        } else {
            if (!ioDecoder) {
                ByteBuf msgByteBuf = byteBuf.slice(readerIndex, bodyLength);
                data = Unpooled.copiedBuffer(msgByteBuf);
            }
        }
        byteBuf.readerIndex(byteBuf.readerIndex() + bodyLength);
        Header header = new Header();
        //header.setMagic(magic);
        header.setEventType(eventType);
        header.setStatus(status);
        header.setSerialType(searialType);
        header.setId(id);
        header.setLength(msgLength);
        if (Constant.REQUEST == eventType) {
            Request request = new Request();
            request.setHeader(header);
            request.setData(data);
            return request;
        } else if (Constant.RESPONSE == eventType) {
            Response response = new Response();
            response.setHeader(header);
            response.setData(data);
            return response;
        }
        return null;
    }

    public static Serialization getSerializer(String serialKey) {
        Serialization serializer = null;
        if ("kryo".equals(serialKey)) {
            serializer = new ProtostuffSerialization();
        }
        return serializer;
    }

    public static Object decodeBody(String serialKey, DecodeObject decodeObject) throws IOException {
        Serialization serializer = getSerializer(serialKey);
        byte[] body = decodeObject.getBody();
        if (body.length == 0) {
            throw new IllegalStateException("body data is null");
        }
        ByteArrayInputStream inputStream = new ByteArrayInputStream(body);
        ObjectInput objectInput = serializer.deserialize(inputStream);
        return objectInput.readObject();
    }
}
