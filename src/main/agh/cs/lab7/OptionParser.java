package agh.cs.lab7;

import java.util.Arrays;

public class OptionParser {

    public MoveDirection[] parse(String []args){
        MoveDirection[]md=new MoveDirection[args.length];
        int i=0;
        for(String a : args){
            switch (a){
                case "f":
                case "forward":
                    md[i]= MoveDirection.FORWARD;
                    i++;
                    break;
                case "r":
                case "right":
                    md[i]= MoveDirection.RIGHT;
                    i++;
                    break;
                case "l":
                case "left":
                    md[i]= MoveDirection.LEFT;
                    i++;
                    break;
                case "b":
                case "backward":
                    md[i]= MoveDirection.BACKWARD;
                    i++;
                    break;
                default:
                    throw new IllegalArgumentException( a +" argument is invalid function parse");


            }
        }
        return Arrays.copyOfRange(md, 0, i);
    }
}
