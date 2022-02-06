package utility;

public class WordChecker {
	private boolean check(String word) {
		if (
			word.equals("#") ||
			word.equals("POP") ||
			word.equals("PUSH") ||
			word.equals("+") ||
			word.equals("-") ||
			word.equals("*") ||
			word.equals("/") ||
			word.equals("SQRT") ||
			word.equals("PRINT") ||
			word.equals("DEFINE")
		) {
			return true;
		}
		return false;
	}
	public boolean isAcceptableCommandName(String commandName) {
		return check(commandName);
	}
	public boolean isAcceptableVariableName(String variableName) {
		return !check(variableName) && variableName.matches("[a-zA-Z]+");
	}
}
