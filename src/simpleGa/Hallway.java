package simpleGa;

public class Hallway {
	//Posicion X y Y del centro del pasillo
	private byte x;
	private byte y;
	
	//Largo del pasillo (en impares)
	private byte length;
	//Dirección que apunta el pasillo
	private byte direction;
	
	//Constructor que genera elementos aleatorios
	public Hallway(){
		this.x = (byte) ( ( Math.random() * 1000 ) % 32 );
		this.y = (byte) ( ( Math.random() * 1000 ) % 32 );
		this.length = (byte) ( ( Math.random() * 100 ) % 8);
		this.direction = (byte) Math.round(Math.random());
		if( this.length % 2 != 0){
			if( this.length > 7 ) this.length--;
			else this.length++;
		}
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
	
	//Función para convertir numeros decimales en binarios
	public byte[] toBinary( byte number ){
		byte[] binary = new byte[5];
		byte base = 16;
		for( int i = 4; i >= 0; i-- ){
			if( number >= base ){
				binary[i] = 1;
				number -= base;
			}
			else binary[i] = 0;
			base /= 2;
		}
		return binary;
	}
	
	//Funcion para codificar el gen a cadena binaria de 24 digitos
	public byte[] codeGene(){
		byte[] gene = new byte[18], a;
		gene[0] = gene[1] = 0;
		byte geneCnt = 2;
		
		a = toBinary(this.x);
        for( int i = a.length-1; i >= 0; i--, geneCnt++ )
        	gene[geneCnt] = a[i];
		
        a = toBinary(this.y);
        for( int i = a.length-1; i >= 0; i--, geneCnt++ )
        	gene[geneCnt] = a[i];
        
        a = toBinary(this.length);
        for( int i = 2; i >= 0; i--, geneCnt++ )
        	gene[geneCnt] = a[i];
        
        gene[geneCnt] = this.direction;
        geneCnt++;

        for( ; geneCnt < 18; geneCnt++)
        	gene[geneCnt] = 0;
        
        
		return gene;
	}
	
	//Getters
	public byte getX(){
		return this.x;
	}
	
	public byte getY(){
		return this.y;
	}
	
	public byte getLength(){
		return this.length;
	}
	
	public byte getDirection(){
		return this.direction;
	}
	
	//ToString
	@Override
	public String toString(){
		String coded = "";
		byte[] a = codeGene();
		for( int i = 0; i < a.length; i++ ){
			coded += a[i];
		}
		
		return coded;
	}
}
