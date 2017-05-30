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
	private Individual chromosome;
	
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
    	Element gene;
    	
    	inicial = (Room) chromosome.getElement(0);
    	for( int i = 1; i < chromosome.elementsSize()-1; i++  ){
    		gene = chromosome.getElement(i);
    		if( gene instanceof Hallway ){
    			halls.add( (Hallway) gene );
    		}else if( gene instanceof Room ){
    			rooms.add( (Room) gene );
    		}else if( gene instanceof Monster ){
    			monsters.add( (Monster) gene );
    		}
    	}
    	
    	terminal = (Room) chromosome.getElement( chromosome.elementsSize()-1 );
    }

    /**
     * Create the frame.
     */
    public GraphIndividual( Individual individual ) {
    	this.chromosome = individual;
    	this.cellSize = 10;
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
        for( int i = 1; i <= 64; i++ ){
        	for( int j = 1; j <= 64; j++ ){
        		g.setColor (Color.black);
        		g.fillRect ((i*this.cellSize)+50, (j*this.cellSize)+50, this.cellSize, this.cellSize);
        		g.setColor (Color.white);
        		g.drawRect ((i*this.cellSize)+50, (j*this.cellSize)+50, this.cellSize, this.cellSize);
        	}
        }
        
        g.setColor(new Color(100, 100, 100, 200));
        paintRoom( inicial, g );
        g.drawString("A", inicial.getX(), inicial.getY());
        
        g.setColor(new Color(0, 255, 0, 200));
        for( int i = 0; i < rooms.size(); i++ )
        	paintRoom( rooms.get(i), g );
        
        
        g.setColor(new Color(255, 0, 0, 200));
        for( int i = 0; i < halls.size(); i++ )
        	paintHall(halls.get(i), g);
        
        g.setColor(new Color(255, 255, 0, 200));
        paintRoom( terminal, g );
        g.drawString("B", terminal.getX(), terminal.getY());
        
        g.setColor(new Color(0, 0, 255, 200));
        for( int i = 0; i < monsters.size(); i++ )
        	paintMonster( monsters.get(i), g );
    }
}
