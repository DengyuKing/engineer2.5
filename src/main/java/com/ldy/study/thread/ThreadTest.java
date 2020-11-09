package com.ldy.study.thread;


import java.util.ArrayList;
import java.util.List;

public class ThreadTest {
    /**
     * 阿里巴巴面试题，给出三个线程，一个只打印t1,一个只打t2,一个只打t3,写出程序，让其顺序打印t1,t2,t3 循环打印5次
     * 实现思路:
     *  维持一个只读数组，
     * @param args
     */
    public static void main(String[] args) {
        generate(5);
    }

    public static List<List<Integer>> generate(int numRows) {
        //第N行应该有N个数
        List<List<Integer>> list = new ArrayList();
        for (int i = 1;i <= numRows;i++){
            List<Integer> li = new ArrayList();
            for (int j = 0;j<i;j++){
                if (j ==0 || j == i-1){
                    li.add(1);
                }else {
                    int tmp = list.get(i-2).get(j-1)+list.get(i-2).get(j);
                    li.add(tmp);
                }

            }
            list.add(li);
        }
        return list;

    }

}

class MyThread extends Thread{
    String [] array ;
    private volatile static int i;
    String name;
    private static Object lock = new Object();
    public MyThread(String [] array, int i,String name){
        this.array = array;
        this.i = i;
        this.name = name;
    }
    @Override
    public void run() {
        while (true) {
            /**
                //立即可见的
            if (i < array.length && name.equals(array[i])) { // 读，
                System.out.println(name);
            }
            synchronized (lock) {

                i++;// 写
            }
             以上这种写法是错误的，因为当执行到name.equals(array[i])时，有可能其他线程修改了i的值，
             此时i可能会出现数组越界的风险。
            **/
                synchronized (lock){
                    if (i < array.length && name.equals(array[i])) {
                        System.out.println(name);
                    i++;
                }

            }
            if (i>=array.length){
                System.out.println("任务执行结束 name="+name);
                break;
            }
        }

    }
}