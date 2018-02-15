import java.io.*;
import java.util.Random;

public class Voter {
    private final int PLUS = 1;     //opinion 1
    private final int MINUS = 2;    //Opinion 2

    private int[][] array;          //array of opinions
    private boolean[][] agents;     //array of agents
    private int size;               //size of array
    private int iterations;         //number of iterations
    private double density;         //initial density of opinion 1
    private double agentDensity;    //density of opinion 1 agents
    private Random random;          //random number generator
    private int run;
//    private OpinionDrawer drawer;

    //Constructor
    public Voter(int size, int iterations, double density, int seed){
        this.array = new int[size][size];   //create array
        this.agents = new boolean[size][size]; //create agents
        this.size = size;
        this.iterations = iterations;       //set iterations
        this.density = density;             //set density
        this.random = new Random(seed);     //start generator
        this.run = 0;
        //drawer
//        this.drawer = new OpinionDrawer(array, size);
    }

    public void newRun(double agentDensity){
        this.agentDensity = agentDensity;   //new agent density
        this.array = new int[size][size];   //create array
        this.agents = new boolean[size][size]; //create agents
        run();
    }

    private void run(){
        run++;
        //initialize opinions
        initialize();
        //iterate
        for (int n = 0; n < iterations; n++){
            draw();
            //update population
            for (int m = 0; m < size*size; m++){
                //pick randomly
                int i = random.nextInt(size);
                int j = random.nextInt(size);
                //change opinion
                changeOpinion(i,j);
            }
            //calculate majority
            if (majority()){
                end(n);
                return;
                //end iteration
            }
            //else continue as normal
        }
        //if never majority
        end(iterations);
    }

    //initialize opinions
    private void initialize(){
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++){
                //assign opinion
                if (random.nextDouble() < density){
                    //opinion 1, decide if agent
                    array[i][j] = PLUS;
                    if (random.nextDouble() < agentDensity){
                        agents[i][j] = true;
                    } else {
                        agents[i][j] = false;
                    }
                } else {
                    array[i][j] = MINUS;
                    agents[i][j] = false;
                }
            }
    }

    //finds opinion of neighbors and change
    private void changeOpinion(int i, int j){

        //if agent don't change
        if (agents[i][j]) {
            //System.out.println("AGENT");
            return;
        }
        //count of opinions
        int countPlus = 0;
        int countMinus = 0;
        //current value being looked at
        int curr;
        //North
        curr = array[(i-1+size)%size][j];
        if (curr == PLUS)
            countPlus++;
        else
            countMinus++;
        //west
        curr = array[i][(j-1+size)%size];
        if (curr == PLUS)
            countPlus++;
        else
            countMinus++;
        //west
        curr = array[i][(j+1)%size];
        if (curr == PLUS)
            countPlus++;
        else
            countMinus++;
        //North
        curr = array[(i+1)%size][j];
        if (curr == PLUS)
            countPlus++;
        else
            countMinus++;

        if (countPlus > countMinus)
            array[i][j] = PLUS;
        else
            array[i][j] = MINUS;
    }

    //checks if opinion1 is majority
    private boolean majority(){
        int countPlus = 0;
        int countMinus = 0;
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++){
                if (array[i][j] == PLUS)
                    countPlus ++;
                else
                    countMinus ++;
            }
        //Check which is majority
        //if opinion 2 is greater
        //System.out.println(countPlus + " - " + countMinus);
        if (countMinus >= countPlus)
            return false; //nothing else
        //opinion 1 is the majority
        else
            return true;
    }

    //print results
    //Most file writing code is from
    // https://www.mkyong.com/java/how-to-append-content-to-file-in-java/
    private void end(int finalIteration){
        BufferedWriter bw = null;
        FileWriter fw = null;
        try{
            String line = "" + run + "," + agentDensity + "," + finalIteration + "\n";

            File file = new File("votes.txt");
            if (!file.exists())
                file.createNewFile();
            //then append file
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);

            bw.write(line);
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
                if(fw != null)
                    fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void draw(){
//        for(int i = 0; i < size; i++) {
//            for (int j = 0; j < size; j++)
//                System.out.print(array[i][j] + " ");
//            System.out.println();
//        }
////        System.out.println();
//        drawer.setArray(array);
//        drawer.repaint();
//        try{Thread.sleep(200);} catch (Exception e){}//pause
    }
}
