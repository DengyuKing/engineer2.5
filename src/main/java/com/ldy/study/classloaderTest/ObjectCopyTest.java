package com.ldy.study.classloaderTest;

import java.io.*;

public class ObjectCopyTest {

    public static void main(String[] args) throws Exception {

//       Arress add=  new Arress("北京");
//        DemoCopyTest a = new DemoCopyTest(1,2f,3d,'4',"aaa",new Integer(100),add);
//        System.out.println(a);
//        DemoCopyTest b = (DemoCopyTest) a.clone();
//        System.out.println(b);
//        System.out.println(a==b);
//        a.a=5;
//        a.b=5f;
//        a.c=5d;
//        a.d='5';
//        a.e="bbb";
//        a.f= new Integer(10);
//        add.address ="上海";
//        System.out.println("a="+a);
//        System.out.println("b="+b);
        A a = new A();
        B b = new B();
        a.a ="test a";
        b.b = "test b" ;

        DemoTest demo = new DemoTest(a,b);

        DemoTest de = demo.myClone();
        System.out.println(demo.toString());
        System.out.println(de.toString());
        a.a = "123";
        b.b = "456";
        System.out.println(demo.toString());
        System.out.println(de.toString());


    }

}

/**
 * clone 克隆实现深度复制 多层引用对象都需要重写clone方法，比较麻烦！
 */

class DemoCopyTest implements Cloneable{
   int a ;
   float b ;
   double c ;
   char d ;
   String e;
   Integer f;
   Arress arress;
  
  


  public DemoCopyTest(int a,float b,double c ,char d ,String e,Integer f, Arress g){
      this.a =a;
      this.b =b;
      this.c=c;
      this.d =d;
      this.e =e;
      this.f = f;
      this.arress = g;
  }



    @Override
    protected Object clone() throws CloneNotSupportedException {

        try{
            DemoCopyTest test= (DemoCopyTest) super.clone();
          test.arress = (Arress) this.arress.clone();
          return test;
      }catch(Exception e){

      }

     return null;
    }

    @Override
    public String toString() {
        return this.a +" "+ this.b +" "+this.c +" "+ this.d + " " +this.e + this.arress.toString();
    }
}

class Arress implements Cloneable{
    String address ;
    public Arress(String address){
        this.address = address;

    }

    @Override
    public String toString() {
        return this.address;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
       return super.clone();
    }
}
/**
 * 序列化方法实现深度复制(实现简单，仅仅要求引用的对象需要实现序列化)
 */

class DemoTest implements Serializable {
    A a;
    B b;
    public DemoTest(A a, B b ){
        this.a =a;
        this.b =b;

    }
    public DemoTest myClone(){
        try {
            //写入对象流
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(out);
            oos.writeObject(this);
            //读取对象流
            InputStream in = new ByteArrayInputStream(out.toByteArray());
            ObjectInputStream osin = new ObjectInputStream(in);
            DemoTest tmp = (DemoTest) osin.readObject();
            return tmp;
        }catch(Exception e ){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return a.a +"  "+b.b;
    }
}

class A  implements Serializable{
    String a ;

}

class B implements Serializable{
    String b;
}
