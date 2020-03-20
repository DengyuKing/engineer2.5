package com.ldy.study.classloaderTest;

import java.io.*;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class ClassLoaderTest {

    public static void main(String[] args) throws Exception {
        String path="./target/classes/com/ldy/study/classloaderTest";
        LdyClassLoader cl = new LdyClassLoader("./target/classes/com/ldy/study/leetcode/Niuke.class");
       Class c = Class.forName("Niuke.class",true,cl);


        System.out.println(c.getClassLoader());
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
    protected Class<?> findClass(String name) throws ClassNotFoundException {
            byte [] b ;
            Class c = null;
            b = getData();
            if (b.length>0){
               c= defineClass(name,b,0,b.length);
               return c;
            }
        return null;
    }
    private byte [] getData(){
        File f = new File(path);
        if (f.exists()){
            try {
                FileInputStream file = new FileInputStream(path);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buff = new byte[1024];
                while (file.read(buff) != -1){
                    out.write(buff);
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


