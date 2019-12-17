package agh.cs.lab7;


import java.util.ArrayList;
import java.util.List;

public class Animal {
    private MapDirection orientation= MapDirection.NORTH;
    private Vector2d position = new Vector2d(2,2);
    private IWorldMap map;
    private List <IPositionChangeObserver> observers = new ArrayList<>();



    public Animal(IWorldMap map, Vector2d initialPosition){
        this.map=map;
        this.position=initialPosition;
    }

    public Animal(IWorldMap map){
        this.map=map;
    }

    @Override
    public String toString(){
        switch (this.orientation){
            case SOUTH:
                return "S";
            case NORTH:
                return "N";
            case WEST:
                return "W";
            case EAST:
                return "E";
            default:
                return "";
        }
    }

    public void move(MoveDirection direction){
        switch (direction){
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
        }
    }

    public Vector2d getPosition(){
        return this.position;
    }

    public void addObserver(IPositionChangeObserver observer){
        observers.add(observer);
    }

    void removeObserver(IPositionChangeObserver observer){
        observers.remove(observer);
    }

}
