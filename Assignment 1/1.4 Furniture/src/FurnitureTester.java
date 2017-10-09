import java.util.Scanner;
import java.io.*;

public class FurnitureTester {
    public static void main(String[] args)throws IOException{
        Scanner keyIn = new Scanner(System.in);

        System.out.println("Enter File Name:");
        String fileName = keyIn.nextLine();

        File file = new File(fileName);
        Scanner sc = new Scanner(file);


        int numOfFuniture = Integer.parseInt(sc.nextLine());

        sc.nextLine();


        int[][] listOfFurniture = new int[numOfFuniture][3];

        for(int i = 0; i < numOfFuniture; i++){
            String line[] = sc.nextLine().split("\\s+");
            listOfFurniture[i][0] = Integer.parseInt(line[0]);
            listOfFurniture[i][1] = Integer.parseInt(line[1]);
            listOfFurniture[i][2] = Integer.parseInt(line[2]);
        }
        sc.nextLine();
        int weightLimit = Integer.parseInt(sc.nextLine());
        FurnitureSolver s = new FurnitureSolver(numOfFuniture,weightLimit,listOfFurniture);
        s.solve();

    }
}
