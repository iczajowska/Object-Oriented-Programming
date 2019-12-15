import agh.cs.lab8.GeneticCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GeneticCodeTest {

    public static final int genesNum = 8;
    GeneticCode g1=new GeneticCode();
    GeneticCode g2=new GeneticCode();

    public static final int dnaNum=32;

        //System.out.println(g1);

        //System.out.println(g2);

    GeneticCode g3=new GeneticCode(g1,g2);
        //System.out.println(g3);
    @Test
    public void CheckGenesTest(){
        int [] tab1 = new int [genesNum];
        int [] tab2 = new int [genesNum];
        int [] tab3 = new int [genesNum];
        for(int i=0; i<genesNum; i++){
            tab1[i]=0;
            tab2[i]=0;
            tab3[i]=0;
        }

        for(int i=0; i<dnaNum; i++){
            tab1[g1.getGeneticCode()[i]]++;
            tab2[g2.getGeneticCode()[i]]++;
            tab3[g3.getGeneticCode()[i]]++;
        }

        for(int i=0; i<genesNum; i++){
            assertTrue(tab1[i]>0);
            assertTrue(tab2[i]>0);
            assertTrue(tab2[i]>0);
        }

    }
}

