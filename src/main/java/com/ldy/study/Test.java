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

        System.out.println(tryCatchTest("123")+"return");
        System.out.println(tryCatchTest(null)+"return");
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
