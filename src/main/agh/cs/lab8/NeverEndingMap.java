package agh.cs.lab8;


import java.util.*;

import com.google.common.collect.*;

public class NeverEndingMap implements IWorldMap, IPositionChangeObserver {
    protected List<Animal> animals = new ArrayList<>();
    //protected List<Grass> grass=new ArrayList<>();
    //protected Map<Vector2d,Object> objects = new HashMap<>();
    private Vector2d lowerLeft;
    private Vector2d upperRight;
    private Vector2d jungleLowerLeft;
    private Vector2d jungleUpperRight;
    private final int startEnergy;
    private final int plantEnergy;
    private final int moveEnergy;
    private Random generator = new Random();

    private ListMultimap<Vector2d, IMapElement> mapElements =ArrayListMultimap.create();
    //TODO dodać statystykę
    private int liveAnimals=0;
    private int deadAnimals=0;
    private int plantNumber=0;
    private double avgLengthOfLife=0;
    private int totalSumOfLivingDays=0;

    public NeverEndingMap(int width, int height,int numbersOfAnimals, double junglePercent, int plantEnergy, int moveEnergy, int startEnergy){
        this.upperRight=new Vector2d(width,height);
        this.lowerLeft=new Vector2d(0,0);

        this.jungleLowerLeft=new Vector2d((width - (int) (width * junglePercent) )/ 2, (height - (int) (height * junglePercent))/ 2);
        this.jungleUpperRight=new Vector2d( this.jungleLowerLeft.getX()+(int) (width * junglePercent)  , this.jungleLowerLeft.getY()+ (int) (height*junglePercent));
        this.plantEnergy=plantEnergy;
        this.moveEnergy=moveEnergy;
        this.startEnergy=startEnergy;
        this.liveAnimals=numbersOfAnimals;

        for(int i=0; i<numbersOfAnimals; i++)
        placeFirstAnimals();
    }


    @Override
    public void run() {
        deleteDeadAnimals();
        for(Animal animal:animals){
            animal.move();
            animal.reduceEnergy(this.moveEnergy);
            //System.out.println(animal.getEnergy());
        }
        animalEatPlant();
        //animalReproduce();
        placeGrass();
    }

    @Override
    public void deleteDeadAnimals(){
        // TODO usuwanie animals
        List <Animal> deadAnimalsList=new ArrayList<>();

        for( Animal animal:this.animals){
            if(animal.getEnergy()<=0){
                deadAnimalsList.add(animal);
            }
        }

        for( Animal deadAnimal: deadAnimalsList){
            mapElements.remove(deadAnimal.getPosition(),deadAnimal);
            animals.remove(deadAnimal);
            liveAnimals--;
            deadAnimals++;
        }
    }

    @Override
    public void animalEatPlant(){

        for(Animal animalsWithGrass:animals){
            Grass grassToRemove = null;
            if(mapElements.get(animalsWithGrass.getPosition()).get(0) instanceof Grass){
                int maxEnergyOfAnimal=0;
                List <Animal> maxEnergyAnimals=new ArrayList<>();
                grassToRemove= (Grass) mapElements.get(animalsWithGrass.getPosition()).get(0);

                for( IMapElement element: mapElements.get(animalsWithGrass.getPosition())){
                    if(element instanceof Animal){
                        if( ((Animal) element).getEnergy()> maxEnergyOfAnimal){
                            maxEnergyOfAnimal=((Animal) element).getEnergy();
                            maxEnergyAnimals.clear();
                            maxEnergyAnimals.add((Animal) element);
                        }else if(((Animal) element).getEnergy()==maxEnergyOfAnimal){
                            maxEnergyAnimals.add((Animal) element);
                        }
                    }
                }

                int grassEnergy=((Grass) mapElements.get(animalsWithGrass.getPosition()).get(0)).additionalEnergy();
                grassEnergy/=maxEnergyAnimals.size();

                for(Animal maxAnimal:maxEnergyAnimals){
                    //Todo add energy to animals at the list
                    maxAnimal.addEnergy(grassEnergy);
                }
            }

            //TODO grassToRemove function
            if(grassToRemove!=null)
            removeGrass(grassToRemove.getPosition(),grassToRemove);
        }

    }

    public void removeGrass(Vector2d grassPosition, Grass grassToRemove){
        mapElements.remove(grassPosition,grassToRemove);
    }

    @Override
    public void placeFirstAnimals(){
        int x;
        int y;

        do {
            do {
                x = generator.nextInt(this.upperRight.getX()+1);
            } while (!(x >= this.lowerLeft.getX() && x <= this.upperRight.getX()));


            do {
                y = generator.nextInt(this.upperRight.getY()+1);
            } while (!(y >= this.lowerLeft.getY() && y <= this.upperRight.getY()));


        } while (isOccupied(new Vector2d(x, y)));

        Vector2d vector=new Vector2d(x,y);
        Animal firstAnimal = new Animal(this, vector, this.startEnergy);

        this.mapElements.put(vector, firstAnimal);
        this.animals.add(firstAnimal);
        firstAnimal.addObserver(this);

    }

