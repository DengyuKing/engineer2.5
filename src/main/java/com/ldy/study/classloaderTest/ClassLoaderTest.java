package com.ldy.study.classloaderTest;

import java.io.*;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class ClassLoaderTest {

    public static void main(String[] args) throws Exception {
        String path="./target/classes/com/ldy/study/classloaderTest";
        LdyClassLoader cl = new LdyClassLoader("./target/classes/com/ldy/study/ClassLoaderTest/Demo.class");
//       Class c = Class.forName("com.ldy.study.leetcode.Niuke",true,cl);
       Class clazz = cl.loadClass("com.ldy.study.classloaderTest.Demo");


        System.out.println(clazz.getClassLoader());
        System.out.println(A.class.getClassLoader());
        System.out.println(B.class.getClassLoader());


    }
}

class LdyClassLoader extends ClassLoader{

    String path ;

    public LdyClassLoader(String classPath){
        this.path = classPath;
    }


    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        System.out.println(Thread.currentThread()+" "+name);
        byte [] b = getData();
        if (findClass(name) == null){
            super.loadClass(name);
        }
        //不用双亲委派模型，会出现各种个样的错误，理解这块内容还是需要把类加载的基本流程了解清楚！
        Class c = defineClass(name,b,0,b.length);
        return c;

    }

    private byte [] getData(){
        File f = new File(path);
        if (f.exists()){
            try {
                FileInputStream file = new FileInputStream(path);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buff = new byte[1024];
                int len = 0;
                while ((len = file.read(buff)) != -1){
                    out.write(buff,0,len);
                }
                return out.toByteArray();


            }catch(FileNotFoundException e ){

            }catch(IOException e ){

            }
        }

        return null;


    }
}

class Demo{
    A A =new A();
    B B = new B();

}


