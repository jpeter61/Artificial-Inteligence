import java.util.LinkedList;
import java.util.Scanner;

public class XAndO {
    // Static values
    private final char PLAYER_PIECE = 'O';
    private final char COMPUTER_PIECE = 'X';
    private final int MIN = 0;
    private final int MAX = 1;
    private final int LIMIT = 6; //Depth limit

    // board class
    private class Board {
        private char[][] array;

        // Constructor
        private Board(int size) {
            array = new char[size][size];
            for (int r = 0; r < size; r++)
                for (int c = 0; c < size; c++)
                    array[r][c] = ' ';
        }

    }

    // private variables passed
    private Board board;
    private int size;

    // constructor
    public XAndO(int size) {
        this.board = new Board(size);
        this.size = size;
    }

    // method that lets the user and computer play
    public void play() {
        displayBoard();
        while (true) {
            // gets the player's move
            board = playerMove();
            displayBoard();
            // checks if board is complete
            if (completed()) {
                break;
            }
            // gets the computer's move
            board = computerMove();
            displayBoard();
            // checks if board is complete
            if (completed()) {
                break;
            }
            printScores();
        }
        printResults(); // show the results
    }

    //compute scores
    private int[] computeScores(){
        int computerScore = 0; // keeps track of the computer score
        int playerScore = 0; // keeps track f player score
        for (int r = 0; r < size; r++)
            for (int c = 0; c < size; c++) {
                if (board.array[r][c] == COMPUTER_PIECE) {// adds the points for twos and
                    computerScore += checkTwos(r, c, COMPUTER_PIECE); // threes at
                    computerScore += checkThrees(r, c, COMPUTER_PIECE);// location
                }
                if (board.array[r][c] == PLAYER_PIECE) {
                    playerScore += checkTwos(r, c, PLAYER_PIECE);
                    playerScore += checkThrees(r, c, PLAYER_PIECE);
                }
            }
        int[] scores = new int[2];
        scores[0] = computerScore;
        scores[1] = playerScore;
        return scores;
    }

    //Print scores
    private void printScores(){
        int[] scores = computeScores();
        int computerScore = scores[0];
        int playerScore = scores[1];
        System.out.println("Player's score: " + playerScore);
        System.out.println("Computer's score: " + computerScore);
    }

    //prints results of game
    private void printResults() {
        int[] scores = computeScores();
        int computerScore = scores[0];
        int playerScore = scores[1];
        // checks who has the higher score and declares a winner
        if (computerScore > playerScore)
            System.out.println("Computer Wins");
        else if (computerScore < playerScore)
            System.out.println("Player Wins");
        else
            System.out.println("Draw :/");
        // shows the scores
        printScores();
    }

    // checks if the board is complete
    private boolean completed() {
        boolean isComplete = true;
        for (int r = 0; r < size; r++)
            for (int c = 0; c < size; c++) {
                if (board.array[r][c] == ' ') {
                    isComplete = false; // if there is an empty space
                    break;// it is not complete
                }
            }
        return isComplete; // return value
    }

    // gets the player move
    private Board playerMove() {
        System.out.print("Player Move: ");
        Scanner userIn = new Scanner(System.in); // keyboard scanner
        int r = userIn.nextInt();
        int c = userIn.nextInt();
        // place piece at location
        board.array[r][c] = PLAYER_PIECE;
        return board;
    }

