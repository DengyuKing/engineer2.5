package com.ldy.study.thread;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池
 */
public class ThreadPool {

    public static void main(String[] args) throws Exception{
        ExecutorService service = Executors.newFixedThreadPool(5);
        CountDownLatch latch = new CountDownLatch(5);
        for (int i = 0;i<10;i++){
            service.submit(new TaskPool(0,latch));
        }
        latch.await();
        service.shutdownNow();

    }









}
class TaskPool implements Runnable{
    private Integer i;
    private CountDownLatch latch;
    public TaskPool(Integer i,CountDownLatch latch){
     this.i = i;
     this.latch = latch;
    }
    @Override
    public void run() {
        ThreadLocal<Integer> avg = new ThreadLocal();
        avg.set(i);
        ThreadLocal<String> tt = new ThreadLocal<>();
        tt.set("name");
        for (int i =0;i<100;i++){
            avg.set(avg.get()+1);
        }
        System.out.println("current thread = "+Thread.currentThread()+" sum="+avg.get());
        latch.countDown();
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
