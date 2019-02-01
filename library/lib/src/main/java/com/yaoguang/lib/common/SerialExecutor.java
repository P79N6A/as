package com.yaoguang.lib.common;

import android.os.SystemClock;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 文件名：
 * 描    述：
 * 作    者：韦理英
 * 时    间：2017/8/2 0002.
 * 版    权：
 */
public class SerialExecutor {
    final BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
    private Thread thread;


    /**
     * 描    述：开始队列
     * 作    者：韦理英
     * 时    间：
     * return
     *        [return说明]
     */

    public void start() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Runnable codeToRunInThisThread = queue.take();
                        codeToRunInThisThread.run();
                        SystemClock.sleep(1000);
                    }
                } catch (InterruptedException ignored) {
                }
            }
        });
        thread.start();
    }

    /**
     * 描    述：添加队列
     * 作    者：韦理英
     * 时    间：
     * @param run
     *
     */

    public void put(Runnable run) {
        if (run == null) return;
        // 添加列列
        try {
            queue.put(run);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 描    述：关闭线程
     * 作    者：韦理英
     * 时    间：
     */

    public void destoryThread() {
        if (thread != null || !thread.isInterrupted()) return;
        thread.interrupt();
        thread = null;
    }

    /**
     * 描    述：线程 不在运行
     * 作    者：韦理英
     * 时    间：
     * return true 是中断 false 不是
     */

    public boolean isInterrupted() {
        return thread == null || thread.isInterrupted();

    }

}