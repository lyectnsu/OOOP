package warehouse;

import config.Config;

public class WarehouseCreator {
	private Config config;
	public WarehouseCreator(Config _config) {
		config = _config;
	}
	
	public Warehouse createEngineWarehouse() {
		return new Warehouse(config.ENGINE_WAREHOUSE_CAPACITY);
	}
	public Warehouse createCarbodyWarehouse() {
		return new Warehouse(config.CARBODY_WAREHOUSE_CAPACITY);
	}
	public Warehouse createAccessoryWarehouse() {
		return new Warehouse(config.ACCESSORY_WAREHOUSE_CAPACITY);
	}
	public Warehouse createDealerWarehouse() {
		return new Warehouse(config.DEALER_WAREHOUSE_CAPACITY);
	}
}
