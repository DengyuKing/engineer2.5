package com.ldy.study.split.file;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class SplitTask extends FutureTask {

    public SplitTask(Callable callable) {
        super(callable);
    }


}
