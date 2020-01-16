package com.ldy.study.collection;



import lombok.extern.slf4j.Slf4j;

import java.util.*;

public class CollectionTest {

    public static void main(String[] args) {
        String billNo = null;
       {
            String dateNumber =String.valueOf(new Date().getTime());
            int modCode = Math.abs("10166834".hashCode() % 32) ;
            billNo = "101001" + dateNumber + format(String.valueOf("2897326605"),12) + modCode;
        }
       System.out.println(billNo);
    }

    private static String format(String str,int length){
        String pre = "00000000000000";
        int len = str.length();
        if(10 <= len) return str;
        else return pre.substring(0,length - len).concat(str);
    }

    /**
     * 当集合获取完迭代器后，如果对集合本身进行增删操作，将会影响
     */
    public static void failFastTest() {
        Collection<String> c = new ArrayList<>();
        c.add("an object");
        Iterator it = c.iterator();
        try{
        it.next();
        }catch(ConcurrentModificationException e){
            System.out.println(e);
        }
    }
    public static void bitSet() {
        BitSet bb = new BitSet();
    }
}
