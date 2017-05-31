package simpleGa;

import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.io.PrintWriter;

public class GA {

    public static void main(String[] args) {
    	//Crea un individuo (cromosoma) aleatorio de 20 elementos 
    	FitnessCalc.setRadius(1);
    	FitnessCalc.setEnemyLimit(2);
    	FitnessCalc.setHallwayProbability( (float) 0.65 );
    	FitnessCalc.setGraphQuantity( 24 );
    	FitnessCalc.setFitnessFunction(0);
    	FitnessCalc.setMapSize(64);
    	FitnessCalc.generationLimit = 150;
    	
    	Population factibles = new Population( 50, true, true );
    	Population infactibles = new Population( 50, true, true );
    	
    	int generationLimit = 50;
    	int actualFitness = factibles.getFittest().getFitness();
    	int lastFitness = 0;
    	int repeatedFitness = 0;
        // Evolve our population until we reach an optimum solution
        int generationCount = 0;
        Individual max = factibles.getFittest();
        
        Population maxFactibles = factibles;
        int qtyMaxFactibles = factibles.getQuantityMaxFitness( actualFitness );
        
        while( repeatedFitness < generationLimit && generationCount < FitnessCalc.generationLimit ){
        	generationCount++;
        	
        	factibles = Algorithm.evolvePopulation(factibles);
        	
        	for( int i = 0; i < factibles.size(); i++ )
        		if( !factibles.getIndividual(i).isFactible() ){
        			infactibles.pushIndividual( factibles.removeIndividual(i) );
        		}
        	
        	infactibles = Algorithm.evolvePopulation(infactibles);
        	
        	for( int i = 0; i < infactibles.size(); i++ )
        		if( infactibles.getIndividual(i).isFactible() ){
        			factibles.pushIndividual( infactibles.removeIndividual(i) );
        		}
        	
        	actualFitness = factibles.getFittest().getFitness();
        	if( factibles.getFittest().getFitness() > max.getFitness() ) max = factibles.getFittest();
        		
        	System.out.println("Generation: " + generationCount + " Fittest: " + actualFitness +" Max Fitness: "+max.getFitness());
        	
        	if( lastFitness != actualFitness ){
        		repeatedFitness = 0;
        		lastFitness = actualFitness;
        	}else
        		repeatedFitness++;
        	
        	if( qtyMaxFactibles < factibles.getQuantityMaxFitness( max.getFitness() ) ){
        		qtyMaxFactibles = factibles.getQuantityMaxFitness( max.getFitness() );
        		maxFactibles = factibles;
        	}
        		
        }
        
        System.out.println("Solution found!");
        System.out.println("Generation: " + generationCount);
        System.out.println("Genes:");
        System.out.println( factibles.getFittest() );

        //Se grafica el cromosoma
        GraphIndividual frame = new GraphIndividual(factibles.getFittest());
        frame.runGraphic();

	    try{
	    	PrintWriter writer = new PrintWriter( "map.json", "UTF-8");
	    	//for( int i = 0; i < maxFactibles.size(); i++ ){
		    	//writer.println(maxFactibles.getIndividual(i).toJSON());
	    	//}
	    	writer.println(maxFactibles.getFittest().toJSON());
	    	writer.close();
	    } catch (IOException e) {
	    	// do something
	    }
    }
}


/*while(true){
ind = new Individual();
ind.generateIndividual();
cont++;
if( ind.getMonstersOutPlaced() == 0 && ind.getRunnableGraph() == 0 )
	break;
}
//System.out.println(cont);
System.out.println(ind.getFitness());*/


//Se trae la codificaci�n del cromosoma
/*String chrome = ind.toString();

//Se grafica el cromosoma
GraphIndividual frame = new GraphIndividual(chrome);
frame.runGraphic();*/

/*try{
PrintWriter writer = new PrintWriter("map.json", "UTF-8");
writer.println(ind.toJSON());
writer.close();
} catch (IOException e) {
// do something
}*/





/**/