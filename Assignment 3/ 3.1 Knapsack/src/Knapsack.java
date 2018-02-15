import java.util.Random;

public class Knapsack {

    //problem specific
    private int[][] table;      //weights and values
    private int numberOfItems;
    private int maximumWeight;

    //genetic values
    private int populationSize;
    private int stringLength;
    private int numberInterations;
    private double crossoverRate;
    private double mutationRate;
    private Random random;      //Random number generator

    //Arrays
    private int[][] population; //population of strings
    private double[] fitnessValues; //fitness values of strings

    //Constructor
    public Knapsack (int[][] table, int numberOfItems, int maximumWeight){
        this.table = table;
        this.numberOfItems = numberOfItems;
        this.maximumWeight = maximumWeight;
    }

    //Gets parameters of algorithm
    public void setParameters(int populationSize, int stringLength,
                              int numberIterations, double crossoverRate,
                              double mutationRate, long seed){
        this.populationSize = populationSize;
        this.stringLength = stringLength;
        this.numberInterations = numberIterations;
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
        for (int i = 0; i < numberInterations; i++){
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
            for (int j = 0; j < stringLength; j++)
                population[i][j] = random.nextInt(2);

        //Initial fitness values are made 0
        for(int i = 0; i < populationSize; i++)
            fitnessValues[i] = 0;
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
                for(int k = cut; k < stringLength; k++){
                    int temp = population[i][k];
                    population[i][j] = population[j][k];
                    population[j][k] = temp;
                }
            }
        }
    }

    //Mutation operation
    private void mutate(){
        for (int i = 0; i < populationSize; i++)
            for (int j = 0; j <stringLength; j++){
                //decides if mutation happens
                if (random.nextDouble() < mutationRate){
                    //flip that bit
                    if (population[i][j] == 0)
                        population[i][j] = 1;
                    else
                        population[i][j] = 0;
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
        //initialize weight and height
        int weight = 0;
        int value = 0;

        //calculate
        for (int i = 0; i < stringLength; i++)
            if (string[i] == 1){
                weight += table[i][0];
                value += table[i][1];
            }

        //if total weight exceeds maximum throw out
        //other wise return fitness
        if (weight <= maximumWeight)
            return value;
        else
            return 0;
    }

    //displays string
    private void display (int[] string){
        System.out.print("Items: ");
        for(int i = 0; i < stringLength; i++)
            if (string[i] == 1)
                System.out.print((i+1) + " ");
        //calculate weight and value again
        int weight = 0;
        int value = 0;

        //calculate
        for (int i = 0; i < stringLength; i++)
            if (string[i] == 1){
                weight += table[i][0];
                value += table[i][1];
            }

        System.out.println("\n\nWeight: " + weight);
        System.out.println("\nValue: " + value);
        //Specify genetic parameters
        System.out.println("\nPopulation Size: " + populationSize);
        System.out.println("\nNumber of Iterations: " + numberInterations);
        System.out.println("\nCrossover Rate: " + crossoverRate);
        System.out.println("\nMutation Rate: " + mutationRate);
    }
}
