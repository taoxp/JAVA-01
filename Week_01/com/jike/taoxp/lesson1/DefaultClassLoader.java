package com.jike.taoxp.lesson1;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * @author: taoxp
 * @create: 2021-01-08 16:40
 */
public class DefaultClassLoader extends ClassLoader {

    private final static String FILE_URL="./Week_01/Hello.xlass";

    public static void main(String[] args) {
        try {
            Object object = new DefaultClassLoader().findClass("Hello").newInstance();
            Method[] methods=object.getClass().getMethods();
            ArrayList<String> superMethods =new ArrayList<>();
            addMethod(object.getClass().getSuperclass().getMethods(),superMethods);
            for (Method method:methods){
                if (method.getParameterCount()==0&&!superMethods.contains(method.getName())){
                    method.invoke(object, null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes=analysisFile(FILE_URL);
        convertToNewByte(bytes);
        return defineClass(name, bytes, 0,bytes.length);
    }

    public byte[] analysisFile(String fileUrl) {

        byte[] bytes;
        try {
            bytes = Files.readAllBytes(Paths.get(fileUrl));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("文件不存在！");

        }
        return bytes;
    }

    public void convertToNewByte(byte[] bytes){
        for (int index=0;index<bytes.length;index++){
            bytes[index]= (byte) (255-bytes[index]);
        }
    }

    public static void addMethod(Method[] methods,ArrayList originalMethods){
        for (Method method:methods){
            originalMethods.add(method.getName());
        }
    }
}
