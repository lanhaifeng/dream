package com.feng.baseframework.util;

import java.io.*;

/**
 * @ProjectName: baseframework
 * @Description: bean工具类
 * @Author: lanhaifeng
 * @CreateDate: 2018/10/24 8:14
 * @UpdateUser:
 * @UpdateDate: 2018/10/24 8:14
 * @UpdateRemark:
 * @Version: 1.0
 */
public class BeanUtil {

    /**
     * 对象的深度克隆，此处的对象涉及Collection接口和Map接口下对象的深度克隆
     * 利用序列化和反序列化的方式进行深度克隆对象
     *
     * @author lanhaifeng
     * @param object 待克隆的对象
     * @param <T> 待克隆对象的数据类型
     * @date 2018/10/24 8:17
     * @return 已经深度克隆过的对象
     */
    public static <T extends Serializable> T deepCloneObject(T object) {
        T deepClone = null;
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            bais = new ByteArrayInputStream(baos
                    .toByteArray());
            ois = new ObjectInputStream(bais);
            deepClone = (T)ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if(baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if(oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try{
                if(bais != null) {
                    bais.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try{
                if(ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return deepClone;
    }


}
