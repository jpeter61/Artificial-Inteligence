import java.util.LinkedList;

public class SlidingFH {
    //Board Inner class
    private class Board{
        private int[][] array;     //board array
        private int gvalue;         //path cost
        private int hvalue;         //heuristic value
        private int fvalue;         //math
        private Board parent;       //parent board

        //Constructor
        private Board(int[][] array, int size){
            //Copy array
            this.array = new int[size][size];
            for (int i = 0; i < size; i++)
                for (int j = 0; j < size; j++)
                    this.array[i][j] = array[i][j];

            this.gvalue = 0;        //path cost is 0
            //Calculate hvalue
            this.hvalue = heuristic(this);
            //Change for different runs
            //this.fvalue = hvalue;
            //this.fvalue = gvalue;
            this.fvalue = gvalue + hvalue;
            this.parent = null;     //No parent
        }
    }

    private Board initial;          //initial board
    private Board goal;             //goal board
    private int size;               //board size

    //Constructor
    public SlidingFH(int[][] initial, int[][] goal, int size){
        this.initial = new Board(initial, size);
        this.goal = new Board(goal, size);
        this.size = size;
    }

    //Solver
    public void solve(){
        LinkedList<Board> openList = new LinkedList<>();
        LinkedList<Board> closedList = new LinkedList<>();

        openList.addFirst(initial);     //add initial board
        while (!openList.isEmpty()){
            int best = selectBest(openList);    //Select best board

            Board board = openList.remove(best);//Remove

            closedList.addLast(board);          //add  to closed list

            if (complete(board)){
                displayPath(board);             //Display path
                return;                         //end search
            }else{
                //create children
                LinkedList<Board> children = generate(board);
                for(int i = 0; i <children.size(); i ++){
                    Board child = children.get(i);
                    //if not in closed list
                    if(!exists(child, closedList)){
                        //if not in open list
                        if(!exists(child, openList))
                            openList.addLast(child);
                        else{
                            int index = find(child, openList);
                            //if fvalue is less than an old fvalue
                            if(child.fvalue < openList.get(index).fvalue){
                                openList.remove(index);
                                openList.addLast(child);
                                //Replace with new copy
                            }
                        }
                    }
                }
            }
        }
        System.out.println("No Solution"); //if no solution
    }

    //Creates children
    private LinkedList<Board> generate(Board board){
        int i = 0, j = 0;
        boolean found = false;
        //find location of empty spot
        for (i = 0; i < size; i ++){
            for(j = 0; j < size; j ++)
                if(board.array[i][j] == 0){
                    found = true;
                    break;
                }
            if(found)
                break;
        }
        //find neighbors
        boolean north, south, east, west;
        north = i == 0 ? false : true;
        south = i == size - 1 ? false : true;
        east = j == size - 1 ? false : true;
        west = j == 0 ? false : true;

        //Create children

        LinkedList<Board> children = new LinkedList<>();

        if(north) children.addLast(createChild(board, i , j, 'N'));
        if(south) children.addLast(createChild(board, i , j, 'S'));
        if(east) children.addLast(createChild(board, i , j, 'E'));
        if(west) children.addLast(createChild(board, i , j, 'W'));

        return children;
    }

    //Create child
    private Board createChild(Board board,int i ,int j,char direction){
        Board child = copy(board);      //copy board
        //North
        if(direction == 'N'){
            child.array[i][j] = child.array[i-1][j];
            child.array[i-1][j] = 0;
        }
        //south
        else if(direction == 'S'){
            child.array[i][j] = child.array[i+1][j];
            child.array[i+1][j] = 0;
        }
        //East
        else if(direction == 'E'){
            child.array[i][j] = child.array[i][j+1];
            child.array[i][j+1] = 0;
        }
        //West
        else {
            child.array[i][j] = child.array[i][j-1];
            child.array[i][j-1] = 0;
        }

        child.gvalue = board.gvalue + 1;    //path cost + 1
        child.hvalue = heuristic(child);     //calculate heuristic
        //Change for different runs
        //child.fvalue = child.hvalue;
        //child.fvalue = child.gvalue;
        child.fvalue = child.gvalue + child.hvalue;

        child.parent = board;               //assign parent

        return child;
    }

    //computes heuristic value
    private int heuristic(Board board){
        int value = 0;          //initial value

        //count mismatched values
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if(board.array[i][j] != goal.array[i][j])
                    value++;

        return  value;
    }

    //locates the board with the minimum f value
    private int selectBest(LinkedList<Board> list){
        int minValue = list.get(0).fvalue;      //start minimum
        int minIndex = 0;
        //Find minimum
        for (int i = 0; i < list.size(); i++){
            int value = list.get(i).fvalue;
            if(value < minValue){
                minValue = value;
                minIndex = i;
            }
        }
        return minIndex;
    }

    //Creates copy of board
    private Board copy(Board board){
        return new Board(board.array, size);
    }

    //Sees if board is complete
    private boolean complete(Board board){
        return identical(board, goal);
    }

    //sees if board exists in list
    private boolean exists(Board board, LinkedList<Board> list){
        for (int i = 0; i < list.size(); i ++)
            if(identical(board, list.get(i)))
                return true;
        //else
        return false;
    }

    //finds location in list
    private int find(Board board, LinkedList<Board> list){
        for (int i = 0; i < list.size(); i ++)
            if(identical(board, list.get(i)))
                return i;
        //else
        return -1;
    }

    //Find if boards are identical
    private boolean identical(Board p, Board q){
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (p.array[i][j] != q.array[i][j])
                    return false; //if mismatch
        //else
        return true;
    }

    //displays path
    private void displayPath(Board board){
        LinkedList<Board> list = new LinkedList<>();

        Board pointer = board;      //start at current

        while (pointer != null){
            list.addFirst(pointer); //add to begining of list
            pointer = pointer.parent; //keep going back
        }
        //print boards in list
        int i;
        for(i = 0; i < list.size(); i++)
            displayBoard(list.get(i));
        //output of steps
        System.out.println("\nSteps: " + i);
    }

    //Displays board
    private void displayBoard(Board board){
        for (int i  = 0; i < size; i++){
            for (int j = 0; j < size; j ++)
                System.out.print(board.array[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }
}
