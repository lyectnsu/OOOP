package worker;

import product.Detail;
import product.DetailNumerator;
import product.ProductType;
import warehouse.Warehouse;

public class Supplier extends Thread{
	private ProductType productType;
	private int producingTime;
	private Warehouse warehouse;
	
	public Supplier(ProductType _productType, int _producingTime, Warehouse wh) {
		productType = _productType;
		producingTime = _producingTime;
		warehouse = wh;
	}
	
	private void produce() {
		if (warehouse.isFull()) {
			try {
				warehouse.waitWhileFull();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		int serialNumber = 0;
		if (productType == ProductType.ENGINE) serialNumber = DetailNumerator.getEngineSerialNumber();
		if (productType == ProductType.CARBODY) serialNumber = DetailNumerator.getCarbodySerialNumber();
		if (productType == ProductType.ACCESSORY) serialNumber = DetailNumerator.getAccessorySerialNumber();
		
		warehouse.add(new Detail(productType, serialNumber));
		warehouse.notifyNotEmpty();
		//System.out.printf("sup %d/%d\n", warehouse.getStored(), warehouse.getCapacity());
	}
	
	public void setProducingTime(int time) {
		producingTime = time;
	}
	
	@Override
	public void run() {
		while (true) {
			produce();
			try {
				sleep(producingTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
