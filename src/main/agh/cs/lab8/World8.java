package agh.cs.lab8;


import java.io.IOException;
import java.lang.reflect.GenericArrayType;
import java.sql.SQLOutput;

public class World8 {

    public static void main(String [] args) {
/*
        GeneticCode g1=new GeneticCode();
        GeneticCode g2=new GeneticCode();

        System.out.println(g1);

        System.out.println(g2);

        try {
            Runtime.getRuntime().exec("clear");
        } catch (IOException e) {
            e.printStackTrace();
        }

        GeneticCode g3=new GeneticCode(g1,g2);
        System.out.println(g3);*/

        //MapDirection a=MapDirection.EAST;
        //System.out.println(a.rotation(1).toString());
        //public NeverEndingMap(int width, int height,int numbersOfAnimals, double junglePercent, int plantEnergy, int moveEnergy, int startEnergy)
        IWorldMap neverEndingMap=new NeverEndingMap(5,5,5,0.3, 5, 1, 10);
        System.out.println(neverEndingMap.toString());

        int days=100;


        neverEndingMap.placeGrass();
        System.out.println(neverEndingMap);

        for(int i=0; i<days; i++) {
            neverEndingMap.run();
            System.out.println(neverEndingMap);
        }

/*
        String s="";
        int index=0;
        for(int i=0; i<900; i++) {
            s = "";
            for (int x = 0; x < 10; x++) {

                s += String.valueOf(index) + " " + Character.toString((char) index) + " ";
                index++;
            }
            System.out.println(s);

        }*/
        /*
        for(int i=0; i<10; i++)
        {
            neverEndingMap.run();
            System.out.println(neverEndingMap.toString());
        }*/










    }
}
