package simpleGa;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;

public class FitnessCalc {

    static byte[] solution = new byte[64];

    /* Public methods */
    // Set a candidate solution as a byte array
    public static void setSolution(byte[] newSolution) {
        solution = newSolution;
    }

    // To make it easier we can use this method to set our candidate solution 
    // with string of 0s and 1s
    static void setSolution(String newSolution) {
        solution = new byte[newSolution.length()];
        // Loop through each character of our string and save it in our byte 
        // array
        for (int i = 0; i < newSolution.length(); i++) {
            String character = newSolution.substring(i, i + 1);
            if (character.contains("0") || character.contains("1")) {
                solution[i] = Byte.parseByte(character);
            } else {
                solution[i] = 0;
            }
        }
    }

    // Calculate inidividuals fittness by comparing it to our candidate solution
    /*static int getFitness(Individual individual) {
        int fitness = 0;
        // Loop through our individuals genes and compare them to our cadidates
        for (int i = 0; i < individual.size() && i < solution.length; i++) {
            if (individual.getGene(i) == solution[i]) {
                fitness++;
            }
        }
        return fitness;
    }*/
    
    public static float getFitness(String cromosoma){
		
		int i = 0;
		int dimX = 31;
		int dimY = 31;
		int monsN, distance, disMax;
		float sum, distFactor, fxa, fya;
		
		StringBuilder sb = new StringBuilder();
		
		ArrayList<Integer> xrL = new ArrayList<>();
		ArrayList<Integer> yrL = new ArrayList<>();
		
		ArrayList<Integer> xiL = new ArrayList<>();
		ArrayList<Integer> yiL = new ArrayList<>();
		
		ArrayList<Float> fx = new ArrayList<>();
		ArrayList<Float> fy = new ArrayList<>();
		
		while (i+2 < cromosoma.length()){
			sb.setLength(0);
			sb.append(cromosoma.charAt(i));
			sb.append(cromosoma.charAt(i+1));
			String id = sb.toString();
			
			if (id.equals("10") && i+11 < cromosoma.length()){
				sb.setLength(0);
				sb.append(cromosoma.charAt(i+2));
				sb.append(cromosoma.charAt(i+3));
				sb.append(cromosoma.charAt(i+4));
				sb.append(cromosoma.charAt(i+5));
				sb.append(cromosoma.charAt(i+6));
				int xr = Integer.parseInt(sb.toString(), 2);
				xrL.add(xr);
				
				sb.setLength(0);
				sb.append(cromosoma.charAt(i+7));
				sb.append(cromosoma.charAt(i+8));
				sb.append(cromosoma.charAt(i+9));
				sb.append(cromosoma.charAt(i+10));
				sb.append(cromosoma.charAt(i+11));
				int yr = Integer.parseInt(sb.toString(), 2);
				yrL.add(yr);
				i = i + 20;	
			} else {
				i = i + 20;
			}
		}
		
		monsN = xrL.size();
		distFactor = ((float)dimX - (float)monsN) / ((float)monsN + 1);
		sum = 0;
		for (int j = 0; j < monsN; j++){
			sum = sum + distFactor;
			xiL.add(j, Math.round(sum));
			yiL.add(j, Math.round(sum));
			sum = sum + 1;			
		}
		Collections.sort(xrL);
		Collections.sort(yrL);
		
		for (int x = 0; x < monsN; x++){
			distance = dimX - xiL.get(x);
			if (distance > xiL.get(x)){
				disMax = distance;
			}else{
				disMax = xiL.get(x);
			}
			fx.add(x, -1 * (-1 + (Math.abs(xiL.get(x)-xrL.get(x))/(float)disMax)));			
		}
		
		for (int x = 0; x < monsN; x++){
			distance = dimY - yiL.get(x);
			if (distance > yiL.get(x)){
				disMax = distance;
			}else{
				disMax = yiL.get(x);
			}
			fy.add(x, -1 * (-1 + (Math.abs(yiL.get(x)-yrL.get(x))/(float)disMax)));			
		}
		
		fxa = calculateAverage(fx);
		fya = calculateAverage(fy);
		

				
		return (fxa+fya)/2;
	}
	
