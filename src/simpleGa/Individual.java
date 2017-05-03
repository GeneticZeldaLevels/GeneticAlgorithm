package simpleGa;

import java.util.HashMap;

public class Individual {

    static int defaultChromosomeLength = 400;
    private byte[] chromosome;
    // Cache
    private int fitness;
    private int chromosomeCnt;
    private byte[][] graph;
    private HashMap< Integer, Element > elements;
    
    public Individual(){
    	graph = new byte[32][32];
    	chromosome = new byte[defaultChromosomeLength];
    	fitness = 0;
    	chromosomeCnt = 0;
    	elements = new HashMap<Integer, Element >();
    }
    
    // Create a random individual
    public void generateIndividual() {
    	Hallway h;
    	Room r;
    	Monster m;
    	byte[] coded;
    	int i = 0;
    	
    	//Se añade el cuarto donde debe iniciar
    	r = new Room();
    	coded = r.codeChromosome();
    	addGeneToChromosome(coded);
    	elements.put( i, r );
    	
    	//Se añaden los demas elementos  
        for ( i = 1; i <= 18; i++) {
            byte gene = (byte) ( (Math.random() * 100) % 3 );
            if( gene == 0 ){
            	h = new Hallway();
            	coded = h.codeChromosome();
            	elements.put( i, h );
            	//System.out.println("hallway - "+ h.getX() +" - "+h.getY()+" - "+h.getLength()+" - "+h.getDirection());
            }else if( gene == 1 ){
            	r = new Room();
            	coded = r.codeChromosome();
            	elements.put( i, r );
            	//System.out.println("room - "+ r.getX() +" - "+ r.getY() +" - "+r.getWidth()+" - "+ r.getBreadth() );
            }else{
            	m = new Monster();
            	coded = m.codeChromosome();
            	elements.put( i, m );
            	//System.out.println("monster - "+ m.getX() +" - "+m.getY());
            }
            addGeneToChromosome(coded);
        }
        
        //Se añade el cuarto donde debe finalizar
    	r = new Room();
    	coded = r.codeChromosome();
    	addGeneToChromosome(coded);
    	elements.put( i, r );
    	
    	//Se crea el grafo del mapa
    	createGraph();
    }
    
    private void addGeneToChromosome( byte[] gene ){
    	for( int j = 0; j < gene.length; j++ ){
    		chromosome[chromosomeCnt] = gene[j];
    		chromosomeCnt++;
    	}
    }
    
    private void createGraph(){
    	for( int i = 0; i < elements.size(); i++ )
    		graph = elements.get(i).drawGraph(graph);
    }
    
    /* Getters and setters */
    // Use this if you want to create individuals with different gene lengths
    public static void setDefaultChromosomeLength(int length) {
    	defaultChromosomeLength = length;
    }
    
    public byte getGene(int index) {
        return chromosome[index];
    }

    public void setGene(int index, byte value) {
        chromosome[index] = value;
        fitness = 0;
    }

    /* Public methods */
    public int size() {
        return chromosome.length;
    }

    public int getFitness() {
        if (fitness == 0) {
            fitness = FitnessCalc.getFitness(this);
        }
        return fitness;
    }
    
    public int getAllElementsIn(){
    	return FitnessCalc.getAllElementsIn(this);
    }
    
    public int getMonstersOutPlaced(){
    	return FitnessCalc.getMonstersOutPlaced(this);
    }
    
    public int getRunnableGraph(){
    	return FitnessCalc.getRunnableGraph(this);
    }
    
    public Element getElement( int index ){
    	return elements.get(index);
    }
    
    public int checkInGraph( int x, int y ){
    	return graph[x][y];
    }

    @Override
    public String toString() {
        String geneString = "";
        for (int i = 0; i < size(); i++) {
            geneString += getGene(i);
        }
        return geneString;
    }
}