/**
 * @Author - Melchor Dominguez
 * Machine Class that will make a finite state machine
 */
public class Machine{
    
    private Machine(){

    }//end constructor
    
    /**
     * Main functon that starts and collects results from all thread
     */
    public static void main(){
        
        /** Scanner to get input from keyboard*/
        Scanner sc = new Scanner(System.in);
        
        //Prompt user for how many machines to create
        System.out.println("How many Finite State Machines to create? > ");
        /** int that holds number of finite state machines */
        int fsm = sc.nextInt();
        
        //Prompt user for how many iterations to perform
        System.out.println("How many iterations for each machine? > ");
        /** int that holds number of iterations*/
        int iterations = sc.nextInt();
        
        //Prompt user for how many threads to create
        System.out.println("How many threads? > ");
        /** int that holds number of threads*/
        int threads = sc.nextInt();

        System.out.println("Please enter input fileName > ");
        /** String that holds file name */
        String filename = sc.nextLine();
        File file = new File(filename);
    }//end machine
}//end Machine class
