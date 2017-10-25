import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class GreedyTester {
    public static void main(String[] args)throws IOException {
        Scanner keyIn = new Scanner(System.in);

        System.out.println("Enter File Name:");
        String fileName = keyIn.nextLine();

        File file = new File(fileName);
        Scanner sc = new Scanner(file);

        String firstLine[] = sc.nextLine().split(" ");
        int vertices = Integer.parseInt(firstLine[0]);
        int edgesCount = Integer.parseInt(firstLine[1]);
        sc.nextLine();
        int[][] edges = new int[edgesCount][3];
        for(int i = 0; i < edgesCount; i++){
            String line[] = sc.nextLine().split("\\s+");
            edges[i][0] = Integer.parseInt(line[0])-1;
            edges[i][1] = Integer.parseInt(line[1])-1;
            edges[i][2] = Integer.parseInt(line[2]);
        }
        GreedyTSP t = new GreedyTSP(vertices, edges);
        t.solve();
    }
}
