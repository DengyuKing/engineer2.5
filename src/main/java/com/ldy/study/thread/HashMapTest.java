package com.ldy.study.thread;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


public class HashMapTest {

    public static void main(String[] args) {

        // hashMap扩容实验
        Class c = null;
        try {


            c = Class.forName("java.util.HashMap");
            HashMap map = (HashMap)c.newInstance();
            Field f = c.getDeclaredField("table");
            f.setAccessible(true);
            for (int i = 0;i<16;i++){
                map.put(1+" ",null);
                Map.Entry [] table = ( Map.Entry [])f.get(map);
                System.out.println("table'size="+table.length+" map元素个数="+map.size());

            }


        }catch(ClassNotFoundException e ){

        }catch(Exception e){
           e.printStackTrace();
        }



    }
}
