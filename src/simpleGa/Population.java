package simpleGa;

import java.util.ArrayList;

public class Population {
	
	ArrayList<Individual> individuals;
    //Individual[] individuals;
    boolean factible;
    /*
     * Constructors
     */
    // Create a population
    public Population(int populationSize, boolean initialise, boolean factibles) {
        individuals = new ArrayList<Individual>();
        
        if( factibles )
        	factible = factibles;
        // Initialise population
        if (initialise) {
            // Loop and create individuals
            for (int i = 0; i < populationSize; ) {
                Individual newIndividual = new Individual();
                newIndividual.generateIndividual();
                if( factibles ){
                	if( newIndividual.isFactible() ){
                		pushIndividual( newIndividual);
                		i++;
                	}
                }else{
                	if( !newIndividual.isFactible() ){
                		pushIndividual( newIndividual);
                		i++;
                	}
                }
            }
        }
    }

    /* Getters */
    public Individual getIndividual(int index) {
        return individuals.get(index);
    }

    public Individual getFittest() {
        Individual fittest = individuals.get(0);
        // Loop through individuals to find fittest
        for (int i = 0; i < size(); i++) {
            if (fittest.getFitness() <= getIndividual(i).getFitness()) {
                fittest = getIndividual(i);
            }
        }
        return fittest;
    }

    /* Public methods */
    // Get population size
    public int size() {
        return individuals.size();
    }

    // Save individual
    public void saveIndividual(int index, Individual indiv) {
	    individuals.set(index, indiv);
    }
    
    public void pushIndividual(Individual indiv ){
    	individuals.add( indiv );
    }
    
    public boolean isFactible(){
    	return factible;
    }
}