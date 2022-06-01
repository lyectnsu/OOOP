package controller;

import java.util.ArrayList;

import warehouse.Warehouse;
import worker.Dealer;
import worker.Inspector;

public class DealerController {
	private int dealersAmount;
	private int dealerRequestingTime;
	private Inspector inspector;
	private Warehouse warehouse;
	
	private ArrayList<Dealer> dealers;
	
	public DealerController(int _dealersAmount, int _dealerRequestingTime, Inspector insp, Warehouse wh) {
		dealersAmount = _dealersAmount;
		dealerRequestingTime = _dealerRequestingTime;
		inspector = insp;
		warehouse = wh;
		
		dealers = new ArrayList<Dealer>();
	}
	
	public void init() {
		for (int i = 0; i < dealersAmount; ++i) {
			dealers.add(
				new Dealer(
					dealerRequestingTime,
					inspector,
					warehouse
				)
			);
		}
	}
	
	public void start() {
		for (Dealer d : dealers) {
			d.start();
		}
	}
	
	public void setRequestingTime(int time) {
		dealerRequestingTime = time;
		for (Dealer d : dealers) {
			d.setRequestingTime(time);
		}
	}
	
	public Inspector getInspector() {
		return inspector;
	}
}
