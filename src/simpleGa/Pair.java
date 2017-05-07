package simpleGa;

import java.util.Objects;

public class Pair<T> {
	private T x;
	private T y;
	
	public Pair( T x, T y ){
		this.x = x;
		this.y = y;
	}
	
	public T getX(){
		return this.x;
	}
	
	public T getY(){
		return this.y;
	}
	
	@Override
	public boolean equals( Object eval ){
		if( eval == null ) return false;
		if (!Pair.class.isAssignableFrom(eval.getClass())) return false;
	    final Pair other = (Pair) eval;
	    if( !other.getX().equals(this.getX()) || !other.getY().equals(this.getY()) ) return false;
	    return true;
	}
	
	@Override
	public int hashCode(){
		return Objects.hash(this.x, this.y);
	}
}
