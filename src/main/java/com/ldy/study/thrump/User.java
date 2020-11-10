package com.ldy.study.thrump;

public class User {
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
