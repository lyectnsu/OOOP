package product;

import java.util.ArrayList;

public class Car implements Product {
	private ProductType productType;
	private int serialNumber;
	private ArrayList<Product> parts;
	public Car(ProductType _productType, int _serialNumber, Product e, Product b, Product a){
		productType = _productType;
		serialNumber = _serialNumber;
		parts = new ArrayList<Product>();
		
		parts.add(e);
		parts.add(b);
		parts.add(a);
	}
	
	@Override
	public ArrayList<Product> getParts() {
		return parts;
	}
	
	@Override
	public ProductType getProductType() {
		return productType;
	}
	
	@Override
	public int getSerial() {
		return serialNumber;
	}
}
