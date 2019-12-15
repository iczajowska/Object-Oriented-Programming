package agh.cs.lab7;

public class RectangularMap extends AbstractWorldMap {
    private int width;
    private int height;

    public RectangularMap(int width, int height){
        this.width=width;
        this.height=height;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.precedes(new Vector2d(this.width, this.height)) && position.follow(new Vector2d(0, 0)) && !super.isOccupied(position);
    }

    @Override
    public String toString(){
        MapVisualizer visualizer= new MapVisualizer(this);
        return visualizer.draw(new Vector2d(0,0), new Vector2d(width,height));
    }
}