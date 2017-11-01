import java.util.LinkedList;
import java.util.Random;

public class MiniChess {
    private final int MIN = 0;
    private final int MAX = 1;
    private final int LIMIT = 6;
    private final boolean PLAYER = true;
    private final boolean Computer = false;


    private class Board{
        private ChessPiece[][] board;

        //Pieces value
        private static final int KING_VALUE = 100;
        private static final int ROOK_VALUE = 5;
        private static final int BISHOP_VALUE = 5;


        //Constructor
        public Board(){
            //Random Generator
            Random generator = new Random();

            this.board = new ChessPiece[6][6];

            //Create Computer Pieces
            LinkedList<ChessPiece> computerPieces = new LinkedList<>();
            computerPieces.addLast(new ChessPiece(KING_VALUE, false, 'K', 'a'));
            computerPieces.addLast(new ChessPiece(ROOK_VALUE, false, 'R', 'c'));
            computerPieces.addLast(new ChessPiece(ROOK_VALUE, false, 'R', 'c'));
            computerPieces.addLast(new ChessPiece(BISHOP_VALUE, false, 'B', 'd'));
            computerPieces.addLast(new ChessPiece(BISHOP_VALUE, false, 'B', 'd'));
            computerPieces.addLast(new ChessPiece(true)); //blank piece in row

            //Create Player Pieces
            LinkedList<ChessPiece> playerPieces = new LinkedList<>();
            playerPieces.addLast(new ChessPiece(KING_VALUE, true, 'k', 'a'));
            playerPieces.addLast(new ChessPiece(ROOK_VALUE, true, 'r', 'c'));
            playerPieces.addLast(new ChessPiece(ROOK_VALUE, true, 'r', 'c'));
            playerPieces.addLast(new ChessPiece(BISHOP_VALUE, true, 'b', 'd'));
            playerPieces.addLast(new ChessPiece(BISHOP_VALUE, true, 'b', 'd'));
            playerPieces.addLast(new ChessPiece(true)); //blank piece in row

            //place pieces
            for (int i = 0; i < computerPieces.size(); i++){
                board[0][i] = computerPieces.remove(generator.nextInt(computerPieces.size()));
            }
            for (int i = 0; i < playerPieces.size(); i++){
                board[5][i] = playerPieces.remove(generator.nextInt(computerPieces.size()));
            }

            //initialize rest with blanks
            for(int i = 1; i < 5; i++)
                for (int j = 0; j < 6; j++)
                    board[i][j] = new ChessPiece(true);
        }

        //Copy Constructor
        private Board(Board other){
            this.board = new ChessPiece[6][6];
            for(int i = 0; i < 6; i++)
                for (int j = 0; j < 6; j++)
                    board[i][j] = new ChessPiece(other.board[i][j]);
        }

        //toString
        public String toString() {
            String line = "-------------\n";
            String output = "  0 1 2 3 4 5\n"+line;
            for (int i = 0; i < 6; i++){
                output = output + i + "|";
                for(int j = 0; j < 6; j++)
                    output = output + board[i][j].getPiece()+"|";
                output = output + "\n"+line;
            }
            return output;
        }
    }

    public void game(){
        Board board = new Board();
        System.out.print(board.toString());
    }
}
