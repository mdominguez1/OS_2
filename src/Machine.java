import java.util.Scanner; 
import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.List;
import java.util.concurrent.Future;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.InputMismatchException;
/**
 * @Author - Melchor Dominguez, Terence Holmes
 * Machine Class that will make a finite state machine
 */
public class Machine{

    /**
     * Main functon that starts and collects results from all thread
     */
    public static void main(String[] args){

        /** int that holds number of finite state machines */
        int fsm = 0;
        /** int that holds number of iterations to perform */
        int iterations = 0;
        /** int that holds number of threads */
        int threads = 0;
        /** String that holds file name */
        String filename = "";
        /** File holding finite state machine info */
        File file = null;
        
        /** Scanner to get input from keyboard*/
        Scanner sc = new Scanner(System.in);
        try{
        //Prompt user for how many machines to create
        System.out.print("How many Finite State Machines to create? > ");
        fsm = sc.nextInt();
        
        //Prompt user for how many iterations to perform
        System.out.print("How many iterations for each machine? > ");
        iterations = sc.nextInt();
        
        //Prompt user for how many threads to create
        System.out.print("How many threads? > ");
        threads = sc.nextInt();
        }
        
        catch(InputMismatchException ime){
            System.out.println("Please ensure that input is an integer!");
            System.exit(1);
        }
        catch(NumberFormatException nfe){
            System.out.println("Please ensure that input is an integer!");
            System.exit(1);
        }
        System.out.print("Please enter input fileName > ");
        filename = sc.next();
        file = new File(filename);
        if(!file.exists() || file.isDirectory()){
            System.out.println("File doesn't exist or is a directory!");
            System.exit(1);
        }
        // Initialize Data for the finite state machines to use
        Data data = new Data(file);
        
        final ExecutorCompletionService<Data> complete = poolParty(threads, 
                            data, file, iterations, fsm, args);
        finalize(data, fsm, complete);

        }//end main
        
        private static ExecutorCompletionService<Data> poolParty(int threads, Data data, File file, int iterations, int fsm, String[] args){
            /** Threadpool to have all the threads*/
            final ExecutorService pool = Executors.newFixedThreadPool(threads);
            /** */
            ExecutorCompletionService<Data> complete =  new ExecutorCompletionService<Data>(pool);
            int startState;
            if(args.length < 2){
                Random ran = new Random();
                startState = ran.nextInt(data.getSize() - 1);
            }else{
                startState = Integer.parseInt(args[1]);
            }

        for(int i = 0; i < fsm; i++){
            data = new Data(file);
            complete.submit(new Markov(startState, iterations, data));
        }//end for
        
        pool.shutdown();
        return complete;
        }

        private static void finalize(Data data, int fsm, ExecutorCompletionService<Data> complete){
            float[] finalResults = new float[data.getSize()];
            float total = 0;

            for(int i = 0; i < fsm; i++){
            try{
                final Future<Data> future = complete.take();
                final Data curData = future.get();
                //System.out.println(count);
                int[] result = curData.getResults();
                for(int j = 0; j < result.length; j++){
                    //System.out.println("-State " + j + ": " + result[j]);
                    finalResults[j] += result[j];
                    total+= result[j];
                }
            }catch(ExecutionException e){
                System.out.println("Error during thread");
            }catch(InterruptedException e){
                System.out.println("Interrupted thread");
            }//end try-catch
        }//end for
        
        for(int i = 0; i < finalResults.length; i++){
            System.out.println("-State " + i + ": " + finalResults[i]/total);
        }//for 
    }//end machine
}//end Machine class
