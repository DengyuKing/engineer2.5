package com.ldy.study.thread;


import java.util.ArrayList;
import java.util.List;

/**
 * 线程池
 */
public class ThreadPool {
    private List<Thread> threads = new ArrayList<>();
    private List<Runnable> tasks;
    class Worker implements Runnable{
        Thread thread;
        private Runnable task;
        public Worker(Runnable task){
            Thread thread = new Thread(this);
            this.thread=thread;
            this.task = task;
        }
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
            runWork();
        }
        public void runWork(){
            task.run();
        }

    }

    public void submit(Runnable task){
        Worker worker = new Worker(task);
        Thread t = worker.thread;

    }

    public static ThreadPool newThreadPool (int sum){
        return new ThreadPool(sum);
    }
    private ThreadPool(int sum){
        if (sum <0 || sum>6)
            throw new RuntimeException("线程数目不合法");
        for (int i =0;i<sum;i++){
            threads.add(new Thread("thread"+i));
        }
    }
    public void doRun(Runnable task){
        if (task == null){
            throw new RuntimeException("获取任务为空");
        }
        if (!threads.isEmpty()){
           task.run();
        }
    }

    public static void main(String[] args) {

//       Task task = new Task();
//        ThreadPool pool = newThreadPool(5);
//        pool.submit(task);

        /**
         * 如果一个线程执行完成后，并且处于Terminated状态，再次start时会报错

        Thread t= new Thread(new Task());
        t.start();
        while (t.isAlive()){
            System.out.println(t.getState());
        }
        System.out.println("end "+t.getName() +" "+t.getState());
        System.out.println("第二次启动");
        t.start();
        while (t.isAlive()){
            System.out.println(t.getState());
        }
        System.out.println("end "+t.getName() +" "+t.getState());
         */


        /**
         * 释放锁到底是什么操作？
         *
         */
       Thread t = new Thread(new Task3());
       t.start();
       System.out.println(" main start to interupt ");
       System.out.println(t.isInterrupted());
      while(true)
      t.interrupt();

    }







}

/**
 * 线程的两种实现方式，一种是通过继承thread类，这种方式相对来说比较单调，一般不会采用这种方式创建线程
 * 第二种方式是继承Runnable接口，这种方式相对灵活，实现了任务和线程的分离，可以先创建好线程池，然后把任务丢到线程池去执行！
 */

class Task2 implements Runnable{
    Object lock1;
    Object lock2;

    public Task2(Object lock1,Object lock2){
        this.lock1 = lock1;
        this.lock2 = lock2;
    }
    @Override
    public void run() {
        synchronized (lock2){
            System.out.println(Thread.currentThread().getName()+"获取到lock2的锁");
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e ){
                System.out.println(Thread.currentThread().getName()+"被打断");
            }
            synchronized (lock1){
                System.out.println(Thread.currentThread().getName()+"获取到lock1的锁");
            }
        }
    }
}



class Task3 implements Runnable{
    Object lock1;
    Object lock2;

    @Override
    public void run() {
       try{
           Thread.sleep(10000);
       }catch(InterruptedException e){
           System.out.println("interupt sleep");
       }
        System.out.println("start to wait");
        try{
            Thread.sleep(100000);
        }catch(InterruptedException e){
            System.out.println("interupt wait +"+Thread.interrupted());
        }
    }
}

class Task implements Runnable{
    Object lock1;
    Object lock2;
    int id = 5;

    public Task(Object lock1,Object lock2){
        this.lock1 = lock1;
        this.lock2 = lock2;
    }
    @Override
    public void run() {
     synchronized (lock1){
         try{
             Thread.sleep(1000);
         }catch(InterruptedException e ){
             System.out.println(Thread.currentThread().getName()+"被打断");
         }
         System.out.println(Thread.currentThread().getName()+"获取到lock1的锁");
         synchronized (lock2){
             System.out.println(Thread.currentThread().getName()+"获取到lock2的锁");
         }
     }
    }
}
