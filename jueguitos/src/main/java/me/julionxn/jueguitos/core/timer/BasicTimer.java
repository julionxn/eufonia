package me.julionxn.jueguitos.core.timer;

public class BasicTimer {

    private boolean running = false;
    private long lastTimeSpan;
    private long remainingTime;

    public BasicTimer(int durationInSeconds){
        lastTimeSpan = System.currentTimeMillis();
        remainingTime = durationInSeconds * 1000L;
    }

    public long getRemainingMills(){
        if (!running) return remainingTime;
        return lastTimeSpan + remainingTime - System.currentTimeMillis();
    }

    public int getRemainingSeconds(){
        return (int) (getRemainingMills() / 1000);
    }

    public void run(){
        running = true;
    }

    public void stop(){
        remainingTime = getRemainingMills();
        lastTimeSpan = System.currentTimeMillis();
        running = false;
    }

    public long getLastTimeSpan(){
        return lastTimeSpan;
    }

}
