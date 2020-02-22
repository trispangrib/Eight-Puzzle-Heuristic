package ai.game.puzzle.core;
import java.util.ArrayList;

public class Demo 
{
    public static void main(String[] args) 
    {
        GenerationBFS g;
        String goal = "0,1,2,3,4,5,6,7,8";
        String current = "1,2,5,3,4,8,6,7,0";
        g = new GenerationBFS(goal, current);
        g.createChildren();
    }
}