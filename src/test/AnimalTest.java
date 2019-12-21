import agh.cs.lab8.*;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class AnimalTest {

    IWorldMap map =new NeverEndingMap(5,5,2,0.3, 5, 1, 10);

    @Test
    public void  animalTest(){
        Animal firstParent= new Animal(map, new Vector2d(1,1), 10, 0);
        Animal secondParent= new Animal(map, new Vector2d(1,1), 10, 1);
        Animal child = new Animal(map, new Vector2d(1,2),firstParent,secondParent,10,2);

        assertTrue(child.getEnergy()==10-firstParent.getEnergy()+10-secondParent.getEnergy());
        assertFalse(child.canReproduce());
        assertEquals(new Vector2d(1,2), child.getPosition());
    }

    @Test
    public void moveTest(){
        Animal animal = new Animal(map, new Vector2d(1,2),10,3);
        Vector2d animalPosition = animal.getPosition();
        animal.move();

        boolean flag = false;
        for( int i=0; i<8; i++){
           if(animal.getPosition().equals(map.vectorPosition(new Vector2d(1,2),MapDirection.values()[i].toUnitVector()))) flag =true;
        }
        assertTrue(flag);
    }
}
