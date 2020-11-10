package com.ldy.study.thrump;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * 点赞任务
 */
@Slf4j
public class ThumbsUpTask implements Runnable{

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
                log.info("用户" + user.getName() + "点赞 " + acter.name + " 被禁止 下次点赞时间为" + user.getNextForbiddenTime() + "当前时间" + currentTime);
                return;
            }

            ThumbsInfo info = acter.getMap().get(user.getName());
            //  1s 之内 点赞次数大于10次
            if (currentTime - info.getLastThumbsTime() < TIME_OUT && info.thumbsCount.get() >= 10) {
                user.setNextForbiddenTime(currentTime + FORBIDDEN_TIME);
                log.info("用户" + user.getName() + "被禁止点赞" + acter.name + " 下次点赞时间为" + user.getNextForbiddenTime() + "点赞次数" + acter.thumbsCount.get());
                return;
            } else if (currentTime - info.getLastThumbsTime() < TIME_OUT) {//1s之内但是没有点赞超过10次

                if (info.thumbsCount.get() > 10) {
                    log.info("多种判断防止 多次点赞 thread= " + Thread.currentThread());
                    return;
                } else if (info.thumbsCount.get() == 10) {
                    log.info("第10次点赞 thread= " + Thread.currentThread());
                }

            } else { //超过1s 需要重新设定时间,重新设定次数,多个线程执行到这 怎么判断已经被更新过了，不会重新更新呢？


                //已经被更新的，不能再更新了,但是会多更，时间在当前时间之前的会更新
                if (currentTime - info.getLastThumbsTime() >= TIME_OUT) {
                    log.info("超时 进入更新 thread=" + Thread.currentThread() + "时间=" + currentTime);
                    info.setLastThumbsTime(currentTime);
                    info.thumbsCount = new AtomicInteger(0);
                } else if (currentTime - info.getLastThumbsTime() < TIME_OUT) {
                    log.info("超时 更新失败 thread=" + Thread.currentThread() + "时间=" + currentTime);
                }
            }
            long count = 0;
            info.thumbsCount.incrementAndGet();
            if (user.isThumbsType()) {
                count = acter.thumbsCount.decrementAndGet();
            } else {
                count = acter.thumbsCount.incrementAndGet();
            }
            log.info("用户" + user.getName() + "点赞" + acter.name + "成功 点赞时间为" + currentTime + " 点赞次数" + count + " thread =" + Thread.currentThread());
        }

    }
}
