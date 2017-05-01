package simpleGa;

public class Hallway {
	private byte x;
	private byte y;
	private byte length;
	private byte direction;
	
	public Hallway(){
		this.x = (byte) ( ( Math.random() * 1000 ) % 128 );
		this.y = (byte) ( ( Math.random() * 1000 ) % 128 );
		this.length = (byte) ( ( Math.random() * 100 ) % 16);
		this.direction = (byte) Math.round(Math.random());
		if( this.length % 2 != 0){
			if( this.length > 7 ) this.length--;
			else this.length++;
		}
	}
	
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
	
	public byte[] toBinary( byte number ){
		byte[] binary = new byte[7];
		byte base = 64;
		for( int i = 6; i >= 0; i-- ){
			if( number >= base ){
				binary[i] = 1;
				number -= base;
			}
			else binary[i] = 0;
			base /= 2;
		}
		return binary;
	}
	
	public byte[] codeGene(){
		byte[] gene = new byte[24], a;
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

        for( ; geneCnt < 24; geneCnt++)
        	gene[geneCnt] = 0;
        
        
		return gene;
	}
	
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
