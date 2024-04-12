package me.julionxn.jueguitos.core.timer;

/**
 * Representación básica de un contador para un minijuego.
 */
public class BasicTimer {

    private boolean running = false;
    private long lastTimeSpan;
    private long remainingTime;

    /**
     * @param durationInSeconds Tiempo en segundos que durará el contador.
     */
    public BasicTimer(int durationInSeconds){
        lastTimeSpan = System.currentTimeMillis();
        remainingTime = durationInSeconds * 1000L;
    }

    /**
     * @return Los milisegundos restantes en el contador.
     */
    public long getRemainingMills(){
        if (!running) return remainingTime;
        return lastTimeSpan + remainingTime - System.currentTimeMillis();
    }

    /**
     * @return Los segundos restantes en el contador.
     */
    public int getRemainingSeconds(){
        return (int) (getRemainingMills() / 1000);
    }

    /**
     * @return Si el contador ha finalizado.
     */
    public boolean isDone(){
        return getRemainingMills() <= 0;
    }

    /**
     * Ejecutar/Continuar el contador.
     */
    public void run(){
        running = true;
    }

    /**
     * Detener el contador.
     */
    public void stop(){
        remainingTime = getRemainingMills();
        lastTimeSpan = System.currentTimeMillis();
        running = false;
    }

    /**
     * @return Ultimo marca de tiempo desde que se ejecutó run
     */
    public long getLastTimeSpan(){
        return lastTimeSpan;
    }

}
