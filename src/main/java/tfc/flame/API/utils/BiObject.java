package tfc.flame.API.utils;

public class BiObject<A, B> {
	private final A first;
	private final B second;
	public BiObject(A a, B b) {
		first = a;
		second = b;
	}
	
	public A getObject1() {
		return first;
	}
	
	public B getObject2() {
		return second;
	}
}
