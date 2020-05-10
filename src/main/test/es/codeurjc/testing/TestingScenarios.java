package es.codeurjc.testing;

import java.util.ArrayList;
import java.util.List;


public class TestingScenarios {
	
	public List<Pedido> correcto;	//Contendrá los datos para crear el producto, el cliente y si la notificación se ha de enviar
	public List<Pedido> noCredito;	//Contendrá los datos para crear el producto, el cliente y el mensaje de la excepcion
	public List<Pedido> noStock;	//Contendrá los datos para crear el producto, el cliente y el mensaje de la excepcion
		
	
	TestingScenarios() {

		//Escenarios donnde hay stock del producto y el cliente tiene crédito suficiente
		correcto= new ArrayList<Pedido>();
		correcto.add(new Pedido("Pan de Pipas",(long)1,5,"Pipo",(long)100, "Purchase: customer=1, product=1",1,0));
		correcto.add(new Pedido("Leche",(long)4.50,20,"Pipo",(long)21, "Purchase: customer=1, product=2",1,0));
		correcto.add(new Pedido("Azucar",(long)2.50,1,"Clara",(long)3, "Purchase: customer=2, product=3",1,0));
		correcto.add(new Pedido("Salchichas",(long)5,100,"Jose",(long)5.01, "Purchase: customer=3, product=4",1,0));
		
		//Escenarios donnde el cliente NO tiene crédito suficiente
		noCredito= new ArrayList<Pedido>();
		noCredito.add(new Pedido("Pan de Pipas",(long)1,5,"Pipo",(long)0.50,"",0,1));
		noCredito.add(new Pedido("Leche",(long)4.50,20,"Pipo",(long)1,"",0,1));
		noCredito.add(new Pedido("Azucar",(long)2.50,1,"Clara",(long)0,"",0,1));
		noCredito.add(new Pedido("Salchichas",(long)5,100,"Jose",(long)4.99,"",0,1));
		
		//Escenarios donnde NO hay stock del producto
		noStock= new ArrayList<Pedido>();
		noStock.add(new Pedido("Pan de Pipas",(long)1,0,"Pipo",(long)100,"",0 ,2));
		noStock.add(new Pedido("Leche",(long)4.50,0,"Pipo",(long)1,"",0,2));
		noStock.add(new Pedido("Azucar",(long)2.50,0,"Clara",(long)3,"",0,2));
		noStock.add(new Pedido("Salchichas",(long)5,0,"Jose",(long)4.99,"",0,2));
		

	}

}
