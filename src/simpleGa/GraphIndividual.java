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
	private ArrayList<Hallway> halls;
	private ArrayList<Room> rooms;
	private ArrayList<Monster> monsters;
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
    
    public byte binaryStringToByte( String binary ){
    	byte number = 0, base = 16;
    	for( int i = 0; i <= binary.length()-1; i++ ){
    		if( binary.charAt(i) == '1' ) number += base;
    		base /= 2;
    	}
    	return number;
    }
    
    public void evalChromosome(){
    	String gene;
    	for( int i = 0; i < chromosome.length(); i += 20 ){
    		 gene = chromosome.substring( i, i+20 );
    		 if( gene.charAt(0) == '0' && gene.charAt(1) == '0' ){
    			 byte x = binaryStringToByte( gene.substring( 2, 7 ) );
    			 byte y = binaryStringToByte( gene.substring( 7, 12 ));
    			 byte length = binaryStringToByte( "0" + gene.substring( 12, 16 ) );
    			 byte direction = (byte) (( gene.charAt(16) == '1' )? 1: 0);
    			 halls.add( new Hallway( x, y, length, direction ) );
    			 //System.out.println("hallway - "+ x +" - "+y+" - "+length+" - "+direction);
    		 }else if( gene.charAt(0) == '0' && gene.charAt(1) == '1' ){
    			 byte x = binaryStringToByte( gene.substring( 2, 7 ) );
    			 byte y = binaryStringToByte( gene.substring( 7, 12 ));
    			 byte width = binaryStringToByte( "0"+gene.substring( 12, 16 ));
    			 byte breadth = binaryStringToByte( "0"+gene.substring( 16, 20 ));
    			 rooms.add(new Room( x, y, width, breadth ));
    			 //System.out.println("room - "+ x +" - "+y+" - "+width+" - "+breadth);
    		 }else if( gene.charAt(0) == '1' && gene.charAt(1) == '0' ){
    			 byte x = binaryStringToByte( gene.substring( 2, 7 ) );
    			 byte y = binaryStringToByte( gene.substring( 7, 12 ));
    			 monsters.add( new Monster( x, y ));
    			 //System.out.println("monster - "+ x +" - "+y);
    		 }
    	}
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
        
        g.setColor(Color.red);
        for( int i = 0; i < halls.size(); i++ ){
        	int init_x = halls.get(i).getX()+1, init_y = halls.get(i).getY()+1;
        	if( halls.get(i).getDirection() == 0 ){
        		init_x -= (byte)(halls.get(i).getLength()/2);
        		int end_x = init_x + halls.get(i).getLength();
        		for( int j = init_x; j <= end_x; j++ ){
        			g.fillRect ( j*this.cellSize+50, init_y*this.cellSize+50, this.cellSize, this.cellSize);
        		}
        	}else if( halls.get(i).getDirection() == 1 ){
        		init_y -= (byte)(halls.get(i).getLength()/2);
        		int end_y = init_y + halls.get(i).getLength();
        		for( int j = init_y; j <= end_y; j++ ){
        			g.fillRect ( init_x*this.cellSize+50, j*this.cellSize+50, this.cellSize, this.cellSize);
        		}
        	}
        }
        
        g.setColor(Color.green);
        for( int i = 0; i < rooms.size(); i++ ){
        	int init_x = rooms.get(i).getX()+1, init_y = rooms.get(i).getY()+1;
        	init_x -= ((byte)(rooms.get(i).getWidth()/2) );
        	init_y -= ((byte)(rooms.get(i).getBreadth()/2) );
        	int end_x = init_x + rooms.get(i).getWidth() - 1, end_y = init_y + rooms.get(i).getBreadth() - 1;
        	for( int j = init_x ; j <= end_x; j++ ){
        		for( int k = init_y; k <= end_y; k++ ){
        			g.fillRect ( j*this.cellSize+50, k*this.cellSize+50, this.cellSize, this.cellSize);
        		}
        	}
        }
        
        g.setColor(Color.blue);
        for( int i = 0; i < monsters.size(); i++ ){
        	g.fillRect ( (monsters.get(i).getX()+1)*this.cellSize+50, (monsters.get(i).getY()+1)*this.cellSize+50, this.cellSize, this.cellSize);
        }
    }
}
