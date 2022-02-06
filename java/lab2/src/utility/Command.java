package utility;

import java.util.ArrayList;

public class Command {
	private CommandType type;
	private ArrayList<String> args;
	
	public Command() {
		this.type = null;
		this.args = null;
	}
	public Command(CommandType newType) {
		this.type = newType;
		this.args = null;
	}
	public Command(ArrayList<String> newArgs) {
		this.type = null;
		this.args = newArgs;
	}
	public Command(CommandType newType, ArrayList<String> newArgs) {
		this.type = newType;
		this.args = newArgs;
	}
	
	public void setType(CommandType newType) {
		this.type = newType;
	}
	public void setArgs(ArrayList<String> newArgs) {
		this.args = newArgs;
	}
	
	public CommandType getType() {
		return this.type;
	}
	public ArrayList<String> getArgs(){
		return this.args;
	}
}
