package exceptions;

public class WorkerException extends Exception {
	private String msg;
	public WorkerException(String workerName, String info) {
		this.msg = "Worker ERROR :\n"
					+ "While constructing a \"" + workerName + "\" class, this occured:\n\t"
					+ info;
	}
	public String toString() {
		return this.msg;
	}
}
