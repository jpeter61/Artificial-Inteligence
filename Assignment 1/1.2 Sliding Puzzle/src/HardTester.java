public class HardTester {
    public static void main(String[] args){
        int[][] initial = {{5,7,1},{2,0,8},{4,6,3}};
        int[][] goal = {{5,0,1},{4,7,8},{6,2,3}};

        Sliding s = new Sliding(initial,goal,3, 3);
        s.solve();
    }
}
