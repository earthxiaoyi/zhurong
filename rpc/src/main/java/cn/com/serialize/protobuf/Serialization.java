package cn.com.serialize.protobuf;

import java.io.*;

public interface Serialization {

    /**
     * 序列化方法
     *
     * @param output
     * @return
     * @throws IOException
     */
    ObjectOutput serialize(OutputStream output) throws IOException;

    /**
     * 反序列化方法
     *
     * @param input
     * @return
     * @throws IOException
     */
    ObjectInput deserialize(InputStream input) throws IOException;

}
