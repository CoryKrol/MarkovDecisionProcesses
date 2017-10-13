package com.ctk150230.markov;

import java.util.LinkedHashMap;

/**
 * <h1>stateNode</h1>
 * <br>
 * A class to hold state information used in the Markov Decision Process<br>
 *     The class accepts an arrary containing the name, reward, and transition data<br>
 *         for each action. It also accepts a LinkedHashMap of other of all other states used<br>
 *             to fill the stateLinks maps which are all the states we can reach from this state <br><br.
 *
 * The <b>actionList</b> contains all the actions we can take here, inside the Map are all states we can<br>
 *     reach with said action and the probabilities of going to each state. <br><br>
 *
 * The <b>stateLinks</b> Map contains all states we can reach from this one and is used to advance to the<br>
 *     next state in the Value Iteration algorithm
 *
 * @see LinkedHashMap
 *
 * @author Charles Krol
 * @since 2017-10-13
 */
public class stateNode {

    String stateName;
    int stateReward;

    // Holds Transition Data
    // Access first HashMap using an Action name
    // Access inner HashMap using a State name
    // Inner HashMap holds transition probabilities
    LinkedHashMap<String, LinkedHashMap<String, Double>> actionList;
    LinkedHashMap<String, stateNode> stateLinks;

    /**
     * Class constructor used to initialize all data to default values when they are<br>
     *     added to the stateMap in the Main class during it's initialization
     */
    stateNode(){
        stateName = " ";
        stateReward = 0;
        actionList = new LinkedHashMap<>();
        stateLinks = new LinkedHashMap<>();

    }

    /**
     * Called from the main function when processing the data from the input file, used<br>
     *     to change default values set during initialization of the stateMap.
     * @param data a String array containing values from 1 line of the input file
     * @param stateMap a HashMap containing all state nodes used to add states we can <br>
     *                 transition to to the stateLinks HashMap
     */
    public void initState(String[] data, LinkedHashMap<String, stateNode> stateMap){
        stateName = data[0];
        stateReward = Integer.parseInt(data[1]);
        processTransitionData(data, stateMap);

    }

    /**
     * A Helper function to process the state data into the correct HashMaps. Mainly added to keep the code clean.
     * @param data a String array containing values from 1 line of the input file
     * @param stateMap a HashMap containing all state nodes used to add states we can <br>
     *                 transition to to the stateLinks HashMap
     */
    private void processTransitionData(String[] data, LinkedHashMap<String, stateNode> stateMap){
        // Start at data[2] as the first 2 entries are
        // The stateName and stateReward
        for(int i = 2; i < data.length; i += 3){
            // Add inner HashMap for action if it doesn't exist
            if(!actionList.containsKey(data[i]))
                actionList.put(data[i], new LinkedHashMap<>());

            // Add node to stateMap if it is not in there
            if(!stateLinks.containsValue(data[i + 1]))
                stateLinks.put(data[i + 1], stateMap.get(data[i + 1]));

            // Add transition data for state to corresponding action
            actionList.get(data[i]).put(data[i + 1], Double.parseDouble(data[i + 2]));
        }

    }
}
