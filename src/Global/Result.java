package Global;

public class Result {
	private String status;
	private String info;
	
	public Result(String s, String i) {
		this.status = s;
		this.info = i;
	} // Pair
	
	public String getStatus() {
		return this.status;
	} // getKey
	
	public String getInfo() {
		return this.info;
	} // getValue
} // Pair
