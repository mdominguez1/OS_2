import java.util.Scanner; 
import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @Author - Melchor Dominguez
 * Machine Class that will make a finite state machine
 */
public class Machine{ 

    /**
     * Main functon that starts and collects results from all thread
     */
    public static void main(String[] args){

        /** Scanner to get input from keyboard*/
        Scanner sc = new Scanner(System.in);
        
        //Prompt user for how many machines to create
        System.out.print("How many Finite State Machines to create? > ");
        /** int that holds number of finite state machines */
        int fsm = sc.nextInt();
        
        //Prompt user for how many iterations to perform
        System.out.print("How many iterations for each machine? > ");
        /** int that holds number of iterations*/
        int iterations = sc.nextInt();
        
        //Prompt user for how many threads to create
        System.out.print("How many threads? > ");
        /** int that holds number of threads*/
        int threads = sc.nextInt();

        System.out.print("Please enter input fileName > ");
        /** String that holds file name */
        String filename = sc.next();
        File file = new File(filename);
        
        // Initialize Data for the finite state machines to use
        Data data = new Data(file);
        data.printMatrix();
        
        final ExecutorService pool = Executors.newFixedThreadPool(threads);
        List<Future<int[]>> threadReturns = new ArrayList<>();
        
        //Start all the threads 
        for(int i = 0; i < threads; i++){
            final Future<int[]>> threadReturn =
                                     pool.submit(new Markov(startState, iterations, stuff));    
            threadReturns.add(threadReturn);            
        }//end for
        
        //check if enough finite state machines have been returned
        if(threadReturns.size() >= fsm)
            pool.shutdown()
    }//end machine
}//end Machine class
