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

    /**
     * Starts the finite state machine
     * @param startState - the Start state for the finite state machine
     * @param iterations - how many times the finite state machine will iterate before done
     * @param stuff - The data that contains a unique ID, the result of the execution, and
     *                the representation of the finite state machine
     */
    protected Markov(int startState, int iterations, Data stuff){
        start = startState;
        this.iterations = iterations;
        data = stuff;
    }//end constructor
    
    /**
     * Run method which will start a thread
     */
    public void run(){
        getEnd();
    }//end run()
    
    /**
     * Method to go through the finite state machine and store the 
     * result in the data.
     */
    private void getEnd(){
        int curState = start;
        float prob;
        data.pushState(curState);
        for(int curIt = 0; curIt < iterations; curIt++){
            prob = ThreadLocalRandom.current().nextFloat();
            curState = data.getState();
            data.pushState(curState);
        }//end for
        
        data.printResults();
    }//end getEnd()

}//end Markov class
