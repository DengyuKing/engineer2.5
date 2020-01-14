package com.ldy.study.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CollectionTest {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<Integer>();

        list.addAll(null);
        System.out.println(list);
    }

    public static void failFastTest() {
        Collection<String> c = new ArrayList<>();
        Iterator it = c.iterator();
        c.add("an object");
        try{

        }catch(Exception e){
        }
    }
}
