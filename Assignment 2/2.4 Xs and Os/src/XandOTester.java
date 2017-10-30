import java.util.Scanner;

public class XandOTester {
    public static void main(String[] args) {
        //gets the board size and plays the game
        System.out.println("Input size of the board: ");
        Scanner userIn = new Scanner(System.in);
        XAndO game = new XAndO(userIn.nextInt());
        game.play();
    }
}
