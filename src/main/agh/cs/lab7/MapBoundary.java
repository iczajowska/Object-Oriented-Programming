package agh.cs.lab7;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Comparator;

public class MapBoundary implements IPositionChangeObserver {
    Comparator<Vector2d> xComparator = Comparator.comparing(Vector2d::getX);
    Comparator<Vector2d> yComparator = Comparator.comparing(Vector2d::getY);
    private SortedSet<Vector2d> sortX=new TreeSet<>(xComparator);
    private SortedSet<Vector2d> sortY=new TreeSet<>(yComparator);

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        this.sortX.remove(oldPosition);
        this.sortX.add(newPosition);

        this.sortY.remove(oldPosition);
        this.sortY.add(newPosition);
    }

    public Vector2d getUpperRight(){
        return new Vector2d(this.sortX.last().x,this.sortY.last().y);
    }

    public Vector2d getLowerLeft(){
        return new Vector2d(this.sortX.first().x, this.sortY.first().y);
    }

    public void addPosition(Vector2d position){
        this.sortY.add(position);
        this.sortX.add(position);
    }
}
