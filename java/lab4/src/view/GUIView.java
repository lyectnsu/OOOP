package view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import config.Config;
import controller.DealerController;
import controller.SupplierController;
import controller.WorkerController;

public class GUIView extends JFrame implements Runnable {
	SupplierController eController;
	SupplierController bController;
	SupplierController aController;
	WorkerController wController;
	DealerController dController;
	JPanel[] lTitles = new JPanel[4];
	JPanel[] rTitles = new JPanel[4];
	JPanel[] sliders = new JPanel[4];
	JPanel[] dTexts = new JPanel[4];
	Config config;
	
	public GUIView(
			Config _config,
			SupplierController _eController,
			SupplierController _bController,
			SupplierController _aController,
			WorkerController _wController,
			DealerController _dController
	) {
		config = _config;
		eController = _eController;
		bController = _bController;
		aController = _aController;
		wController = _wController;
		dController = _dController;
		
		lTitles = new JPanel[4];
		rTitles = new JPanel[4];
		sliders = new JPanel[4];
		dTexts = new JPanel[4];
	}
	
	private void init() {
		setSize(config.WINDOW_WIDTH, config.WINDOW_HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Car factory");
		setLayout(LayoutCreator.getFrameLayout());
		setVisible(true);
	}
	
	private void initLTitles() {
		for (int i = 0; i < 4; ++i) {
			lTitles[i] = new JPanel();
			lTitles[i].setLayout(LayoutCreator.getLTitleLayout());
		}
		
		lTitles[0].add(ElementCreator.getStaticText("Engine suppliers producing time"), "grow");
		lTitles[1].add(ElementCreator.getStaticText("Carbody suppliers producing time"), "grow");
		lTitles[2].add(ElementCreator.getStaticText("Accessory suppliers producing time"), "grow");
		lTitles[3].add(ElementCreator.getStaticText("Dealers requesting time"), "grow");
	}
	
	private void initRTitles() {
		for (int i = 0; i < 4; ++i) {
			rTitles[i] = new JPanel();
			rTitles[i].setLayout(LayoutCreator.getRTitleLayout());
		}
		
		rTitles[0].add(ElementCreator.getStaticText("Engine warehouse"), "grow");
		rTitles[1].add(ElementCreator.getStaticText("Carbody warehouse"), "grow");
		rTitles[2].add(ElementCreator.getStaticText("Accessory warehouse"), "grow");
		rTitles[3].add(ElementCreator.getStaticText("Sold"), "grow");
	}
	
	private void initSliders() {
		for (int i = 0; i < 4; ++i) {
			sliders[i] = new JPanel();
			sliders[i].setLayout(LayoutCreator.getSliderLayout());
		}
		
		sliders[0].add(ElementCreator.getSupplierSlider(100, 1000, config.ENGINE_SUPPLIER_PRODUCING_TIME, eController), "grow");
		sliders[1].add(ElementCreator.getSupplierSlider(100, 1000, config.ACCESSORY_SUPPLIER_PRODUCING_TIME, bController), "grow");
		sliders[2].add(ElementCreator.getSupplierSlider(100, 1000, config.CARBODY_SUPPLIER_PRODUCING_TIME, aController), "grow");
		sliders[3].add(ElementCreator.getDealerSlider(100, 1000, config.DEALER_REQUESTING_TIME, dController), "grow");
	}
	
	private void initDTexts() {
		for (int i = 0; i < 4; ++i) {
			dTexts[i] = new JPanel();
			dTexts[i].setLayout(LayoutCreator.getDTextLayout());
		}
		JLabel dText0 = ElementCreator.getStaticText("0/0");
		JLabel dText1 = ElementCreator.getStaticText("0/0");
		JLabel dText2 = ElementCreator.getStaticText("0/0");
		JLabel dText3 = ElementCreator.getStaticText("0");
		
		eController.getWarehouse().bindLabel(dText0);
		bController.getWarehouse().bindLabel(dText1);
		aController.getWarehouse().bindLabel(dText2);
		dController.getInspector().bindLabel(dText3);
		
		dTexts[0].add(dText0, "grow");
		dTexts[1].add(dText1, "grow");
		dTexts[2].add(dText2, "grow");
		dTexts[3].add(dText3, "grow");
	}
	
	private void addElements() {
		for (int i = 0; i < 4; ++i) {
			add(lTitles[i], "grow");
			add(sliders[i], "grow");
			add(rTitles[i], "grow");
			add(dTexts[i], "grow");
		}
	}
	
	@Override
	public void run() {
		init();
		
		initLTitles();
		initRTitles();
		initSliders();
		initDTexts();
		
		addElements();
	}

}
