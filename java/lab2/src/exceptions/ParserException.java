package exceptions;

public class ParserException extends Exception {
	private String msg;
	public ParserException(String info, String command) {
		this.msg = 
			"Parser ERROR :\n\t"
				+ "While parsing command \"" + command + "\" this occured:\n\t\t"
				+ info + "\n";
	}
	public String toString() {
		return this.msg;
	}
}
