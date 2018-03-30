# GeneticAlgorithm
GeneticAlgorithm is the Java application that creates randomized levels for a Legend Of Zelda’s style game using a Feasible-Infeasible Two Populations Genetic Algorithm, generates a JSON file that after obtained, can be deployed in a Unity simple level renderer that is written in this [repo](https://github.com/GeneticZeldaLevels/GeneticLevels)

## Instructions
If you downloaded this project and want a test, just set up your favorite Java IDE and run the GA.java file, which it have a main test of the genetic algorithm process, feel free to tweak it like you want to. At the end of the execution (it takes a lot of time to end an execution) it will deploy a simple visualization of the map that created the algorithm.

The genetic algorithm used in here is the Feasible-Infeasible two populations as described in in this [article](https://www.researchgate.net/publication/222668244_Introducing_a_Feasible-Infeasible_Two-Population_FI-2Pop_Genetic_Algorithm_for_Constrained_Optimization_Distance_Tracing_and_No_Free_Lunch)

Here it's an example run of the algorithm:

After 50 generations, the result it's something like this:

![Resulting map](https://github.com/GeneticZeldaLevels/GeneticAlgorithm/blob/master/img/Screenshot%20from%202018-03-29%2019-02-30.png)

In this example, the gray square it's the final room and the yellow one it's the first room, the little blue squares are enemies, the green squares are inner rooms and the red lines of squares are corridor that try to connect the rooms.

For this example, we've set a maximum of generations of 50 for fast results, but you can set the parameters as you see fit. 