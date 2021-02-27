package cn.com.serialize.protobuf;

import java.io.*;


/**
 * @author jiaming
 */
public class ProtostuffSerialization implements Serialization {

    @Override
    public ObjectOutput serialize(OutputStream output) throws IOException {
        return new ProtostuffObjectOutput(output);
    }

    @Override
    public ObjectInput deserialize(InputStream input) throws IOException {
        return new ProtostuffObjectInput(input);
    }
}
