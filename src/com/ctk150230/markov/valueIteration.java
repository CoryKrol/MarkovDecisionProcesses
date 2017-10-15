package com.ctk150230.markov;

import sun.awt.image.ImageWatched;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class valueIteration {

    int numOfIterations;
    int numOfStates;
    int numOfActions;
    double discountFactor;
    LinkedHashMap<String, stateNode> stateMap;


    ArrayList<LinkedHashMap<String, jValueNode>> jValues;

    public valueIteration(LinkedHashMap<String, stateNode> stateM, int iterations, int states, int actions, double discount){
        stateMap = stateM;
        numOfIterations = iterations;
        numOfStates = states;
        numOfActions = actions;
        discountFactor = discount;

        jValues = new ArrayList<>();

        for(int i = 0; i < numOfIterations; i++)
            jValues.add(new LinkedHashMap<>());

        this.calculateFirstJValues();
        this.calculateRestJValues();
    }

    private void calculateFirstJValues(){
        for(Map.Entry<String, stateNode> entry : stateMap.entrySet())
            jValues.get(0).put(entry.getKey(), new jValueNode("first", (double)entry.getValue().getStateReward()));
    }

    private void calculateRestJValues(){
        for(int i = 1 ; i < numOfIterations; i++){
            for(Map.Entry<String, stateNode> entry : stateMap.entrySet()){
                jValues.get(i).put(entry.getKey(), maxAction(i, entry.getValue()));
            }
        }
    }

    private jValueNode maxAction(int iterationNumber, stateNode currentState){
        LinkedHashMap<String, Double> potentialValueMap = new LinkedHashMap<>();

        for(Map.Entry<String, LinkedHashMap<String, Double>> action : currentState.getActionList().entrySet()){
            double actionValue = currentState.getStateReward();
            for(Map.Entry<String, Double> potentialNextState : action.getValue().entrySet()){
                actionValue += discountFactor * potentialNextState.getValue() * jValues.get(iterationNumber - 1).get(potentialNextState.getKey()).getExpectedDiscountedReward();
            }
            potentialValueMap.put(action.getKey(), actionValue);
        }

        jValueNode maxAction = new jValueNode();
        for(Map.Entry<String, Double> action : potentialValueMap.entrySet()){
            if(maxAction.getExpectedDiscountedReward() < action.getValue())
                maxAction = new jValueNode(action.getKey(), action.getValue());
        }
        return maxAction;
    }
}
