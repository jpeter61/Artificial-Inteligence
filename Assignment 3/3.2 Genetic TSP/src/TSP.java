import java.util.Random;

public class TSP {

    //problem specific
    private int[][] matrix;      //weights and values
    private int numberOfVertices;
    private int maximumEdge;

    //genetic values
    private int populationSize;
    private int stringLength;
    private int numberIterations;
    private double crossoverRate;
    private double mutationRate;
    private Random random;      //Random number generator

    //Arrays
    private int[][] population; //population of strings
    private double[] fitnessValues; //fitness values of strings

    //Constructor
    public TSP (int[][] matrix, int numberOfVertices, int maximumEdge){
        this.matrix = matrix;
        this.numberOfVertices = numberOfVertices;
        this.maximumEdge = maximumEdge;
    }

    //Gets parameters of algorithm
    public void setParameters(int populationSize, int stringLength,
                              int numberIterations, double crossoverRate,
                              double mutationRate, long seed){
        this.populationSize = populationSize;
        this.stringLength = stringLength;
        this.numberIterations = numberIterations;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.random = new Random(seed);
        //create arrays
        this.population = new int[populationSize][stringLength];
        this.fitnessValues = new double[populationSize];
    }

    //run the genetic algorithm
    public void run(){
        //initialize population
        initialize();

        //create generations
        for (int i = 0; i < numberIterations; i++){
            crossover();
            mutate();
            reproduce();
        }
        //select best string
        solution();
    }

    //initialize the population
    private void initialize(){
        //Initialize strings with random 1/0
        for(int i = 0; i < populationSize; i++)
            randomPermutation(population[i]);

        //Initial fitness values are made 0
        for(int i = 0; i < populationSize; i++)
            fitnessValues[i] = 0;
    }

    //fills string with a random permutation
    private void randomPermutation(int[] string){
        //fill string with integers from 1 to string length
        for(int i = 0; i < stringLength; i++)
            string[i] = i+1;

        //randomize
        for (int i = stringLength-1; i >= 0; i--){
            int j = random.nextInt(i+1);
            int temp = string[i];
            string[i] = string[j];
            string[j] = temp;
        }
    }


    //crossover operation
    private void crossover(){
        for(int i = 0; i < populationSize; i++){
            //decide if crossover happens
            if(random.nextDouble()<crossoverRate){
                //choose random string
                int j = random.nextInt(stringLength);
                //choose crossover point
                int cut = random.nextInt(stringLength);
                //crossover those bits
                //make copy o ith string
                int[] copy  = new int[stringLength];
                for (int k = 0; k < stringLength; k++)
                    copy[k] = population[i][k];

                //cross jth string into ith string
                for (int k = cut; k < stringLength; k++)
                    swap(population[i][k], population[j][k], population[i]);

                //cross ith string into jth string
                for (int k = cut; k < stringLength; k++)
                    swap(copy[k], population[j][k], population[j]);
            }
        }
    }

    //swaps two values in a string
    private  void swap (int a, int b, int[] string){
        //find location of one value
        int i;
        for (i  = 0; i < stringLength; i++)
            if (string[i] == a)
                break;
        //find location of other value
        int j;
        for (j  = 0; j < stringLength; j++)
            if (string[j] == b)
                break;
        //swap two values
        int temp = string[i];
        string[i] = string[j];
        string[j] = temp;

    }

    //Mutation operation
    private void mutate(){
        for (int i = 0; i < populationSize; i++)
            for (int j = 0; j <stringLength; j++){
                //decides if mutation happens
                if (random.nextDouble() < mutationRate){
                    //select another value in the string
                    int k = random.nextInt(stringLength);
                    //swap the two values
                    int temp = population[i][j];
                    population[i][j] = population[i][k];
                    population[i][k] = temp;
                }
            }
    }

    //reproduction operation
    private void reproduce(){
        //find fitness
        computeFitnessValues();

        //create array for next generation
        int[][] nextGeneration = new int[populationSize][stringLength];

        for(int i = 0; i < populationSize; i++){
            //select a string based on fitness
            int j = select();
            //copy string for next generation
            for(int k = 0; k < stringLength; k++)
                nextGeneration[i][k] = population[j][k];
        }
        //next generation becomes the current
        for (int i = 0; i < populationSize; i++)
            for (int j = 0; j < stringLength; j++)
                population[i][j] = nextGeneration[i][j];
    }

    //compute fitness values of all strings
    private void computeFitnessValues(){
        //compute fitness values
        for(int i = 0; i < populationSize; i++)
            fitnessValues[i] = fitness(population[i]);

        //accumulate values
        for(int i = 1; i < populationSize; i++)
            fitnessValues[i] = fitnessValues[i] + fitnessValues[i-1];

        //normalize values
        for(int i = 0; i < populationSize; i++)
            fitnessValues[i] = fitnessValues[i]/fitnessValues[populationSize-1];
    }

    //selects a string based on fitness values
    private int select(){
        double value = random.nextDouble();
        int i;
        for (i = 0; i < populationSize; i++)
            if (value <= fitnessValues[i])
                break;
        //return where the number fell
        return i;
    }

    //finds the best solution
    private void solution(){
        //compute fitness values
        for(int i = 0; i < populationSize; i++)
            fitnessValues[i] = fitness(population[i]);

        //find best value
        int best = 0;
        for(int i = 0; i < populationSize; i++)
            if(fitnessValues[i] > fitnessValues[best])
                best = i;

        //display best string
        display(population[best]);
    }

    //computes fitness value of a string
    private double fitness(int[] string){
        //compute cycle cost
        double sum = 0;
        for (int i = 0; i < stringLength; i++)
            sum += matrix[string[i]-1][string[(i+1)%stringLength]-1];
        //fitness value is number of verts times maximum edge weight
        //minus cycle cost
        return numberOfVertices*maximumEdge - sum;
    }

    //displays string
    private void display (int[] string){
        System.out.print("Cycle: ");
        for(int i = 0; i < stringLength; i++)
            System.out.print(string[i] + " ");
        double sum = 0;
        for (int i = 0; i < stringLength; i++)
            sum += matrix[string[i]-1][string[(i+1)%stringLength]-1];
        System.out.println("\n\nLength: " + sum);
        //Specify genetic parameters
        System.out.println("\nPopulation Size: " + populationSize);
        System.out.println("\nNumber of Iterations: " + numberIterations);
        System.out.println("\nCrossover Rate: " + crossoverRate);
        System.out.println("\nMutation Rate: " + mutationRate);
    }
}