	public static Float calculateAverage(List<Float> marks) {
		Float sum = (float) 0;
		if(!marks.isEmpty()) {
			for (Float mark : marks) {
				sum += mark;
			}
			return (float) (sum.floatValue() / marks.size());
		}
		return sum;
	}
    
    // Get optimum fitness
    static int getMaxFitness() {
        int maxFitness = solution.length;
        return maxFitness;
    }
    
    public static byte binaryStringToByte( String binary ){
    	byte number = 0, base = 16;
    	for( int i = 0; i <= binary.length()-1; i++ ){
    		if( binary.charAt(i) == '1' ) number += base;
    		base /= 2;
    	}
    	return number;
    }
    
    public static Hallway geneToHallway(String gene){
    	Hallway hall;
    	byte x = binaryStringToByte( gene.substring( 2, 7 ) );
		byte y = binaryStringToByte( gene.substring( 7, 12 ));
		byte length = binaryStringToByte( "0" + gene.substring( 12, 16 ) );
		byte direction = (byte) (( gene.charAt(16) == '1' )? 1: 0);
		hall = ( new Hallway( x, y, length, direction ) );
		return hall;
    }
    
    public static Room geneToRoom( String gene ){
    	Room room;
    	byte x = binaryStringToByte( gene.substring( 2, 7 ) );
		byte y = binaryStringToByte( gene.substring( 7, 12 ));
		byte width = binaryStringToByte( "0"+gene.substring( 12, 16 ));
		byte breadth = binaryStringToByte( "0"+gene.substring( 16, 20 ));		
		room = (new Room( x, y, width, breadth ));
    	return room;
    }
    
    public static Monster geneToMonster( String gene ){
    	Monster monster;
    	byte x = binaryStringToByte( gene.substring( 2, 7 ) );
		byte y = binaryStringToByte( gene.substring( 7, 12 ));
		monster = ( new Monster( x, y ));
    	return monster;
    }
    
    public static int getAllElementsIn( Individual chromosome ){
    	int outside = 0;
    	
    	Hallway hall;
    	Room room;
    	Monster monster;
    	
    	String coded = chromosome.toString(), gene;
    	for( int i = 0; i < coded.length(); i += 20 ){
			 gene = coded.substring( i, i+20 );
			 if( gene.charAt(0) == '0' && gene.charAt(1) == '0' ){
				 hall = geneToHallway(gene);
				 
				 //Esquina izquierda o superior
				 byte x_iu = hall.getX(), y_iu = hall.getY();
				 if( hall.getDirection() == 0 )
					 x_iu -= (byte) hall.getLength()/2;
				 else
					 y_iu -= (byte) hall.getLength()/2;
				 
				 
				 //Esquina derecha o inferior
				 byte x_rd = hall.getX(), y_rd = hall.getY();
				 if( hall.getDirection() == 0 )
					 x_rd += (byte) hall.getLength()/2;
				 else
					 y_rd += (byte) hall.getLength()/2;
				 
				 if( ( x_iu < 0 || y_iu < 0 ) || ( x_rd > 31 || y_rd > 31 ) ) outside++;
				 
			 }else if( gene.charAt(0) == '0' && gene.charAt(1) == '1' ){
				 room = geneToRoom(gene);
				 
				 //Esquina superior izquierda
				 byte x_si = room.getX(), y_si =room.getY();
				 x_si -= room.getWidth()/2; y_si -= room.getBreadth()/2;
				 
				 //Esquina superior derecha
				 byte x_sd = room.getX(), y_sd =room.getY();
				 x_sd += room.getWidth()/2; y_sd -= room.getBreadth()/2;
				 
				//Esquina inferior izquierda
				 byte x_ii = room.getX(), y_ii =room.getY();
				 x_ii -= room.getWidth()/2; y_ii += room.getBreadth()/2;
				 
				 //Esquina inferior derecha
				 byte x_id = room.getX(), y_id =room.getY();
				 x_id += room.getWidth()/2; y_id += room.getBreadth()/2;
				 
				 if( ( x_si < 0 || y_si < 0 ) || ( x_sd > 31 || y_sd > 31 ) || ( x_ii < 0 || y_ii < 0 ) || ( x_id > 31 || y_id > 31 ) ) outside++;
			 }
    	}
    	
    	return outside;
    }
    
