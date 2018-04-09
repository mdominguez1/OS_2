import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
/**
 * @Author - Melchor Dominguez, Terence Holmes
 * @Version - 4.16.2018
 * Data class that i don't know what it does yet.
 */
public class Data{
    
    /** int to hold a unique ID for the Data class*/
    private static final int uniqueID = 1300135;
    
    /** Matrix to hold the probabilities of direction of the finite state machine*/
    private float[][] matrix;

    private float[][] line;

    /**
     * Gets data required to make the finite state machines.
     * @param - file where the data would come from.
     */
    public Data(File file){
        parseData(file);
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
            
            String line;
            int numLine = 0;
            
            while((line = br.readLine()) != null){
                
                String[] splitLine = line.split(" ");

                if(numLine == 0){
                    int size = Integer.parseInt(splitLine[0]);
                    matrix = new float[size][size];
                    this.line = new float[size][size];
                }else{  
                    int column = 0;
                    int row = numLine - 1;
                    while(column < matrix.length){
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
        makeLine();
        return true;
    }//end parseData
    
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
     * Returns the end state after an iterations and counts it
     * @param curState - the current state which the finite state machine is in
     * @param rNum - random number to use 
     * @return - the int representing the end state
     */
    protected int getState(int curState, float rNum){
        int newState = 0;
        for(int i = 0; i < line.length; i++){
            if(rNum < line[i][curState]){
                newState = i;
                break;
            }//end if
        }//end for
    }//end getState()   
    
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

    }//end getState()

}//end Data class
