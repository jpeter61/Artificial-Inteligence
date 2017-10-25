import java.util.Scanner;
import java.io.*;

public class SlidingFHTester {
    public static void main(String[] args)throws IOException{
        Scanner keyIn = new Scanner(System.in);

        System.out.println("Enter File Name:");
        String fileName = keyIn.nextLine();

        File file = new File(fileName);
        Scanner sc = new Scanner(file);

        String firstLine[] = sc.nextLine().split(" ");

        int size = Integer.parseInt(firstLine[0]);

        sc.nextLine();
        int[][] initial = new int[size][size];
        for(int i = 0; i < size; i++){
            String line[] = sc.nextLine().split("\\s+");
            for(int j = 0; j < size; j++ ){
                initial[i][j] = Integer.parseInt(line[j]);
            }
        }

        sc.nextLine();
        int[][] goal = new int[size][size];
        for(int i = 0; i < size; i++){
            String line[] = sc.nextLine().split("\\s+");
            for(int j = 0; j < size; j++ ){
                goal[i][j] = Integer.parseInt(line[j]);
            }
        }
        //start timer
        long startTime = System.nanoTime();

        SlidingFH s = new SlidingFH(initial, goal, size);
        s.solve();

        //end timer
        long endTime = System.nanoTime();
        System.out.println("Time: " + (endTime - startTime)/1000000 + " milliseconds");
    }
}
