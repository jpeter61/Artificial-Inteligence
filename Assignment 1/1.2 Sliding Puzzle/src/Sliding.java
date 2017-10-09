import java.util.LinkedList;

//Solves Sliding Puzzle
public class Sliding {

    //Inner Board class
    private class Board{
        private int[][] array;                 //Board array
        private Board parent;                   //Parent board

        //Constructor
        private Board(int[][] array, int m, int n) {
            this.array = new int[m][n];

            for (int i = 0; i < m; i++)
                for (int j = 0; j < n; j++)
                    this.array[i][j] = array[i][j];

            this.parent = null;
        }
    }

    private Board initial;
    private Board goal;
    private int m;
    private int n;

    //Constructor
    public Sliding(int[][] initial, int [][] goal, int m, int n){
        this.initial = new Board(initial,m,n);
        this.goal = new Board(goal,m,n);
        this.m = m;
        this.n = n;
    }

    //Solve puzzle
    public void solve(){
        LinkedList<Board> openList = new LinkedList<Board>();
        LinkedList<Board> closedList = new LinkedList<Board>();

        openList.addFirst(initial);                 //add initial board to open list

        while(!openList.isEmpty()){
            Board board = openList.removeFirst();   //add boards that have been looked at

            if(complete(board)){                    //If goal
                displayPath(board);                 //Display path to goal
                return;                             //Stop
            }else{
                LinkedList<Board> children = generate(board);   //Create children

                for(int i = 0; i < children.size(); i ++){
                    Board child = children.get(i);
                    if(!exists(child, openList) && !exists(child, closedList))
                        openList.addLast(child);        //Add if not in open or closed lists
                }
            }
        }
        System.out.println("No Solution");
    }


    //Creates children of a board
    private LinkedList<Board> generate(Board board){
        int i = 0;
        int j = 0;
        boolean found = false;

        for(i = 0; i < m; i ++){                    //find location of empty slot
            for(j = 0; j < n; j++)
                if(board.array[i][j] == 0){
                    found = true;
                    break;
                }
            if(found)
                break;
        }

        boolean north, south, east, west;           //decide whether empty slot
        north = i == 0 ? false : true;              // has N, S, E, W neighbors
        south = i == m - 1 ? false : true;
        east = j == n - 1 ? false : true;
        west = j == 0 ? false : true;

        LinkedList<Board> children = new LinkedList<Board>(); //list of children

        if(north) children.addLast(createChild(board, i , j, 'N'));
        if(south) children.addLast(createChild(board, i , j, 'S'));
        if(east) children.addLast(createChild(board, i , j, 'E'));
        if(west) children.addLast(createChild(board, i , j, 'W'));

        return children;
    }

    //Swaps places to create children
    private Board createChild(Board board, int i , int j, char direction){
        Board child = copy(board);

        if(direction == 'N'){
            child.array[i][j] = child.array [i-1][j];
            child.array[i-1][j] =0;
        }else if(direction == 'S'){
            child.array[i][j] = child.array [i+1][j];
            child.array[i+1][j] =0;
        }else if(direction == 'E'){
            child.array[i][j] = child.array [i][j+1];
            child.array[i][j+1] =0;
        }else{
            child.array[i][j] = child.array [i][j-1];
            child.array[i][j-1] =0;
        }

        child.parent = board;                   //assign parent
        return child;
    }

    //Creates copy of board
    private Board copy(Board board){
        return new Board(board.array, m, n);
    }

    //Decides if board is complete
    private boolean complete(Board board){
        return identical(board, goal);
    }

    //If a board exists in a list
    private boolean exists(Board board, LinkedList<Board> list){
        for (int i = 0; i < list.size(); i++)       //compare board with each element
            if(identical(board, list.get(i)))
                return true;
        return false;
    }

    //if two boards identical
    private boolean identical(Board p, Board q){
        for(int i = 0; i < m; i++)
            for(int j = 0; j < n; j++)
                if(p.array[i][j] != q.array[i][j])
                    return false;       //If there is a mismatch then false

        return true;
    }

    //Displays path form initial to current board
    private void displayPath(Board board){
        LinkedList<Board> list = new LinkedList<Board>();

        Board pointer = board;      //start at current
        while(pointer != null){
            list.addFirst(pointer);     //add to list

            pointer = pointer.parent;
        }

        for(int i = 0; i < list.size(); i++)
            displayBoard(list.get(i));
    }

    //Displays board
    private void displayBoard(Board board){
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++ )
                System.out.print(board.array[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }
}
