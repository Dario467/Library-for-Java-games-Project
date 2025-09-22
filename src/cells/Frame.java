package cells;
import ai.Pathfinder;
import entity.Entity;
import main.FPSController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Frame extends JPanel implements Runnable{
    //Instancias:
    private JFrame window;
    private Thread gameThread;
    private FPSController fpsController;
    private CellManager cellM = new CellManager(this);
    private final Pathfinder pathfinder;

    private ArrayList<Entity> entities=new ArrayList<>();

    //Atributos:
    private final int cellSize;
    private final int maxCellCol;
    private final int maxCellRow;
    private final int screenwidth;
    private final int screenHeight;

    private final int FPS;

    public Frame(int cellSize,int maxCellCol,int maxCellRow,Color color,int FPS) {
        this.cellSize = cellSize;
        this.maxCellCol = maxCellCol;
        this.maxCellRow = maxCellRow;
        this.screenwidth = cellSize * maxCellCol;
        this.screenHeight = cellSize * maxCellRow;

        this.setPreferredSize(new Dimension(screenwidth, screenHeight));
        this.setBackground(color);

        this.FPS = FPS;
        this.fpsController= new FPSController(FPS);

        this.pathfinder = new Pathfinder(this);

        gameThread = new Thread(this);
        gameThread.start();

    }

    public void windowSet(String title,int closeOperation, boolean resizable){
        window = new JFrame(title);
        window.setResizable(resizable);
        window.setDefaultCloseOperation(closeOperation);
        window.add(this);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    @Override
    public void run() {
        while(gameThread != null){
            if(fpsController.shouldUpdate()){
                update();
                repaint();
            }
        }
    }

    public void update(){
        for(Entity e:entities){
            e.update();
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 =(Graphics2D) g;
        cellM.draw(g2);
        for(Entity e:entities){
            e.draw(g2);
        }
        g2.dispose();
    }

    public void addCell(String direction,boolean collides){
        cellM.addCell1(direction,collides);
    }

    public void generateMap(int col, int row, int cellIndex){
        cellM.generateMap1(col,row,cellIndex);
    }

    public void fill(int x1, int y1,int x2, int y2,int cellIndex,boolean hollow){
        cellM.fill1(x1,y1,x2,y2,cellIndex,hollow);
    }

    public void addEntity(Entity entity){
        entities.add(entity);
    }

    public int getCellSize(){
        return cellSize;
    }

    public int getMaxCol(){
        return maxCellCol;
    }

    public int getMaxRow(){
        return maxCellRow;
    }

    public CellManager getCellManager(){
        return cellM;
    }

    public int getFPS() {
        return FPS;
    }

    public Pathfinder getPathfinder() {
        return pathfinder;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }
}