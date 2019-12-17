package agh.cs.lab8;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;


public class World8 {

    public static void main(String [] args) {


        JSONParser jsonParser = new JSONParser();
        String dir = System.getProperty("user.dir");

        try (FileReader reader = new FileReader(dir+"/parameters.json")) {
            Object obj = jsonParser.parse(reader);
            JSONObject jsonObject = (JSONObject) obj;
            int mapWidth = ((Long) jsonObject.get("width")).intValue();
            int mapHeight = ((Long) jsonObject.get("height")).intValue();
            int startEnergy = ((Long) jsonObject.get("startEnergy")).intValue();
            int moveEnergy = ((Long) jsonObject.get("moveEnergy")).intValue();
            int plantEnergy = ((Long) jsonObject.get("plantEnergy")).intValue();
            double jungleRatio = (Double) jsonObject.get("jungleRatio");
            int animalNumber = ((Long) jsonObject.get("animalNumber")).intValue();

            IWorldMap neverEndingMap=new NeverEndingMap(mapWidth,mapHeight,animalNumber,jungleRatio, plantEnergy, moveEnergy, startEnergy);
            System.out.println(neverEndingMap.toString());

            PrintWriter zapis = new PrintWriter(dir+"/results_by_era.txt");
            int era=100;

            for(int i=0; i<era; i++) {
                neverEndingMap.run();
                System.out.println(neverEndingMap);
                //System.out.print("\033[H\033[2J"); czyszczenie ekranu
                zapis.println("Era number "+i+" statistics:\n"+((NeverEndingMap) neverEndingMap).getStatistics()+"\n");
            }
            zapis.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ParseException pe) {
            pe.printStackTrace();
        }catch(IllegalArgumentException ex){
            System.out.println(ex.toString());;
        }

    }
}
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