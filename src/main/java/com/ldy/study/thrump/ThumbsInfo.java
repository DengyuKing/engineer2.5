package com.ldy.study.thrump;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 点赞信息
 */
public class ThumbsInfo {
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
