import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class NeuralNetwork {
    //Training record class
    private class Record{
        private double[] input;
        private double[] output;

        //Constructor
        private Record(double[] input, double[] output){
            this.input = input;
            this.output = output;
        }
    }

    //parameters
    private int numberOfRecords;
    private int numberOfInputs;
    private int numberOfOutputs;
    private int numberOfMiddleNodes;
    private int numberOfIterations;
    private double rate;
    //List of training records
    private ArrayList<Record> records;
    //Node Values
    private double[] inputs;
    private double[] middle;
    private double[] outputs;

    private double[] errorsMiddle;
    private double[] errorsOut;

    private double[] thetasMiddle;
    private double[] thetasOut;

    private double[][] matrixMiddle;
    private double[][] matrixOut;

    //Neural Net Constructor
    public NeuralNetwork (){
        //set parameters to 0
        numberOfInputs = 0;
        numberOfIterations = 0;
        numberOfMiddleNodes = 0;
        numberOfOutputs = 0;
        numberOfRecords = 0;
        rate = 0.0;
        //set empty arrays
        records = null;
        inputs = null;
        middle = null;
        outputs = null;
        errorsMiddle = null;
        errorsOut = null;
        thetasMiddle = null;
        thetasOut = null;
        matrixMiddle = null;
        matrixOut = null;
    }

    //loads training data
    public void loadTrainingData(String trainingFile) throws IOException{
        Scanner inFile = new Scanner(new File(trainingFile));
        //read number of records, inputs, outputs
        numberOfRecords = inFile.nextInt();
        numberOfInputs = inFile.nextInt();
        numberOfOutputs = inFile.nextInt();
        //empty list of records
        records = new ArrayList<>();
        //iterate through records
        for (int i = 0; i < numberOfRecords; i++){
            //read inputs
            double[] input = new double[numberOfInputs];
            for (int j = 0; j < numberOfInputs; j++)
                input[j] = inFile.nextDouble();
            //read outputs
            double[] output = new double[numberOfOutputs];
            for (int j = 0; j < numberOfOutputs; j++)
                output[j] = inFile.nextDouble();
            //create record and add to array
            records.add(new Record(input, output));
        }
        inFile.close();
    }

    //parameters of the network
    public void setParameters(int numberOfMiddleNodes, int numberOfIterations, long seed, double rate){
        this.numberOfMiddleNodes = numberOfMiddleNodes;
        this.numberOfIterations = numberOfIterations;
        this.rate = rate;
        Random rand = new Random(seed);
        //create input/output arrays
        inputs = new double[numberOfInputs];
        middle = new double[numberOfMiddleNodes];
        outputs = new double[numberOfOutputs];
        //error arrays
        errorsMiddle = new double[numberOfMiddleNodes];
        errorsOut = new double[numberOfOutputs];
        //thetas
        thetasMiddle = new double[numberOfMiddleNodes];
        thetasOut = new double[numberOfOutputs];
        //initialize thetas
        for (int i = 0; i < numberOfMiddleNodes; i++)
            thetasMiddle[i] = 2*rand.nextDouble()-1;
        for (int i = 0; i < numberOfOutputs; i++)
            thetasOut[i] = 2*rand.nextDouble()-1;
        //initialize weights between input/hidden
        matrixMiddle = new double[numberOfInputs][numberOfMiddleNodes];
        for (int i = 0; i < numberOfInputs; i++)
            for (int j = 0; j < numberOfMiddleNodes; j++)
                matrixMiddle[i][j] = 2*rand.nextDouble()-1;
        //initialize weights between hidden/output
        matrixOut = new double[numberOfMiddleNodes][numberOfOutputs];
        for (int i = 0; i < numberOfMiddleNodes; i++)
            for (int j = 0; j < numberOfOutputs; j++)
                matrixOut[i][j] = 2*rand.nextDouble()-1;
    }

    //trains neural network
    public void train(){
        //repeat for numberOfIterations
        for (int  i = 0; i < numberOfIterations; i++)
            for (int j = 0; j < numberOfRecords; j++){
                //calculate forward input/output
                forwardCalculation(records.get(j).input);
                //compute errors, update weights/thetas
                backwardCalculation(records.get(j).output);
            }
    }

    //performs forward pass - compute input/output
    private void forwardCalculation(double[] trainingInput){
        //feed inputs
        for (int i = 0; i < numberOfInputs; i++)
            inputs[i] = trainingInput[i];
        //for each hidden node
        for (int i = 0; i < numberOfMiddleNodes; i++){
            double sum = 0;
            //compute input at hidden nodes
            for(int j = 0; j < numberOfInputs; j++)
                sum += inputs[j]*matrixMiddle[j][i];
            //add theta
            sum += thetasMiddle[i];
            //compute output at hidden node
            middle[i] = 1/(1+Math.exp(-sum));
        }
        // for each output
        for (int i = 0; i < numberOfOutputs; i++){
            double sum = 0;
            //compute input at output
            for (int j = 0; j < numberOfMiddleNodes; j++)
                sum += middle[j]*matrixOut[j][i];
            //add theta
            sum += thetasOut[i];
            //compute output at output node
            outputs[i] = 1/(1 + Math.exp(-sum));
        }
    }

    //Method performs backward pass - computes errors, updates weights/thetas
    private void backwardCalculation(double[] trainingOutput){
        //compute error at each output node
        for (int i = 0; i < numberOfOutputs; i++)
            errorsOut[i] = outputs[i]*(1-outputs[i])*(trainingOutput[i]-outputs[i]);
        //compute error at each hidden node
        for (int i = 0; i < numberOfMiddleNodes; i++){
            double sum = 0;
            for (int j = 0; j < numberOfOutputs; j++)
                sum += matrixOut[i][j]*errorsOut[j];
            errorsMiddle[i] = middle[i]*(1-middle[i])*sum;
        }

        //Update weights between hidden/output
        for (int i = 0; i < numberOfOutputs; i++)
            for(int j = 0; j < numberOfOutputs; j++)
                matrixOut[i][j] += rate*middle[i]*errorsOut[j];

        //Update weights between input/hidden
        for (int i = 0; i < numberOfInputs; i++)
            for(int j = 0; j < numberOfMiddleNodes; j++)
                matrixMiddle[i][j] += rate*inputs[i]*errorsMiddle[j];

        //update thetas at output nodes
        for (int i = 0; i < numberOfOutputs; i++)
            thetasOut[i] +=  rate*errorsOut[i];
        //update thetas at hidden nodes
        for (int i = 0; i < numberOfMiddleNodes; i ++)
            thetasMiddle[i] += rate*errorsMiddle[i];
    }

    //computes output of an input
    private double[] test(double[] input){
        //forward pass input
        forwardCalculation(input);
        //return processed output
        return outputs;
    }

    //reads inputs from input file and writes outputs to output file
    public void testData(String inputFile, String outputFile) throws IOException{
        Scanner inFile = new Scanner(new File(inputFile));
        PrintWriter outFile = new PrintWriter(new FileWriter(outputFile));

        int numberOFRecords = inFile.nextInt();

        outFile.println(numberOFRecords + "\n");
        for (int i = 0; i < numberOFRecords; i++){
            double[] input = new double[numberOfInputs];
            //read input from input file
            for (int j = 0; j < numberOfInputs; j++)
                input[j] = inFile.nextDouble();
            //find output
            double[] output = test(input);
            //write output to file
            for(int j = 0; j < numberOfOutputs; j++)
                outFile.print(output[j] + " ");
            outFile.println();
        }
        inFile.close();
        outFile.close();
    }

    //validates network using data from a file
    public void validate(String validationFile) throws IOException{
        Scanner inFile = new Scanner(new File(validationFile));

        int records = inFile.nextInt();

        for (int i = 0; i < records; i++){
            //read inputs
            double[] input = new double[numberOfInputs];
            for ( int j = 0; j < numberOfInputs; j++)
                input[j] = inFile.nextDouble();
            //read actual outputs
            double[] actualOutput = new double[numberOfOutputs];
            for (int j = 0; j < numberOfOutputs; j++)
                actualOutput[j] = inFile.nextDouble();
            //find predicted output
            double[] predictedOutput = test(input);
            //write actual and predicted outputs
            for (int j = 0; j < numberOfOutputs; j++)
                System.out.println(actualOutput[j]+" - " + predictedOutput[j]);
        }
        inFile.close();
    }

    //Finds root mean square error between actual and predicted output
    private double computeError(double[] actualOutput, double[] predictedOutput){
        double error = 0;
        //sum of squares of errors
        for (int i = 0; i < actualOutput.length; i++)
            error += Math.pow(actualOutput[i] -  predictedOutput[i], 2);

        //root mean square error
        return Math.sqrt(error/actualOutput.length);
    }
}
