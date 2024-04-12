package me.julionxn.jueguitosclient.core.timer;

/**
 * La representación del tiempo de un minijuego.
 */
public class BasicTimer {

    private boolean running;
    private long lastTimeSpan;
    private long remainingTime;

    public BasicTimer(long lastTimeSpan, long remainingTime){
        this.lastTimeSpan = lastTimeSpan;
        this.remainingTime = remainingTime;
    }

    /**
     * @return Los milisegundos restantes del contador.
     */
    public long getRemainingMills(){
        if (!running) return remainingTime;
        return lastTimeSpan + remainingTime - System.currentTimeMillis();
    }

    /**
     * @return Los segundos restantes del contador.
     */
    public int getRemainingSeconds(){
        return (int) (getRemainingMills() / 1000);
    }

    /**
     * @param lastTimeSpan Ultimo tiempo marcado desde el servidor como nuevo inicio.
     * @param remainingTime Tiempo restante en milisegundos del contador.
     */
    public void run(long lastTimeSpan, long remainingTime){
        this.lastTimeSpan = lastTimeSpan;
        this.remainingTime = remainingTime;
        running = true;
    }

    /**
     * @param lastTimeSpan Ultimo tiempo marcado desde el servidor como nuevo inicio.
     * @param remainingTime Tiempo restante en milisegundos del contador.
     */
    public void stop(long lastTimeSpan, long remainingTime){
        this.lastTimeSpan = lastTimeSpan;
        this.remainingTime = remainingTime;
        running = false;
    }

}
