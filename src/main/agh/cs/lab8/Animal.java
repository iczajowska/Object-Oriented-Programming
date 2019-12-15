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
//TODO długość życia mysia pysia
    private int livingLength =0;


    public Animal(IWorldMap map, Vector2d initialPosition, int startEnergy){
        this.map=map;
        this.position=initialPosition;
        this.minReproduceEnergy = startEnergy/2;
        this.geneticCode=new GeneticCode();
        this.energy=startEnergy;
        this.orientation=MapDirection.getRandomDirection();
    }
/*
    public Animal(IWorldMap map, int startEnergy){
        this.geneticCode=new GeneticCode();
        this.map=map;
        this.energy=startEnergy;
        this.minReproduceEnergy=startEnergy/2;
        this.orientation=MapDirection.getRandomDirection();
    }
*/

    public Animal(IWorldMap map, Vector2d initialPosition, Animal firstParent, Animal secondParent, int startEnergy){
        this.map=map;
        this.position=initialPosition;
        this.geneticCode=new GeneticCode(firstParent.geneticCode,secondParent.geneticCode);
        this.energy= (int) (0.25*firstParent.getEnergy())+ (int) (0.25*secondParent.getEnergy());
        this.minReproduceEnergy=startEnergy/2;
        this.orientation= MapDirection.getRandomDirection();

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

        //System.out.println(this.geneticCode.getGeneticCodeByIndex(index));
        //System.out.println(toMove.toString());
        //System.out.println(oldPosition);
        //System.out.println(newCorrectPosition);


        for (IPositionChangeObserver observer: observers){
            observer.positionChanged(oldPosition, newCorrectPosition, this);
        }

        this.position=newCorrectPosition;
        this.livingLength++;
    }

    public int lengthOfLiving(){
        return this.livingLength;
    }

        /*switch (direction){
            case RIGHT:
                this.orientation=this.orientation.next();
                break;
            case LEFT:
                this.orientation=this.orientation.previous();
                break;
            case FORWARD:
                Vector2d v=this.orientation.toUnitVector();
                v=this.position.add(v);

                if (map.canMoveTo(this.position.add(v))){
                    for(IPositionChangeObserver o:observers){
                        o.positionChanged(this.position,v);

                    }

                    this.position=v;
                }
                break;
            case BACKWARD:
                Vector2d u=this.orientation.toUnitVector().opposite();
                u=this.position.add(u);
                if (map.canMoveTo(u)){
                    for(IPositionChangeObserver o:observers){
                        o.positionChanged(this.position,u);

                    }

                    this.position=u;
                }
                break;
        }*/
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

    public int [] getGeneticCode(){
        return this.geneticCode.getGeneticCode();
    }
}
