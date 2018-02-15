import java.io.IOException;

public class NetworkTester {
    public static void main(String[] args) throws IOException{
        //construct
        NeuralNetwork network = new NeuralNetwork();
        ProcessInput pi = new ProcessInput();
        //load training data
        pi.processTraining("C:\\Users\\Jamie\\Desktop\\AI\\Assignment 3\\3.3 Neural Net\\src\\rawTraining", "C:\\Users\\Jamie\\Desktop\\AI\\Assignment 3\\3.3 Neural Net\\src\\processedTraining");
        network.loadTrainingData("C:\\Users\\Jamie\\Desktop\\AI\\Assignment 3\\3.3 Neural Net\\src\\processedTraining");
        //set parameters of network
        network.setParameters(4, 10000, 625228709, 0.6);
        //train
        network.train();
        //Validate
        //pi.processInput("C:\\Users\\Jamie\\Desktop\\AI\\Assignment 3\\3.3 Neural Net\\src\\rawValidation", "C:\\Users\\Jamie\\Desktop\\AI\\Assignment 3\\3.3 Neural Net\\src\\processedValidation");
        //network.validate("C:\\Users\\Jamie\\Desktop\\AI\\Assignment 3\\3.3 Neural Net\\src\\processedValidation");
        //test
        pi.processInput("C:\\Users\\Jamie\\Desktop\\AI\\Assignment 3\\3.3 Neural Net\\src\\rawInput", "C:\\Users\\Jamie\\Desktop\\AI\\Assignment 3\\3.3 Neural Net\\src\\processedInput");
        network.testData("C:\\Users\\Jamie\\Desktop\\AI\\Assignment 3\\3.3 Neural Net\\src\\processedInput", "C:\\Users\\Jamie\\Desktop\\AI\\Assignment 3\\3.3 Neural Net\\src\\rawOutput");
        pi.processOutput("C:\\Users\\Jamie\\Desktop\\AI\\Assignment 3\\3.3 Neural Net\\src\\rawOutput", "C:\\Users\\Jamie\\Desktop\\AI\\Assignment 3\\3.3 Neural Net\\src\\processedOutput");

    }
}
