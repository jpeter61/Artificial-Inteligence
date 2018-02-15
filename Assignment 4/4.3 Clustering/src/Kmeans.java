import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class Kmeans {
    private int numberRecords;
    private int numberAttributes;
    private int numberClusters;
    private int numberIterations;

    private double[][] records;
    private double[][] centroids;
    private int[] clusters;
    private Random random;
    private double[] sumSquaredError;

    //Constructor
    public Kmeans(int seed){
        numberRecords = 0;
        numberAttributes = 0;
        numberClusters = 0;
        numberIterations = 0;

        records = null;
        centroids = null;
        clusters = null;
        random = new Random(seed);
    }

    //loads records from input file
    public void load (String inputFile){
        Scanner inFile = null;
        try {
            inFile = new Scanner(new File(inputFile));
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: file input");
            return;
        }
        //read number of records, attributes
        numberRecords = inFile.nextInt();
        //System.out.println(numberRecords);
        numberAttributes = inFile.nextInt();
        //System.out.println(numberAttributes);

        //creates array of new records
        records = new double[numberRecords][numberAttributes];
        for (int i = 0; i < numberRecords; i++)
            for(int j = 0; j < numberAttributes; j++)
                records[i][j] = inFile.nextDouble();

    }

    //set rest of parameters
    public void setParameters(int numberClusters, int numberIterations){
        this.numberClusters = numberClusters;
        this.numberIterations = numberIterations;
    }

    //run algorithm
    public void cluster(){
        initializeClusters();
        //initialize centroids
        initializeCentroids();

        //for n iterations
        for (int i = 0; i < numberIterations; i++){
            assignClusters();
            updateCentroids();
        }
        //calculate errors
        calculateErrors();
    }

    //initializes clusters
    private void initializeClusters(){
        clusters = new int[numberRecords];
        for (int i = 0; i < numberRecords; i++)
            clusters[i] = -1;
    }

    //initializes centroids of clusters
    private void initializeCentroids(){
        centroids = new double[numberClusters][numberAttributes];

        for (int i = 0; i < numberClusters; i++){
            int index = random.nextInt(numberRecords);
            for(int j = 0; j < numberAttributes; j++)
                centroids[i][j] = records[index][j];
        }
    }

    //assign clusters to records
    private void assignClusters(){
        for (int i = 0; i < numberRecords; i++ ) {
            double minValue = distance(records[i], centroids[0]);
            int mindex = 0; //starting to find minimum

            for (int j = 0; j < numberClusters; j++){
                double distance = distance(records[i], centroids[j]);
                if (distance < minValue){
                    minValue = distance;
                    mindex = j;
                }
            }
            clusters[i] = mindex;
        }
    }

    //updates the centroids of clusters
    private void updateCentroids(){
        //create array of cluster sums and initialize
        double[][] clusterSum = new double[numberClusters][numberAttributes];
        for (int i = 0; i < numberClusters; i++)
            for (int j = 0; j < numberAttributes; j++)
                clusterSum[i][j] = 0;

        //create array of cluster sizes and initialize
        int[] clusterSize = new int[numberClusters];
        for (int i = 0; i < numberClusters; i++)
            clusterSize[i] = 0;

        //for each record
        for(int  i = 0; i < numberRecords; i++){
            //find cluster
            int cluster = clusters[i];
            //add record to cluster sum
            clusterSum[cluster] = sum(clusterSum[cluster], records[i]);
            //cincrement cluster size
            clusterSize[cluster]++;
        }
        //find centroid of each cluster
        for (int i = 0; i < numberClusters; i++)
            if (clusterSize[i] > 0)
                centroids[i] = scale(clusterSum[i], 1.0/clusterSize[i]);
    }

    //calculates distance between two records
    private double distance(double[] u, double[] v){
        double sum = 0;
        //find euclidean distance square between two vectors
        for(int i = 0; i < u.length; i++)
            sum += (u[i] - v[i])*(u[i]-v[i]);
        return sum;
    }

    private double[] sum(double[] u, double[] v){
        double[] result = new double[u.length];
        //add corresponding attributes of records
        for (int i = 0; i < u.length; i++)
            result[i] = u[i] + v[i];
        return result;
    }

    //finds scaler multiple of a record
    private double[] scale(double[] u, double k){
        double[] result = new double[u.length];
        //multiply attributes of record by scaler
        for (int i = 0; i < u.length; i++)
            result[i] = u[i]*k;
        return result;
    }

    //Writes records and their clusters to output file
    public void display (String outputFile){
        PrintWriter outFile;
        try {
            outFile = new PrintWriter(new FileWriter(outputFile));
        } catch (IOException e) {
            System.out.println("ERROR: file output");
            return;
        }
        for (int i = 0; i < numberRecords; i++){
            for (int j = 0; j < numberAttributes; j++){
                outFile.print(records[i][j] + " ");
            }
            outFile.println(clusters[i]+1);
        }
        outFile.close();
    }

    //find the sumsquared error for each record
    private void calculateErrors(){
        //get all distances between records and square them then sum them up
        double sum = 0;
        for (int i = 0 ; i < numberRecords; i++){
            //get records cluster
            int cluster = clusters[i];
            double distance = distance(records[i], centroids[cluster]);
            distance = Math.pow(distance, 2);
            sum += distance;
        }
        System.out.println(sum);
    }
}

