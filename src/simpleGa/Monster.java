package simpleGa;

public class Monster {
	private byte x;
	private byte y;
	
	public Monster(){
		this.x = (byte) ( ( Math.random() * 1000 ) % 128 );
		this.y = (byte) ( ( Math.random() * 1000 ) % 128 );
	}
	
	public Monster( byte x, byte y ){
		this.x = x;
		this.y = y;
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
		gene[0] = 1; gene[1] = 0;
		byte geneCnt = 2;
		
		a = toBinary(this.x);
        for( int i = a.length-1; i >= 0; i--, geneCnt++ )
        	gene[geneCnt] = a[i];
		
        a = toBinary(this.y);
        for( int i = a.length-1; i >= 0; i--, geneCnt++ )
        	gene[geneCnt] = a[i];
        
        geneCnt++;
        for( ; geneCnt < 24; geneCnt++ )
        	gene[geneCnt] = 0;
        
		return gene;
	}
	
	public byte getX(){
		return this.x;
	}
	
	public byte getY(){
		return this.y;
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
