package ai;
import cells.Frame;
import java.util.ArrayList;

public class Pathfinder {
    private final Frame frame;
    private Node[][] node;
    private Node startNode, goalNode, currentNode;
    private final ArrayList<Node> openList = new ArrayList<>();
    private ArrayList<Node> pathList = new ArrayList<>();
    private boolean goalReached = false;
    private int step=0;

    public Pathfinder(Frame frame) {
        this.frame = frame;
        instanNodes();
    }

    public void instanNodes(){
        node = new Node[frame.getMaxCol()][frame.getMaxRow()];
        for(int row=0;row< frame.getMaxRow();row++) {
            for(int col=0;col< frame.getMaxCol() ;col++) {
                node[col][row] = new Node(col,row);
            }
        }
    }

    public void resetNodes() {
        for(int row=0;row< frame.getMaxRow();row++) {
            for(int col=0;col< frame.getMaxCol();col++) {
                node[col][row].open = false;
                node[col][row].checked = false;
                node[col][row].solid = false;
            }
        }
        openList.clear();
        pathList.clear();
        goalReached = false;
        step=0;
    }

    public void setNodes(int startCol, int startRow, int goalCol, int goalRow) {
        resetNodes();

        startNode = node[startCol][startRow];
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];
        openList.add(currentNode);

        for(int row=0;row< frame.getMaxRow();row++) {
            for(int col=0;col< frame.getMaxCol();col++) {
                //si el collider de la celda en esa posicion es verdadero
                int tileNum = frame.getCellManager().getCellMap()[col][row];
                if(frame.getCellManager().getCell(tileNum).collides){
                    node[col][row].solid = true;
                }
                //agregar costo a cada nodo
                getCost(node[col][row]);
            }
        }
    }

    public void getCost(Node node) {
        int XDistance = Math.abs(node.nodeCol - startNode.nodeCol);
        int YDistance = Math.abs(node.nodeRow - startNode.nodeRow);
        node.gCost= XDistance + YDistance;
        //H
        XDistance = Math.abs(node.nodeCol - goalNode.nodeCol);
        YDistance = Math.abs(node.nodeRow - goalNode.nodeRow);
        node.hCost= XDistance + YDistance;
        //f
        node.fCost = node.gCost + node.hCost;
    }

    public boolean search(){
        while (!goalReached && step<500) {
            int col = currentNode.nodeCol;
            int row = currentNode.nodeRow;

            if(currentNode == goalNode) {
                break;
            }
            currentNode.checked = true;
            openList.remove(currentNode);

            //abrir hacia las 4 direcciones del nodo actual
            if(row-1 >= 0){
                openNode(node[col][row-1]);
            }
            if(col-1 >=0){
                openNode(node[col-1][row]);
            }
            if(row+1 < frame.getMaxRow()){
                openNode(node[col][row+1]);
            }
            if(col+1 < frame.getMaxCol()){
                openNode(node[col+1][row]);
            }

            int bestNodeIndex = 0;
            int bestNodefCost = 999;

            for (int i = 0; i < openList.size(); i++) {
                Node n = openList.get(i);
                if (n.fCost < bestNodefCost || (n.fCost == bestNodefCost && n.gCost < openList.get(bestNodeIndex).gCost)) {
                    bestNodeIndex = i;
                    bestNodefCost = n.fCost;
                }
            }
            //si ya no quedan nodos abiertos
            if(openList.isEmpty()){
                break;
            }
            //para el siguiente loop el nodo actual sera ahora el mejor nodo de todos los nodos abiertos
            currentNode = openList.get(bestNodeIndex);
            //checar si ya llegamos a la meta
            if(currentNode == goalNode){
                goalReached = true;
                trackPath();
            }
            step++;
        }
        return goalReached;
    }

    private void openNode(Node node) {
        //si no el nodo entrante no a sido abierto, checado y no es solido/colider
        if(node.open == false && node.checked == false && node.solid == false){
            //dibujamos el nodo como abierto
            node.open = true;
            node.parent = currentNode;
            //lo agregamos a la lista de los nodos abiertos
            openList.add(node);
        }
    }

    private  void  trackPath(){
        Node current = goalNode;
        while (current != startNode) {
            //el primero agregado sera el goal el ultimo sera el start asi que remplazamos el indice 0 hasta que quede el primero
            pathList.add(0,current);

            //obtenemos el padre para saber el nodo validado anterior a el
            current = current.parent;
        }
    }

    public ArrayList<Node> getOpenList(){
        return openList;
    }

    public ArrayList<Node> getPathList() {
        return pathList;
    }
}
