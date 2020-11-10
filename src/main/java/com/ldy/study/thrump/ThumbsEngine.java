package com.ldy.study.thrump;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class ThumbsEngine {

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
       log.info("ThumbsEngine start");
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

    public void sort(){

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
