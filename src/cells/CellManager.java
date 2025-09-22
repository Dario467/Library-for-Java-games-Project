package cells;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class CellManager {
    private final Frame gp;
    private final ArrayList<Cell> cell;

    private int[][] cellMap= null;
    private int mapCol;
    private int mapRow;

    CellManager(Frame gp) {
        this.gp = gp;
        this.cell = new ArrayList<>();
    }

    public void addCell1(String direction,boolean collides){
        try {
            Cell ncell = new Cell();
            ncell.collides=collides;
            cell.add(ncell);
            cell.get(cell.size()-1).image= ImageIO.read(getClass().getResourceAsStream(direction));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void generateMap1(int col, int row, int cellIndex){
        this.mapCol = col;
        this.mapRow = row;
        cellMap = new int[col][row];
        for (int i = 0;i < mapCol; i++){
            for (int j = 0;j < mapRow;j++){
                cellMap[i][j] = cellIndex;
            }
        }
    }

    public void fill1(int x1, int y1,int x2, int y2,int cellIndex,boolean hollow){
        if(cellMap == null){
            this.mapCol = x2;
            this.mapRow = y2;
            cellMap = new int[x2][y2];
        }
        if(x1 >= mapCol || x2 >= mapCol || y1 >= mapRow || y2 >= mapRow){
            System.out.println("Error you are trying to draw out of index");
            x1 = 0;
            y1 = 0;
            x2 = 0;
            y2 = 0;
        }
        for(int i = x1;i <= x2; i++){
            for(int j = y1;j <= y2; j++){
                if(hollow){
                    if(i == x1 || j == y1 || i == x2 || j == y2){
                        cellMap[i][j] = cellIndex;
                    }
                }else{
                    cellMap[i][j] = cellIndex;
                }
            }
        }
    }

    public void draw(Graphics2D g2){
        for(int col = 0; col < mapCol; col++){
            for(int row = 0; row < mapRow; row++){
                int x = col* gp.getCellSize();
                int y = row * gp.getCellSize();
                int cellNum = cellMap[col][row];
                g2.drawImage(cell.get(cellNum).image,x,y,gp.getCellSize(), gp.getCellSize(), null);
            }
        }
    }

    public Cell getCell(int index){
        return cell.get(index);
    }

    public int[][] getCellMap() {
        return cellMap;
    }
}
