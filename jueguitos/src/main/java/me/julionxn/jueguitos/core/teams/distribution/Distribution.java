package me.julionxn.jueguitos.core.teams.distribution;

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

    public static Distribution fixed(int size){
        return new Distribution(DistributionType.FIXED, size);
    }

    public static Distribution remaining(){
        return new Distribution(DistributionType.REMAINING, -1);
    }

}
