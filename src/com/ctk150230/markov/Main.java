package com.ctk150230.markov;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;

/**
 * <h1>Markov Decision Process</h1>
 *
 * A program that implements the Markov Decision Process Value Iteration Algorithm
 * to compute the optimal policy for a given starting state.
 *
 * @see stateNode
 * @see BufferedReader
 * @see FileReader
 * @see IOException
 * @see LinkedHashMap
 *
 * @author Charles Krol
 * @since 2017-10-13
 */
public class Main {

    public static void main(String[] args) throws IOException{
        if(args.length != 4) {
            usage();
            System.exit(1);
        }

        // Grab arguments program was launched with
        int numberOfStates = Integer.parseInt(args[0]);
        int numberOfActions = Integer.parseInt(args[1]);
        String fileName = args[2];
        double discountFactor = Double.parseDouble(args[3]);

        LinkedHashMap<String, stateNode> stateMap = initStateMap(fileName, numberOfStates);

        valueIteration valueList = new valueIteration(stateMap, 20, numberOfStates, numberOfActions, discountFactor);

    }

    /**
     * Used to display the proper usage if the correct number of arguments are not supplied when launching the
     * program from the console.
     */
    public static void usage(){
        System.out.println("Markov Decision Processes");
        System.out.println("Author: Charles Krol\n");
        System.out.println("Usage: java -c Main <number_of_states_in_MDP> <num_of_possible_actions> <input_filename> <discount_factor_\uD835\uDEC4>");
    }

    /**
     * Used to initialize the stateMap with all data from the file. A Helper function mainly here to keep the code clean.
     * @param fileName the name of the file containing the input data
     * @param numberOfStates the number of states in this MDP
     * @return the fully initialized stateMap holding stateNodes fully initialized with all data from the input file
     * @throws IOException
     */
    public static LinkedHashMap<String, stateNode> initStateMap(String fileName, int numberOfStates) throws IOException{

        FileReader fr = null;
        BufferedReader br = null;

        // HashMap to hold state nodes for easy access
        LinkedHashMap<String, stateNode> stateMap = new LinkedHashMap<>();

        // Holds lines read from input file
        String line;

        // String Array to hold lines from file split on whitespaces, (, )
        String[] splitStr;

        // Initialize Map to hold state information
        for(int i = 1; i <= numberOfStates; i++)
        {
            stateMap.put("s" + i, new stateNode());
        }

        try{
            //Create FileReader and BufferedReader
            fr = new FileReader(fileName);
            br = new BufferedReader(fr);

            // Read in line from file
            line  = br.readLine();

            // Keep track of the state currently being processed
            int currentState = 1;

            while(line != null){
                // Split string based on whitespaces, (, and )
                // Use replaceAll to replace ( and ) with white spaces
                // Then split on whitespaces
                splitStr = line.replaceAll("[()]", "").split("\\s");

                // Initialize corresponding state in stateMap
                stateMap.get("s" + currentState).initState(splitStr, stateMap);

                // Prepare to process next state
                currentState++;
                line = br.readLine();
            }
        } catch (FileNotFoundException e){
            System.out.println("Error: Filename " + fileName + " does not exist");
            System.exit(1);
        } finally {
            if (br != null)
                br.close();
            if (fr != null)
                fr.close();
            return stateMap;
        }
    }
}
