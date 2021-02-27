package cn.com;

import cn.com.serialize.protobuf.ObjectInput;
import cn.com.serialize.protobuf.ObjectOutput;
import cn.com.serialize.protobuf.ProtostuffSerialization;
import cn.com.serialize.protobuf.Serialization;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;

public class ProtostuffSerializationTest {

    private Serialization serialization = new ProtostuffSerialization();

    static {

    }

    @Test
    public void testSerialize() throws IOException, ClassNotFoundException {
        Person person = new Person();
        person.age = 10;
        person.name = "张三";
        person.loginName = "zhangsan";
        person.pwd = "123123";
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutput output = serialization.serialize(byteArrayOutputStream);
        output.writeBool(false);
        output.writeInt(1113);
        output.writeObject(person);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        ObjectInput input = serialization.deserialize(byteArrayInputStream);

        Assert.assertEquals(false, input.readBool());
        Assert.assertEquals(1113,input.readInt());
        Assert.assertEquals(person, input.readObject(Person.class));
    }

}
