package com.ctk150230.markov;

/**
 * A custom data structure used to hold an action's name and its potential
 * future rewards value.
 *
 * @see valueIteration
 * @see stateNode
 *
 * @author Charles Krol
 * @since 2017-10-15
 */
class jValueNode {
    String bestAction;
    double expectedDiscountedReward;

    /**
     * Default constructor creates a dummy node used in comparisons in finding the best action
     */
    jValueNode() {
        this.bestAction = "dummy";
        this.expectedDiscountedReward = -1000000.0;
    }

    /**
     * Creates a jValueNode that holds the name of the best action and it's expected discounted rewards value
     * @param bestAction the name of the best action
     * @param expectedDiscountedReward the value of the expectedDiscountedRewards
     */
    jValueNode(String bestAction, double expectedDiscountedReward) {
        this.bestAction = bestAction;
        this.expectedDiscountedReward = expectedDiscountedReward;
    }

    /**
     * Getter to return the best action name
     * @return returns the name of the best action
     */
    String getBestAction() {
        return bestAction;
    }

    /**
     * Getter to return the expectedDiscountedReward value
     * @return the value of the best action's expected discounted rewards
     */
    double getExpectedDiscountedReward() {
        return expectedDiscountedReward;
    }
}
