package agh.cs.lab7;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    protected List<Animal> animals = new ArrayList<>();
    protected Map<Vector2d,Object> objects = new HashMap<>();

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position)!=null;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !isOccupied(position);
    }

    @Override
    public boolean place(Animal animal) {
        if(canMoveTo(animal.getPosition())){
            animals.add(animal);
            objects.put(animal.getPosition(),animal);

            animal.addObserver(this);
            return true;
        }
        throw new IllegalArgumentException("Position "+animal.getPosition().toString()+" is already occupied");
    }

    @Override
    public void run(MoveDirection[] directions) {
        int i=0;
        int s=animals.size();
        for(MoveDirection d:directions){
            //Vector2d prevLocation=animals.get(i).getPosition();
            //Animal a=animals.get(i);
            animals.get(i).move(d);
            //objects.remove(prevLocation);
            //objects.put(a.getPosition(),a);
            i++;
            i=i%s;
        }
    }

    @Override
    public Object objectAt(Vector2d position) {
        return objects.get(position);
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        Object o=objects.get(oldPosition);
        objects.remove(oldPosition);
        objects.put(newPosition,o);
    }

}