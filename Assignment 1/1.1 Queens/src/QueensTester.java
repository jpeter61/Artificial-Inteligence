import java.util.Scanner;
public class QueensTester {
    public static void main(String[] args){
        Scanner keyIn = new Scanner(System.in);
        int m;
        int n;
        Queens q;
        System.out.println("Enter M then N. Where N >= M");
        m = keyIn.nextInt();
        n = keyIn.nextInt();

        if(m <= n) {
            q = new Queens(m, n);
            q.solve();
        }
        else
            System.out.println("Error: N must be >= M");
    }
}
