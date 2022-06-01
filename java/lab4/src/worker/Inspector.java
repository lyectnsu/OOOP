package worker;

import javax.swing.JLabel;

public class Inspector {
	private Object SMB_NEED_CAR = new Object(); // Somebody
	private Object NBD_NEED_CAR = new Object(); // Nobody
	private int countNeed = 0;
	private int sold = 0;
	
	// костыль на архитектуру
	JLabel relatedLabel = null;
	
	public synchronized void requestCar() {
		++countNeed;
	}
	
	public synchronized boolean needCar() {
		return countNeed > 0;
	}
	
	public synchronized void somebodyGotTheCar() {
		--countNeed;
	}
	
	public synchronized void carSold() {
		++sold;
		updateLabel();
	}
	
	public void bindLabel(JLabel label) {
		relatedLabel = label;
	}
	
	private void updateLabel() {
		if (relatedLabel != null) {
			relatedLabel.setText(Integer.toString(sold));
		}
	}
}
