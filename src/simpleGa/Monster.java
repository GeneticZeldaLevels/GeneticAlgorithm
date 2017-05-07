package simpleGa;

public class Monster extends Element {
	//Constructor que genera elementos aleatorios
	public Monster(){
		this.x = (byte) ( ( Math.random() * 1000 ) % 32 );
		this.y = (byte) ( ( Math.random() * 1000 ) % 32 );
	}
	
	//Constructor que recibe paremetros
	public Monster( byte x, byte y ){
		this.x = x;
		this.y = y;
	}
	
	//Funcion para codificar el gen a cadena binaria de 24 digitos
	public byte[] codeChromosome(){
		byte[] gene = new byte[20], a;
		gene[0] = 1; gene[1] = 0;
		byte geneCnt = 2;
		
		a = toBinary(this.x);
        for( int i = a.length-1; i >= 0; i--, geneCnt++ )
        	gene[geneCnt] = a[i];
		
        a = toBinary(this.y);
        for( int i = a.length-1; i >= 0; i--, geneCnt++ )
        	gene[geneCnt] = a[i];
        
        geneCnt++;
        for( ; geneCnt < 20; geneCnt++ )
        	gene[geneCnt] = 0;
        
		return gene;
	}
	
	//Funcion que recibe un byte[][] como grafo y dibuja su posición
	public byte[][] drawGraph( byte[][] graph, byte elementIndex ){
		return graph;
	}
}
