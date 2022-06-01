package product;

import java.util.ArrayList;

public interface Product {
	public ArrayList<Product> getParts();
	public ProductType getProductType();
	public int getSerial();
}
