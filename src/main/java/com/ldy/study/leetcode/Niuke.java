package com.ldy.study.leetcode;

public class Niuke {
/**
 * 给予一个串 ，做出以下改变
 * 1 如果串中有三个连续的字母，去掉一个 helllo ->hello
 * 2 对于串中有AABB的转为  AABB -> AAB
 */
    public static void strCheck(String str){

        if (str.length()<4)
            System.out.println(str);

        StringBuffer sb = new StringBuffer();
        int i = 3;
        while (i<str.length()){

        }


    }

    /**
     * 给予一个正数，对正数进行加法分解，求分解得到的数的最大积
     * @param num
     */
    public static void maxNum(int num){
     long max = findMaxNum(num);
     System.out.println(max);

    }
    private static long findMaxNum(int num){
        if (num == 1 || num==2)
            return 1;

        long [] arr = new long[num];
        arr[0] = 1;
        arr[1] = 1;
        for (int i=3;i<=num;i++){
            long max = 0;
            for (int j=1;j<i;j++){

                max = Math.max(max,j*arr[i-1-j]);
                max = Math.max(max,j*(i-j));
            }
            arr[i-1]= max;
        }
        return arr[num-1];
    }

    public static void main(String[] args) {

    }


}
