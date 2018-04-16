import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.Callable;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
/**
 * @Author - Melchor Dominguez
 * @Version - 4.16.2018
 * Markov class create the finite state machine
 *
 */
public class Markov implements Callable<Data>{
    
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
    protected Markov(int startState, int iterations, BlockingQueue queue){
        start = startState;
        this.iterations = iterations;

        try{
            data = (Data)queue.take();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }//end constructor
    
    /**
     * Run method which will start a thread
     */
    public Data call(){

        while(true){
            //check if the thread is ever interrupted 
            if(Thread.currentThread().isInterrupted()){
                System.out.println("I'm interrupted!");
                break;
            }//end if
            synchronized(data){
                data.getEnd(start, iterations);
                System.out.println("Return");
                return data;
            }//end synchronize
        }//end while
        return null;
    }//end run()
}//end Markov class
