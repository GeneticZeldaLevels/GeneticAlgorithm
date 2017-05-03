package simpleGa;

public abstract class Element {
	//Posicion X y Y del centro del pasillo
	protected byte x;
	protected byte y;
	
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
	public abstract byte[] codeChromosome();
	
	//Funcion que recibe un byte[][] como grafo y dibuja su posición
	public abstract byte[][] drawGraph(byte[][] graph);
	
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
		byte[] a = codeChromosome();
		for( int i = 0; i < a.length; i++ ){
			coded += a[i];
		}
		
		return coded;
	}
}
