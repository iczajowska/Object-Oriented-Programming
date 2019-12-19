package agh.cs.lab8;

import java.text.DecimalFormat;

public class TotalStatistics {
    private int liveAnimals;
    private int deadAnimals;
    private int plantNumber;
    private double avgLengthOfLife;
    private int totalSumOfDescendants; //TODO add to parent number of decendants + add
    private double avgEnergy;
    //most popular gen
    private static DecimalFormat decimalFormat = new DecimalFormat("0.00");
    private int day;
    private String geneticCode;


    public TotalStatistics (){
        this.liveAnimals=0;
        this.deadAnimals=0;
        this.plantNumber=0;
        this.avgLengthOfLife=0;
        this.avgEnergy=0;
        this.totalSumOfDescendants=0;
        this.day=0;
    }

    public void addDayToTotalStatistics(int liveAnimals, int deadAnimals, int plantNumber, double avgLengthOfLife, double avgEnergy, int totalSumOfDescendants, int day, String geneticCode){
        this.liveAnimals+=liveAnimals;
        this.deadAnimals+=deadAnimals;
        this.plantNumber+=plantNumber;
        this.avgLengthOfLife+=avgLengthOfLife;
        this.avgEnergy+=avgEnergy;
        this.totalSumOfDescendants+=totalSumOfDescendants;
        this.day=day;

        if(!geneticCode.equals(this.geneticCode)){
            this.geneticCode=geneticCode;
        }
    }

    @Override
    public String toString(){
        String s="Total statistics after "+day+" days:\n";
        s+="Average number of living animals: "+decimalFormat.format((double) liveAnimals/day)+"\n";
        s+="Average length of life: "+ decimalFormat.format(avgLengthOfLife/day) +"\n";
        s+="Number of plants: "+decimalFormat.format((double) plantNumber/day)+"\n";
        s+="Average energy of living animals: "+ decimalFormat.format(avgEnergy/day)+"\n";
        s+="Most popular genes of all animals: "+geneticCode;


        return s;
    }

}
