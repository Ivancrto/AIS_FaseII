import java.util.ArrayList;
import java.util.List;

public class testingScenarios {
	public List<Pedido> scenario1;
	public List<Pedido> scenario2;
	public List<Pedido> scenario3;
	
	public void TestingScenarios() {
		scenario1= new ArrayList<Pedido>();
		scenario2= new ArrayList<Pedido>();
		scenario3= new ArrayList<Pedido>();
		
		Pedido p=new Pedido(1, 1, 350, 1, 1, 1, 1, 1);
		scenario1.add(p);	//Caso correcto
		p=new Pedido(2, 1, 60, 1, 1, 1, 0, 0);
		scenario2.add(p);		//Caso sin credito
		p=new Pedido(3, 1, 4999, 1, 1, 0, 0, 0);
		scenario3.add(p);	//Caso sin stock
	}

}