package worker;

import java.util.ArrayList;

import product.Product;
import warehouse.Warehouse;

public class Dealer extends Thread{
	
	private int requestingTime;
	private Inspector inspector;
	private Warehouse warehouse;
	
	public Dealer(int _requestingTime, Inspector insp, Warehouse wh) {
		requestingTime = _requestingTime;
		inspector = insp;
		warehouse = wh;
	}
	
	private void consume() {
		if (warehouse.isEmpty()) {
			inspector.requestCar();
			try {
				warehouse.waitWhileEmpty();
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Product carToSell = warehouse.get();
		ArrayList<Product> carDetails = carToSell.getParts();
		inspector.carSold();
		warehouse.notifyNotFull();
		
		System.out.printf("Sold car #%d with parts: E#%d, B#%d, A#%d\n", 
			carToSell.getSerial(),
			carDetails.get(0).getSerial(),
			carDetails.get(1).getSerial(),
			carDetails.get(2).getSerial()
		);
	}
	
	public void setRequestingTime(int time) {
		requestingTime = time;
	}
	
	@Override
	public void run() {
		while (true) {
			consume();
			try {
				sleep(requestingTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
