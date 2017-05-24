package simpleGa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    	r = new Room((byte) 0, (byte) 0);
    	//r = new Room();
    	coded = r.codeChromosome();
    	addGeneToChromosome(coded);
    	elements.put( i, r );
    	this.graph = elements.get(0).drawGraph( graph, (byte) 1 );
    	
    	//Se añaden los demas elementos  
        for ( i = 1; i <= 18; i++) {
            byte gene = (byte) ( (Math.random() * 100) % 3 );
            if( gene == 0 ){
            	h = new Hallway();
            	coded = h.codeChromosome();
            	elements.put( i, h );
            	this.graph = h.drawGraph( graph, (byte) (i+1) );
            	//System.out.println("hallway - "+ h.getX() +" - "+h.getY()+" - "+h.getLength()+" - "+h.getDirection());
            }else if( gene == 1 ){
            	r = new Room();
            	coded = r.codeChromosome();
            	elements.put( i, r );
            	this.graph = r.drawGraph( graph, (byte) (i+1) );
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
    	r = new Room((byte)31,(byte)31);
        //r = new Room();
    	coded = r.codeChromosome();
    	addGeneToChromosome(coded);
    	elements.put( i, r );
    	graph = elements.get(19).drawGraph( graph, (byte)20 );
    }
    
    private void addGeneToChromosome( byte[] gene ){
    	for( int j = 0; j < gene.length; j++ ){
    		this.chromosome[this.chromosomeCnt] = gene[j];
    		this.chromosomeCnt++;
    	}
    }
    
    public void printGraph(){
    	for( byte i = 0; i < 32; i++ ){
    		for( byte j = 0; j < 32; j++ ){
    			String printed = ""+this.graph[i][j];
    			if( this.graph[i][j] < 10 ){
    				printed = "0"+this.graph[i][j];
    			}
    			System.out.print(printed+" ");
    		}
    		System.out.println();
    	}
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
    public int checkNotFounds( List<Integer> founds ){
    	int notFounds = 0, routeElements = 0;
    	for( int i = 0; i < elements.size(); i++ )
    		if( ( elements.get(i) instanceof Hallway || elements.get(i) instanceof Room ) )
    			routeElements++;
    	
    	return routeElements - founds.size();
    }
    
    public int elementsSize() {
        return elements.size();
    }
    
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
    
    public int checkInGraph( byte x, byte y ){
    	return this.graph[y][x];
    }
    
    public String toJSON(){
    	List<Hallway> halls = new ArrayList<Hallway>();
    	List<Room> rooms = new ArrayList<Room>();
    	List<Monster> monsters = new ArrayList<Monster>();
    	
    	for( int i = 0; i < elementsSize(); i++ ){
    		if( getElement(i) instanceof Hallway ) halls.add( (Hallway) getElement(i));
    		else if( getElement(i) instanceof Room ) rooms.add( (Room) getElement(i));
    		else if( getElement(i) instanceof Monster ) monsters.add( (Monster) getElement(i));
    	}
    	
    	String json = "{";
    	json += "\"cuartos\":[ ";
    	
    	for( int i = 0; i < rooms.size(); i++ ){
    		json += "{";
    		json += "\"x\": "+rooms.get(i).getX()+",";
    		json += "\"y\": "+rooms.get(i).getY()+",";
    		json += "\"ancho\": "+rooms.get(i).getWidth()+",";
    		json += "\"alto\": "+rooms.get(i).getBreadth();
    		json += "}";
    		if( i != rooms.size()-1 )
    			json += ",";
    	}
    	
    	json += "],";
    	
    	json += "\"pasillos\":[ ";
    	
    	for( int i = 0; i < halls.size(); i++ ){
    		json += "{";
    		json += "\"x\": "+halls.get(i).getX()+",";
    		json += "\"y\": "+halls.get(i).getY()+",";
    		json += "\"largo\": "+halls.get(i).getLength()+",";
    		json += "\"direccion\": "+halls.get(i).getDirection();
    		json += "}";
    		if( i != halls.size()-1 )
    			json += ",";
    	}
    	
    	json += "],";
    	
    	json += "\"monstruos\":[ ";
    	
    	for( int i = 0; i < monsters.size(); i++ ){
    		json += "{";
    		json += "\"x\": "+monsters.get(i).getX()+",";
    		json += "\"y\": "+monsters.get(i).getY();
    		json += "}";
    		if( i != monsters.size()-1 )
    			json += ",";
    	}
    	
    	json += "]";
    	json += "}";
    	return json;
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