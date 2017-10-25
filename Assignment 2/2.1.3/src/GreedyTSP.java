import java.util.LinkedList;

//Solves the travelling salesman problem
public class GreedyTSP {
    //path class
    private class Path{
        private LinkedList<Integer> list;               //Vertices in path
        private int cost;                               //Cost of path

        //Constructor
        private Path(){
            list = new LinkedList<>();                  //Empty list of vertices
            cost = 0;                                   //Cost is 0
        }

        //Copy constructor
        private Path(Path other){
            list = new LinkedList<>();                  //Empty list of vertices

            for(int i = 0; i < other.list.size(); i++)
                list.addLast(other.list.get(i));        //Copy to list

            cost = other.cost;                          //Copy cost
        }

        //Method finds last vertex of path
        private void add(int vertex, int weight){
            list.addLast(vertex);                       //add vertex at the end
            cost += weight;                             //increment cost
        }

        //Finds last vertex
        private int last(){
            return list.getLast();
        }

        //returns cost
        private int cost(){
            return cost;
        }

        //returns length
        private int size(){
            return list.size();
        }

        //Decide whether path contains a given vertex
        private boolean contains(int vertex){
            for(int i = 0; i < list.size(); i++)        //compare vertex with Vertices of path
                if(list.get(i) == vertex)
                    return true;

            return false;
        }

        //displays path and cost
        private void display(){
            System.out.println("Cycle: ");
            for(int i = 0; i < list.size(); i++)        //print path
                System.out.print(list.get(i)+1 + " " );
            System.out.println("\n");
            System.out.println("Miles: " + cost + "\n");            //cost
        }
    }

    private int size;                                   //Number of vertices of graph
    private int[][] matrix;                             //adjacency matrix of graph
    private int initial;                                //starting/ending vertex

    //Constructor
    public GreedyTSP(int vertices, int[][] edges){
        size = vertices;                                //assign vertices

        matrix = new int[size][size];                   //initialize adjacency matrix
        for(int i = 0; i < size; i++)
            for(int j = 0; j < size; j++)
                matrix[i][j] = 0;

        for(int i = 0; i < edges.length; i++){
            int u = edges[i][0];                        //place weights
            int v = edges[i][1];
            int weight = edges[i][2];
            matrix[u][v] = weight;
            matrix[v][u] = weight;
        }

        initial = edges[0][0];                          //Pick a origin
    }

    //Finds shortest cycle
    public void solve(){
        long startTime = System.nanoTime();             //Start Timer
        Path shortestPath = null;                       //initialize shortest
        int minimumCost = Integer.MAX_VALUE;            //minimum cost

        LinkedList<Path> list = new LinkedList<>();     //list of paths

        Path path = new Path();                         //Create initial path and add to list
        path.add(initial,0);
        list.addFirst(path);

        //While list has paths
        while (!list.isEmpty()){
            path  = list.removeFirst();                 //Remove first path

            if(complete(path)){                         //if Path has a cycle
                if(path.cost() < minimumCost){
                    minimumCost = path.cost();          //update
                    shortestPath =  path;
                }
            }
            else{
                //generate children of path
                LinkedList<Path> children = generate(path);

                if (children != null){
                    int best = selectBest(children); //find best
                    list.addFirst(children.get(best));
                }
            }
        }
        if(shortestPath == null)                        //if no cycle
            System.out.println("No Solution");          //no solution
        else
            shortestPath.display();
        long endTime = System.nanoTime();
        System.out.println("Time: " + (endTime - startTime)/1000000 + " milliseconds");
    }

    //Generates Children
    private LinkedList<Path> generate(Path path){
        LinkedList<Path> children = new LinkedList<>();
        int lastVertex = path.last();

        for(int i = 0; i < size; i++){          //Iterate
            if(matrix[lastVertex][i] != 0){     //if vertex is neighbor
                if(i == initial){
                    if(path.size() == size){    //if path has size vertices
                        Path child = new Path(path);
                        child.add(i, matrix[lastVertex][i]);
                        //add vertex to path
                        children.addLast(child);//add extend path to child list
                    }
                }
                else{                           //If vertex is not initial
                    if(!path.contains(i)) {      //if vertex is not in path
                        Path child = new Path(path);
                        child.add(i, matrix[lastVertex][i]);
                        children.addLast(child);
                    }
                }
            }
        }
        return children;
    }

    //Locates the pat with minimum cost in a list of paths
    private int selectBest(LinkedList<Path> list){
        int minValue = list.get(0).cost;    //initialize minimum
        int minIndex = 0;                   //value and location
        for(int i = 0; i < list.size(); i++){
            int value = list.get(i).cost;
            //updates minimums if board with smaller value is found
            if (value < minValue){
                minValue = value;
                minIndex = i;
            }
        }
        return minIndex;
    }

    //decides if path is complete
    boolean complete(Path path){
        return path.size() == size + 1; //check path has size + 1
    }
}
