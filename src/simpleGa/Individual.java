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
    	graph = new byte[FitnessCalc.mapSize][FitnessCalc.mapSize];
    	monstersGraph = new boolean[FitnessCalc.mapSize][FitnessCalc.mapSize];
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
    	
    	byte x_init = 0, y_init = 0;
    	byte x_range = 0, y_range = 0, x_monster_init = 0, y_monster_init = 0;
    	byte x_step = 0, y_step = 0, x_monster_step = 0, y_monster_step = 0;
    	
    	byte distribution = (byte) ( (Math.random() * 100) % 4 );
    	distribution = 0;
    	if( distribution == 0 ){
    		x_init = y_init = 0;
    		x_step = y_step = 8;
    	}else if( distribution == 1 ){
    		x_init  = 63;
    		y_init = 0;
    		x_step = -8;
    		y_step = 8;
    	}else if( distribution == 2 ){
    		x_init = y_init = 63;
    		x_step = y_step = -10;
    	}else if( distribution == 3 ){
    		x_init =  0;
    		y_init = 63;
    		x_step = 8;
    		y_step = -8;
    	}
    	
    	//Se añade el cuarto donde debe iniciar
    	r = new Room((byte)0, (byte)0);
    	//r = new Room();
    	coded = r.codeChromosome();
    	addGeneToChromosome(coded);
    	elements.put( i, r );
    	this.graph = elements.get(0).drawGraph( graph, (byte) 1 );
    	
    	int type = 0;
    	//Se añaden los demas elementos  
        for ( i = 0; i < FitnessCalc.graphQuantity; i++) {
        	Element gene;
        	if( (i+1) % 3 == 0 ){
        		x_init += x_step;
        		y_init += y_step;
        	}
            if( Math.random() < FitnessCalc.hallwayProbability ){
            	type = 2;
            	gene = new Hallway( (byte) ( ((Math.random() * 100) % x_step)+x_init ), (byte) ( ((Math.random() * 100) % y_step)+y_init ) );
            }else{
            	type = 3;
            	gene = new Room((byte) ( ((Math.random() * 100) % x_step)+x_init ), (byte) ( ((Math.random() * 100) % y_step)+y_init ));
            }
            coded = gene.codeChromosome();
        	elements.put( i+1, gene );
        	this.graph = gene.drawGraph( graph, (byte) (type) );
        	
        	//System.out.println( gene.getX()+" - "+gene.getY() );
        	
            //x_range += x_step;
            //y_range += y_step;
            addGeneToChromosome(coded);
        }
        
        
    	byte monstersQuantity = 6;
    	if( distribution == 0 ){
    		x_monster_init = y_monster_init = 0;
    		x_monster_step = 8;
    		y_monster_step = 8;
    	}else if( distribution == 1 ){
    		x_monster_init = 57;
    		y_monster_init = 0;
    		x_monster_step = -8;
    		y_monster_step = 8;
    	}else if( distribution == 2 ){
    		x_monster_init = y_monster_init = 57;
    		x_monster_step = -8;
    		y_monster_step = -8;
    	}else if( distribution == 3 ){
    		x_monster_init = 0;
    		y_monster_init = 57;
    		x_monster_step = 8;
    		y_monster_step = -8;
    	}
    	int monsterLimit = 45, monsterCounter = 0;
    	while(monstersQuantity-- >= 0 && monsterCounter <= monsterLimit ){
    		
    		byte quantity = (byte) ( ((Math.random() * 100) % 5) + 1 );
        	while( quantity-- > 0 ){
        		byte x_monster = -1, y_monster = -1;
        		while( x_monster < 0 || x_monster > 63 )
        			x_monster = (byte) ( ((Math.random() * 100) % x_monster_step) + x_monster_init );
        		while( y_monster < 0 || y_monster > 63 )
        			y_monster = (byte) ( ((Math.random() * 100) % y_monster_step) + y_monster_init );
        		m = new Monster( x_monster , y_monster );
    			coded = m.codeChromosome();
    			elements.put( i, m );
    			addGeneToChromosome(coded);
    			i++;
    			monstersGraph[x_monster][y_monster] = true;
    			monsterCounter++;
        	}
        	
        	x_monster_init += x_monster_step;
			y_monster_init += y_monster_step;
    	}
    	
    	//Se añade el cuarto donde debe finalizar
    	r = new Room((byte)63, (byte)63);
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
        	if( FitnessCalc.fitnessFunction== 0 )
            	fitness = FitnessCalc.getFitnessADot( this );
        	else
        		fitness = (int) (FitnessCalc.getFitnessRowsColumns( this.toString() ) * 100);
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
    	return ( getMonstersOutPlaced() == 0 && getRunnableGraph() <= 25 );
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