package agh.cs.lab7;


import java.util.List;

public class UnboundedMap extends AbstractWorldMap {
    private List<RockStack> rockStacks;
    private MapBoundary mapBoundary= new MapBoundary();

    public UnboundedMap(List<RockStack> rs){
        this.rockStacks = rs;
        for(RockStack rockStack:rs){
            objects.put(rockStack.getPosition(),rockStack);
            mapBoundary.addPosition(rockStack.getPosition());
        }
    }

    @Override
    public boolean place(Animal animal) {
        boolean bool=super.place(animal);
        mapBoundary.addPosition(animal.getPosition());

        animal.addObserver(mapBoundary);
        return bool;
    }

    /*
    @Override
    public boolean isOccupied(Vector2d position) {
        for (RockStack rs: rockStacks){
            if(rs.getPosition().equals(position)) return true;
        }
        return super.isOccupied(position);
    }
    @Override
    public boolean canMoveTo(Vector2d position) {
        return !isOccupied(position);
    }

    @Override
    public boolean place(Animal animal) {
        if(canMoveTo(animal.getPosition())) {
            animals.add(animal);
            return true;
        }
        return false;
    }

    @Override
    public void run(MoveDirection[] directions) {
        int i=0;
        int s=animals.size();
        for(MoveDirection d:directions){
            animals.get(i).move(d);
            i++;
            i=i%s;
        }
    }

    @Override
    public Object objectAt(Vector2d position) {
        Object object = super.objectAt(position);
        for (RockStack rs:rockStacks){
            if (rs.getPosition().equals(position)) return rs;
        }
        return object;
    }*/

    @Override
    public String toString(){
        MapVisualizer visualizer= new MapVisualizer(this);
        /*Vector2d leftCorner=new Vector2d(0,0);
        Vector2d rightCorner=new Vector2d(0,0);

        for(Animal a:animals){
            leftCorner=a.getPosition().lowerLeft(leftCorner);
            rightCorner=a.getPosition().upperRight(rightCorner);
        }
        for(RockStack rs:rockStacks){
            leftCorner=rs.getPosition().lowerLeft(leftCorner);
            rightCorner=rs.getPosition().upperRight(rightCorner);
        }
        return visualizer.draw(leftCorner, rightCorner);*/

        return visualizer.draw(mapBoundary.getLowerLeft(),mapBoundary.getUpperRight());
    }

}
