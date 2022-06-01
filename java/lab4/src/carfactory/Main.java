package carfactory;

import java.io.IOException;

import javax.swing.SwingUtilities;

import config.Config;
import config.ConfigException;
import controller.ControllerCreator;
import controller.DealerController;
import controller.SupplierController;
import controller.WorkerController;
import view.GUIView;
import warehouse.Warehouse;
import warehouse.WarehouseCreator;
import worker.Inspector;

public class Main {

	public static void main(String[] args) {
		
		Config config = null;
		try {
			config = new Config("/home/lyect/Desktop/OOOP/java/lab4/config.cfg");
		} catch (IOException | ConfigException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		WarehouseCreator warehouseCreator = new WarehouseCreator(config);
		
		Warehouse eWarehouse = warehouseCreator.createEngineWarehouse();
		Warehouse bWarehouse = warehouseCreator.createCarbodyWarehouse();
		Warehouse aWarehouse = warehouseCreator.createAccessoryWarehouse();
		Warehouse dWarehouse = warehouseCreator.createDealerWarehouse();
		
		ControllerCreator controllerCreator = new ControllerCreator(config);
		
		SupplierController eController = controllerCreator.createEngineSupplierController(eWarehouse);
		SupplierController bController = controllerCreator.createCarbodySupplierController(bWarehouse);
		SupplierController aController = controllerCreator.createAccessorySupplierController(aWarehouse);
		
		Inspector inspector = new Inspector();
		
		WorkerController wController = controllerCreator.createWorkerController(inspector, eWarehouse, bWarehouse, aWarehouse, dWarehouse);
		DealerController dController = controllerCreator.createDealerController(inspector, dWarehouse);
		
		GUIView view = new GUIView(config, eController, bController, aController, wController, dController);
		
		javax.swing.SwingUtilities.invokeLater(view);
		
		eController.init();
		bController.init();
		aController.init();
		dController.init();
		wController.init();
		
		
		eController.start();
		bController.start();
		aController.start();
		dController.start();
		wController.start();
	}

}
