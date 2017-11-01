public class ChessPiece {
    private int value;
    private boolean player;//true == human
                            //false == computer
    private char piece;
    private char movement;
    private boolean blank; //Check if blank

    //Constructor normal
    public ChessPiece(int value, boolean player, char piece, char movement){
        this.value = value;
        this.player = player;
        this.piece = piece;
        this.movement = movement;
    }

    //Constructor Blank
    public ChessPiece(boolean blank){
        this.blank = blank;
        this.piece = ' ';
    }

    //Copy Constructor
    public ChessPiece(ChessPiece other){
        if(other.isBlank()){
            this.blank = true;
            this.piece = ' ';
            return;
        }
        //if blank we don't need anything else
        this.value = other.getValue();
        this.player = other.isPlayer();
        this.piece = other.getPiece();
        this.movement = other.getMovement();
    }

    public int getValue() {
        return value;
    }

    public char getMovement() {
        return movement;
    }

    public char getPiece() {
        return piece;
    }

    public boolean isPlayer() {
        return player;
    }

    public boolean isBlank() {
        return blank;
    }
}
