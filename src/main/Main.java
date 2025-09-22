package main;
import cells.Frame;
import entity.Human;
import entity.Pig;

import javax.swing.*;
import java.awt.*;
public class Main {
    public static void main(String[] args) {
        Frame frame = new Frame(48,26,16, Color.BLACK,60);

        frame.addCell("/cell/grass.png",false);
        frame.addCell("/cell/tree.png",true);
        frame.addCell("/cell/roca.png",true);

        frame.generateMap(26,16,0);
        frame.fill(0,0,25,15,1,true);
        frame.fill(18,12,21,12,2,false);
        frame.fill(1,4,7,4,1,false);
        frame.fill(4,8,4,11,2,false);
        frame.fill(21,1,21,6,1,false);
        frame.fill(17,2,18,3,2,false);
        frame.fill(7,6,7,13,1,false);
        frame.fill(20,9,24,9,1,false);
        frame.fill(13,1,13,5,1,false);
        frame.fill(8,9,15,9,1,false);

        Human human = new Human(9,10,frame);
        Human human1 = new Human(4,5,frame);
        Pig pig = new Pig(16,10,frame);
        Pig pig1 = new Pig(2,10,frame);
        Pig pig2 = new Pig(20,2,frame);

        human.setExplorationLevel(4);
        human1.setExplorationLevel(6);

        frame.addEntity(pig);
        frame.addEntity(pig1);
        frame.addEntity(pig2);
        frame.addEntity(human);
        frame.addEntity(human1);

        frame.windowSet("2D Game",JFrame.EXIT_ON_CLOSE,false);
    }
}