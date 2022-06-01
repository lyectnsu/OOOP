package product;

public class DetailNumerator {
	private static int engineSerialNumber = -1;
	private static int carbodySerialNumber = -1;
	private static int accessorySerialNumber = -1;
	
	public static synchronized int getEngineSerialNumber() {
		engineSerialNumber++;
		return engineSerialNumber;
	}
	
	public static synchronized int getCarbodySerialNumber() {
		carbodySerialNumber++;
		return carbodySerialNumber;
	}
	
	public static synchronized int getAccessorySerialNumber() {
		accessorySerialNumber++;
		return accessorySerialNumber;
	}
}
