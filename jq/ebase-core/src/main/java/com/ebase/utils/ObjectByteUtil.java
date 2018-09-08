package com.ebase.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectByteUtil {
    /**
     * 将字节转换为对象
     *
     * @param bytes
     * @return
     */
    public static Object ByteToObject(byte[] bytes) throws Exception {
        Object obj = null;
        // bytearray to object
        ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
        ObjectInputStream oi = new ObjectInputStream(bi);

        obj = oi.readObject();
        bi.close();
        oi.close();
        return obj;
    }

    /**
     * 将对像转换为字节
     *
     * @param obj
     * @return
     */
    public static byte[] ObjectToByte(Object obj) throws Exception {
        byte[] bytes = null;
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(obj);

        bytes = bo.toByteArray();

        bo.close();
        oo.close();
        return bytes;
    }
}
