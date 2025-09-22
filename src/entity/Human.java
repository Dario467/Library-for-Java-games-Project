package entity;
import ai.Node;
import cells.Frame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;


public class Human extends Entity implements Peaceful{
    private Node rNode;
    private int stopTimer = 0;
    private boolean wanderState = true;
    private int explorationLevel;
    private int count= 0;
    private int firstX = -1;
    private int firstY = -1;
    private boolean hunger = false;
    private int hungerBar = 7;
    private Entity en;
    int num = 0;
    int timer;

    public Human(int x, int y, Frame frame){
        super(frame);
        this.x = x*48;
        this.y = y*48;
        this.explorationLevel= 2;
        this.collision = true;
        this.canBeEaten = false;
        getImage();
    }
    @Override
    public void getImage() {
        try {
            sprite = ImageIO.read(getClass().getResourceAsStream("/sprites/humanSprite.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        if(step >= 500){
            hungerBar--;
            step = 0;
            System.out.println("hungerBar: "+hungerBar);
        }
        if(hungerBar <= 5){
            if(num== 0){
              searching = false;
              num++;
            }
            hunger = true;
        }
        if(hunger){
            System.out.println("Hunger");
            searchForFood();
        }else if(wanderState){
            timer++;
            if(timer/60 >= 20){
                explore();
            }else {
                wander();
            }
        }else{
            num = 0;
            count = 0;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(sprite,x,y,frame.getCellSize(),frame.getCellSize(),null);
    }

    public void setExplorationLevel(int level){
        this.explorationLevel = level;
    }
    public void addExplorationLevel(int addLevel){
        this.explorationLevel += addLevel;
    }

    public void explore(){
        if(count <=explorationLevel){
            if(!this.searching){
                count++;
                rNode= randomCordsMove(explorationLevel);
            }
            step++;
            serchPath(rNode.nodeCol,rNode.nodeRow);
        }else{
            stopTimer++;
            if(stopTimer/frame.getFPS() >= 30/explorationLevel){
                stopTimer = 0;
                count=0;
            }
        }
    }
    @Override
    public void wander() {
        if(firstX == -1 && firstY == -1){
            firstX = this.x/48;
            firstY = this.y/48;
        }
        if(count < 2){
            stopTimer--;
            if(stopTimer/frame.getFPS() <= 0){
                if(!this.searching){
                    count++;
                    rNode= randomCordsMove(2);
                }
                step++;
                serchPath(rNode.nodeCol,rNode.nodeRow);
            }
        }else {
            stopTimer++;
            if(stopTimer/frame.getFPS() >= 5){
                if(!this.searching){
                    count=0;
                }
                step++;
                serchPath(firstX,firstY);
            }
        }
    }

    private void searchForFood(){
        if(!this.searching){
            en = getFoodCords();
        }
        if(en != null){
            serchPath(en.x/ frame.getCellSize(),en.y/frame.getCellSize());
            if(this.x == en.x && this.y == en.y){
                en.death();
                hungerBar = 10;
                hunger = false;
                searching = false;
            }
            if(!en.isAlive){
                en = null;
                searching = false;
            }
        }
    }
}
