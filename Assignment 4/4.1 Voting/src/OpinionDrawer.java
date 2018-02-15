import javax.swing.*;
import java.awt.*;

public class OpinionDrawer extends JFrame {
    private final int PLUS = 1;
    private final int MINUS = 2;

    private int[][] array;
    private int size;

    //Constructor
    public OpinionDrawer(int[][] array, int size){
        setSize(10*size, 10*size);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        this.array = array;
        this.size = size;
    }

    public void setArray(int[][] array){
        this.array = new int[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                this.array[i][j] = array[i][j];
    }

    public void paint (Graphics g){
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++){
                if(array[i][j] == PLUS){
                    g.setColor(Color.RED);
                    g.fillRect(10*j,10*i, 10,10);
                } else if (array[i][j] == MINUS){
                    g.setColor(Color.BLUE);
                    g.fillRect(10*j,10*i, 10,10);
                }
            }
    }
}
