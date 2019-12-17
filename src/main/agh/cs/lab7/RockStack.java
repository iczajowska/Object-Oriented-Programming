package agh.cs.lab7;

public class RockStack {
    private Vector2d position;

    public RockStack(Vector2d position){
        this.position=position;
    }

    public Vector2d getPosition(){
        return this.position;
    }

    @Override
    public String toString(){
        return "rs";
    }
}
