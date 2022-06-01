package view;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controller.DealerController;
import controller.SupplierController;

public class ElementCreator {
	public static JLabel getStaticText(String text) {
		return new JLabel(text);
	}
	
	private static JSlider getRawSlider(int minTick, int maxTick, int curTick) {
		JSlider slider = new JSlider(minTick, maxTick, curTick);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setMajorTickSpacing(100);
        slider.setMinorTickSpacing(50);
        return slider;
	}
	
	public static JSlider getSupplierSlider(int minTick, int maxTick, int curTick, SupplierController controller) {
		JSlider slider = getRawSlider(minTick, maxTick, curTick);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
					int value = slider.getValue();
					controller.setProducingTime(value);
				}
		    }
		);
        return slider;
	}
	
	public static JSlider getDealerSlider(int minTick, int maxTick, int curTick, DealerController controller) {
		JSlider slider = getRawSlider(minTick, maxTick, curTick);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
					int value = slider.getValue();
					controller.setRequestingTime(value);
				}
		    }
		);
        
        return slider;
	}
}
