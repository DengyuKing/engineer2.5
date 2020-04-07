package com.ldy.study.leetcode;

import java.util.Stack;

public class LeetCode {

    /**
     * 原语Str
     */

//    public static String removeOuterParentheses(String S) {
//       if (S== null || S.length()<0 )
//           return null;
//       StringBuffer sb = new StringBuffer();
//
//       int i=0,j=0;
//        Stack<Character> stack = new Stack<>();
//       while (i<S.length()){
//
//           if (S.charAt(i) =='('){
//               stack.add(S.charAt(i++));
//               continue;
//           }
//           if (S.charAt(i) ==')'){
//               stack.pop();
//               if (stack.isEmpty()){
//                   sb.append(S.substring(j+1,i));
//                   j=i+1;
//               }
//               i++;
//           }
//
//
//       }
//       return sb.toString();
//
//    }

//    public static void main(String[] args) {
//        System.out.println(removeOuterParentheses("()()"));
//    }
    public static void main(String[] args) {
        System.out.println(huiwen("abcba"));
    }

    /**
     * 删除一个字符判断是不是回文
     * 分为两种情况
     * 第一种情况字符串两边的字符是相等的
     * 第二中情况，字符串两边的字符是不等的
     *   由于只能删除一个字符，那么可以尝试删除左边的，或者删除右边的 两种情况
     *
     * 思考 ，如果允许删除多个字符呢?
     *   删除最少的字符构成回文呢？
     * @param s
     * @return
     */
    public static boolean huiwen(String s ){

        if(s==null || s.length() <=0 || s.length() == 1 ){
            return true;
        }
        int i = 0,j =s.length()-1;
        while (i<j){
            if (s.charAt(i)!=s.charAt(j)){
                return isParams(s,i+1,j) || isParams(s,i,j-1);
            }
            i++;
            j--;
        }
        return true;

    }

    private static  boolean isParams (String s ,int start,int end){
        while (start<end){
            if (s.charAt(start)!=s.charAt(end)){
                return false;
            }
            start++;
            end--;
        }
        return true;
    }


}
