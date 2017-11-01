import java.util.LinkedList;

public class ShortestPath {
    //Node Class
    private class Node{
        private int id;     //Identifier
        private double gvalue;
        private double hvalue;
        private double fvalue;

        private Node parent;    //Parent Node
        //Constructor
        private Node(int id){
            this.id = id;
            gvalue = 0;
            hvalue = 0;
            fvalue = gvalue + hvalue;
            this.parent = null;
        }
    }

    private double[][] matrix;  //adjacency matrix of graph
    private double[][] coordinates; //coordinates of graph
    private int size;           //Number of Vertices
    private Node initial;       //Starting Node
    private Node goal;          //Goal Node

    //Constructor
    public ShortestPath (int vertices,double[][] coordinates, int[][] edges, int initial, int goal){
        this.size = vertices;
        this.coordinates = new double[size][size];
        //copy coordinates
        for (int i = 0; i < size; i++){
            this.coordinates[i][0] = coordinates[i][0];
            this.coordinates[i][1] = coordinates[i][1];
        }

        //Initialize adjacency matrix
        matrix = new double[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                matrix[i][j] = 0.0;

        //Calculate and place weights
        for(int i = 0; i < edges.length; i++){
            int u = edges[i][0];
            int v = edges[i][1];
                                                    //x then y
            matrix[u][v] = matrix[v][u] = distance(coordinates[u][0], coordinates[u][1],
                    coordinates[v][0], coordinates[v][1]);
        }
        //Save initial and goal
        this.initial = new Node(initial);
        this.goal = new Node(goal);

        //update initial h and f value
        this.initial.hvalue = heuristic(this.initial);
        //gvalue(g == 0) plus hvalue
        this.initial.fvalue = this.initial.hvalue;
    }

    //Uses the distance formula to find direct distance between 2 nodes
    private double distance(double x1, double y1, double x2,  double y2){
        double xValue = Math.pow((x2 - x1),2);
        double yValue = Math.pow((y2 - y1),2);

        return Math.sqrt((xValue+yValue));
    }

    //Finds the shortest Path
    public void solve(){
        //Open list + closed list
        LinkedList<Node> openList = new LinkedList<>();
        LinkedList<Node> closedList = new LinkedList<>();

        openList.addFirst(initial); //Add initial node

        while(!openList.isEmpty()){
            int best = selectBest(openList);    //Select Best Node
            Node node = openList.remove(best);  //remove from open list
            closedList.addLast(node);           //add to closed list
            //if node is goal
            if(complete(node)){
                displayPath(node);
                return;
            }
            else{
                //create children
                LinkedList<Node> children = generate(node);
                //for each child
                for (int i = 0; i < children.size(); i++){
                    Node child = children.get(i);
                    //if not in closed list
                    if(!exists(child, closedList)){
                        //if not in open list add to it
                        if(!exists(child, openList))
                            openList.addLast(child);
                        else{
                            int index = find(child, openList);
                            //compare f values
                            if(child.fvalue < openList.get(index).fvalue){
                                //replace old value with new value
                                openList.remove(index);
                                openList.addLast(child);
                            }
                        }
                    }
                }
            }
        }
        //if no solution
        System.out.println("No Solution");
    }

    //Creates children of a node
    private LinkedList<Node> generate(Node node){
        LinkedList<Node> children = new LinkedList<>();

        for (int i = 0; i < size; i++)
            if(matrix[node.id][i] != 0.0 ){
                Node child = new Node(i);       //create neighbor
                //parent cost + edge
                child.gvalue = node.gvalue + matrix[node.id][i];
                //heuristic value
                child.hvalue = heuristic(child);
                //gvalue plus hvalue
                child.fvalue = child.gvalue + child.hvalue;
                //assign parent to child
                child.parent = node;
                //add children to list
                children.addLast(child);
            }
            return children;
    }

    //computes heuristic value of node
    private double heuristic(Node node){
        //Get node coordinates
        int id = node.id;
        double nodeX = coordinates[id][0];
        double nodeY = coordinates[id][1];
        //Get goal coordinates
        int goalID = goal.id;
        double goalX = coordinates[goalID][0];
        double goalY = coordinates[goalID][1];
        //Return the distance
        return distance(nodeX, nodeY, goalX, goalY);
    }

    //Locates the node with minimum fvalue in a list of nodes
    private int selectBest(LinkedList<Node> list){
        //initialize minimum
        double minValue = list.get(0).fvalue;
        int minIndex = 0;
        //updates minimum if node has a smaller value
        for (int i = 0; i < list.size(); i++){
            double value = list.get(i).fvalue;
            if (value < minValue){
                minIndex = i;
                minValue = value;
            }
        }
        return minIndex;    //return minimum location
    }

    //checks if node is goal
    private boolean complete(Node node){
        return identical(node, goal);
    }

    //checks if a node is in a list
    private boolean exists(Node node, LinkedList<Node> list){
        for(int i = 0; i  < list.size(); i++)
            if(identical(node, list.get(i)))
                return true;
        return false;
    }

    //finds location of node in a list
    private int find(Node node, LinkedList<Node> list){
        for(int i = 0; i  < list.size(); i++)
            if(identical(node, list.get(i)))
                return i;
        return -1;
    }

    //checks if nodes are identical
    private boolean identical(Node one, Node two){
        return one.id == two.id;
    }

    //displays path from initial to current node
    private void displayPath(Node node){
        LinkedList<Node> list = new LinkedList<>();
        //Start at current node
        Node pointer = node;
        //Loops back
        while(pointer != null){
            list.addFirst(pointer);
            pointer = pointer.parent;
        }
        //Print nodes in list
        for (int i = 0; i < list.size(); i++)
            displayNode(list.get(i));

        System.out.println("\nPath Cost: " + list.getLast().gvalue);
    }

    //displays node
    private void displayNode(Node node){
        System.out.print(node.id+1 + " ");
    }

}
