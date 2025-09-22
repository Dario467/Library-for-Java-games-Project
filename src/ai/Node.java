package ai;
public class Node{
    public Node parent;
    public int nodeCol;
    public int nodeRow;
    public int gCost;
    public int hCost;
    int fCost;
    boolean start;
    boolean solid;
    boolean open;
    boolean checked;

    public Node(int nodeCol, int nodeRow) {
        this.nodeCol = nodeCol;
        this.nodeRow = nodeRow;
    }
}