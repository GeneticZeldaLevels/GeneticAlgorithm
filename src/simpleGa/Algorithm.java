package simpleGa;

public class Algorithm {

    /* GA parameters */
    private static final double uniformRate = 0.5;
    private static final double mutationRate = 0.01;
    private static final int tournamentSize = 3;
    private static final boolean elitism = false;

    /* Public methods */
    
    
    // Evolve a population
    public static Population evolvePopulation(Population pop) {
        Population newPopulation = new Population(pop.size(), false, true);

        // Keep our best individual
        if (elitism) {
            newPopulation.saveIndividual(0, pop.getFittest());
        }

        // Crossover population
        int elitismOffset;
        if (elitism) {
            elitismOffset = 1;
        } else {
            elitismOffset = 0;
        }
        // Loop over the population size and create new individuals with
        // crossover
        
        if( pop.isFactible() ){
	        int factibleCounter = 0;
	        for( int i = 0; i < 5000; i++ ){
	        	Individual indiv1 = tournamentSelection(pop);
	            Individual indiv2 = tournamentSelection(pop);
	            
	            Individual newIndiv = crossover(indiv1, indiv2);
	            
	            newPopulation.pushIndividual( newIndiv);
	            
	            if( factibleCounter == 50 )
	        		break;
	            
	        	if( newIndiv.isFactible() ){
	            	factibleCounter++;
	        	}
	        }
        }else{
	    	for (int i = elitismOffset; i < pop.size(); i++) {
	            Individual indiv1 = tournamentSelection(pop);
	            Individual indiv2 = tournamentSelection(pop);
	            Individual newIndiv = crossover(indiv1, indiv2);
	            newPopulation.pushIndividual( newIndiv );
	        }
        }
        
        // Mutate population
        for (int i = elitismOffset; i < newPopulation.size(); i++) {
            mutate(newPopulation.getIndividual(i));
        }

        return newPopulation;
    }

    // Crossover individuals
    private static Individual crossover(Individual indiv1, Individual indiv2) {
        Individual newSol = new Individual();
        Individual rest;
        
        // Loop through genes
        int cap = Math.min( indiv1.elementsSize(), indiv2.elementsSize() ) - 1, i;
        
        for (i = 0; i < cap; i++) {
            // Crossover
            if (Math.random() <= uniformRate) {
                newSol.setElement(i, indiv1.getElement(i));
            } else {
                newSol.setElement(i, indiv2.getElement(i));
            }
        }
        
        if( indiv1.elementsSize() > indiv2.elementsSize() ) rest = indiv1;
        else rest = indiv2;
        
        for( int j = i ; j < rest.elementsSize()-1; j++ ){
        	
        	if (Math.random() <= uniformRate){
        		newSol.setElement( i, rest.getElement(i) );
        		i++;
        	}
        }
               
        if (Math.random() <= uniformRate) newSol.setElement(i, indiv1.getElement( indiv1.elementsSize()-1 ));
        else newSol.setElement(i, indiv2.getElement( indiv2.elementsSize()-1 ));
        
        newSol.generateChromosome();
        
        return newSol;
    }

    // Mutate an individual
    private static void mutate(Individual indiv) {
        // Loop through genes
    	byte x_range, y_range;
    	int i = 0;
    	Element e;
    	
    	if (Math.random() <= mutationRate) {
    		x_range = y_range = -1;
    		while( x_range < 0 || x_range > 31 ){
    			x_range = (byte) ( ((Math.random() * 100) % 9) - 4 );
    			x_range = (byte) ( indiv.getElement(i).getX() + x_range );
    		}
    		while( y_range < 0 || y_range > 31 ){
    			y_range = (byte) ( ((Math.random() * 100) % 9) - 4 );
    			y_range = (byte) ( indiv.getElement(i).getX() + y_range );
    		}
    		
        	e = new Room( x_range, y_range );
        	indiv.setElement(i, e);
        }
    	i++;
        for ( ; i < FitnessCalc.graphQuantity+1; i++) {
            if (Math.random() <= mutationRate) {
                // Create random gene
                x_range = y_range = -1;
        		while( x_range < 0 || x_range > 31 ){
        			x_range = (byte) ( ((Math.random() * 100) % 9) - 4 );
        			x_range = (byte) ( indiv.getElement(i).getX() + x_range );
        		}
        		while( y_range < 0 || y_range > 31 ){
        			y_range = (byte) ( ((Math.random() * 100) % 9) - 4 );
        			y_range = (byte) ( indiv.getElement(i).getX() + y_range );
        		}
                
                e = null;
                if( Math.random() < FitnessCalc.hallwayProbability ){
                	e = new Hallway( x_range, y_range );
                }else{
                	e = new Room( x_range, y_range );
                }
                indiv.setElement(i, e);
            }
        }
        
        for( ; i < indiv.elementsSize()-1; i++ ){
        	if (Math.random() <= mutationRate) {
        		x_range = y_range = -1;
        		while( x_range < 0 || x_range > 31 ){
        			x_range = (byte) ( ((Math.random() * 100) % 9) - 4 );
        			x_range = (byte) ( indiv.getElement(i).getX() + x_range );
        		}
        		while( y_range < 0 || y_range > 31 ){
        			y_range = (byte) ( ((Math.random() * 100) % 9) - 4 );
        			y_range = (byte) ( indiv.getElement(i).getX() + y_range );
        		}
            	e = new Monster( x_range, y_range );
            	indiv.setElement(i, e);
        	}
        }
        if (Math.random() <= mutationRate) {
        	x_range = y_range = -1;
    		while( x_range < 0 || x_range > 31 ){
    			x_range = (byte) ( ((Math.random() * 100) % 9) - 4 );
    			x_range = (byte) ( indiv.getElement(i).getX() + x_range );
    		}
    		while( y_range < 0 || y_range > 31 ){
    			y_range = (byte) ( ((Math.random() * 100) % 9) - 4 );
    			y_range = (byte) ( indiv.getElement(i).getX() + y_range );
    		}
        	e = new Room( x_range, y_range );
        	indiv.setElement(i, e);
        }
    }

    // Select individuals for crossover
    private static Individual tournamentSelection(Population pop) {
        // Create a tournament population
        Population tournament = new Population(tournamentSize, false, true);
        
        // For each place in the tournament get a random individual
        for (int i = 0; i < tournamentSize; i++) {
            int randomId = (int) (Math.random() * pop.size());
            tournament.pushIndividual( pop.getIndividual(randomId));
        }
        
        // Get the fittest
        Individual fittest = tournament.getFittest();
        
        return fittest;
    }
}