package com.ldy.study.thread;

import java.util.Comparator;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class AlibabaThreadTest {


    public static void main(String[] args) throws Exception{

        Acter acter1 =new Acter("acter1");
        Acter acter2 = new Acter("acter2");


        User user1 = new User("小A",true);
        User user2 = new User("小B",true);
        User user3 = new User("小C",true);




    }


}

/**
 * 点赞任务
 */
class ThumbsUpTask implements Runnable{


    Acter acter ;

    User user;

    Object lock ;


    private final  int TIME_OUT = 1000;

    private final int FORBIDDEN_TIME= 1000*60;

    public ThumbsUpTask(Acter acter, User user,Object lock){
        this.acter = acter;
        this.user = user;
        this.lock = lock;
    }
    @Override
    public void run() {

        long currentTime = System.currentTimeMillis();


        acter.getMap().putIfAbsent(user.getName(), new ThumbsInfo(currentTime));



        //锁定关系
        synchronized (lock) {
            // 判断禁止时间
            if (user.getNextForbiddenTime() > currentTime) {
                System.out.println("用户" + user.getName() + "点赞 " + acter.name + " 被禁止 下次点赞时间为" + user.getNextForbiddenTime() + "当前时间" + currentTime);
                return;
            }

            ThumbsInfo info = acter.getMap().get(user.getName());
            //  1s 之内 点赞次数大于10次
            if (currentTime - info.getLastThumbsTime() < TIME_OUT && info.thumbsCount.get() >= 10) {
                user.setNextForbiddenTime(currentTime + FORBIDDEN_TIME);
                System.out.println("用户" + user.getName() + "被禁止点赞" + acter.name + " 下次点赞时间为" + user.getNextForbiddenTime() + "点赞次数" + acter.thumbsCount.get());
                return;
            } else if (currentTime - info.getLastThumbsTime() < TIME_OUT) {//1s之内但是没有点赞超过10次

                if (info.thumbsCount.get() > 10) {
                    System.out.println("多种判断防止 多次点赞 thread= " + Thread.currentThread());
                    return;
                } else if (info.thumbsCount.get() == 10) {
                    System.out.println("第10次点赞 thread= " + Thread.currentThread());
                }

            } else { //超过1s 需要重新设定时间,重新设定次数,多个线程执行到这 怎么判断已经被更新过了，不会重新更新呢？


                    //已经被更新的，不能再更新了,但是会多更，时间在当前时间之前的会更新
                    if (currentTime - info.getLastThumbsTime() >= TIME_OUT) {
                        System.out.println("超时 进入更新 thread=" + Thread.currentThread() + "时间=" + currentTime);
                        info.setLastThumbsTime(currentTime);
                        info.thumbsCount = new AtomicInteger(0);
                    } else if (currentTime - info.getLastThumbsTime() < TIME_OUT) {
                        System.out.println("超时 更新失败 thread=" + Thread.currentThread() + "时间=" + currentTime);
                    }
            }
            long count = 0;
            info.thumbsCount.incrementAndGet();
            if (user.isThumbsType()) {
                count = acter.thumbsCount.decrementAndGet();
            } else {
                count = acter.thumbsCount.incrementAndGet();
            }
            System.out.println("用户" + user.getName() + "点赞" + acter.name + "成功 点赞时间为" + currentTime + " 点赞次数" + count + " thread =" + Thread.currentThread());
        }

    }
}

/**
 * 主播
 */
class Acter  {



    public ConcurrentHashMap<String, ThumbsInfo> getMap() {
        return map;
    }

    public void setMap(ConcurrentHashMap<String, ThumbsInfo> map) {
        this.map = map;
    }

    ConcurrentHashMap<String,ThumbsInfo> map = new ConcurrentHashMap();
    AtomicLong thumbsCount = new AtomicLong() ;

    String name ;

    public Acter(String name ){
        this.name = name;
    }



}

/**
 * 排序
 *
 */
class ActerCompare implements Comparator<Acter>{

