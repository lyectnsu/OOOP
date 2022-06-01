package controller;

import config.Config;
import product.ProductType;
import warehouse.Warehouse;
import worker.Inspector;

public class ControllerCreator {
	private Config config;
	public ControllerCreator(Config _config) {
		config = _config;
	}
	
	public SupplierController createEngineSupplierController(Warehouse wh) {
		return new SupplierController(
			ProductType.ENGINE,
			config.NUMBER_OF_ENGINE_SUPPLIERS,
			config.ENGINE_SUPPLIER_PRODUCING_TIME,
			wh
		);
	}
	
	public SupplierController createCarbodySupplierController(Warehouse wh) {
		return new SupplierController(
			ProductType.CARBODY,
			config.NUMBER_OF_CARBODY_SUPPLIERS,
			config.CARBODY_SUPPLIER_PRODUCING_TIME,
			wh
		);
	}

	public SupplierController createAccessorySupplierController(Warehouse wh) {
		return new SupplierController(
			ProductType.ACCESSORY,
			config.NUMBER_OF_ACCESSORY_SUPPLIERS,
			config.ACCESSORY_SUPPLIER_PRODUCING_TIME,
			wh
		);
	}

	public WorkerController createWorkerController(Inspector insp, Warehouse ewh, Warehouse bwh, Warehouse awh, Warehouse dwh) {
		return new WorkerController(
			config.NUMBER_OF_WORKERS,
			insp, ewh, bwh, awh, dwh
		);
	}
	
	public DealerController createDealerController(Inspector insp, Warehouse wh) {
		return new DealerController(
			config.NUMBER_OF_DEALERS,
			config.DEALER_REQUESTING_TIME,
			insp,
			wh
		);
	}
}
