package product;

public class CarNumerator {
	private static int serialNumber = -1;
	
	public static synchronized int getSerialNumber() {
		serialNumber++;
		return serialNumber;
	}
}
