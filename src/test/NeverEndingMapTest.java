import agh.cs.lab8.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NeverEndingMapTest {

    public Vector2d animalPosition=new Vector2d(5,5);
    public IWorldMap map =new NeverEndingMap(5,5,1,0.3, 5, 1, 10);


    @Test
    public void vectorPositionTest(){
        map.vectorPosition(animalPosition, new Vector2d(1,1));
        Vector2d v_0_0=new Vector2d(0,0 );
        assertEquals(map.vectorPosition(animalPosition, new Vector2d(1,1)), v_0_0);
    }


    @Test
    public void placeFirstAnimalsTest(){
        map.placeFirstAnimals();
        Animal animal=map.getAnimalByIndex(0);
        assertTrue(map.isOccupied(animal.getPosition()));
    }


    @Test
    public void isOccupiedTest(){
        assertFalse(map.isOccupied(new Vector2d(1,1) ));
        assertTrue(map.isOccupied(map.getAnimalByIndex(0).getPosition()));
    }

    @Test
    public void insideJungleTest(){
        assertFalse(map.insideJungle(map.getUpperRight()));
        assertTrue(map.insideJungle(new Vector2d(2,2)));

    }

    @Test
    public void animalEatPlantTest(){
        int days=50;
        for(int i=0; i<days; i++) {
            map.run();
            System.out.println(map);
            map.objectsAt(map.getAnimalByIndex(0).getPosition());
            Grass grass=new Grass(map.getAnimalByIndex(0).getPosition(), 5);
            assertNotEquals(grass, map.objectsAt(map.getAnimalByIndex(0).getPosition()).get(0));
        }
    }
    /*
    @Test
    public void placeGrassTest(){



    }*/
}
