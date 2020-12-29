package com.mxy.car.util;

/**
 * 可暂停的线程
 * Created by seth.yang on 2015/6/10.
 */
public abstract class PausableThread extends Thread {
    protected final Object locker = new Object ();
    protected boolean paused = true;
    protected boolean running = false;

    /**
     * 线程逻辑，由具体实现类来决定
     */
    protected abstract void doWork ();

    /**
     * 构造函数，创建一个指定状态的可暂停线程
     */
    public PausableThread (boolean paused) {
        this.paused = paused;
        start ();
    }

    /**
     * 恢复执行线程
     */
    public void proceed () {
        synchronized (locker) {
            paused = false;
            locker.notifyAll ();
        }
    }

    /**
     * 暂停线程
     */
    public void pause () {
        paused = true;
    }

    public boolean isPaused () {
        return paused;
    }

    /**
     * 退出线程，布尔值block表示是否等待线程退出
     */
    public void shutdown (boolean block) throws InterruptedException {
        running = false;
        if (paused)
            proceed ();
        if (block && (Thread.currentThread () != this))
            this.join ();
    }

    @Override
    public synchronized void start () {
        if (!running) {
            running = true;
            super.start ();
        }
    }

    @Override
    public void run () {
        while (running) {
            while (paused) {
                synchronized (locker) {
                    try {
                        locker.wait ();
                    } catch (InterruptedException e) {
                        e.printStackTrace ();
                    }
                }
            }

            doWork ();
        }
    }
}

