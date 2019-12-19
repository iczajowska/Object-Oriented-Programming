package agh.cs.lab8;


import java.util.*;
import com.google.common.collect.*;

public class NeverEndingMap implements IWorldMap, IPositionChangeObserver {

    private Statistics mapStatistics ;
    private Random generator = new Random();
    private ListMultimap<Vector2d, IMapElement> mapElements =ArrayListMultimap.create();
    protected List<Animal> animals = new ArrayList<>();
    protected List<Animal> deadAnimals = new ArrayList<>();

    private Vector2d lowerLeft;
    private Vector2d upperRight;
    private Vector2d jungleLowerLeft;
    private Vector2d jungleUpperRight;
    private final int startEnergy;
    private final int plantEnergy;
    private final int moveEnergy;


    public NeverEndingMap(int width, int height,int numbersOfAnimals, double junglePercent, int plantEnergy, int moveEnergy, int startEnergy){
        this.upperRight=new Vector2d(width,height);
        this.lowerLeft=new Vector2d(0,0);

        this.jungleLowerLeft=new Vector2d((width - (int) (width * junglePercent) )/ 2, (height - (int) (height * junglePercent))/ 2);
        this.jungleUpperRight=new Vector2d( this.jungleLowerLeft.getX()+(int) (width * junglePercent)  , this.jungleLowerLeft.getY()+ (int) (height*junglePercent));
        this.plantEnergy=plantEnergy;
        this.moveEnergy=moveEnergy;
        this.startEnergy=startEnergy;
        this.mapStatistics = new Statistics();

        if(this.jungleLowerLeft.precedes(this.lowerLeft) )
            throw new IllegalArgumentException("Wrong jungleRatio start parameter");

        if(this.jungleUpperRight.follow(this.upperRight))
            throw new IllegalArgumentException("Wrong jungleRatio start parameter");

        if(numbersOfAnimals>(width+1)*(height+1) || numbersOfAnimals<0)
            throw new IllegalArgumentException("Wrong animalNumber start parameter");

        if(plantEnergy<=0 || moveEnergy<=0 || startEnergy<=0)
            throw new IllegalArgumentException("Energy parameters must be greater than 0");

        for(int i=0; i<numbersOfAnimals; i++)
        placeFirstAnimals();

        //System.out.println(jungleLowerLeft.toString()+" "+jungleUpperRight.toString()+"\n"+lowerLeft.toString()+" "+upperRight.toString());
    }


    @Override
    public void run() {
        deleteDeadAnimals();
        this.mapStatistics.clearTotalSumOfLivingEnergy();
        for(Animal animal:animals){
            animal.move();
            animal.reduceEnergy(this.moveEnergy);
            //System.out.println(animal.getIdOfAnimal()+" "+animal.getEnergy()+" "+animal.getGeneticCodeString());
            this.mapStatistics.addToTotalSumOfLivingDays();
            this.mapStatistics.addToTotalSumOfLivingEnergy(animal);
        }
        animalEatPlant();
        animalReproduce();
        placeGrass();
    }

    public void animalReproduce(){

        List <Vector2d> positionsManyAnimals=new ArrayList<>();
        for(Animal animalToReproduce:this.animals) {
            if(objectsAt(animalToReproduce.getPosition()).size()>=2){
                if(!positionsManyAnimals.contains(animalToReproduce.getPosition())){
                    positionsManyAnimals.add(animalToReproduce.getPosition());      //list of positions with many animals on it without repeat
                }
            }
        }

        if(positionsManyAnimals.size()!=0) {
            for (Vector2d vector2d : positionsManyAnimals) {
                //System.out.println(vector2d);
                List <IMapElement> animalsAtTheSamePosition=objectsAt(vector2d);    //list of animals at the same position
                Animal firstAnimal=null;
                int firstMaxEnergy=0;

                Animal secondAnimal=null;
                int secondMaxEnergy=0;

                for(IMapElement animal:animalsAtTheSamePosition){
                    if(animal instanceof Animal){
                        if(((Animal) animal).canReproduce()){    //check if the animal has enough energy to reproduce

                            if(((Animal) animal).getEnergy()>firstMaxEnergy){
                                secondMaxEnergy=firstMaxEnergy;
                                firstMaxEnergy=((Animal) animal).getEnergy();
                                secondAnimal=firstAnimal;
                                firstAnimal= (Animal) animal;
                            }else if(((Animal) animal).getEnergy()>secondMaxEnergy){
                                secondMaxEnergy=((Animal) animal).getEnergy();
                                secondAnimal=(Animal) animal;
                            }

                        }
                    }
                }

                if(firstAnimal!=null && secondAnimal!=null){ //first and second animal with enough energy to reproduce
                    //System.out.println(firstAnimal.toString()+firstAnimal.getEnergy()+" "+secondAnimal.toString()+secondAnimal.getEnergy());

                    Vector2d childPosition=childPosition(firstAnimal);
                    //System.out.println(childPosition);

                    Animal child=new Animal(this,childPosition,firstAnimal,secondAnimal,this.startEnergy,this.mapStatistics.getTotalNumberOfAnimals());
                    placeAnimal(child);
                }

            }
        }
    }

    @Override
    public boolean placeAnimal(Animal animal) {

        this.mapElements.put(animal.getPosition(), animal);
        this.animals.add(animal);
        animal.addObserver(this);
        this.mapStatistics.addLivingAnimal();
        this.mapStatistics.addToPopularGenes(animal);

        return true;
    }


