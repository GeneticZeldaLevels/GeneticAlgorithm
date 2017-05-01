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
    	byte number = 0, base = 64;
    	for( int i = 0; i <= binary.length()-1; i++ ){
    		if( binary.charAt(i) == '1' ) number += base;
    		base /= 2;
    	}
    	return number;
    }
    
    public void evalChromosome(){
    	String gene;
    	for( int i = 0; i < chromosome.length(); i += 24 ){
    		 gene = chromosome.substring( i, i+24 );
    		 if( gene.charAt(0) == '0' && gene.charAt(1) == '0' ){
    			 byte x = binaryStringToByte( gene.substring( 2, 9 ) );
    			 byte y = binaryStringToByte( gene.substring( 9, 16 ));
    			 byte length = binaryStringToByte( "000" + gene.substring( 16, 20 ) );
    			 byte direction = (byte) (( gene.charAt(20) == '1' )? 1: 0);
    			 halls.add( new Hallway( x, y, length, direction ) );
    		 }else if( gene.charAt(0) == '0' && gene.charAt(1) == '1' ){
    			 byte x = binaryStringToByte( gene.substring( 2, 9 ) );
    			 byte y = binaryStringToByte( gene.substring( 9, 16 ));
    			 byte width = binaryStringToByte( "000"+gene.substring( 16, 20 ));
    			 byte breadth = binaryStringToByte( "000"+gene.substring( 20, 24 ));
    			 rooms.add(new Room( x, y, width, breadth ));
    		 }else if( gene.charAt(0) == '1' && gene.charAt(1) == '0' ){
    			 byte x = binaryStringToByte( gene.substring( 2, 9 ) );
    			 byte y = binaryStringToByte( gene.substring( 9, 16 ));
    			 monsters.add( new Monster( x, y ));
    		 }
    	}
    }

    /**
     * Create the frame.
     */
    public GraphIndividual( String chrome ) {
    	this.chromosome = chrome;
    	halls = new ArrayList<Hallway>();
    	rooms = new ArrayList<Room>();
    	monsters = new ArrayList<Monster>();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        setBounds(0,0,640,640);
    }
    
    public void paint (Graphics g)
    {
        super.paint(g);

        for( int i = 1; i <= 128; i++ ){
        	for( int j = 1; j <= 128; j++ ){
        		g.setColor (Color.black);
        		g.fillRect ((i*5)+50, (j*5)+50, 5, 5);
        		g.setColor (Color.white);
        		g.drawRect ((i*5)+50, (j*5)+50, 5, 5);
        	}
        }
        
        g.setColor(Color.red);
        for( int i = 0; i < halls.size(); i++ ){
        	int init_x = halls.get(i).getX(), init_y = halls.get(i).getY();
        	if( halls.get(i).getDirection() == 0 ){
        		init_x -= (byte)(halls.get(i).getLength()/2);
        		int end_x = init_x + halls.get(i).getLength();
        		for( int j = init_x; j <= end_x; j++ ){
        			g.fillRect ( j*5+50, init_x*5+50, 5, 5);
        		}
        	}else if( halls.get(i).getDirection() == 1 ){
        		init_y -= (byte)(halls.get(i).getLength()/2);
        		int end_y = init_y + halls.get(i).getLength();
        		for( int j = init_y; j <= end_y; j++ ){
        			g.fillRect ( init_y*5+50, j*5+50, 5, 5);
        		}
        	}
        }
        
        g.setColor(Color.green);
        for( int i = 0; i < rooms.size(); i++ ){
        	int init_x = rooms.get(i).getX() - ((byte)(rooms.get(i).getWidth()/2) ), init_y = rooms.get(i).getY() - ((byte)(rooms.get(i).getBreadth()/2) );
        	int end_x = init_x + rooms.get(i).getWidth(), end_y = init_y + rooms.get(i).getBreadth();
        	for( int j = init_x ; j <= end_x; j++ ){
        		for( int k = init_y; k <= end_y; k++ ){
        			g.fillRect ( j*5+50, k*5+50, 5, 5);
        		}
        	}
        }
        
        g.setColor(Color.blue);
        for( int i = 0; i < monsters.size(); i++ ){
        	g.fillRect ( monsters.get(i).getX()*5+50, monsters.get(i).getY()*5+50, 5, 5);
        }
    }
}
