package simpleGa;

public class Monster {
	//Posicion X y Y del monstruo
	private byte x;
	private byte y;
	
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
		gene[0] = 1; gene[1] = 0;
		byte geneCnt = 2;
		
		a = toBinary(this.x);
        for( int i = a.length-1; i >= 0; i--, geneCnt++ )
        	gene[geneCnt] = a[i];
		
        a = toBinary(this.y);
        for( int i = a.length-1; i >= 0; i--, geneCnt++ )
        	gene[geneCnt] = a[i];
        
        geneCnt++;
        for( ; geneCnt < 18; geneCnt++ )
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
