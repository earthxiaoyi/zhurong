package cn.com.serialize.protobuf;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author jiaming
 */
public interface ObjectInput extends DataInput {

    /**
     * read object
     *
     * @return object
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if an ClassNotFoundException occurs
     */
    Object readObject() throws IOException;

    /**
     * read object
     *
     * @param cls object class
     * @return object
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if an ClassNotFoundException occurs
     */
    <T> T readObject(Class<T> cls) throws IOException;

    /**
     * read object
     *
     * @param cls object class
     * @param type object type
     * @return object
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if an ClassNotFoundException occurs
     */
    <T> T readObject(Class<T> cls, Type type) throws IOException;

}