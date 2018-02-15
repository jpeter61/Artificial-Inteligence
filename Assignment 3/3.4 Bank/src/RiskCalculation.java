import java.io.IOException;

public class RiskCalculation {
    public static void main(String[] args) throws IOException {
        //construct
        NeuralNetwork network = new NeuralNetwork();
        ProcessInput pi = new ProcessInput();
        //load training data
        pi.processTraining("C:\\Users\\Jamie\\Desktop\\AI\\Assignment 3\\3.4 Bank\\src\\rawTraining", "C:\\Users\\Jamie\\Desktop\\AI\\Assignment 3\\3.4 Bank\\src\\processedTraining");
        network.loadTrainingData("C:\\Users\\Jamie\\Desktop\\AI\\Assignment 3\\3.4 Bank\\src\\processedTraining");
        //set parameters of network
        network.setParameters(6, 9000, 636284, 0.5);
        //train
        network.train();
        //test
        pi.processInput("C:\\Users\\Jamie\\Desktop\\AI\\Assignment 3\\3.4 Bank\\src\\rawInput", "C:\\Users\\Jamie\\Desktop\\AI\\Assignment 3\\3.4 Bank\\src\\processedInput");
        network.testData("C:\\Users\\Jamie\\Desktop\\AI\\Assignment 3\\3.4 Bank\\src\\processedInput", "C:\\Users\\Jamie\\Desktop\\AI\\Assignment 3\\3.4 Bank\\src\\rawOutput");
        pi.processOutput("C:\\Users\\Jamie\\Desktop\\AI\\Assignment 3\\3.4 Bank\\src\\rawOutput", "C:\\Users\\Jamie\\Desktop\\AI\\Assignment 3\\3.4 Bank\\src\\processedOutput");

    }
}
