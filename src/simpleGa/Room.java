package simpleGa;

public class Room extends Element {
	//Ancho del cuarto
	private byte width;
	//Alto del cuarto
	private byte breadth;
	
	//Constructor que genera elementos aleatorios
	public Room(){
		this.x = (byte) ( ( Math.random() * 1000 ) % 32 );
		this.y = (byte) ( ( Math.random() * 1000 ) % 32 );
		
		this.width = 1;
		while( this.width < 5 ){
			this.width = (byte) ( ( Math.random() * 100 ) % 16);
			if( this.width % 2 == 0 ){
				if( this.width > 9 ) this.width--;
				else this.width++;
			}
		}
		
		this.breadth = 1;
		while( this.breadth < 5 ){
			this.breadth = (byte) ( ( Math.random() * 100 ) % 16);
			if( this.breadth % 2 == 0 ){
				if( this.breadth > 9 ) this.breadth--;
				else this.breadth++;
			}
		}
	}
	
	//Constructor que recibe paremetros
	public Room(byte x, byte y, byte width, byte breadth){
		this.x = x;
		this.y = y;
		this.width = width;
		this.breadth = breadth;
		/*if( this.width % 2 != 0){
			if( this.width > 7 ) this.width--;
			else this.width++;
		}
		if( this.breadth % 2 != 0){
			if( this.breadth > 7 ) this.breadth--;
			else this.breadth++;
		}*/
	}
	
	//Funcion para codificar el gen a cadena binaria de 24 digitos
	public byte[] codeChromosome(){
		byte[] gene = new byte[20], a;
		gene[0] = 0; gene[1] = 1;
		byte geneCnt = 2;
		
		a = toBinary(this.x);
        for( int i = a.length-1; i >= 0; i--, geneCnt++ )
        	gene[geneCnt] = a[i];
		
        a = toBinary(this.y);
        for( int i = a.length-1; i >= 0; i--, geneCnt++ )
        	gene[geneCnt] = a[i];
        
        a = toBinary(this.width);
        for( int i = 3; i >= 0; i--, geneCnt++ )
        	gene[geneCnt] = a[i];
        
        a = toBinary(this.breadth);
        for( int i = 3; i >= 0; i--, geneCnt++ )
        	gene[geneCnt] = a[i];
        
		return gene;
	}
	
	//Funcion que recibe un byte[][] como grafo y dibuja su posición
	public byte[][] drawGraph( byte[][] graph, byte elementIndex ){
		byte x_from = getX(), y_from = getY(), x_to = getX(), y_to = getY();
		x_from -= getWidth()/2;
		y_from -= getBreadth()/2;
		x_to += getWidth()/2;
		y_to += getBreadth()/2;
		for( int j = x_from; j <= x_to; j++ )
			for( int k = y_from; k <= y_to; k++ )
				if( ( j >= 0 && j <= 31  ) && ( k >= 0 && k <= 31 ) )
					graph[j][k] = 1;
		return graph;
	}
	
	public byte getWidth(){
		return this.width;
	}
	
	public byte getBreadth(){
		return this.breadth;
	}
}
