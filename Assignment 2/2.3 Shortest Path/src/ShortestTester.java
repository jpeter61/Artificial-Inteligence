import java.util.Scanner;
import java.io.*;

public class ShortestTester {
    public static void main(String[] args)throws IOException{
        Scanner keyIn = new Scanner(System.in);

        System.out.println("Enter File Name:");
        String fileName = keyIn.nextLine();

        File file = new File(fileName);
        Scanner sc = new Scanner(file);

        String firstLine[] = sc.nextLine().split(" ");
        int vertices = Integer.parseInt(firstLine[0]);
        int edgesCount = Integer.parseInt(firstLine[1]);
        sc.nextLine();
        //Locations
        double[][] locations = new double[vertices][2];
        for(int i = 0; i < vertices; i++){
            String line[] = sc.nextLine().split("\\s+");
            locations[i][0] = Double.parseDouble(line[1]);
            locations[i][1] = Double.parseDouble(line[2]);
        }
        sc.nextLine();
        //Edges
        int[][] edges = new int[edgesCount][3];
        for(int i = 0; i < edgesCount; i++){
            String line[] = sc.nextLine().split("\\s+");
            edges[i][0] = Integer.parseInt(line[0])-1;
            edges[i][1] = Integer.parseInt(line[1])-1;
        }
        sc.nextLine();
        String LastLine[] = sc.nextLine().split(" ");
        int start = Integer.parseInt(firstLine[0])-1;
        int goal = Integer.parseInt(firstLine[1])-1;

        //start timer
        long startTime = System.nanoTime();

        ShortestPath s = new ShortestPath(vertices, locations, edges, start, goal);
        s.solve();

        //end timer
        long endTime = System.nanoTime();
        System.out.println("Time: " + (endTime - startTime)/1000000 + " milliseconds");
    }
}
