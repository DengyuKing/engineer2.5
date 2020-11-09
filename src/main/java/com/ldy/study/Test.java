package com.ldy.study;

public class Test {
    public static void main(String[] args) {
//        for (int i = 0;i<32;i++) {
////         System.out.println("alter table fs_bill_detail"+i+"  change splitBatch split_batch varchar (32) default null comment '分账批次';");
////         System.out.println("alter table fs_split_bill_detail"+i+"  change splitBatch split_batch varchar (32) default null comment '分账批次';");
////         System.out.println("alter table fs_split_pending"+i+"  change splitBatch split_batch varchar (32) default null comment '分账批次'; ");
//
////            System.out.println("desc fs_bill_task"+i+";");
////            System.out.println("desc fs_bill_detail"+i+";");
////            System.out.println("desc fs_split_bill_detail"+i+";");
////            System.out.println("desc fs_split_pending"+i+";");
//
//
//
//        }

//        System.out.println(tryCatchTest("123")+"return");
//        System.out.println(tryCatchTest(null)+"return");

//        String str ="12345";
//        System.out.println(str.length());


        String a = "a";
        String b = "a";  //直接赋值的String串使用了缓存技术

        String c = new String("a"); //new 关键字是直接在堆上分配的对象，
        String d = new String("a");// == 比较的是引用类型的对象地址是否相等。
        System.out.println(a == b);
        System.out.println(a==c);
        System.out.println(b==c);
        System.out.println(c==d);

    }


    public static  String tryCatchTest(String test){

        try{
            if ("123".equals(test)){
               // return "this is 123";
            }else {
                throw new RuntimeException("抛异常");
            }
        }catch(Exception e ){
            /**
             * 异常被catch住的话，这里的作用相当于条件语句，下面的逻辑也会执行
             */
            System.out.println("this is exception");
            //return "this is a exception";

        }finally {
            System.out.println("this is finally");
            /**
             * 无论是发生异常还是正常的逻辑，只要finally中有return 的话，会最终返回finally中的return内容
             * 所以finally中永远不能写return语句
             *
             * finally里也会抛异常，
             *
             */
           //return "this is finally";
        }
         System.out.println("this is the end ");
        return "this is the end";

    }
}
