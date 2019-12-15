import agh.cs.lab8.MapDirection;
import agh.cs.lab8.Vector2d;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class MapDirectionTest {

    MapDirection mde=MapDirection.EAST;
    MapDirection mdse=MapDirection.SOUTHEAST;
    MapDirection mds=MapDirection.SOUTH;
    MapDirection mdsw=MapDirection.SOUTHWEST;
    MapDirection mdw=MapDirection.WEST;
    MapDirection mdnw=MapDirection.NORTHWEST;
    MapDirection mdn=MapDirection.NORTH;
    MapDirection mdne=MapDirection.NORTHEAST;


    @Test
    public void testNext(){
        assertEquals(mde.next(),mdse);
        assertEquals(mdse.next(),mds);
        assertEquals(mds.next(),mdsw);
        assertEquals(mdsw.next(),mdw);
        assertEquals(mdw.next(),mdnw);
        assertEquals(mdnw.next(),mdn);
        assertEquals(mdn.next(),mdne);
        assertEquals(mdne.next(),mde);
    }

    @Test
    public void testPrevious(){
        assertEquals(mde.previous(),mdne);
        assertEquals(mdne.previous(),mdn);
        assertEquals(mdn.previous(),mdnw);
        assertEquals(mdnw.previous(),mdw);
        assertEquals(mdw.previous(),mdsw);
        assertEquals(mdsw.previous(),mds);
        assertEquals(mds.previous(),mdse);
        assertEquals(mdse.previous(),mde);
    }

    @Test
    public void testOpposite(){
        assertEquals(mde.opposite(),mdw);
        assertEquals(mdne.opposite(),mdsw);
        assertEquals(mdn.opposite(),mds);
        assertEquals(mdnw.opposite(),mdse);
        assertEquals(mdw.opposite(),mde);
        assertEquals(mdsw.opposite(),mdne);
        assertEquals(mds.opposite(),mdn);
        assertEquals(mdse.opposite(),mdnw);
    }


    @Test
    public void testToUnitVector(){
        assertEquals(mde.toUnitVector(),new Vector2d(1, 0));
        assertEquals(mdse.toUnitVector(),new Vector2d(1, -1));
        assertEquals(mds.toUnitVector(),new Vector2d(0, -1));
        assertEquals(mdsw.toUnitVector(),new Vector2d(-1, -1));
        assertEquals(mdw.toUnitVector(),new Vector2d(-1,0));
        assertEquals(mdnw.toUnitVector(), new Vector2d(-1,1));
        assertEquals(mdn.toUnitVector(),new Vector2d(0,1));
        assertEquals(mdne.toUnitVector(),new Vector2d(1,1));
    }

    @Test
    public void rotationTest(){
        assertEquals(mde.rotation(0), mde);
        assertEquals(mde.rotation(1), mdse);
        assertEquals(mde.rotation(2), mds);
        assertEquals(mde.rotation(3), mdsw);
        assertEquals(mde.rotation(4), mdw);
        assertEquals(mde.rotation(5), mdnw);
        assertEquals(mde.rotation(6), mdn);
        assertEquals(mde.rotation(7), mdne);



    }
}
