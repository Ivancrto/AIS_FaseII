package es.codeurjc.testing;

import java.util.ArrayList;
import java.util.List;

public class TestingScenarios {
	
	public List<Pedido> scenario1;
	public List<Pedido> scenario2;
	public List<Pedido> scenario3;
	public List<Pedido> scenario1W;
	public List<Pedido> scenario2W;
	public List<Pedido> scenario3W;
	
	
	
	TestingScenarios() {

		scenario1= new ArrayList<Pedido>();
		scenario2= new ArrayList<Pedido>();
		scenario3= new ArrayList<Pedido>();
		
		scenario1W= new ArrayList<Pedido>();
		scenario2W= new ArrayList<Pedido>();
		scenario3W= new ArrayList<Pedido>();
		
		Pedido p=new Pedido(1, 1, 350, 1, 1, 1, 1, 1);
		scenario1.add(p);	//Caso correcto
		
		p=new Pedido(2, 1, 60, 1, 1, 1, 0, 0);
		scenario2.add(p);		//Caso sin credito
		
		p=new Pedido(3, 1, 4999, 1, 1, 0, 0, 0);
		scenario3.add(p);	//Caso sin stock
		
		//long idC,long idP, long cost, int getProductCost, int withdrawProduct, int reserveCredit, int notify, int save
		scenario1W.add(new Pedido(6, 3, 350, 1, 1, 1, 1, 1,"Successful purchase"));
		scenario2W.add(new Pedido(5, 3, 350, 1, 1, 1, 1, 1,"Error: CustomerCreditLimitExceededException"));
		scenario3W.add(new Pedido(6, 1, 350, 1, 1, 1, 1, 1,"Successful purchase"));
		scenario3W.add(new Pedido(4, 1, 350, 1, 1, 1, 1, 1,"Error: ProductStockWithdrawExceededException"));
		}

}
