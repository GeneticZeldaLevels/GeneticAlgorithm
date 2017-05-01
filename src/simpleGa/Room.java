package simpleGa;

public class Room {
	private byte x;
	private byte y;
	private byte width;
	private byte breadth;
	
	public Room(){
		this.x = (byte) ( ( Math.random() * 1000 ) % 128 );
		this.y = (byte) ( ( Math.random() * 1000 ) % 128 );
		this.width = (byte) ( ( Math.random() * 100 ) % 16);
		this.breadth = (byte) ( ( Math.random() * 100 ) % 16);
		if( this.width % 2 != 0){
			if( this.width > 7 ) this.width--;
			else this.width++;
		}
		if( this.breadth % 2 != 0){
			if( this.breadth > 7 ) this.breadth--;
			else this.breadth++;
		}
	}
	
	public Room(byte x, byte y, byte width, byte breadth){
		this.x = x;
		this.y = y;
		this.width = width;
		this.breadth = breadth;
		if( this.width % 2 != 0){
			if( this.width > 7 ) this.width--;
			else this.width++;
		}
		if( this.breadth % 2 != 0){
			if( this.breadth > 7 ) this.breadth--;
			else this.breadth++;
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
	
	public byte getX(){
		return this.x;
	}
	
	public byte getY(){
		return this.y;
	}
	
	public byte getWidth(){
		return this.width;
	}
	
	public byte getBreadth(){
		return this.breadth;
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
