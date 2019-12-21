package agh.cs.lab8;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


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

            NeverEndingMap firstNeverEndingMap = new NeverEndingMap(mapWidth,mapHeight,animalNumber,jungleRatio, plantEnergy, moveEnergy, startEnergy);
            NeverEndingMap secondNeverEndingMap =  new NeverEndingMap(mapWidth,mapHeight,animalNumber,jungleRatio, plantEnergy, moveEnergy, startEnergy);



            Visualization visualization = new Visualization(firstNeverEndingMap,secondNeverEndingMap);
            visualization.startAnimation();

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
