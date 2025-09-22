package entity;
import ai.Node;
import cells.Frame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Pig extends Entity implements Peaceful{
    private boolean wanderMove = false;
    private boolean wanderState = false;
    private int count = 0;
    private int pigTimer = 0;
    private Node cords;
    private int randomtime;

    public Pig(int x, int y,Frame f) {
        super(f);
        this.x = x*frame.getCellSize();
        this.y = y*frame.getCellSize();
        this.collision = true;
        this.canBeEaten = true;
        getImage();
    }

    @Override
    public void getImage() {
        try {
            sprite = ImageIO.read(getClass().getResourceAsStream("/sprites/pigSprite.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        if(!isAlive){
            return;
        }
        if(wanderState){
            wander();
        }else{
            this.randomtime =random.nextInt(10);
            wanderState = true;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        if(!isAlive){
            return;
        }
        g2.drawImage(sprite,x,y,frame.getCellSize(),frame.getCellSize(),null);
    }

    @Override
    public void wander() {
        if (!isAlive){
            return;
        }
        if(wanderMove){
            pigTimer = 0;
            if(!this.searching){
                wanderMove = false;
                cords = randomCordsMove(2);
            }
            serchPath(cords.nodeCol,cords.nodeRow);
        }else{
            pigTimer++;
            if(pigTimer/frame.getFPS() >= randomtime){
                wanderMove = true;
            }
        }
    }
}