    @Override
    public Animal getAnimalByIndex(int index) {
        return this.animals.get(index);
    }


    @Override
    public boolean canMoveTo(Vector2d position) {
        return !isOccupied(position);
    }

    @Override
    public boolean placeAnimal(Animal animal) {


        return false;
    }

    @Override
    public void placeGrass(){
        int xSavanna;
        int ySavanna;

        int countSavanna=(upperRight.getX()+1)*(upperRight.getY()+1);
        do{
            xSavanna=generator.nextInt(upperRight.getX()+1);
            ySavanna=generator.nextInt(upperRight.getY()+1);
            countSavanna--;
            if(countSavanna == 0) break;
        }while(this.insideJungle(new Vector2d(xSavanna,ySavanna))  || isOccupied(new Vector2d(xSavanna,ySavanna)));


        if (countSavanna >0) {
            Vector2d grassSavannaPosition = new Vector2d(xSavanna, ySavanna);
            Grass savannaGrass = new Grass(grassSavannaPosition, this.plantEnergy);
            this.mapElements.put(grassSavannaPosition,savannaGrass);

        }

        int yJungle;
        int xJungle;

        int countJungle=(jungleUpperRight.getX()-jungleLowerLeft.getX()+1)*(jungleUpperRight.getY()-jungleLowerLeft.getY()+1);
        do{
            xJungle=generator.nextInt(jungleUpperRight.getX()-jungleLowerLeft.getX()+1)+jungleLowerLeft.getX();
            yJungle=generator.nextInt(jungleUpperRight.getY()-jungleLowerLeft.getY()+1)+jungleUpperRight.getY();
            countJungle--;
            if(countJungle == 0) break;
        }while(!this.insideJungle(new Vector2d(xJungle,yJungle)) || isOccupied(new Vector2d(xJungle,yJungle)) );

        if(countJungle > 0) {
            Vector2d grassJunglePosition = new Vector2d(xJungle, yJungle);
            Grass jungleGrass = new Grass(grassJunglePosition, this.plantEnergy);
            this.mapElements.put(grassJunglePosition,jungleGrass);

        }
        //this.grass.add(savannaGrass);
        //this.grass.add(jungleGrass);
    }

    @Override
    public boolean insideJungle(Vector2d vector2d){
        return vector2d.follow(this.jungleLowerLeft) && vector2d.precedes(this.jungleUpperRight);
    }



    @Override
    public boolean isOccupied(Vector2d position) {
        return objectsAt(position).size()!=0;
    }
//zmienić objectsAt!!! CORRECT
    @Override
    public List<IMapElement> objectsAt(Vector2d position) {
        return mapElements.get(position);
    }

//dopisać!!!!!
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal){
        //Objects o=objects.get(oldPosition);
        //objects.remove(oldPosition);
        //objects.put(newPosition,o);

        /*List<IMapElement>elementsAtOldPosition=this.objectsAt(oldPosition);
        System.out.println(this);
        for(IMapElement element:elementsAtOldPosition){
            if(element.getPosition().equals(newPosition)){
                System.out.println(element);
                System.out.println(element.getPosition());
                //this.mapElements.remove(oldPosition,element);
                //this.mapElements.remove(oldPosition,element);
                this.mapElements.put(newPosition,element);
            }
        }*/

        this.mapElements.remove(oldPosition,animal);
        this.mapElements.put(newPosition,animal);
        //elementsAtOldPosition.remove();
    }

    @Override
    public String toString() {
        MapVisualizer visualizer = new MapVisualizer(this);
        return visualizer.draw(lowerLeft,upperRight);
    }

    @Override
    public Vector2d vectorPosition(Vector2d animalPosition, Vector2d toMove){
        int x=animalPosition.getX()+toMove.getX();
        int y=animalPosition.getY()+toMove.getY();
        if(x>this.upperRight.getX()){
            x=0;
        }
        else if(x<0) x=this.upperRight.getX();

        if(y>this.upperRight.getY()){
            y=0;
        }
        else if(y<0) y=this.upperRight.getY();

        return new Vector2d(x,y);
    }

    public String getStatistics(){
        //TODO wypisywanie statystyk o zwierzętach
        return "";
    }

    @Override
    public Vector2d getLowerLeft(){
        return this.lowerLeft;
    }

    @Override
    public Vector2d getUpperRight(){
        return this.upperRight;
    }


}
