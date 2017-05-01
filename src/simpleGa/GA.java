package simpleGa;

public class GA {

    public static void main(String[] args) {
    	//Crea un individuo (cromosoma) aleatorio de 20 elementos 
    	Individual ind = new Individual();
    	ind.generateIndividual();
    	
    	//Se trae la codificaci�n del cromosoma
    	String chrome = ind.toString();
    	
    	//Se grafica el cromosoma
    	GraphIndividual frame = new GraphIndividual(chrome);
    	frame.runGraphic();
    	
    	
    	/*
        // Set a candidate solution
        FitnessCalc.setSolution("1111000000000000000000000000000000000000000000000000000000001111");

        // Create an initial population
        Population myPop = new Population(50, true);
        
        // Evolve our population until we reach an optimum solution
        int generationCount = 0;
        while (myPop.getFittest().getFitness() < FitnessCalc.getMaxFitness()) {
            generationCount++;
            System.out.println("Generation: " + generationCount + " Fittest: " + myPop.getFittest().getFitness());
            myPop = Algorithm.evolvePopulation(myPop);
        }
        System.out.println("Solution found!");
        System.out.println("Generation: " + generationCount);
        System.out.println("Genes:");
        System.out.println(myPop.getFittest());
    	 */
    }
}