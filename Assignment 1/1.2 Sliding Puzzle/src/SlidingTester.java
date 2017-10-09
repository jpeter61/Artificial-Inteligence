import java.util.Scanner;
import java.io.*;

public class SlidingTester {
    public static void main(String[] args)throws IOException{
        Scanner keyIn = new Scanner(System.in);

        System.out.println("Enter File Name:");
        String fileName = keyIn.nextLine();

        File file = new File(fileName);
        Scanner sc = new Scanner(file);

        String firstLine[] = sc.nextLine().split(" ");

        int m = Integer.parseInt(firstLine[0]);

        int n = Integer.parseInt(firstLine[1]);

        sc.nextLine();
        int[][] initial = new int[m][n];
        for(int i = 0; i < m; i++){
            String line[] = sc.nextLine().split("\\s+");
            for(int j = 0; j < n; j++ ){
                initial[i][j] = Integer.parseInt(line[j]);
            }
        }

        sc.nextLine();
        int[][] goal = new int[m][n];
        for(int i = 0; i < m; i++){
            String line[] = sc.nextLine().split("\\s+");
            for(int j = 0; j < n; j++ ){
                goal[i][j] = Integer.parseInt(line[j]);
            }
        }
        Sliding s = new Sliding(initial, goal, m, n);
        s.solve();
    }
}
