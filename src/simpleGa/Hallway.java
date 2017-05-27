package simpleGa;

public class Hallway extends Element {
	//Largo del pasillo (en impares)
	private byte length;
	//Direcci�n que apunta el pasillo
	private byte direction;
	
	//Constructor que genera elementos aleatorios
	public Hallway(){
		this.x = (byte) ( ( Math.random() * 1000 ) % 32 );
		this.y = (byte) ( ( Math.random() * 1000 ) % 32 );

		this.length = 1;
		while( this.length < 3 ){
			this.length = (byte) ( ( Math.random() * 100 ) % 16);
			if( this.length % 2 != 0){
				if( this.length > 7 ) this.length--;
				else this.length++;
			}
		}
		
		this.direction = (byte) Math.round(Math.random());
	}
	
	//Constructor que recibe paremetros
	public Hallway( byte x, byte y, byte length, byte direction ){
		this.x = x;
		this.y = y;
		this.length = length;
		this.direction = direction;
		if( this.length % 2 != 0){
			if( this.length > 7 ) this.length--;
			else this.length++;
		}
	}
	
	//Funcion para codificar el gen a cadena binaria de 24 digitos
	public byte[] codeChromosome(){
		byte[] gene = new byte[20], a;
		gene[0] = gene[1] = 0;
		byte geneCnt = 2;
		
		a = toBinary(this.x);
        for( int i = a.length-1; i >= 0; i--, geneCnt++ )
        	gene[geneCnt] = a[i];
		
        a = toBinary(this.y);
        for( int i = a.length-1; i >= 0; i--, geneCnt++ )
        	gene[geneCnt] = a[i];
        
        a = toBinary(this.length);
        for( int i = 3; i >= 0; i--, geneCnt++ )
        	gene[geneCnt] = a[i];
        
        gene[geneCnt] = this.direction;
        geneCnt++;

        for( ; geneCnt < 20; geneCnt++)
        	gene[geneCnt] = 0;
        
        
		return gene;
	}
	
	/*int init_x = hall.getX()+1, init_y = hall.getY()+1;
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
	}*/
	
	
	//Funcion que recibe un byte[][] como grafo y dibuja su posici�n
	public byte[][] drawGraph( byte[][] graph, byte elementIndex ){	
		int init_x = this.getX(), init_y = this.getY();
		if( this.getDirection() == 0 ){
			init_x -= (byte)(this.getLength()/2);
			int end_x = init_x + this.getLength();
			for( int j = init_x; j <= end_x; j++ ){
				if( ( j >= 0 && j <= 31  ) && ( init_y >= 0 && init_y <= 31 ) )
					graph[init_y][j] = elementIndex;
				
			}
		}else if( this.getDirection() == 1 ){
			init_y -= (byte)(this.getLength()/2);
			int end_y = init_y + this.getLength();
			for( int j = init_y; j <= end_y; j++ ){
				if( ( j >= 0 && j <= 31  ) && ( init_x >= 0 && init_x <= 31 ) )
					graph[j][init_x] = elementIndex;
			}
		}
		return graph;
	}

	public byte getLength(){
		return this.length;
	}
	
	public byte getDirection(){
		return this.direction;
	}
}
