import java.util.Random;

public class Traffic {
    //constants
    private final int EMPTY = 0;
    private final int DOWN = 1;
    private final int RIGHT = 2;

    private int[][] array;
    private int size;
    private int iterations;
    private double density;
    private Random random;
    private TrafficDrawer drawer;

    //Constructor
    public Traffic(int size, int iterations, double density, int seed){
        this.array = new int[size][size];
        this.size = size;
        this.iterations = iterations;
        this.density = density;
        this.random = new Random(seed);
        this.drawer = new TrafficDrawer(array, size);
    }

    //runs the simulation
    public void run(){
        initialize();

        //run iterations
        for(int n = 0; n < iterations; n++){
             draw();
             //update
            for (int m = 0; m < size*size; m++){
                //random location
                int i = random.nextInt(size);
                int j = random.nextInt(size);

                //move
                move(i,j);
            }
        }
    }

    private void initialize(){
        //go through all locations
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++){
                if (random.nextDouble() < density){
                    //if location has a vehicle
                    if(random.nextDouble() < 0.5)
                        //50% chance each
                        array[i][j] = DOWN;
                    else
                        array[i][j] = RIGHT;
                }
            }
    }

    //Draw array and dictate speed
    private void draw(){
        drawer.repaint();
        try{Thread.sleep(100);}catch (Exception e){}
    }

    private void move(int i, int j){
        //Traditional Rule Set
        //traditional(i, j);
        //Custom Rule Set
        custom(i, j);
    }

    //Traditional Rule Set
    private void traditional(int i, int j){
        //move down if down
        if (array[i][j] == DOWN){
            //if room
            if (array[(i+1)%size][j] == EMPTY){
                array[i][j] = EMPTY;
                array[(i+1)%size][j] = DOWN;
            }
        }
        //move right if right
        if (array[i][j] == RIGHT){
            //if room
            if (array[i][(j+1)%size] == EMPTY){
                array[i][j] = EMPTY;
                array[i][(j+1)%size] = RIGHT;
            }
        }
        //if empty do nothing
    }

    //My custom rules,
    //if a vehicle gets stuck it will move a space
    //to it's side randomly if clear if not it will
    //try the other
    private void custom(int i, int j){
        //move down if down
        if (array[i][j] == DOWN){
            //if room
            if (array[(i+1)%size][j] == EMPTY){
                array[i][j] = EMPTY;
                array[(i+1)%size][j] = DOWN;
            } else {
                if(random.nextDouble() < 0.5) {
                    //right
                    if (array[i][(j+1)%size] == EMPTY){
                        array[i][j] = EMPTY;
                        array[i][(j+1)%size] = DOWN;
                    }
                    //if right is not empty try the other
                    else if (array[i][((j-1)+size)%size] == EMPTY){
                        array[i][j] = EMPTY;
                        array[i][((j-1)+size)%size] = DOWN;
                    }
                }
                else {
                    //left
                    if (array[i][((j-1)+size)%size] == EMPTY){
                        array[i][j] = EMPTY;
                        array[i][((j-1)+size)%size] = DOWN;
                    }
                    //if left is not empty try the other
                    else if (array[i][(j+1)%size] == EMPTY){
                        array[i][j] = EMPTY;
                        array[i][(j+1)%size] = DOWN;
                    }

                }
//                if (array[i][(j+1)%size] == EMPTY){
//                    array[i][j] = EMPTY;
//                    array[i][(j+1)%size] = DOWN;
//                }
            }
        }
        //move right if right
        if (array[i][j] == RIGHT){
            //if room
            if (array[i][(j+1)%size] == EMPTY){
                array[i][j] = EMPTY;
                array[i][(j+1)%size] = RIGHT;
            } else {
                if(random.nextDouble() < 0.5) {
                    //Down
                    if (array[(i+1)%size][j] == EMPTY){
                        array[i][j] = EMPTY;
                        array[(i+1)%size][j] = RIGHT;
                    }
                    //if down is not empty try the other
                    else if (array[((i-1)+size)%size][j] == EMPTY){
                        array[i][j] = EMPTY;
                        array[((i-1)+size)%size][j] = RIGHT;
                    }
                }
                else {
                    //Up
                    if (array[((i-1)+size)%size][j] == EMPTY){
                        array[i][j] = EMPTY;
                        array[((i-1)+size)%size][j] = RIGHT;
                    }
                    //if up is not empty try the other
                    else if (array[(i+1)%size][j] == EMPTY){
                        array[i][j] = EMPTY;
                        array[(i+1)%size][j] = RIGHT;
                    }
                }
//                if (array[(i+1)%size][j] == EMPTY) {
//                    array[i][j] = EMPTY;
//                    array[(i + 1) % size][j] = RIGHT;
//                }
            }
        }
        //if empty do nothing
    }
}