    // displays the board
    private void displayBoard() {
        // horizontal lines
        for (int i = 0; i < size; i++)
            System.out.print("--");
        System.out.println(); // space
        // shows whats inside the array
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                System.out.print("|");
                System.out.print(board.array[r][c]);
            }
            System.out.println("|");
            for (int i = 0; i < size; i++)
                System.out.print("--");// more horizontal lines
            System.out.println(); // spacing
        }

    }

    // gets the computer's move
    private Board computerMove() {
        System.out.println("Computer move: ");
        // creates children
        LinkedList<Board> children = generate(COMPUTER_PIECE);

        int maxIndex = 0;
        int maxValue = minmax(children.get(0), MIN, 1, Integer.MIN_VALUE,
                Integer.MAX_VALUE);
        // goes through children and uses alpha beta pruning to reduce
        for (int i = 1; i < children.size(); i++) { // compute times
            int currentValue = minmax(children.get(i), MIN, 1,
                    Integer.MIN_VALUE, Integer.MAX_VALUE);
            if (currentValue > maxValue) {
                maxIndex = i;
                maxValue = currentValue;
            }
        }
        // prints and returns the result
        Board result = children.get(maxIndex);
        return result;
    }

    // returns the min or max value of the board
    private int minmax(Board curr, int level, int depth, int alpha, int beta) {
        if (depth >= LIMIT || completed()) // checks if the board is
            return evaluateBoard();// complete or at LIMIT, returns score
        else if (level == MAX) {// checks if looking for max Value
            int maxValue = Integer.MIN_VALUE;

            LinkedList<Board> children = generate(COMPUTER_PIECE);
            // goes through children and returns those who have high values
            for (int i = 0; i < children.size(); i++) {
                int currentValue = minmax(children.get(i), MIN, depth + 1,
                        alpha, beta);
                if (currentValue > maxValue)
                    maxValue = currentValue;
                if (maxValue >= beta)
                    return maxValue;
                if (maxValue > alpha)
                    alpha = maxValue;
            }
            return maxValue;
        } else {
            int minValue = Integer.MAX_VALUE;
            LinkedList<Board> children = generate(PLAYER_PIECE);
            // goes through children and finds those who have low values
            for (int i = 0; i < children.size(); i++) {
                int currentValue = minmax(children.get(i), MAX, depth + 1,
                        alpha, beta);
                if (currentValue < minValue)// if currentValue us lower than
                    minValue = currentValue; // min, min is equal to current
                if (minValue <= alpha) // if the minValue is lower than or
                    return minValue;// equal to alpha return minValue
                if (minValue < beta)//if beta is greater than the
                    beta = minValue; //set beta equal to minvalue
            }
            return minValue;
        }
    }

    // evaluates the score based on the computer's interpretation of good/bad
    private int evaluateBoard() {
        int returnValue = 0;
        for (int r = 0; r < size; r++)
            for (int c = 0; c < size; c++) {
                if (board.array[r][c] == COMPUTER_PIECE) { // adds score of Computer
                    returnValue += checkTwos(r, c, COMPUTER_PIECE);// twos or
                    returnValue += checkThrees(r, c, COMPUTER_PIECE);// threes
                }
                if (board.array[r][c] == PLAYER_PIECE) {// removes scores that Player has
                    returnValue -= checkTwos(r, c, PLAYER_PIECE);// twos or
                    returnValue -= checkThrees(r, c, PLAYER_PIECE);// threes
                }
            }
        return returnValue; // returns the score evaluation
    }

    //check for 3 sets
    private int checkThrees(int r, int c, char piece) {
        double returnValue = 0;
        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++) {
                // if there is the same piece at r+i and c+j,
                if (i + r >= 0 && i + r < size && j + c >= 0 && j + c < size
                        && (i != 0 || j != 0) && 1 == Math.abs(i + j)
                        && board.array[r + i][c + j] == piece) {
                    // and there is a piece that extends it by one
                    if ((r + i * 2 < size && r + i * 2 >= 0 && j * 2 + c >= 0
                            && j * 2 + c < size
                            && board.array[r + i * 2][c + j * 2] == piece)) {
                        returnValue++;// there is a three, add points
                    }
                    // or there is one on the opposite side of the piece
                    if (r + i * -1 < size && r + i * -1 >= 0 && j * -1 + c >= 0
                            && j * -1 + c < size
                            && board.array[i * -1 + r][j * -1 + c] == piece) {
                        returnValue += 0.5; // that is counted twice, add 1/2

                    }
                }
            }
        return (int) returnValue; // return the return value
    }

    //Check 2 sets
    private int checkTwos(int r, int c, char piece) {
        int returnValue = 0;
        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++) {
                if (i + r >= 0 && i + r < size && j + c >= 0 && j + c < size
                        && (i != 0 || j != 0)) {
                    if (1 == Math.abs(i + j)
                            && board.array[r + i][c + j] == piece) {
                        returnValue++;
                    }
                }
            }
        return returnValue;
    }

    //generate children
    private LinkedList<Board> generate(char piece) {
        LinkedList<Board> children = new LinkedList();

        for (int r = 0; r < size; r++)
            for (int c = 0; c < size; c++)
                if (board.array[r][c] == ' ') {
                    Board child = copy();
                    child.array[r][c] = piece;
                    children.addLast(child);
                }

        return children;
    }

    //copy board
    private Board copy() {
        Board returnBoard = new Board(size);
        for (int r = 0; r < size; r++)
            for (int c = 0; c < size; c++)
                returnBoard.array[r][c] = board.array[r][c];
        return returnBoard;
    }
}
