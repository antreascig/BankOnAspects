package Global;

public class Update <E>{
	private String key;
	private E value;
	
	public Update(String k, E v) {
		this.key = k;
		this.value = v;
	} // Pair
	
	public String getKey() {
		return this.key;
	} // getKey
	
	public E getValue() {
		return this.value;
	} // getValue
	
} // Pair
