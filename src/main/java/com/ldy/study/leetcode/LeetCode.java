package com.ldy.study.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class LeetCode {
    /**
     * 原语Str
     */

    public static String removeOuterParentheses(String S) {
       if (S== null || S.length()<0 )
           return null;
       StringBuffer sb = new StringBuffer();

       int i=0,j=0;
        Stack<Character> stack = new Stack<>();
       while (i<S.length()){

           if (S.charAt(i) =='('){
               stack.add(S.charAt(i++));
               continue;
           }
           if (S.charAt(i) ==')'){
               stack.pop();
               if (stack.isEmpty()){
                   sb.append(S.substring(j+1,i));
                   j=i+1;
               }
               i++;
           }


       }
       return sb.toString();

    }

    public static void main(String[] args) {
//      String str = "100";
        int [] str = {-19,-46,-19,-46,-9,-9,-19,17,17,17,-13,-13,-9,-13,-46,-28};

       int i = -100;
       int s = Integer.parseUnsignedInt("11111111111111111111111111111101",2);

      System.out.println(reverseBits(s));
    }

    public static int reverseBits(int n) {

        // 10进制和二进制需要转换吗？
        // 整理二进制的用法
        // 思路 把最左边的位移动到该位最终的位置上去
        //位运算，如果不把数字打印出来，就不会转换为10 进制！
        int power = 31;//1后有31个0 正好32位
        int res = 0;
        while (n!=0){
            res +=(n&1)<<power;
            power --;
            n>>>=1;
        }
        return res;

        }

    public static int singleNumber(int[] nums) {
        /**
         *方法1  申请长度为32的数组,记录nums中每位出现的频率,
         * 某位上模k不为0,此位置为1
         */

        int[] bits = new int [32];
        for (int i = 0;i<nums.length;i++){
            //记录每位出现的频率
            int val = nums[i];
            int j = 31;
            while (val !=0){
                bits[j--] += val&1;
                val >>>=1;
            }
        }
        StringBuilder sb = new StringBuilder();

        for (int i = 0;i<bits.length;i++){
            if (bits[i]%3 ==0){
                sb.append("0");
            }else {
                sb.append("1");
            }
        }
        boolean flag = false;
        if(sb.charAt(0) == '1'){
            flag = true;
        }
        //解析无符号位的二进制数 ，什么叫无符号位,带有-号的叫做符号位
        int re = Integer.parseUnsignedInt(sb.toString(),2);
        return re;


    }

    public static boolean hasGroupsSizeX(int[] deck) {
        //数组中记数目最少的元素个数为a
        //当a >1且 与其他元素有公约数时，输出TRUE
        //HashMap
        Map<Integer,Integer> map = new HashMap();
        for (int i =0;i<deck.length;i++){
            if (map.containsKey(deck[i])){
                int val = map.get(deck[i]);
                map.put(deck[i],val+1);
            }else {
                map.put(deck[i],1);
            }
        }
        //查找最小的值
        int min = Integer.MAX_VALUE;
        int length = 0;
        for(Integer i:map.keySet()){
            min = Math.min(min,map.get(i));
            length++;
        }
        if (min<2){
            return false;
        }
        //数组中的所有元素是否存在公约数
        int num =0;
        int max =0;
        for (int j =2;j<=min;j++){
            num =0;
            for (Integer i:map.keySet()){
                if (map.get(i)%j!=0){
                    break;
                }
                num++;
            }
            max = Math.max(max,num);//记录最大的num
        }
        return max == length?true:false;

    }

    public static int[] relativeSortArray(int[] arr1, int[] arr2) {
        /**
         * 第一种思路 原数组排序
         * 第二种思路 用map记录重复，然后排序
         */
        int index = 0;
        for (int i = 0;i<arr2.length;i++){
            int j = index+1;
            while (j<arr1.length){
                while (index < arr1.length && arr1[index] == arr2[i]) {
                    index++;
                }
                if (j<=index){
                    j = index+1;
                }
                if (j<arr1.length && arr1[j] == arr2[i]){//交换
                    int tmp = arr1[index];
                    arr1[index] = arr1[j];
                    arr1[j] = tmp;
                    index ++;
                }
                j++;
            }
        }
        if (index<arr1.length){
            //排序
        }
        return arr1;

    }




    public static int divide(int dividend, int divisor) {
        //怎么判断溢出呢？
        /**
         * 第一种思路 把原来的值转换为更大的数据类型（int 转long）
         * 然后拿最终的值与int的边界值比较
         */

        /**
         * 本题的思路
         * 乘法的本质是多个数相加，12=3+3+3+3 = 3*4
         * 那么除法作为乘法的逆运算，可以用减法实现
         * 12/3 = 12-3-3-3-3,12减四次3 ,减了4次，结果是4
         * 但是这种做法太低效，可定会超时。
         * 除法变移位和减法
         * a>>2 a左移两位
         * a<<2 a右移两位
         * a>>>2 a 无符号左移两位
         * 借助二进制的除法，然后转为10进制的除法
         */
        boolean sign = (dividend>0)^(divisor>0);
        long divid = dividend;
        long divis = divisor;
        divid = Math.abs(divid);
        divis = Math.abs(divis);
        int count = 0;
        while(divis<=divid){
            divis<<=1;
            count+=1;//记录一共右移多少次
        }
        long result = 0;
        long bit = 1L;
        while(count>0){
            count --;
            divis>>=1;
            if (divis<=divid){
                result+=bit<<count;
                divid -= divis;
            }
        }
        result = sign==false?result:-result;
        if (result>Integer.MAX_VALUE || result<Integer.MIN_VALUE){
            return Integer.MAX_VALUE;
        }
        return (int)result;
    }




    public static int mySqrt(int x) {
        if (x <0){
            return -1;
        }
        if (x==0)
            return 0;
        long start =1,end = x/2;
        //不需要start与end相遇
        while (start<end){
            long mid = start+(end-start)/2;
            if (mid*mid == x){
                return (int)mid;
            }
            //[start,end] [mid,end][start,mid] [mid,mid] 四种情况
            if (end-start == 1){
                if(start*start<x && end*end>x){
                    return (int)start;
                }
                return (int)end;
            }

            //start 和mid 需要赋值为mid ,如果赋值为midi-1或者mid+1 很可能错过 end-start =1 and start*start<x<end*end
            if (mid*mid<x){
                start =mid;
            }else if (mid*mid >x){
                end = mid;
            }
        }
        return 1;

    }

}
