package entity;
import ai.Node;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import cells.Frame;


public abstract class Entity {
    protected int x,y;
    protected int speed = 1;
    protected BufferedImage sprite;
    protected boolean collision = false;
    protected boolean searching = false;
    protected int step = 0;
    protected boolean canBeEaten;
    protected boolean isAlive = true;

    private boolean up ,right = false;
    private final ArrayList<Node> allowCords = new ArrayList<>();

    protected Frame frame;
    protected Random random = new Random();

    public Entity(Frame f) {
        this.frame = f;
    }

    public abstract void getImage();
    public abstract void update();
    public abstract void draw(Graphics2D g2);

    public void serchPath(int goalx, int goaly) {
        int startx = this.x/frame.getCellSize();
        int starty = this.y/frame.getCellSize();

        if(startx == goalx && starty == goaly) {
            searching = false;
        }else {
            searching = true;
        }
        if(frame.getPathfinder().getOpenList().isEmpty()){
            searching = false;
        }

        if(right){
            startx++;
        }if (up){
            starty++;
        }
        right = false;
        up = false;

        frame.getPathfinder().setNodes(startx,starty, goalx, goaly);

        if(frame.getPathfinder().search()){
            int xmove = frame.getPathfinder().getPathList().get(0).nodeCol * frame.getCellSize();
            int ymove = frame.getPathfinder().getPathList().get(0).nodeRow * frame.getCellSize();

            if( this.x>xmove && (this.x-1)/ frame.getCellSize() < startx){
                right = true;
            }if (this.y>ymove && this.y/ frame.getCellSize()<starty){
                up = true;
            }
            if(this.y > ymove){
                y-=speed;
            }else if(this.y < ymove){
                y+=speed;
            }else{
                if(this.x > xmove){
                    x-=speed;
                }
                if(this.x < xmove){
                    x+=speed;
                }
            }
        }
    }

    public Node randomCordsMove(int radius){
        int xCorner = (this.x/frame.getCellSize())-radius;
        int yCorner = (this.y/frame.getCellSize())-radius;

        testforNoSolids(xCorner,yCorner,this.allowCords,radius);

        int randomIndex = random.nextInt(allowCords.size());
        return allowCords.get(randomIndex);
    }

    private void testforNoSolids(int xCorner, int yCorner, ArrayList<Node> array, int radius){
        array.clear();
        for(int row=yCorner; row <= (yCorner+(2*radius)); row++){
            for (int col=xCorner; col <= (xCorner+(2*radius)); col++){
                if(row >= 0 && col >= 0 && row < frame.getMaxRow() && col < frame.getMaxCol()){
                    int tileNum = frame.getCellManager().getCellMap()[col][row];
                    if(!frame.getCellManager().getCell(tileNum).collides && col != xCorner+radius && row != yCorner+radius){
                        array.add(new Node(col,row));
                    }
                }
            }
        }
    }

    protected Entity getFoodCords(){
        for(Entity e: frame.getEntities()){
            if(e.canBeEaten && e.isAlive){
                return e;
            }
        }
        return null;
    }

    protected void death(){
        isAlive = false;
        System.out.println("death");
    }
}
