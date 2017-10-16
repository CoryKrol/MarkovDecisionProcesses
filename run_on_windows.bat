if ("%4"=="") goto error


dir /s /B *.java > sources.txt
mkdir out
mkdir out\production
mkdir out\production\MarkovDecisionProcesses\
javac @sources.txt -d out\production\MarkovDecisionProcesses\

java -cp out\production\MarkovDecisionProcesses\ com.ctk150230.markov.Main %1 %2 %3 %4

:error
@echo Error: Not enough arguments supplied, please provide <number_of_states_in_MDP> <num_of_possible_actions> <input_filename> <discount_factor_𝛄>
exit 1