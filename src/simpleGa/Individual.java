package simpleGa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Individual {

    static int defaultChromosomeLength = 400;
    private ArrayList<Byte> chromosome;
    // Cache
    private int fitness;
    private int chromosomeCnt;
    private byte[][] graph;
    private boolean[][] monstersGraph;
    private HashMap< Integer, Element > elements;
    
    public Individual(){
    	graph = new byte[32][32];
    	monstersGraph = new boolean[32][32];
    	this.chromosome = new ArrayList<Byte>();
    	//chromosome = new byte[defaultChromosomeLength];
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
    	
    	byte x_init = 0, y_init = 0, x_end = 0, y_end = 0;
    	byte x_range = 0, y_range = 0, x_monster_init = 0, y_monster_init = 0;
    	byte x_step = 0, y_step = 0, x_monster_step = 0, y_monster_step = 0;
    	
    	byte distribution = (byte) ( (Math.random() * 100) % 4 );
    	
    	if( distribution == 0 ){
    		x_init = y_init = 0;
    		x_end = y_end = 31;
    		x_range = y_range = 3;
    		x_step = y_step = 3;
    	}else if( distribution == 1 ){
    		x_init = y_end = 31;
    		y_init = x_end = 0;
    		x_range = 28;
    		y_range = 3;
    		x_step = -3;
    		y_step = 3;
    	}else if( distribution == 2 ){
    		x_end = y_end = 0;
    		x_init = y_init = 31;
    		x_range = y_range = 28;
    		x_step = y_step = -3;
    	}else if( distribution == 3 ){
    		x_init = y_end = 0;
    		y_init = x_end = 31;
    		x_range = 3;
    		y_range = 28;
    		x_step = 3;
    		y_step = -3;
    	}
    	
    	//Se añade el cuarto donde debe iniciar
    	r = new Room(x_init, y_init);
    	//r = new Room();
    	coded = r.codeChromosome();
    	addGeneToChromosome(coded);
    	elements.put( i, r );
    	this.graph = elements.get(0).drawGraph( graph, (byte) 1 );
    	
    	//Se añaden los demas elementos  
        for ( i = 1; i <= FitnessCalc.graphQuantity; i++) {
            if( Math.random() < FitnessCalc.hallwayProbability ){
            	h = new Hallway( (byte) ( ((Math.random() * 100) % 2)+x_range ), (byte) ( ((Math.random() * 100) % 2)+y_range ) );
            	coded = h.codeChromosome();
            	elements.put( i, h );
            	this.graph = h.drawGraph( graph, (byte) (i+1) );
            }else{
            	r = new Room((byte) ( ((Math.random() * 100) % 2)+x_range ), (byte) ( ((Math.random() * 100) % 2)+y_range ));
            	coded = r.codeChromosome();
            	elements.put( i, r );
            	this.graph = r.drawGraph( graph, (byte) (i+1) );
            }
                       
            x_range += x_step;
            y_range += y_step;
            addGeneToChromosome(coded);
        }
        
    	byte monstersQuantity = 5;
    	if( distribution == 0 ){
    		x_monster_init = y_monster_init = 0;
    		x_monster_step = 5;
    		y_monster_step = 5;
    	}else if( distribution == 1 ){
    		x_monster_init = 26;
    		y_monster_init = 0;
    		x_monster_step = -5;
    		y_monster_step = 5;
    	}else if( distribution == 2 ){
    		x_monster_init = y_monster_init = 26;
    		x_monster_step = -5;
    		y_monster_step = -5;
    	}else if( distribution == 3 ){
    		x_monster_init = 0;
    		y_monster_init = 26;
    		x_monster_step = 5;
    		y_monster_step = -5;
    	}
    	while(monstersQuantity-- >= 0){
    		byte quantity = (byte) ( ((Math.random() * 100) % 5) + 1 );
        	while( quantity-- > 0  ){
        		byte x_monster = (byte) ( ((Math.random() * 100) % 5) + x_monster_init ), y_monster = (byte) ( ((Math.random() * 100) % 5) + y_monster_init );
        		m = new Monster( x_monster , y_monster );
    			coded = m.codeChromosome();
    			elements.put( i, m );
    			addGeneToChromosome(coded);
    			i++;
    			monstersGraph[x_monster][y_monster] = true;
        	}
        	x_monster_init += x_monster_step;
			y_monster_init += y_monster_step;
    	}
    	
    	//Se añade el cuarto donde debe finalizar
    	r = new Room(x_end, y_end);
        //r = new Room();
    	coded = r.codeChromosome();
    	addGeneToChromosome(coded);
    	elements.put( i, r );
    	graph = elements.get(elementsSize()-1).drawGraph( graph, (byte)20 );
    	i++;
    }
    
    private void addGeneToChromosome( byte[] gene ){
    	for( int j = 0; j < gene.length; j++ ){
    		this.chromosome.add(gene[j]);
    		//this.chromosome[this.chromosomeCnt] = gene[j];
    		this.chromosomeCnt++;
    	}
    }
    
    /*public void printGraph(){
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
    
    public void printMonstersGraph(){
    	for( int i = 0; i < 32; i++ ){
    		for( int j = 0; j < 32; j++ ){
    			String printed = "";
    			if( this.monstersGraph[j][i] == false ) printed = "0";
    			else printed = "1";
    			System.out.print(printed+" ");
    		}
    		System.out.println();
    	}
    }*/
    
    /* Getters and setters */
    // Use this if you want to create individuals with different gene lengths
    public static void setDefaultChromosomeLength(int length) {
    	defaultChromosomeLength = length;
    }
    
    public byte getGene(int index) {
        return chromosome.get(index);
    }

    public void setGene(int index, byte value) {
        chromosome.set(index, value);
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
        return chromosome.size();
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
    
    public boolean isFactible(){
    	return ( getMonstersOutPlaced() == 0 && getRunnableGraph() == 0 );
    }
    
    public void setElement( int index, Element element ){
    	elements.put(index, element);
    }
    
    public Element getElement( int index ){
    	return elements.get(index);
    }
    
    public int checkInGraph( byte x, byte y ){
    	return this.graph[y][x];
    }
    
    public boolean checkInMonstersGraph( byte x, byte y ){
    	return this.monstersGraph[x][y];
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

	public void generateChromosome() {
		byte[] coded;
		Element e;
		int roomCounter = 0;
		
		for( int i = 0; i < elementsSize(); i++ ){
			e = elements.get(i);
			//System.out.println(e);
			coded = e.codeChromosome();
            addGeneToChromosome(coded);
            
            if( e instanceof Hallway || e instanceof Room ){
            	roomCounter++;
            	this.graph = e.drawGraph( graph, (byte) (roomCounter+1) );
            }else if( e instanceof Monster )
            	monstersGraph[e.getX()][e.getY()] = true;
		}
	}
}