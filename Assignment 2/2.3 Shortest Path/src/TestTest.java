import java.io.IOException;

public class TestTest {
    public static void main(String[] args)throws IOException {
        int nodes = 5;
        int edges = 6;

        double[][] locations = new double[nodes][2];
        locations[0][0] = 4.1;
        locations[0][1] = 1.2;
        locations[1][0] = 0.5;
        locations[1][1] = 2.1;
        locations[2][0] = 1.9;
        locations[2][1] = 4.5;
        locations[3][0] = 1.2;
        locations[3][1] = 0.4;
        locations[4][0] = 3.8;
        locations[4][1] = 1.7;

        int[][] listofedges = new int[edges][2];
        listofedges[0][0] = 0;
        listofedges[0][1] = 4;
        listofedges[1][0] = 1;
        listofedges[1][1] = 2;
        listofedges[2][0] = 0;
        listofedges[2][1] = 2;
        listofedges[3][0] = 1;
        listofedges[3][1] = 3;
        listofedges[4][0] = 2;
        listofedges[4][1] = 4;
        listofedges[5][0] = 1;
        listofedges[5][1] = 3;

        ShortestPath s = new ShortestPath(nodes, locations, listofedges, 3,4);
        s.solve();
    }
}
