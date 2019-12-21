package agh.cs.lab8;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Statistics {
    private int liveAnimals;
    private int deadAnimals;
    private int plantNumber;
    private double avgLengthOfLife;
    private int totalSumOfLivingDays;
    private int totalSumOfLivingEnergy;
    private int totalSumOfDescendants; //TODO add to parent number of decendants + add
    private double avgEnergy;
    private Map<GeneticCode,Integer> geneticCodeIntegerMap = new HashMap<>();
    private int maxNumberOfTheSameGeneticCode=0;
    private String geneticCode;

    private static DecimalFormat decimalFormat = new DecimalFormat("0.00");
    private int day;
    private TotalStatistics totalStatistics;


    public Statistics(){
        this.liveAnimals=0;
        this.deadAnimals=0;
        this.plantNumber=0;
        this.avgLengthOfLife=0;
        this.totalSumOfLivingDays=0;
        this.totalSumOfLivingEnergy=0;
        this.avgEnergy=0;
        this.totalSumOfDescendants=0;
        this.day=0;

        totalStatistics = new TotalStatistics();

    }

    public void addToPopularGenes(Animal animal){
        GeneticCode animalGeneticCode=animal.animalGeneticCode();

        if(this.geneticCodeIntegerMap.containsKey(animalGeneticCode)){
            int i = this.geneticCodeIntegerMap.get(animalGeneticCode);
            int x=i+1;
            this.geneticCodeIntegerMap.replace(animalGeneticCode, i , x);
            System.out.println(animalGeneticCode.toString()+" "+x);
        }
        else this.geneticCodeIntegerMap.put(animal.animalGeneticCode(), 1);

        if(this.geneticCodeIntegerMap.get(animalGeneticCode)>maxNumberOfTheSameGeneticCode){
            maxNumberOfTheSameGeneticCode=this.geneticCodeIntegerMap.get(animalGeneticCode);
            this.geneticCode=animalGeneticCode.toString();
        }
    }

    public String toString(){
        this.day++;

        String s="Statistics day "+day+":\n";
        avgLengthOfLife=(double) totalSumOfLivingDays/(liveAnimals+deadAnimals);
        s+="Number of living animals: "+liveAnimals+"\n";
        s+="Average length of life: "+ decimalFormat.format(avgLengthOfLife) +"\n";
        s+="Number of plants: "+plantNumber+"\n";
        avgEnergy=(double)totalSumOfLivingEnergy/liveAnimals;
        s+="Average energy of living animals: "+ decimalFormat.format(avgEnergy)+"\n";
        s+="Most popular genes of all animals: "+geneticCode;
        s+="\n";

        totalStatistics.addDayToTotalStatistics(this.liveAnimals,this.deadAnimals,this.plantNumber,this.avgLengthOfLife,this.avgEnergy,this.totalSumOfDescendants,this.day, this.geneticCode);
        return s;
    }

    public String getTotalStatistics(){
        return this.totalStatistics.toString();
    }

    public void addLivingAnimal(){
        this.liveAnimals++;
    }

    public void removeAnimalFromLivingAnimals(){
        this.liveAnimals--;
        this.deadAnimals++;
    }

    public void addPlant(){
        this.plantNumber++;
    }

    public void removePlant(){
        this.plantNumber--;
    }

    public void addToTotalSumOfLivingDays(){
        this.totalSumOfLivingDays++;
    }

    public void clearTotalSumOfLivingEnergy(){
        this.totalSumOfLivingEnergy=0;
    }

    public void addToTotalSumOfLivingEnergy(Animal animal){
        this.totalSumOfLivingEnergy+=animal.getEnergy();
    }

    public int getTotalNumberOfAnimals(){
        return this.deadAnimals+this.liveAnimals;
    }

    public int getDay(){
        return this.day;
    }


}
