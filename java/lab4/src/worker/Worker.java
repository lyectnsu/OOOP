package worker;

import product.Car;
import product.CarNumerator;
import product.Product;
import product.ProductType;
import warehouse.Warehouse;

public class Worker implements Runnable{
	private Inspector inspector;
	private Warehouse eWarehouse;
	private Warehouse bWarehouse;
	private Warehouse aWarehouse;
	private Warehouse dWarehouse;
	public Worker(Inspector insp, Warehouse ewh, Warehouse bwh, Warehouse awh, Warehouse dwh) {
		inspector = insp;
		eWarehouse = ewh;
		bWarehouse = bwh;
		aWarehouse = awh;
		dWarehouse = dwh;
	}
	
	private Product getDetailFromWarehouse(Warehouse warehouse) {
		if (warehouse.isEmpty()) {
			try {
				warehouse.waitWhileEmpty();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Product detail = warehouse.get();
		warehouse.notifyNotFull();
		
		return detail;
	}
	
	public void assemble() {
		Product engine = getDetailFromWarehouse(eWarehouse);
		Product carbody = getDetailFromWarehouse(bWarehouse);
		Product accessory = getDetailFromWarehouse(aWarehouse);
		
		if (dWarehouse.isFull()) {
			try {
				dWarehouse.waitWhileFull();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		dWarehouse.add(
			new Car(
				ProductType.CAR, 
				CarNumerator.getSerialNumber(), 
				engine,
				carbody,
				accessory
			)
		);
		dWarehouse.notifyNotEmpty();
		
	}
	
	@Override
	public void run() {
		assemble();
	}
}