    @Override
    public void deleteDeadAnimals(){
        List <Animal> deadAnimalsList=new ArrayList<>();

        for( Animal animal:this.animals){
            if(animal.getEnergy()<=0){
                deadAnimalsList.add(animal);
            }
        }

        for( Animal deadAnimal: deadAnimalsList){

            mapElements.remove(deadAnimal.getPosition(),deadAnimal);
            animals.remove(deadAnimal);
            deadAnimals.add(deadAnimal);
            this.mapStatistics.removeAnimalFromLivingAnimals();
            deadAnimal.animalDeath(this.mapStatistics.getDay());
        }
    }

    @Override
    public void animalEatPlant(){

        for(Animal animalsWithGrass:animals){
            Grass grassToRemove = null;
            if(mapElements.get(animalsWithGrass.getPosition()).get(0) instanceof Grass){   //if the first element on the position is grass
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
                    maxAnimal.addEnergy(grassEnergy);
                }
            }

            if(grassToRemove!=null)
            removeGrass(grassToRemove.getPosition(),grassToRemove);
        }

    }

    public void removeGrass(Vector2d grassPosition, Grass grassToRemove){
        this.mapElements.remove(grassPosition,grassToRemove);
        this.mapStatistics.removePlant();
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
            this.mapStatistics.addPlant();
        }

        int yJungle;
        int xJungle;

        int countJungle=(jungleUpperRight.getX()-jungleLowerLeft.getX()+1)*(jungleUpperRight.getY()-jungleLowerLeft.getY()+1);
        do{
            xJungle=generator.nextInt(jungleUpperRight.getX()-jungleLowerLeft.getX()+1)+jungleLowerLeft.getX();
            yJungle=generator.nextInt(jungleUpperRight.getY()-jungleLowerLeft.getY()+1)+jungleLowerLeft.getY();
            countJungle--;
            if(countJungle == 0) break;
        }while(!this.insideJungle(new Vector2d(xJungle,yJungle)) || isOccupied(new Vector2d(xJungle,yJungle)) );

        if(countJungle > 0) {
            Vector2d grassJunglePosition = new Vector2d(xJungle, yJungle);
            Grass jungleGrass = new Grass(grassJunglePosition, this.plantEnergy);
            this.mapElements.put(grassJunglePosition,jungleGrass);
            this.mapStatistics.addPlant();
        }

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
        Animal firstAnimal = new Animal(this, vector, this.startEnergy,this.mapStatistics.getTotalNumberOfAnimals());

        this.mapElements.put(vector, firstAnimal);
        this.animals.add(firstAnimal);
        firstAnimal.addObserver(this);
        this.mapStatistics.addLivingAnimal();

        //todo genetic statistics
        this.mapStatistics.addToPopularGenes(firstAnimal);
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
    public boolean insideJungle(Vector2d vector2d){
        return vector2d.follow(this.jungleLowerLeft) && vector2d.precedes(this.jungleUpperRight);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectsAt(position).size()!=0;
    }

    @Override
    public List<IMapElement> objectsAt(Vector2d position) {
        return mapElements.get(position);
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal){
        this.mapElements.remove(oldPosition,animal);
        this.mapElements.put(newPosition,animal);
    }

    public boolean areAnimalsAlive(){
        return this.animals.size()>0;
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

    public Vector2d childPosition(Animal firstParent){

        List <Vector2d> notOccupiedPositions=new Vector<>();
        for(int i=0; i<8; i++){
            Vector2d correctPosition=vectorPosition(firstParent.getPosition(), Objects.requireNonNull(MapDirection.values()[i].toUnitVector()));
            if(!isOccupied(correctPosition)){
                notOccupiedPositions.add(correctPosition);
            }
        }

        if(notOccupiedPositions.size()!=0){                             //child init position if not occupied position exists
            int index=generator.nextInt(notOccupiedPositions.size());
            return notOccupiedPositions.get(index);
        } else {                                                        //child init position is random from positions next to the parent
            return vectorPosition(firstParent.getPosition(), Objects.requireNonNull(MapDirection.values()[generator.nextInt(8)].toUnitVector()));
        }
    }


    @Override
    public Vector2d getLowerLeft(){
        return this.lowerLeft;
    }

    @Override
    public Vector2d getUpperRight(){
        return this.upperRight;
    }


    public String getStatistics(){
        return this.mapStatistics.toString();
    }

    public String getTotalStatistics() {return this.mapStatistics.getTotalStatistics();}


    @Override
    public String toString() {
        MapVisualizer visualizer = new MapVisualizer(this);
        return this.mapStatistics.toString()+"\n"+visualizer.draw(lowerLeft,upperRight);
    }

    public int getTotalNumberOfAnimals(){
        return deadAnimals.size()+animals.size();
    }

    public String getAnimalStatistic(int id){
        Animal findAnimal=null;
        for(Animal animal:animals){
            if(animal.getIdOfAnimal()==id)  {
                findAnimal=animal;
                return findAnimal.seeAnimalStatistic();
            }
        }

        for(Animal deadAnimal:deadAnimals){
            if(deadAnimal.getIdOfAnimal()==id)  {
                findAnimal=deadAnimal;
                return findAnimal.seeAnimalStatistic();
            }
        }

        return "Animal was not found";
    }
}
