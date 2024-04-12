package me.julionxn.jueguitos.core.teams.distribution;

/**
 * Representa la manera en que serán los jugadores distribuidos dentro de un equipo.
 */
public class Distribution {

    private final DistributionType distributionType;
    private final int size;

    private Distribution(DistributionType distributionType, int size){
        this.distributionType = distributionType;
        this.size = size;
    }

    public DistributionType distributionType(){
        return distributionType;
    }

    public int size(){
        return size;
    }

    /**
     * @param size El tamaño fijo del equipo.
     * @return Distribution
     */
    public static Distribution fixed(int size){
        return new Distribution(DistributionType.FIXED, size);
    }

    /**
     * Marca un tamaño variable que se ajusta de acuerdo a la cantidad de jugadores restantes.
     * @return Distribution
     */
    public static Distribution remaining(){
        return new Distribution(DistributionType.REMAINING, -1);
    }

}
