package com.ctk150230.markov;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <h1>Value Iteration</h1>
 *
 * This class computes the J* values and the optimal policies at each time division
 * for each state in the system and stores them in a custom data structure jValueNode.
 *
 * The jValueNode for each time and state pair is stored in an ArrayList accessed by the time
 * division which returns a LinkedHashMap accessed by the stateName value.
 *
 * @see stateNode
 * @see ArrayList
 * @see LinkedHashMap
 *
 * @author Charles Krol
 * @since 2017-10-15
 */


class valueIteration {

    // MDP Data
    int numOfIterations;
    int numOfStates;
    int numOfActions;
    double discountFactor;

    // MDP States
    LinkedHashMap<String, stateNode> stateMap;

    // Holds calculated J* Values and the actions which maximize the future rewards
    // for each state
    ArrayList<LinkedHashMap<String, jValueNode>> jValues;

    /**
     * Constructor which initializes all fields
     * After initializing the fields it loads LinkedHashMaps into the jValues ArrayList
     *
     * Then the J* Values for the first time division are copied over to the jValues List
     *
     * Then the J* Values for the rest of the time divisions are calculated and entered into the
     * ArrayList
     *
     * @param stateM the LinkedHashMap containing all states and data
     * @param iterations the number of iterations for the value iteration algorithm
     * @param states the number of states in the MDP
     * @param actions the number of actions in the MDP
     * @param discount the discount factor 𝛄
     */
    valueIteration(LinkedHashMap<String, stateNode> stateM, int iterations, int states, int actions, double discount){
        stateMap = stateM;
        numOfIterations = iterations;
        numOfStates = states;
        numOfActions = actions;
        discountFactor = discount;

        jValues = new ArrayList<>();

        // Initialize LinkedHashMaps in jValues list
        for(int i = 0; i < numOfIterations; i++)
            jValues.add(new LinkedHashMap<>());

        // Calculate jValues for all time iterations
        this.calculateFirstJValues();
        this.calculateRestJValues();
    }

    /**
     * Copies over each state's rewards to the jValues Map into the first time division
     */
    private void calculateFirstJValues(){
        // Copy over each state's reward to the jValues list for time t = 1
        for(Map.Entry<String, stateNode> entry : stateMap.entrySet())
            jValues.get(0).put(entry.getKey(), new jValueNode(bestFirstAction(entry.getValue()).getBestAction(), (double)entry.getValue().getStateReward()));
    }

    /**
     * Calculates the J values for each state at the rest of the time divisions
     */
    private void calculateRestJValues(){
        // Calculate the rest of the iterations
        for(int i = 1 ; i < numOfIterations; i++){
            // Calculate J Values for each state at time t = i
            for(Map.Entry<String, stateNode> entry : stateMap.entrySet()){
                // Add calculated entry to the HashMap inside jValues[i]
                jValues.get(i).put(entry.getKey(), maxAction(i, entry.getValue()));
            }
        }
    }

    /**
     * Calculate the best action at time = 1 for a given state based on the reward of the next state
     * Not used in calculation of current state's J Value, just picking the best next action
     *
     * @param currentState the current state we are in
     * @return a jValueNode for the next based state based only on it's rewards alone
     */
    private jValueNode bestFirstAction(stateNode currentState){
        LinkedHashMap<String, Double> potentialValueMap = new LinkedHashMap<>();

        // Iterate over each action in the current state
        for(Map.Entry<String, LinkedHashMap<String, Double>> action : currentState.getActionList().entrySet()){

            double actionValue = 0;
            // Iterate over each potential next state and add to the rewards value
            // actionValue += P(nextState)*Rewards(NextState)
            for(Map.Entry<String, Double> potentialNextState : action.getValue().entrySet()){
                actionValue += potentialNextState.getValue() * stateMap.get(potentialNextState.getKey()).getStateReward();
            }
            // Add the calculated actionValue and the action name to the potentialValueMap
            potentialValueMap.put(action.getKey(), actionValue);
        }

        // Create a dummy node with a bad Reward Value
        jValueNode maxAction = new jValueNode();

        // The first iteration is always copied over to replace the dummy node
        // Iterate over all calculated actionValues and find the maximum
        // Upon finding a new maximum value a new jValueNode is created with it's values
        for(Map.Entry<String, Double> action : potentialValueMap.entrySet()){
            if(maxAction.getExpectedDiscountedReward() < action.getValue())
                maxAction = new jValueNode(action.getKey(), action.getValue());
        }
        // Return the best action
        return maxAction;
    }

    /**
     * Calculates the J values for each action and determines the best action to take at each state.
     *
     * They action name and the J Value are entered into a jValueNode and returned to the calling
     * method which will copy the node into the jValues List
     * @param iterationNumber the current iteration number, used to grab the previous iteration's J
     *                        Value
     * @param currentState  the current node processing used to get the reward as well as the stateName
     *                      to pull the appropriate J Value out of the jValues List
     * @return a jValueNode containing the name of the best action to take on this state at the current
     * time division as well as the total reward gained from taking the action at this state
     */
    private jValueNode maxAction(int iterationNumber, stateNode currentState){
        // Used to hold calculated values before determining the maximum rewards value
        LinkedHashMap<String, Double> potentialValueMap = new LinkedHashMap<>();

        // Iterate over each action in the current state
        for(Map.Entry<String, LinkedHashMap<String, Double>> action : currentState.getActionList().entrySet()){
            // Grab state's reward value
            double actionValue = currentState.getStateReward();
            // Iterate over each potential next state and add to the rewards value
            // actionValue += 𝛄 * P(nextState)*J(nextState)
            for(Map.Entry<String, Double> potentialNextState : action.getValue().entrySet()){
                actionValue += discountFactor * potentialNextState.getValue() * jValues.get(iterationNumber - 1).get(potentialNextState.getKey()).getExpectedDiscountedReward();
            }
            // Add the calculated actionValue and the action name to the potentialValueMap
            potentialValueMap.put(action.getKey(), actionValue);
        }

        // Create a dummy node with a bad jValue
        jValueNode maxAction = new jValueNode();

        // The first iteration is always copied over to replace the dummy node
        // Iterate over all calculated actionValues and find the maximum
        // Upon finding a new maximum value a new jValueNode is created with it's values
        for(Map.Entry<String, Double> action : potentialValueMap.entrySet()){
            if(maxAction.getExpectedDiscountedReward() < action.getValue())
                maxAction = new jValueNode(action.getKey(), action.getValue());
        }
        // Return the best action
        return maxAction;
    }

    /**
     * Getter to return the calculated jValues list for output to console in the main class
     * @return jValues ArrayList
     */
    ArrayList<LinkedHashMap<String, jValueNode>> getjValues() {
        return jValues;
    }
}
