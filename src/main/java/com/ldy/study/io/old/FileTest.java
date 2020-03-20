package com.ldy.study.io.old;

import java.io.File;

public class FileTest {

    public static void main(String[] args) {
        File path= new File(".");
        String [] list;
        list = path.list();
        for (String i :list){
            System.out.println(i);
        }
    }
}
