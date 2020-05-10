package es.codeurjc.testing;

public class Pedido{
	
		public String productName;
		public Long productPrice;
		public int productStock;
		public String clientName;
		public Long customerCredit;
		public String msg;
		public int timesOfNotify;
		public int typeOfScene;	//Si vale 0 es el caso correcto, si vale 1, es el caso sin credito, si vale 2 es el caso sin stock
		
		public Pedido() {		
		}
		
		public Pedido(String pn, Long pp, int ps, String cn, Long cc, String m, int n,int t) {	
			productName=pn;
			productPrice=pp;
			productStock=ps;
			clientName=cn;
			customerCredit=cc;
			msg=m;
			timesOfNotify=n;
			typeOfScene=t;
		}
		
	}