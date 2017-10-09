import java.util.Scanner;
import java.io.*;

public class ProfessorsTester {
    public static void main(String[] args)throws IOException{
        Scanner keyIn = new Scanner(System.in);

        System.out.println("Enter File Name:");
        String fileName = keyIn.nextLine();

        File file = new File(fileName);
        Scanner sc = new Scanner(file);

        String firstLine[] = sc.nextLine().split(" ");

        int m = Integer.parseInt(firstLine[0]);

        int n = Integer.parseInt(firstLine[1]);

        int k = Integer.parseInt(firstLine[2]);


        sc.nextLine();
        int[][] preferenceList = new int[m][n];
        for(int i = 0; i < m; i++){
            String line[] = sc.nextLine().split(" : |\\s+");
            for(int j = 1; j <= n; j++)
                preferenceList[i][j-1] = Integer.parseInt(line[j]);
        }

        Professors s = new Professors(m,k,n,preferenceList);
        s.solve();
    }
}
