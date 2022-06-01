package controller;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import warehouse.Warehouse;
import worker.Dealer;
import worker.Inspector;
import worker.Worker;

public class WorkerController {
	private int workersAmount;
	private Inspector inspector;
	private Warehouse eWarehouse;
	private Warehouse bWarehouse;
	private Warehouse aWarehouse;
	private Warehouse dWarehouse;
	private ThreadPoolExecutor executor;
	ArrayList<Worker> workers;
	
	public WorkerController(int _workersAmount, Inspector insp, Warehouse ewh, Warehouse bwh, Warehouse awh, Warehouse dwh) {
		workersAmount = _workersAmount;
		inspector = insp;
		eWarehouse = ewh;
		bWarehouse = bwh;
		aWarehouse = awh;
		dWarehouse = dwh;
		executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(workersAmount);
		workers = new ArrayList<Worker>();
	}
	
	public void init() {
		
	}
	
	public void start() {
		while (true) {
			if (inspector.needCar()) {
				inspector.somebodyGotTheCar();
				executor.submit(
					new Worker(
						inspector,
						eWarehouse,
						bWarehouse,
						aWarehouse,
						dWarehouse
					)
				);
				
			}
		}
	}
}
