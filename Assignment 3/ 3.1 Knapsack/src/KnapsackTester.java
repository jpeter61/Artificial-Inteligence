import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class KnapsackTester {
    public static void main(String[] args)throws IOException {
        Scanner keyIn = new Scanner(System.in);

        System.out.println("Enter File Name:");
        String fileName = keyIn.nextLine();

        File file = new File(fileName);
        Scanner sc = new Scanner(file);


        int numOfFurniture = Integer.parseInt(sc.nextLine());

        sc.nextLine();

        int[][] listOfFurniture = new int[numOfFurniture][2];

        for(int i = 0; i < numOfFurniture; i++){
            String line[] = sc.nextLine().split("\\s+");
            listOfFurniture[i][0] = Integer.parseInt(line[1]);
            listOfFurniture[i][1] = Integer.parseInt(line[2]);
        }
        sc.nextLine();
        int weightLimit = Integer.parseInt(sc.nextLine());
        Knapsack knapsack = new Knapsack(listOfFurniture,numOfFurniture,weightLimit);
        knapsack.setParameters(50,numOfFurniture,20000,
                0.8,0.01,47895209);
        knapsack.run();
    }
}