    @Override
    public int compare(Acter o1, Acter o2) {
        if (o1.thumbsCount.get()>=o2.thumbsCount.get()){
            return 1;
        }
        return -1;
    }
}




/**
 * 用户
 */

class User{


    String name;

    /**
     * 下次点赞禁止时间
     */
    volatile long nextForbiddenTime;


    /**
     * True为点赞
     * False为取消点赞
     */
    boolean thumbsType ;

    public long getNextForbiddenTime() {
        return nextForbiddenTime;
    }

    public void setNextForbiddenTime(long nextForbiddenTime) {
        this.nextForbiddenTime = nextForbiddenTime;
    }



    public boolean isThumbsType() {
        return thumbsType;
    }

    public void setThumbsType(boolean thumbsType) {
        this.thumbsType = thumbsType;
    }



    public String getName() {
        return name;
    }


    public User(String name,boolean Type){
        this.name = name;
    }

}

/**
 * 点赞信息
 */
class ThumbsInfo{
    //
    volatile Long lastThumbsTime;
    //
    AtomicInteger thumbsCount = new AtomicInteger();

    public ThumbsInfo(long lastThumbsTime){
      this.lastThumbsTime = lastThumbsTime;
    }

    public long getLastThumbsTime() {
        return lastThumbsTime;
    }

    public void setLastThumbsTime(Long lastThumbsTime) {
        this.lastThumbsTime = lastThumbsTime;
    }

}

class ThumbsEngine {

    // 用户和主播之间的关系记录
    private ConcurrentHashMap<String, ConcurrentLinkedDeque<ThumbsUpTask>> taskMap = new ConcurrentHashMap<>();

    private ConcurrentLinkedQueue<Acter> acters = new ConcurrentLinkedQueue<>();

    private static  ThumbsEngine  thumbsEngine = null;

    private ExecutorService service;

    // 单例
    public static ThumbsEngine getInstance(){
        if (thumbsEngine == null){
            synchronized (ThumbsEngine.class){
                if (thumbsEngine == null){
                    return new ThumbsEngine();
                }
            }
        }
        return thumbsEngine;
    }

    private ThumbsEngine(){
            service= Executors.newFixedThreadPool(10, new ThreadFactory() {
            AtomicInteger in = new AtomicInteger();
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r,"ThumbsEngine_thread"+in.incrementAndGet());
            }
        });
            System.out.println("ThumbsEngine start");
    }


    public void addTask(Acter acter,User u){
        if (acter == null || u == null){
            return ;
        }
        String key = String.format("%s_%s",acter.name,u.name);
        if (taskMap.get(key) == null){
            ThumbsUpTask thump = new ThumbsUpTask(acter,u,key);
            ConcurrentLinkedDeque<ThumbsUpTask> task = new ConcurrentLinkedDeque<>();
            task.add(thump);
            // 如果插入时已经存在了，则返回值不为空
            ConcurrentLinkedDeque<ThumbsUpTask> result =  taskMap.putIfAbsent(key,task);
            if (result != null){
                result.add(thump);
                taskMap.put(key,result);
            }
        }else {
            ThumbsUpTask thump = new ThumbsUpTask(acter,u,key);
            ConcurrentLinkedDeque<ThumbsUpTask> result =  taskMap.get(key);
            result.add(thump);
            taskMap.put(key,result);
        }

    }

   public void start(){
       executeTask();
   }

   public void executeTask (){
        while (true){
            for (ConcurrentLinkedDeque<ThumbsUpTask> task:taskMap.values()){
                 if (!task.isEmpty()){//如果是null的话会报空指针
                     ThumbsUpTask thump = task.pollFirst();
                     if (thump != null) {
                         service.submit(thump);
                     }
                 }
                 continue;
            }

            try{
                Thread.sleep(1000);
            }catch(Exception e){
                e.printStackTrace();
            }

        }
   }
   public void  stop(){
        try{
            if (!service.isShutdown()) {
                service.shutdown();
            }
        }catch(Exception e ){
            e.printStackTrace();
        }
   }

}



