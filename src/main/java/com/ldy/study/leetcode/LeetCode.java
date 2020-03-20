package com.ldy.study.leetcode;

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
        System.out.println(removeOuterParentheses("()()"));
    }
}
