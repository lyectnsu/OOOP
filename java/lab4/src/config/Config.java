package config;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Config {
	
	public int ENGINE_WAREHOUSE_CAPACITY = -1;
	public int CARBODY_WAREHOUSE_CAPACITY = -1;
	public int ACCESSORY_WAREHOUSE_CAPACITY = -1;
	public int DEALER_WAREHOUSE_CAPACITY = -1;
	
	public int NUMBER_OF_ENGINE_SUPPLIERS = -1;
	public int ENGINE_SUPPLIER_PRODUCING_TIME = -1;
	
	public int NUMBER_OF_CARBODY_SUPPLIERS = -1;
	public int CARBODY_SUPPLIER_PRODUCING_TIME = -1;
	
	public int NUMBER_OF_ACCESSORY_SUPPLIERS = -1;
	public int ACCESSORY_SUPPLIER_PRODUCING_TIME = -1;
	
	public int NUMBER_OF_WORKERS = -1;
	
	public int NUMBER_OF_DEALERS = -1;
	public int DEALER_REQUESTING_TIME = -1;
	
	public int WINDOW_HEIGHT = 500;
	public int WINDOW_WIDTH = 1000;
	
	private int parseOption(String k, String v) throws ConfigException{
		int vi = -1;
		try {
			vi = Integer.parseInt(v);
		}
		catch (NumberFormatException e) {
			throw new ConfigException("Error while parsing value for option \"" + k + "\"");
		}
		return vi;
	}
	
	public Config(String configFilePath) throws FileNotFoundException, IOException, ConfigException{
		BufferedReader br = new BufferedReader(new FileReader(configFilePath));
		
		int optionsSetCount = 0;
		String line;
		while ((line = br.readLine()) != null) {
			String[] kwarg = line.split("=");
			if (kwarg[0].equals("\n")) {
				continue;
			}
			if (kwarg.length == 2) {
				String k = kwarg[0];
				String v = kwarg[1];
				
				switch (k) {
					case ("engineWarehouseCapacity"):
						optionsSetCount++;
						ENGINE_WAREHOUSE_CAPACITY = parseOption(k, v);
						break;
					case ("carbodyWarehouseCapacity"):
						optionsSetCount++;
						CARBODY_WAREHOUSE_CAPACITY = parseOption(k, v);
						break;
					case ("accessoryWarehouseCapacity"):
						optionsSetCount++;
						ACCESSORY_WAREHOUSE_CAPACITY = parseOption(k, v);
						break;
					case ("dealerWarehouseCapacity"):
						optionsSetCount++;
						DEALER_WAREHOUSE_CAPACITY = parseOption(k, v);
						break;
					case ("numberOfEngineSuppliers"):
						optionsSetCount++;
						NUMBER_OF_ENGINE_SUPPLIERS = parseOption(k, v);
						break;
					case ("numberOfCarbodySuppliers"):
						optionsSetCount++;
						NUMBER_OF_CARBODY_SUPPLIERS = parseOption(k, v);
						break;
					case ("numberOfAccessorySuppliers"):
						optionsSetCount++;
						NUMBER_OF_ACCESSORY_SUPPLIERS = parseOption(k, v);
						break;
					case ("engineSupplierProducingTime"):
						optionsSetCount++;
						ENGINE_SUPPLIER_PRODUCING_TIME = parseOption(k, v);
						break;
					case ("carbodySupplierProducingTime"):
						optionsSetCount++;
						CARBODY_SUPPLIER_PRODUCING_TIME = parseOption(k, v);
						break;
					case ("accessorySupplierProducingTime"):
						optionsSetCount++;
						ACCESSORY_SUPPLIER_PRODUCING_TIME = parseOption(k, v);
						break;
					case ("numberOfWorkers"):
						optionsSetCount++;
						NUMBER_OF_WORKERS = parseOption(k, v);
						break;
					case ("numberOfDealers"):
						optionsSetCount++;
						NUMBER_OF_DEALERS = parseOption(k, v);
						break;
					case ("dealerRequestingTime"):
						optionsSetCount++;
						DEALER_REQUESTING_TIME = parseOption(k, v);
						break;
					default:
						throw new ConfigException("Unknown option \"" + k + "\"");
				}
			}
			else {
				throw new ConfigException("Error while parsing line \"" + line + "\"");
			}
		}
		
		if (optionsSetCount != 13) {
			throw new ConfigException("Some of options are not set or some of them are repeated");
		}
		
		if (ENGINE_WAREHOUSE_CAPACITY <= 0) {
			throw new ConfigException("Value for option \"engineWarehouseCapacity\" must be greater than zero");
		}
		if (CARBODY_WAREHOUSE_CAPACITY <= 0) {
			throw new ConfigException("Value for option \"carbodyWarehouseCapacity\" must be greater than zero");
		}
		if (ACCESSORY_WAREHOUSE_CAPACITY <= 0) {
			throw new ConfigException("Value for option \"accessoryWarehouseCapacity\" must be greater than zero");
		}
		if (DEALER_WAREHOUSE_CAPACITY <= 0) {
			throw new ConfigException("Value for option \"dealerWarehouseCapacity\" must be greater than zero");
		}
		
		if (NUMBER_OF_ENGINE_SUPPLIERS <= 0) {
			throw new ConfigException("Value for option \"numberOfEngineSuppliers\" must be greater than zero");
		}
		if (ENGINE_SUPPLIER_PRODUCING_TIME < 100 || ENGINE_SUPPLIER_PRODUCING_TIME > 1000) {
			throw new ConfigException("Value for option \"engineSupplierProducingTime\" must be between 100 and 1000");
		}
		
		if (NUMBER_OF_CARBODY_SUPPLIERS <= 0) {
			throw new ConfigException("Value for option \"numberOfCarbodySuppliers\" must be greater than zero");
		}
		if (CARBODY_SUPPLIER_PRODUCING_TIME < 100 || CARBODY_SUPPLIER_PRODUCING_TIME > 1000) {
			throw new ConfigException("Value for option \"carbodySupplierProducingTime\" must be between 100 and 1000");
		}
		
		if (NUMBER_OF_ACCESSORY_SUPPLIERS <= 0) {
			throw new ConfigException("Value for option \"numberOfAccessorySuppliers\" must be greater than zero");
		}
		if (ACCESSORY_SUPPLIER_PRODUCING_TIME < 100 || ACCESSORY_SUPPLIER_PRODUCING_TIME > 1000) {
			throw new ConfigException("Value for option \"accessorySupplierProducingTime\" must be between 100 and 1000");
		}
		
		if (NUMBER_OF_WORKERS <= 0) {
			throw new ConfigException("Value for option \"numberOfWorkers\" must be greater than zero");
		}
		if (NUMBER_OF_DEALERS <= 0) {
			throw new ConfigException("Value for option \"numberOfDealers\" must be greater than zero");
		}
		
		if (DEALER_REQUESTING_TIME < 100 || DEALER_REQUESTING_TIME > 1000) {
			throw new ConfigException("Value for option \"dealerRequestingTime\" must be between 100 and 1000");
		}
		
		br.close();
	}
}
