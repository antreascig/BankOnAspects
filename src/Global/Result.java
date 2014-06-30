package Global;

public class Result <E>{
	private String status;
	private E info;
	
	public Result(String s, E i) {
		this.status = s;
		this.info = i;
	} // Pair
	
	public String getStatus() {
		return this.status;
	} // getKey
	
	public E getInfo() {
		return this.info;
	} // getValue
} // Pair
