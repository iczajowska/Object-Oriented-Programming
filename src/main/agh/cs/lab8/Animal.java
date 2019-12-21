package agh.cs.lab8;


import java.util.*;

public class Animal implements IMapElement {
    private MapDirection orientation;
    private Vector2d position ;
    private IWorldMap map;
    private GeneticCode geneticCode;
    private int energy;
    private Random generator = new Random();
    private final int minReproduceEnergy;
    private List <IPositionChangeObserver> observers = new ArrayList<>();
    private int livingLength;
    private int dayOfDeath=0;
    private int idOfAnimal;



    public Animal(IWorldMap map, Vector2d initialPosition, int startEnergy, int idOfAnimal){
        this.map=map;
        this.position=initialPosition;
        this.minReproduceEnergy = startEnergy/2;
        this.geneticCode=new GeneticCode();
        this.energy=startEnergy;
        this.orientation=MapDirection.getRandomDirection();
        this.idOfAnimal=idOfAnimal;

    }

    public Animal(IWorldMap map, Vector2d initialPosition, Animal firstParent, Animal secondParent, int startEnergy, int idOfAnimal){
        this.map=map;
        this.position=initialPosition;
        this.geneticCode=new GeneticCode(firstParent.geneticCode,secondParent.geneticCode);
        this.energy= (int) (0.25*firstParent.getEnergy())+ (int) (0.25*secondParent.getEnergy());
        this.minReproduceEnergy=startEnergy/2;
        this.orientation= MapDirection.getRandomDirection();
        this.idOfAnimal=idOfAnimal;

        firstParent.reduceEnergy((int) (0.25*firstParent.getEnergy()));
        secondParent.reduceEnergy((int) (0.25*secondParent.getEnergy()));
    }

    @Override
    public String toString(){
        return this.orientation.toString();
    }



    public void move() {
        int index=this.geneticCode.getGeneticCodeByIndex(generator.nextInt(32));
        this.orientation=this.orientation.rotation(this.geneticCode.getGeneticCodeByIndex(index));
        Vector2d toMove=this.orientation.toUnitVector();
        Vector2d newCorrectPosition = this.map.vectorPosition(this.position,toMove);
        Vector2d oldPosition=this.position;

        for (IPositionChangeObserver observer: observers){
            observer.positionChanged(oldPosition, newCorrectPosition, this);
        }

        this.position=newCorrectPosition;
        this.livingLength++;
    }

    public boolean canReproduce(){

        return this.energy>=this.minReproduceEnergy;
    }

    public Vector2d getPosition(){
        return this.position;
    }

    public void addObserver(IPositionChangeObserver observer){
        observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer){
        observers.remove(observer);
    }

    public int getEnergy(){
        return this.energy;
    }

    public void reduceEnergy(int energy){
        this.energy-=energy;
    }

    public void addEnergy(int energy){
        this.energy+=energy;
    }

    public String getGeneticCodeString(){
        return this.geneticCode.toString();
    }

    public  GeneticCode animalGeneticCode() {return this.geneticCode; }

    public int [] getGeneticCode(){
        return this.geneticCode.getGeneticCode();
    }

    public int getIdOfAnimal(){
        return this.idOfAnimal;
    }

    public void animalDeath(int dayOfDeath){
        this.dayOfDeath=dayOfDeath;
    }

    public String seeAnimalStatistic(){
        String s="";
        if(this.dayOfDeath == 0) {


            s += idOfAnimal + " animal still lives\n";
        }
        else {
            s += idOfAnimal + " animal died on " + dayOfDeath + " day\nit lived " + this.livingLength + "\n";
        }
        s+="animal position: "+this.position.toString()+"\n"+"animal direction: "+this.orientation.toString()+"\nanimal genetic code: "+this.geneticCode.toString()+"\n";

        return s;
    }
}
