package me.julionxn.jueguitosclient.core.timer;

public class BasicTimer {

    private boolean running;
    private long lastTimeSpan;
    private long remainingTime;

    public BasicTimer(long lastTimeSpan, long remainingTime){
        this.lastTimeSpan = lastTimeSpan;
        this.remainingTime = remainingTime;
    }

    public long getRemainingMills(){
        if (!running) return remainingTime;
        return lastTimeSpan + remainingTime - System.currentTimeMillis();
    }

    public int getRemainingSeconds(){
        return (int) (getRemainingMills() / 1000);
    }

    public void run(long lastTimeSpan, long remainingTime){
        this.lastTimeSpan = lastTimeSpan;
        this.remainingTime = remainingTime;
        running = true;
    }

    public void stop(long lastTimeSpan, long remainingTime){
        this.lastTimeSpan = lastTimeSpan;
        this.remainingTime = remainingTime;
        running = false;
    }

}
