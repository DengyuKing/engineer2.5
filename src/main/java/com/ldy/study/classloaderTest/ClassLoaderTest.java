package com.ldy.study.classloaderTest;

import com.sun.org.apache.bcel.internal.util.ClassPath;
import sun.net.www.http.HttpClient;

import java.io.*;
import java.lang.reflect.Constructor;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class ClassLoaderTest {

    public static void main(String[] args) throws Exception {

        ClassLoaderTest test = new ClassLoaderTest();
        MyClassLoader ldy = new MyClassLoader() ;
        // 获取其他路径的类名时，找不到资源
        Class demo = ldy.loadClass("com.ldy.study.thread.AlibabaThreadTest");
        Constructor c = demo.getDeclaredConstructor();
        c.setAccessible(true);

        System.out.println(ldy.getClass().getClassLoader().getResource("/"));
        System.out.println(ldy.getClass().getClassLoader().getResource(""));
        System.out.println(MyClassLoader.class.getResource("/"));
        System.out.println(MyClassLoader.class.getResource(""));



    }
}

/**
 * 获取指定包名下的class
 */
class MyClassLoader extends ClassLoader{


    String path = "com.ldy.study";
    Properties properties = new Properties();

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        try {
            String fileName = name.substring(name.lastIndexOf(".")+1)+".class";

            /**
             * 获取MyClassLoader同一个路径下的类，因为在进行名称拼接时，使用改方法会默认取
             * com.ldy.study.classloaderTest 当前包，该方法适合查找同一个包名下方法。
             *
             */

            InputStream is = getClass().getResourceAsStream(fileName);
            if (is == null) {
                return  super.loadClass(name);
            }
            byte[] b = new byte[is.available()];
            is.read(b);
            return defineClass(name, b, 0, b.length);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return super.findClass(name);


    }
}

class SimpleClassLoader extends ClassLoader{

    /**
     * Loads the class with the specified <a href="#name">binary name</a>.
     * This method searches for classes in the same manner as the {@link
     * #loadClass(String, boolean)} method.  It is invoked by the Java virtual
     * machine to resolve class references.  Invoking this method is equivalent
     * to invoking {@link #loadClass(String, boolean) <tt>loadClass(name,
     * false)</tt>}.
     *
     * @param name The <a href="#name">binary name</a> of the class
     * @return The resulting <tt>Class</tt> object
     * @throws ClassNotFoundException If the class was not found
     */
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return Class.forName(name);
    }
}




class Demo{
    A A =new A();
    B B = new B();

}


