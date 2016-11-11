package com.android.commonframe.tools;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程工具类
 * Created by feilong.guo on 16/11/11.
 */
public class ThreadPollUtil {
    private static final int CORE_POOL_SIZE = 5;
    private static final int MAX_IMUM_POOL_SIZE = 10;
    private static final long KEEP_ALIVE_TIME = 5000;
    private static final int CAPACITY_VALUE = 10;

    private static ThreadPoolExecutor threadPoolExecutor;

    static {
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(CAPACITY_VALUE);
        ThreadPoolExecutor.DiscardPolicy discardPolicy = new ThreadPoolExecutor.DiscardPolicy();
        threadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_IMUM_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, workQueue, discardPolicy);
    }

    private ThreadPollUtil() {
    }

    public static void execPool(Runnable runnable) {
        threadPoolExecutor.execute(runnable);
    }
}
