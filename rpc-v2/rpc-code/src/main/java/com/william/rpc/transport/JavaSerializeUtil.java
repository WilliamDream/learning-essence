package com.william.rpc.transport;


import java.io.*;

public class JavaSerializeUtil {

    /**
     * 序列化
     * @param object
     * @param <T>
     * @return
     */
    public static  <T> byte[] serialize(T object){
        //序列化是输出字节数值，所以是ByteArrayOutputStream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            //是将java对象放在输出流中
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

    /**
     * 反序列化
     * @param data
     * @param <T>
     * @return
     */
    public static  <T> T deserialize(byte[] data){
        //反序列化是将字节数组作为输入对象，所以是ByteArrayInputStream
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        try {
            ObjectInput objectInput = new ObjectInputStream(bais);
            return (T)objectInput.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
