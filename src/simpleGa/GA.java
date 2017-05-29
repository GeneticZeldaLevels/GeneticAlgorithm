package simpleGa;

import java.io.IOException;
import java.io.PrintWriter;

public class GA {

    public static void main(String[] args) {
    	//Crea un individuo (cromosoma) aleatorio de 20 elementos 
    	Individual ind;
    	int cont = 0;
    	while(true){
    		ind = new Individual();
    		ind.generateIndividual();
    		cont++;
    		if( ind.getMonstersOutPlaced() == 0 && ind.getRunnableGraph() == 0 && ind.getFitness() >= 0.6)
    			break;
    	}
    	
    	System.out.println(cont);
    	
    	System.out.println(ind.getFitness());
    	
    	//Se trae la codificación del cromosoma
    	String chrome = ind.toString();
    	
    	System.out.println( chrome );
    	
    	//Se grafica el cromosoma
    	GraphIndividual frame = new GraphIndividual(chrome);
    	frame.runGraphic();

    	try{
    	    PrintWriter writer = new PrintWriter("map.json", "UTF-8");
    	    writer.println(ind.toJSON());
    	    writer.close();
    	} catch (IOException e) {
    	   // do something
    	}
    	
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