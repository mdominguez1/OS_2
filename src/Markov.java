import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.Callable;
/**
 * @Author - Melchor Dominguez
 * @Version - 4.16.2018
 * Markov class create the finite state machine
 *
 */
public class Markov implements Callable<int[]>{
    
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
    public int[] call(){
        int[] results = new int[data.getSize];

        results = Arrays.fill(results, 0);

        while(true){
            /** 
             * Do work until interuppted to stop
             */
            if(Thread.currentThread().isInterrupted()){
                System.out.println("I'm interrupted!");
                break;
            }//end if
            int[] singleResults = getEnd();

            for(i = 0; i < results.length; i++){
                results = results[i] + singleResults[i];    
            }
        }//end while
        
        return results;

    }//end run()
    
    /**
     * Method to go through the finite state machine and store the 
     * result in the data.
     * @return - the results of a single finite state machine 
     */
    private int[] getEnd(){
        /** grab the start state for a machine*/
        int curState = start;
        /** float probability to determine the random number for getting the next state*/
        float prob;
        /** int array to hold how many times a state was accessed*/
        int[] result = new int[data.getSize];
        //Fill the result with zero to begin with
        Arrays.fill(result, 0);

        //data.pushState(curState);

        for(int curIt = 0; curIt < iterations; curIt++){
            prob = ThreadLocalRandom.current().nextFloat();
            curState = getState(curState, prob);
            result[curState]++;
        }//end for
        

        //Testing Purpose:
        //Print the result for a single 
        for(int i = 0; i < results.length; i++){
            System.out.println("State " + Integer.toString(i) + ": " +
                                Integer.toString(results[i]));
        }//end for

    }//end getEnd()
    
    /**
     * Returns the next State depending on the probability of the finite state machine
     * @param curState - the current state which the finite state state machine is in
     * @param rNum - random number to use
     * @return - the int representing the end state
     */
    private int getState(int curState, float rNum){
        float[][] line = data.getLine();
        for(int i = 0; i < line.length; i++){
            if(rNum < line[i][curState]){
                int newState = i;
                return newState;
            }//end if
        }//end for
        return -1;
    }//end getState

}//end Markov class
