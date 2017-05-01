package simpleGa;

public class Individual {

    static int defaultChromosomeLength = 400;
    private byte[] chromosome = new byte[defaultChromosomeLength];
    // Cache
    private int fitness = 0;

    // Create a random individual
    public void generateIndividual() {
    	Hallway h;
    	Room r;
    	Monster m;
    	byte[] coded;
    	int chromosomeCnt = 0;
        for (int i = 0; i < 20; i++) {
            byte gene = (byte) ( (Math.random() * 100) % 3 );
            if( gene == 0 ){
            	h = new Hallway();
            	coded = h.codeGene();
            	//System.out.println("hallway - "+ h.getX() +" - "+h.getY()+" - "+h.getLength()+" - "+h.getDirection());
            }else if( gene == 1 ){
            	r = new Room();
            	coded = r.codeGene();
            	//System.out.println("room - "+ r.getX() +" - "+ r.getY() +" - "+r.getWidth()+" - "+ r.getBreadth() );
            }else{
            	m = new Monster();
            	coded = m.codeGene();
            	//System.out.println("monster - "+ m.getX() +" - "+m.getY());
            }
            for( int j = 0; j < coded.length; j++ ){
        		chromosome[chromosomeCnt] = coded[j];
        		chromosomeCnt++;
        	}
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

    @Override
    public String toString() {
        String geneString = "";
        for (int i = 0; i < size(); i++) {
            geneString += getGene(i);
        }
        return geneString;
    }
}