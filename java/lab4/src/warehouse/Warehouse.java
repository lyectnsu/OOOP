package warehouse;

import java.util.ArrayList;

import javax.swing.JLabel;

import product.Product;

public class Warehouse {
	private int capacity = -1;
	
	public final Object NOT_FULL = new Object();
	public final Object NOT_EMPTY = new Object();
	
	ArrayList<Product> products;
	
	// костыль на архитектуру
	JLabel relatedLabel = null;
	
	public Warehouse(int _capacity){
		capacity = _capacity;
		products = new ArrayList<Product>();
	}
	
	public synchronized void add(Product product){
		synchronized (products) {
			updateLabel();
			products.add(product);
		}
		
	}
	
	public Product get() {
		synchronized (products) {
			updateLabel();
			Product product;
			product = products.remove(products.size() - 1);
			return product;
		}
		
		
	}
	
	public void waitWhileFull() throws InterruptedException {
		synchronized (NOT_FULL) {
			NOT_FULL.wait();
		}
	}
	
	public void waitWhileEmpty() throws InterruptedException {
		synchronized (NOT_EMPTY) {
			NOT_EMPTY.wait();
		}
	}
	
	public void notifyNotFull() {
		synchronized (NOT_FULL) {
			NOT_FULL.notify();
		}
	}
	
	public void notifyNotEmpty() {
		synchronized (NOT_EMPTY) {
			NOT_EMPTY.notify();
		}
	}
	
	public synchronized int getStored() {
		return products.size();
	}
	public synchronized int getCapacity() {
		return capacity;
	}
	public synchronized boolean isFull() {
		return products.size() >= capacity;
	}
	public synchronized boolean isEmpty() {
		return products.size() < 1;
	}
	
	public void bindLabel(JLabel label) {
		relatedLabel = label;
	}
	
	private void updateLabel() {
		if (relatedLabel != null) {
			relatedLabel.setText(Integer.toString(products.size()) + "/" + Integer.toString(capacity));
		}
	}
}
