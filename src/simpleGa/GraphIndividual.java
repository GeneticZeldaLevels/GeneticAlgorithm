package simpleGa;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class GraphIndividual extends JFrame {
	private JPanel contentPane;
	private String chromosome;
	
	private Room inicial;
	private Room terminal;
	
	//Listas de pasillos, cuartos y monstruos
	private ArrayList<Hallway> halls;
	private ArrayList<Room> rooms;
	private ArrayList<Monster> monsters;
	
	//Tamaño de celda grafica
	private int cellSize;
	
    /**
     * Launch the application.
     */
    public void runGraphic() {
    	evalChromosome();
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	//GraphIndividual frame = new GraphIndividual();
                    setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public void evalChromosome(){
    	String gene;
    	
    	//Se saca el cuarto inicial
    	inicial = FitnessCalc.geneToRoom( chromosome.substring( 0, 20 ) );
    	
    	//Se calcula el resto de elementos
    	int i = 20;
    	for( ; i < chromosome.length()-20; i += 20 ){
    		 gene = chromosome.substring( i, i+20 );
    		 if( gene.charAt(0) == '0' && gene.charAt(1) == '0' ){
    			 halls.add( FitnessCalc.geneToHallway(gene) );
    			 //System.out.println("hallway - "+ x +" - "+y+" - "+length+" - "+direction);
    		 }else if( gene.charAt(0) == '0' && gene.charAt(1) == '1' ){
    			 rooms.add( FitnessCalc.geneToRoom(gene) );
    			 //System.out.println("room - "+ x +" - "+y+" - "+width+" - "+breadth);
    		 }else if( gene.charAt(0) == '1' && gene.charAt(1) == '0' ){
    			 monsters.add( FitnessCalc.geneToMonster(gene) );
    			 //System.out.println("monster - "+ x +" - "+y);
    		 }
    	}
    	
    	//Se saca el cuarto terminal
    	terminal = FitnessCalc.geneToRoom( chromosome.substring( i, i+20 ) );
    }

    /**
     * Create the frame.
     */
    public GraphIndividual( String chrome ) {
    	this.chromosome = chrome;
    	this.cellSize = 17;
    	halls = new ArrayList<Hallway>();
    	rooms = new ArrayList<Room>();
    	monsters = new ArrayList<Monster>();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        setBounds(0,0,700,700);
    }
    
    private void paintHall( Hallway hall, Graphics g ){
    	int init_x = hall.getX()+1, init_y = hall.getY()+1;
    	if( hall.getDirection() == 0 ){
    		init_x -= (byte)(hall.getLength()/2);
    		int end_x = init_x + hall.getLength();
    		for( int j = init_x; j <= end_x; j++ ){
    			g.fillRect ( j*this.cellSize+50, init_y*this.cellSize+50, this.cellSize, this.cellSize);
    		}
    	}else if( hall.getDirection() == 1 ){
    		init_y -= (byte)(hall.getLength()/2);
    		int end_y = init_y + hall.getLength();
    		for( int j = init_y; j <= end_y; j++ ){
    			g.fillRect ( init_x*this.cellSize+50, j*this.cellSize+50, this.cellSize, this.cellSize);
    		}
    	}
    }
    
    private void paintRoom( Room room, Graphics g ){
    	int init_x = room.getX()+1, init_y = room.getY()+1;
    	
    	init_x -= ((byte)(room.getWidth()/2) );
    	init_y -= ((byte)(room.getBreadth()/2) );
    	
    	int end_x = init_x + room.getWidth() - 1, end_y = init_y + room.getBreadth() - 1;
    	for( int j = init_x ; j <= end_x; j++ ){
    		for( int k = init_y; k <= end_y; k++ ){
    			g.fillRect ( j*this.cellSize+50, k*this.cellSize+50, this.cellSize, this.cellSize);
    		}
    	}
    }
    
    private void paintMonster( Monster monster, Graphics g ){
    	g.fillRect ( (monster.getX()+1)*this.cellSize+50, (monster.getY()+1)*this.cellSize+50, this.cellSize, this.cellSize);
    }
    
    public void paint (Graphics g)
    {
        super.paint(g);
        String[] nums = {"0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
        g.drawString(nums[0], 72, 65);
        int x = 72, y = 65;
        for( int i = 0; i < nums.length; i++ ){
        	g.drawString(nums[i], i*this.cellSize + x, y);
        }
        x -= 19; y += 17;
        for( int i = 0; i < nums.length; i++ ){
        	g.drawString(nums[i], x, i*this.cellSize + y);
        }
        for( int i = 1; i <= 32; i++ ){
        	for( int j = 1; j <= 32; j++ ){
        		g.setColor (Color.black);
        		g.fillRect ((i*this.cellSize)+50, (j*this.cellSize)+50, this.cellSize, this.cellSize);
        		g.setColor (Color.white);
        		g.drawRect ((i*this.cellSize)+50, (j*this.cellSize)+50, this.cellSize, this.cellSize);
        	}
        }
        
        g.setColor(new Color(100, 100, 100));
        paintRoom( inicial, g );
        g.drawString("A", inicial.getX(), inicial.getY());
        
        int counter = 0;
        for( int i = 0; i < rooms.size(); i++ ){
        	g.setColor(new Color(150, 0, counter));
        	paintRoom( rooms.get(i), g );
        	counter += 25;
        }
        
        g.setColor(Color.green);
        for( int i = 0; i < rooms.size(); i++ )
        	paintRoom( rooms.get(i), g );
        
        
        g.setColor(Color.red);
        for( int i = 0; i < halls.size(); i++ )
        	paintHall(halls.get(i), g);
        
        g.setColor(Color.yellow);
        paintRoom( terminal, g );
        g.drawString("B", terminal.getX(), terminal.getY());
        
        g.setColor(Color.blue);
        for( int i = 0; i < monsters.size(); i++ )
        	paintMonster( monsters.get(i), g );
    }
}
