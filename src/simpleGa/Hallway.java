package simpleGa;

public class Hallway extends Element {
	//Largo del pasillo (en impares)
	private byte length;
	//Dirección que apunta el pasillo
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
	
	//Funcion que recibe un byte[][] como grafo y dibuja su posición
	public byte[][] drawGraph( byte[][] graph, byte elementIndex ){	
		byte x = getX(), y = getY();
		if( getDirection() == 0 ){
			x -= getLength()/2;
			for( ; x <= (getX() + getLength()/2 ); x++ ){
				if( ( x >= 0 && x <= 31  ) && ( y >= 0 && y <= 31 ) )
					graph[x][y] = elementIndex;
			}
		}else{
			y -= getLength()/2;
			for( ; y <= (getY() + getLength()/2 ); y++ ){
				if( ( x >= 0 && x <= 31  ) && ( y >= 0 && y <= 31 ) )
					graph[x][y] = elementIndex;
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