    public static int getMonstersOutPlaced( Individual chromosome ){
    	int monstersOut = 0;
    	for( int i = 11; i < chromosome.elementsSize(); i++ ){
    		Element e = chromosome.getElement(i);
    		if( e instanceof Monster ){
    			//System.out.println(e.getX()+" - "+e.getY()+"	"+chromosome.checkInGraph( e.getX(), e.getY() ));
    			if( chromosome.checkInGraph( e.getX(), e.getY() ) == 0 )
    				monstersOut++;
    		}
    	}
    	
    	return monstersOut;
    }
    
    public static int getRunnableGraph( Individual chromosome ){
    	int runnable = 0;
    	int notFounds = 0;
    	
    	boolean canRun = false;
    	
    	Room inicial = (Room) chromosome.getElement(0), terminal = (Room) chromosome.getElement( chromosome.elementsSize()-1 );
    	
    	List<Integer> founds = new ArrayList<Integer>();
    	Queue< Pair<Byte> > q = new ArrayDeque<Pair<Byte>>();
    	HashMap<Pair<Byte>, Integer> seen = new HashMap<Pair<Byte>, Integer>();
    	Pair<Byte> actual, back;
    	
    	q.add( new Pair<Byte>(inicial.getX(), inicial.getY()) );
    	seen.put(new Pair<Byte>(inicial.getX(), inicial.getY()), 1 );
    	while( !q.isEmpty() ){
    		actual = q.poll();
    		
    		if( !founds.contains( chromosome.checkInGraph( actual.getX(), actual.getY()) ) )
    			founds.add( chromosome.checkInGraph( actual.getX(), actual.getY()) );
    		
    		if( actual.equals( new Pair<Byte>(terminal.getX(), terminal.getY()) ) ) canRun = true;
    		
    		//Arriba
    		back = new Pair<Byte>( actual.getX(), (byte)(actual.getY() - 1) );
    		//System.out.println( seen.get( back ) == null );
    		if( actual.getY() - 1 >= 0 && ( seen.get( back ) == null ) && chromosome.checkInGraph( back.getX(), back.getY()) != 0 ){
    			seen.put(back, 1);
    			q.add( back );
    		}
    		
    		//Derecha
    		back = new Pair<Byte>( (byte)(actual.getX() + 1), actual.getY() );
    		//System.out.println( seen.get( back ) == null );
    		if( actual.getX() + 1 <= 31 && seen.get( back ) == null && chromosome.checkInGraph( back.getX(), back.getY()) != 0 ){
    			seen.put(back, 1);
    			q.add( back );
    		}
    		
    		//Abajo
    		back = new Pair<Byte>( actual.getX(), (byte)(actual.getY() + 1) );
    		//System.out.println( seen.get( back ) == null );
    		if( actual.getY() + 1 <= 31 && seen.get( back ) == null && chromosome.checkInGraph( back.getX(), back.getY()) != 0 ){
    			seen.put(back, 1);
    			q.add( back );
    		}
    		
    		//Left
    		back = new Pair<Byte>( (byte)(actual.getX() - 1), actual.getY() );
    		//System.out.println( seen.get( back ) == null );
    		if( actual.getX() - 1 >= 0 && seen.get( back ) == null && chromosome.checkInGraph( back.getX(), back.getY()) != 0 ){
    			seen.put(back, 1);
    			q.add( back );
    		}
    	}
    	
    	notFounds = chromosome.checkNotFounds( founds );
    	if( !canRun ) notFounds += 100;
    	
    	return notFounds;
    }
}