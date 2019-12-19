package agh.cs.lab8;

public class Grass implements IMapElement {
    private final Vector2d position;
    private final int plantEnergy;

    public Grass(Vector2d vector, int plantEnergy){
        this.position=vector;
        this.plantEnergy=plantEnergy;
    }

    @Override
    public Vector2d getPosition() {
        return this.position;
    }

    @Override
    public String toString(){
        return Character.toString((char) 10048)+ " ";
    }

    public int additionalEnergy(){
        return this.plantEnergy;
    }

}
