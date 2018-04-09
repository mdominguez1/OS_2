import java.util.concurrent.ThreadLocalRandom;
/**
 * @Author - Melchor Dominguez
 * @Version - 4.16.2018
 * Markov class create the finite state machine
 *
 */
public class Markov implements Runnable{
    
    /** The state where the finite state machine will start*/
    private int start;

    /** The number of iterations that the finite state machine will
     * go through until it reaches the end state
     */
    private int iterations;

    /** The data that contains a unique ID, the result of execution, and the representation
     * of the finite state machine 
     */
    private Data data;

    private ThreadLocalRandom rand;

    /**
     * Starts the finite state machine
     * @param startState - the Start state for the finite state machine
     * @param iterations - how many times the finite state machine will iterate before done
     * @param stuff - The data that contains a unique ID, the result of the execution, and
     *                the representation of the finite state machine
     */
    private Markov(int startState, int iterations, Data stuff){
        start = startState;
        this.iterations = iterations;
        data = stuff;
        rand = new ThreadLocalRandom()    
    }//end constructor
    
    /**
     * Run method which will start a thread
     */
    protected Data run(){
        
    }
    
    /**
     * Method to go through the finite state machine and store the 
     * result in the data.
     */
    private void getEnd(){
        int curState = start;
        float prob;

        for(int curIt = 0; curInt < iterations; curIt++){
            prob = rand.Float();
        }//end for

    }//end getEnd()

}//end Markov class
