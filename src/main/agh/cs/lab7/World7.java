package agh.cs.lab7;

import java.util.ArrayList;
import java.util.List;

public class World7 {
    public static void main(String [] args) {

        try {
            MoveDirection[] directions = new OptionParser().parse(args);
            List<RockStack> rocks=new ArrayList<>();
            rocks.add(new RockStack(new Vector2d(-4,-4)));
            rocks.add(new RockStack(new Vector2d(7,7)));
            rocks.add(new RockStack(new Vector2d(3,6)));
            rocks.add(new RockStack(new Vector2d(2,0)));
            IWorldMap map = new UnboundedMap(rocks);
            map.place(new Animal(map,new Vector2d(1,1)));
            map.place(new Animal(map));

            System.out.println(map.toString());
            map.run(directions);
            System.out.println(map.toString());
        } catch(IllegalArgumentException ex) {
            System.out.println(ex.toString());;
        }
    }


}
