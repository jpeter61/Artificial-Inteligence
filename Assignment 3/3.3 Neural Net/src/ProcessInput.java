import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ProcessInput {
    private int numberOfRecords;
    private int numberOfInputs;
    private int numberOfOutputs;
    private double[] maxInputs;
    private double[] minInputs;
    private double[] minOutputs;
    private double[] maxOutputs;

    public ProcessInput() throws IOException{
    }

    public void processTraining(String inputFileName, String outputFileName)throws IOException{
        Scanner inFile = new Scanner(new File(inputFileName));
        //read number of records, inputs, outputs
        numberOfRecords = inFile.nextInt();
        numberOfInputs = inFile.nextInt();
        numberOfOutputs = inFile.nextInt();
        //create arrays input
        double[][] inputs = new double[numberOfRecords][numberOfInputs];
        maxInputs = new double[numberOfInputs];
        minInputs = new double[numberOfInputs];
        //create arrays output
        double[][] outputs = new double[numberOfRecords][numberOfOutputs];
        maxOutputs = new double[numberOfOutputs];
        minOutputs = new double[numberOfOutputs];
        //iterate through records
        for (int i = 0; i < numberOfRecords; i++){
            //read inputs
            for (int j = 0; j < numberOfInputs; j++)
                inputs[i][j] = inFile.nextDouble();
            //read outputs
            for (int j = 0; j < numberOfOutputs; j++)
                outputs[i][j] = inFile.nextDouble();
        }
        inFile.close();
        //get min and max for each input
        for (int i = 0; i < numberOfInputs; i++){
            maxInputs[i] = 0;
            minInputs[i] = Double.MAX_VALUE;
            //per column
            for(int j = 0; j < numberOfRecords; j++){
                //max
                if (inputs[j][i] > maxInputs[i])
                    maxInputs[i] = inputs[j][i];
                if (inputs[j][i] < minInputs[i])
                    minInputs[i] = inputs[j][i];
            }
        }
        //get min and max for each output
        for (int i = 0; i < numberOfOutputs; i++){
            maxOutputs[i] = 0;
            minOutputs[i] = Double.MAX_VALUE;
            //per column
            for(int j = 0; j < numberOfRecords; j++){
                //max
                if (outputs[j][i] > maxOutputs[i])
                    maxOutputs[i] = outputs[j][i];
                if (outputs[j][i] < minOutputs[i])
                    minOutputs[i] = outputs[j][i];
            }
        }

        double[][] newInputs = newInputs(inputs);
        double[][] newOutputs = newOutputs(outputs);
        writeProcessed(outputFileName, newInputs, newOutputs, true, false, false);
    }

    private double[][] newInputs(double[][] oldInputs){
        //calculate new inputs
        double[][] newInputs = new double[oldInputs.length][numberOfInputs];
        for (int i = 0; i < numberOfInputs; i++){
            for(int j = 0; j < oldInputs.length; j++){
                newInputs[j][i] = (oldInputs[j][i]-minInputs[i])/(maxInputs[i]-minInputs[i]);
            }
        }
        return newInputs;
    }

    private double[][] newOutputs(double[][] oldOutputs){
        //calculate new outputs
        double[][] newOutputs = new double[numberOfRecords][numberOfOutputs];
        for (int i = 0; i < numberOfOutputs; i++){
            for(int j = 0; j < numberOfRecords; j++){
                newOutputs[j][i] = (oldOutputs[j][i]-minOutputs[i])/(maxOutputs[i]-minOutputs[i]);
            }
        }
        return newOutputs;
    }

    private void writeProcessed(String fileName, double[][] inputs, double[][] outputs, boolean isTraining,boolean isFinal, boolean isValidation)throws IOException{
        PrintWriter outFile = new PrintWriter(new FileWriter(fileName));
        if(isTraining) {
            outFile.print(numberOfRecords + " ");
            outFile.print(numberOfInputs + " ");
            outFile.print(numberOfOutputs);

        } else if(isFinal){
            outFile.print(outputs.length);
        }else
            outFile.print(inputs.length);

        outFile.println("\n");
        if(isFinal){
            for (int i = 0; i < outputs.length; i++){
                for (int j = 0; j < numberOfOutputs; j++)
                    outFile.print(outputs[i][j] + " ");
                outFile.println();
            }
        }else {
            for (int i = 0; i < inputs.length; i++) {
                //print inputs
                for (int j = 0; j < numberOfInputs; j++)
                    outFile.print(inputs[i][j] + " ");
                if (isTraining || isValidation)
                    //print outputs
                    for (int j = 0; j < numberOfOutputs; j++)
                        outFile.print(outputs[i][j] + " ");
                outFile.println();
            }
        }
        outFile.close();
    }

    public void processValidation(String inputFileName, String outputFileName) throws IOException{
        Scanner inFile = new Scanner(new File(inputFileName));
        int records = inFile.nextInt();
        double[][] inputs = new double[records][numberOfInputs];
        double[][] outputs = new double[records][numberOfOutputs];
        for (int i = 0; i < records; i++){
            //read inputs
            for (int j = 0; j < numberOfInputs; j++)
                inputs[i][j] = inFile.nextDouble();
            for (int j = 0; j < numberOfOutputs; j++)
                outputs[i][j] = inFile.nextDouble();
        }
        double[][] newInputs = newInputs(inputs);
        double[][] newOutputs = newOutputs(outputs);
        writeProcessed(outputFileName, newInputs,newOutputs,false , false, true);
    }

    public void processInput(String inputFileName, String outputFileName) throws IOException{
        Scanner inFile = new Scanner(new File(inputFileName));
        int records = inFile.nextInt();
        double[][] inputs = new double[records][numberOfInputs];
        for (int i = 0; i < records; i++){
            //read inputs
            for (int j = 0; j < numberOfInputs; j++)
                inputs[i][j] = inFile.nextDouble();
        }
        double[][] newInputs = newInputs(inputs);
        writeProcessed(outputFileName, newInputs,null,false , false, false);
    }

    public void processOutput(String inputFileName, String outputFileName) throws IOException{
        Scanner inFile = new Scanner(new File(inputFileName));
        int records = inFile.nextInt();
        double[][] outputs = new double[records][numberOfOutputs];
        for(int i = 0; i < records; i++){
            //read outputs
            for (int j = 0; j < numberOfOutputs; j++)
                outputs[i][j] = inFile.nextDouble();
        }
        double[][] newOutputs = finalOutputs(outputs);
        writeProcessed(outputFileName, null,newOutputs,false, true, false);

    }

    private double[][] finalOutputs(double[][] oldOutputs){
        double[][] newOutputs = new double[oldOutputs.length][numberOfOutputs];
        for (int j = 0; j < numberOfOutputs; j++)
            for (int i = 0; i < oldOutputs.length; i++)
                newOutputs[i][j] = oldOutputs[i][j]*(maxOutputs[j]-minOutputs[j])+minOutputs[j];
        return newOutputs;
    }
}
