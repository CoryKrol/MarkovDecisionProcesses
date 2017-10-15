package com.ctk150230.markov;

public class jValueNode {
    String bestAction;
    double expectedDiscountedReward;

    public jValueNode() {
        this.bestAction = "dummy";
        this.expectedDiscountedReward = -1000000.0;
    }

    public jValueNode(String bestAction, double expectedDiscountedReward) {
        this.bestAction = bestAction;
        this.expectedDiscountedReward = expectedDiscountedReward;
    }

    public String getBestAction() {
        return bestAction;
    }

    public double getExpectedDiscountedReward() {
        return expectedDiscountedReward;
    }
}
