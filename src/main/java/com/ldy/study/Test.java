package com.ldy.study;

public class Test {
    public static void main(String[] args) {
        for (int i = 0;i<32;i++) {
//         System.out.println("alter table fs_bill_detail"+i+"  change splitBatch split_batch varchar (32) default null comment '分账批次';");
//         System.out.println("alter table fs_split_bill_detail"+i+"  change splitBatch split_batch varchar (32) default null comment '分账批次';");
//         System.out.println("alter table fs_split_pending"+i+"  change splitBatch split_batch varchar (32) default null comment '分账批次'; ");

            System.out.println("alter table fs_bill_task"+i+"  add  split_batch varchar (32) default null comment '分账批次'; ");
        }
    }
}
