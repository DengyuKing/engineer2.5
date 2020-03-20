package com.ldy.study.thread;

import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicTest {

    public static void main(String[] args) throws Exception{
        /**
         * 原子类
         */
        AtomicInteger at = new AtomicInteger();
        System.out.println(at.getAndAdd(100));
    }

    /**
     * 虚引用测试test
     * 当一个强引用和有一个弱引用同时指向一个对象时，该对象不会被回收
     * 当把强引用去掉，只剩一个弱引用时，对象会被回收
     */
    public static void weakTest() throws Exception{
        Integer in = new Integer(100);
        WeakReference <Integer> test = new WeakReference<>(in);
        System.out.println(in);
        System.out.println(test.get());
        System.gc();
        in = null;
        Thread.sleep(100);
        System.gc();
        System.out.println(in);
        System.out.println(test.get());
    }
}



