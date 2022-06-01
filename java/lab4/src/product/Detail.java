package product;

import java.util.ArrayList;

public class Detail implements Product {
	private ProductType productType;
	private int serialNumber;
	
	public Detail(ProductType _productType, int _serialNumber) {
		productType = _productType;
		serialNumber = _serialNumber;
	}
	
	@Override
	public ArrayList<Product> getParts() {
		return null;
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
