import agh.cs.lab8.Vector2d;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Vector2dTest {
    private Vector2d v_1_1 = new Vector2d(1,1);
    private Vector2d v_2_3= new Vector2d(2,3);
    private Vector2d v_1_4= new Vector2d(1,4);

    @Test
    public void testEquals(){
        assertTrue(v_1_1.equals(new Vector2d(1,1)));
        assertFalse(v_1_1.equals(new Vector2d(1,3)));
    }

    @Test
    public void testToString(){
        assertEquals("(1,1)",v_1_1.toString());
    }

    @Test
    public void testPrecedes(){
        assertFalse(v_2_3.precedes(v_1_1));
        assertTrue(v_1_1.precedes(v_2_3));
    }

    @Test
    public void testFollow(){
        assertTrue(v_2_3.follow(v_1_1));
        assertFalse(v_1_1.follow(v_2_3));
    }

    @Test
    public void testUpperRight(){
        assertEquals(new Vector2d(2,4),v_1_4.upperRight(v_2_3));
    }

    @Test
    public void testLowerLeft(){
        assertEquals(new Vector2d(1,3),v_1_4.lowerLeft(v_2_3));
    }

    @Test
    public void testAdd(){
        assertEquals(new Vector2d(3,7),v_1_4.add(v_2_3));
    }

    @Test
    public void testSubtract(){
        assertEquals(new Vector2d(1,-1),v_2_3.subtract(v_1_4));
    }

    @Test
    public void testOpposite(){
        assertEquals(new Vector2d(-1,-1), v_1_1.opposite());
    }
}
