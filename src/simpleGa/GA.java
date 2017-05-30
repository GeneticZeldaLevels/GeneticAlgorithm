package simpleGa;

import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.io.PrintWriter;

public class GA {

    public static void main(String[] args) {
    	//Crea un individuo (cromosoma) aleatorio de 20 elementos 
    	FitnessCalc.setRadius(1);
    	FitnessCalc.setEnemyLimit(3);
    	FitnessCalc.setHallwayProbability( (float) 0.8 );
    	FitnessCalc.setGraphQuantity( 8 );
    	
    	Population factibles = new Population( 50, true, true );
    	Population infactibles = new Population( 50, true, true );
    	
    	int generationLimit = 20;
    	int actualFitness = factibles.getFittest().getFitness();
    	int lastFitness = 0;
    	int repeatedFitness = 0;
        // Evolve our population until we reach an optimum solution
        int generationCount = 0;
        while( repeatedFitness < generationLimit ){
        	generationCount++;
        	System.out.print( factibles.size()+" - "+infactibles.size()+" ---------------- " );
        	
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
        	
        	System.out.print( factibles.size()+" - "+infactibles.size() );
        	System.out.println();
        	System.out.println("Generation: " + generationCount + " Fittest: " + factibles.getFittest().getFitness());
        	if( lastFitness != actualFitness ){
        		repeatedFitness = 0;
        		lastFitness = actualFitness;
        	}else
        		repeatedFitness++;
        }
        
        System.out.println("Solution found!");
        System.out.println("Generation: " + generationCount);
        System.out.println("Genes:");
        System.out.println( factibles.getFittest() );
    	
        String chrome = factibles.getFittest().toString();

        //Se grafica el cromosoma
        GraphIndividual frame = new GraphIndividual(chrome);
        frame.runGraphic();

	      try{
	      PrintWriter writer = new PrintWriter("map.json", "UTF-8");
	      writer.println(factibles.getFittest().toJSON());
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





/*Individual ind = new Individual();
ind.generateIndividual();
int cont = 0;
while(true){
	ind = new Individual();
	ind.generateIndividual();
	cont++;
	if( ind.getMonstersOutPlaced() == 0 && ind.getRunnableGraph() == 0 )
		break;
}

//Se trae la codificaci�n del cromosoma
String chrome = ind.toString();
System.out.println(cont);
//Se grafica el cromosoma
GraphIndividual frame = new GraphIndividual(chrome);
frame.runGraphic();*/