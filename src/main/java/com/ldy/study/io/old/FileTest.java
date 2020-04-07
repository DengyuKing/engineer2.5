package com.ldy.study.io.old;

import java.io.*;
import java.util.Arrays;

public class FileTest {

    public static void main(String[] args) throws Exception {
        File path= new File(".");
        String [] list;
        list = path.list();
        for (String i :list){
            System.out.println(i);
        }
//        getFile();


    }

//    public static byte [] getFile() throws FileNotFoundException{
//        File file = new File("D:\\yndetails20191230begin国际化明细.csv");
//        BufferedInputStream bf = new BufferedReader((file));
//        byte [] buffer = new byte[1024];
//        try {
//            while (bf.read(buffer,0,buffer.length) != -1){
//                System.out.println(Arrays.toString(buffer));
//            }
//        }catch(IOException e ){
//
//        }
//        return null;
//    }
}
