package es.codeurjc.shop;

import java.util.ArrayList;
import java.util.List;

public class TestingScenarios {

	public List<Pedido> scenario1;
	public List<Pedido> scenario2;
	public List<Pedido> scenario3;

	public List<Pedido> scenario1S;
	public List<Pedido> scenario2S;
	public List<Pedido> scenario3S;

	public List<Pedido> scenario1API;
	public List<Pedido> scenario2API;
	public List<Pedido> scenario3API;

	TestingScenarios() {

		scenario1 = new ArrayList<Pedido>();
		scenario2 = new ArrayList<Pedido>();
		scenario3 = new ArrayList<Pedido>();

		scenario1S = new ArrayList<Pedido>();
		scenario2S = new ArrayList<Pedido>();
		scenario3S = new ArrayList<Pedido>();

		scenario1API = new ArrayList<Pedido>();
		scenario2API = new ArrayList<Pedido>();
		scenario3API = new ArrayList<Pedido>();

		Pedido p = new Pedido(1, 1, 350, 1, 1, 1, 1, 1);
		scenario1.add(p); // Caso correcto

		p = new Pedido(2, 1, 60, 1, 1, 1, 0, 0);
		scenario2.add(p); // Caso sin credito

		p = new Pedido(3, 1, 4999, 1, 1, 0, 0, 0);
		scenario3.add(p); // Caso sin stock

		// long idC,long idP, long cost, int getProductCost, int withdrawProduct, int
		// reserveCredit, int notify, int save
		scenario1S.add(new Pedido(6, 3, "Successful purchase"));
		scenario2S.add(new Pedido(5, 3, "Error: CustomerCreditLimitExceededException"));
		scenario3S.add(new Pedido(6, 1, "Successful purchase"));
		scenario3S.add(new Pedido(4, 1, "Error: ProductStockWithdrawExceededException"));

		// long idP, long idC, int statusAPI,String m, String messageAPI, int id, String
		// idAPI
		scenario1API.add(new Pedido(3, 6, 200, "id", "", 7));
		scenario2API.add(new Pedido(3, 5, 400, "message", "CustomerCreditLimitExceededException", 0));
		scenario3API.add(new Pedido(2, 4, 400, "message", "ProductStockWithdrawExceededException", 0));

	}

}
