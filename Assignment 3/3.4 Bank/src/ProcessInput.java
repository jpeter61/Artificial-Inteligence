import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ProcessInput {
    private double[] maxInputs;
    private double[] minInputs;
    //input values
    private final static double MALE = 0.0;
    private final static double FEMALE = 1.0;
    private final static double MARRIED = 1.0;
    private final static double SINGLE = 0.5;
    private final static double DIVORCED = 0.0;
    //output values
    private final static double UNDETERMINED = 0.25;
    private final static double LOW = 0.5;
    private final static double MEDIUM = 0.75;
    private final static double HIGH = 1.0;

    public ProcessInput(){
        //number inputs max
        maxInputs = new double[] {900, 90, 80};
        minInputs = new double[] {500, 30, 30};
    }

    public void processTraining(String inputFileName, String outputFileName)throws IOException{
        Scanner inFile = new Scanner(new File(inputFileName));
        //read number of records
        int numberOfRecords = inFile.nextInt();
        //create input arrays
        double[] credit = new double[numberOfRecords];
        double[] income = new double[numberOfRecords];
        double[] age = new double[numberOfRecords];
        double[] sex = new double[numberOfRecords];
        double[] maritalStatus = new double[numberOfRecords];
        //Create output array
        double[]risk = new double[numberOfRecords];
        //iterate through records
        inFile.nextLine();
        inFile.nextLine();
        for (int i = 0; i < numberOfRecords; i++){
            String[] line = inFile.nextLine().split("\\s+");
            //credit
            credit[i] = normalize(Integer.parseInt(line[0]), 0);
            //income
            income[i] = normalize(Integer.parseInt(line[1]), 1);
            //age
            age[i] = normalize(Integer.parseInt(line[2]), 2);
            //sex
            sex[i] = convertSex(line[3]);
            //marital Status
            maritalStatus[i] = convertMaritalStatus(line[4]);
            //risk
            risk[i] = convertRisk(line[5]);
        }
        inFile.close();
        //write processed records
        PrintWriter outFile = new PrintWriter(new FileWriter(outputFileName));
        outFile.print(numberOfRecords + " ");
        outFile.print(5 + " ");
        outFile.print(1 + " ");
        outFile.println("\n");
        for (int i = 0; i < numberOfRecords; i++){
            outFile.print(credit[i] + " ");
            outFile.print(income[i] + " ");
            outFile.print(age[i] + " ");
            outFile.print(sex[i] + " ");
            outFile.print(maritalStatus[i] + " ");
            outFile.print(risk[i] + " ");
            outFile.println();
        }
        outFile.close();
    }

    public void processInput(String inputFileName, String outputFileName) throws IOException{
        Scanner inFile = new Scanner(new File(inputFileName));
        //read number of records
        int numberOfRecords = inFile.nextInt();
        //create input arrays
        double[] credit = new double[numberOfRecords];
        double[] income = new double[numberOfRecords];
        double[] age = new double[numberOfRecords];
        double[] sex = new double[numberOfRecords];
        double[] maritalStatus = new double[numberOfRecords];
        inFile.nextLine();
        inFile.nextLine();
        //iterate through records
        for (int i = 0; i < numberOfRecords; i++){
            String[] line = inFile.nextLine().split("\\s+");
            //credit
            credit[i] = normalize(Integer.parseInt(line[0]), 0);
            //income
            income[i] = normalize(Integer.parseInt(line[1]), 1);
            //age
            age[i] = normalize(Integer.parseInt(line[2]), 2);
            //sex
            sex[i] = convertSex(line[3]);
            //marital Status
            maritalStatus[i] = convertMaritalStatus(line[4]);
        }
        inFile.close();
        //write processed records
        PrintWriter outFile = new PrintWriter(new FileWriter(outputFileName));
        outFile.println(numberOfRecords);
        outFile.println("\n");
        for (int i = 0; i < numberOfRecords; i++){
            outFile.print(credit[i] + " ");
            outFile.print(income[i] + " ");
            outFile.print(age[i] + " ");
            outFile.print(sex[i] + " ");
            outFile.print(maritalStatus[i] + " ");
            outFile.println();
        }
        outFile.close();
    }

    public void processOutput(String inputFileName, String outputFileName) throws IOException{
        Scanner inFile = new Scanner(new File(inputFileName));
        //read number of records
        int numberOfRecords = inFile.nextInt();
        //Create output array
        String[]risk = new String[numberOfRecords];
        //iterate through records
        for (int i = 0; i < numberOfRecords; i++){
            //risk
            risk[i] = convertRiskOutput(inFile.nextDouble());
        }
        inFile.close();
        //write processed records
        PrintWriter outFile = new PrintWriter(new FileWriter(outputFileName));
        outFile.println(numberOfRecords);
        outFile.println("\n");
        for (int i = 0; i < numberOfRecords; i++){
            outFile.print(risk[i] + " ");
            outFile.println();
        }
        outFile.close();
    }

    private String convertRiskOutput(double value){
        if(value <= UNDETERMINED)
            return "undetermined";
        else if (value <= LOW)
            return "low";
        else if (value <= MEDIUM)
            return "medium";
        else
            return "high";
    }

    private double normalize(int value, int input){
        return (value-minInputs[input])/(maxInputs[input]-minInputs[input]);
    }

    private double convertSex(String value){
        if (value.charAt(0) == 'm')
            return MALE;
        else
            return FEMALE;
    }

    private double convertMaritalStatus(String value){
        switch (value) {
            case "single":
                return SINGLE;
            case "divorced":
                return DIVORCED;
            default:
                return MARRIED;
        }
    }

    private double convertRisk(String value){
        switch (value) {
            case "high":
                return HIGH;
            case "low":
                return LOW;
            case "medium":
                return MEDIUM;
            default:
                return UNDETERMINED;
        }
    }
}
