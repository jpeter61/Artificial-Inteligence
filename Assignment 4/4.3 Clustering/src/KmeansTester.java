public class KmeansTester {
    public static void main(String[] args) {
        //code to find best clustering
//        for (int i  = 1; i <= 10; i++) {
//            Kmeans k = new Kmeans(65765675);
//            k.load("C:\\Users\\Jamie\\Desktop\\AI\\Assignment 4\\4.3 Clustering\\inputfile.txt");
//            k.setParameters(i, 100);
//            k.cluster();
//            k.display("outputfile");
//        }
        Kmeans k = new Kmeans(65765675);
        k.load("C:\\Users\\Jamie\\Desktop\\AI\\Assignment 4\\4.3 Clustering\\inputfile.txt");
        k.setParameters(4, 100);
        k.cluster();
        k.display("outputfile");
    }
}
