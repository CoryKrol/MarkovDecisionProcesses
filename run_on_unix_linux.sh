#! /bin/bash

if [ $# -eq 4 ]
  then
    mkdir -p out/production/MarkovDecisionProcesses
    find ./src/com/ctk150230/markov -name "*.java" > sources.txt
    javac @sources.txt -d ./out/production/MarkovDecisionProcesses

    java -cp ./out/production/MarkovDecisionProcesses com.ctk150230.markov.Main $1 $2 $3 $4
else
    echo "Error: Not enough arguments supplied, please provide <number_of_states_in_MDP> <num_of_possible_actions> <input_filename> <discount_factor_𝛄>"
fi