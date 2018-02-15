public class VoterTester {
    public static void main (String[] args){
        //New Voter
        Voter v = new Voter(100, 10000, 0.3, 255234455);
        double agentDensity = 0.00;
        for (int i = 0; i < 100; i++){
            agentDensity += 0.01;
            v.newRun(agentDensity);
            System.out.println("Run " + (i+1) + " is over");
        }
//        v.newRun(0.87);
    }
}
