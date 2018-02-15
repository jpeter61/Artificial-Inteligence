import javax.swing.*;
import java.awt.*;

public class TrafficDrawer extends JFrame {
    //constants
    private final int DOWN = 1;
    private final int RIGHT = 2;

    private int[][] array;
    private int size;

    //Constructor
    public TrafficDrawer (int[][] array, int size){
        //window size
        setSize(10*size, 10*size);
        //standard window settings
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        //set array to be drawn
        this.array = array;
        //save size
        this.size = size;
    }

    //paint the window
    public void paint(Graphics g){
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                if (array[i][j] == DOWN){
                    g.setColor(Color.RED);
                    g.fillRect(10*j, 10*i, 10, 10);
                }
                else if (array[i][j] == RIGHT){
                    g.setColor(Color.BLUE);
                    g.fillRect(10*j, 10*i, 10, 10);
                }
                else {
                    g.setColor(Color.WHITE);
                    g.fillRect(10*j, 10*i, 10, 10);
                }
            }
        }
    }
}
