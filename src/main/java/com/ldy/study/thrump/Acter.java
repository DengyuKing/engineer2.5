package com.ldy.study.thrump;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;


/**
 * 主播
 */
public class Acter {

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
