package controller;

import java.util.ArrayList;

import product.ProductType;
import warehouse.Warehouse;
import worker.Supplier;

public class SupplierController {
	private ProductType productType;
	private int suppliersAmount;
	private int supplierProducingTime;
	private Warehouse warehouse;
	
	private ArrayList<Supplier> suppliers;
	
	public SupplierController(ProductType _productType, int _suppliersAmount, int _supplierProducingTime, Warehouse wh) {
		productType = _productType;
		suppliersAmount = _suppliersAmount;
		supplierProducingTime = _supplierProducingTime;
		warehouse = wh;
		
		suppliers = new ArrayList<Supplier>();
	}
	
	public void init() {
		for (int i = 0; i < suppliersAmount; ++i) {
			suppliers.add(
				new Supplier(
					productType, 
					supplierProducingTime, 
					warehouse
				)
			);
		}
	}
	
	public void start() {
		for (Supplier s: suppliers) {
			s.start();
		}
	}
	
	public void setProducingTime(int time) {
		supplierProducingTime = time;
		for (Supplier s : suppliers) {
			s.setProducingTime(time);
		}
	}
	
	public Warehouse getWarehouse() {
		return warehouse;
	}
}
