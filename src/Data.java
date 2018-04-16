import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
/**
 * @Author - Melchor Dominguez, Terence Holmes
 * @Version - 4.16.2018
 * Data class that i don't know what it does yet.
 */
public class Data{
    
    /** int to hold a unique ID for the Data class*/
    private int uniqueID;

    /** The max number for a unique ID for the Data class*/
    private final int IDMAX = 1000;
    
    /** Matrix to hold the probabilities of direction of the finite state machine*/
    private float[][] matrix;
    
    /** Matrix transformed to number line probabilities */
    private float[][] line;
    
    /** Array to hold how many iterations a state landed in*/
    private int[] results;

    /**
     * Gets data required to make the finite state machines.
     * @param - file where the data would come from.
     */
    public Data(File file){
        parseData(file);
        Arrays.fill(results, 0);
    }//end constructor
    
    /**
     * Method that will go through the input file.
     * @param File - file to hold data
     * @throws IllegalArgumentException - if File is empty.
     * @throws FileNotFoundException - if the file does not exist. 
     * @throws IOException - If an I/O error occurs
     */
    protected boolean parseData(File file){
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            /** String to read a line from the files*/
            String line;
            /** Integer to track what line number of the file there is*/
            int numLine = 0;
            
            //Read through the lines in the file
            while((line = br.readLine()) != null){
                /** String array to split the lines by spaces*/
                String[] splitLine = line.split(" ");
                
                //Check if the line is the first line of the file
                if(numLine == 0){
                    /** Track how many elements are in the line*/
                    int size = Integer.parseInt(splitLine[0]);
                    //Initialize the matrix to be the size specified
                    matrix = new float[size][size];
                    //Initialize the size of the number line
                    this.line = new float[size][size];
                    //Initialize the size of the result array
                    this.results = new int[size];
                }else{  
                    /** Integer to track the column number present*/
                    int column = 0;
                    /** Integer to track the row number*/
                    int row = numLine - 1;
                    // interate through the line to add element to the matrix
                    while(column < matrix.length){
                        //Add element from the line to the matrix
                        float elem = Float.parseFloat(splitLine[column]);
                        matrix[row][column] = elem;
                        column++;
                    }//end while
                }//end if-else

                numLine++;
            
            }//end while

        }catch(IllegalArgumentException e){
            System.err.println("IllegalArgumentException: " + e.getMessage());
            return false;
        }catch(FileNotFoundException e){
            System.err.println("FileNotFoundException: " + e.getMessage());
            return false;
        }catch(IOException e){
            System.err.println("IOException: " + e.getMessage());
            return false;
        }//end try-catch
        //Call to make the number line 
        makeLine();
        return true;
    }//end parseData
    
    /**
     * Returns the size of the matrix
     * @return - size of matrix
     */
    protected int getSize(){
        return matrix.length;
    }//end getSize()
    
    /**
     * Returns the number line to use for finite state machine
     * @return - number line for data inserted
     */
    protected float[][] getLine(){
        return line;
    }//end getMatrix

    /**
     * Method to print the matrix
     */
    protected void printMatrix(){
        for(int i = 0; i < matrix.length; i++){
            System.out.print(Integer.toString(i) + ": ");
            for(int j = 0; j < matrix.length; j++){
                System.out.print(Float.toString(line[i][j]) + " ");
            }//end for
            System.out.println("");
        }//end for
    }//end printMatrix()
    
    /**
     * Method which will make a number line to determine the probability
     * of state directions
     */
    private void makeLine(){
        
        //Figure out the numLine to use
        for(int c = 0; c < matrix.length; c++){
            for(int r = 0; r < matrix.length; r++){

                if(r > 0)
                    line[r][c] = line[r-1][c] + matrix[r][c];
                else
                    line[r][c] = matrix[r][c];
            }//end for
        }//end for

    }//end makeLine()
    
    /**
     * change the unique ID for the 
     */
    protected void setID(){
        uniqueID = ThreadLocalRandom.current().nextInt(IDMAX);
    }//end setID()
    
    /**
     * get the unique ID 
     */
    protected int getID(){
        return uniqueID;
    }//end getID()
    
    protected int[] getResults(){
        return results;
    }//end getResults()
      
    /** 
     * Method to go through the finite state machine and 
     * store the result in the data.
     * @param startState - int that shows the beginning state of the finite 
     *                      state machine
     * @param iterations - the number of times the finite state machine will 
     *                      go to a new state
     */
    protected void getEnd(int startState, int iterations){
        Arrays.fill(results, 0);
        int curState = startState;
        float prob;
        for(int curIt = 0; curIt < iterations; curIt++){
            prob = ThreadLocalRandom.current().nextFloat();
            curState = getState(curState, prob);
            results[curState]++;
        }//end for

        for(int i = 0; i < results.length; i++){
            System.out.println("State " + Integer.toString(i) + ": " + 
                                Integer.toString(results[i]));
        }//end for

    }//end getEnd()
    
    /**
     * Returns the next State depending on the probability of the finite state machine
     * @param curState - the current state which the finite state machien is in
     * @param rNum - random number to use 
     * @return - the int representing the end state 
     */
    private int getState(int curState, float rNum){
        for(int i = 0; i < line.length; i++){
            if(rNum < line[i][curState]){
                int newState  = i;
                return newState; 
            }//end if
        }//end for
        return -1;
    }//end getState()

}//end Data class
