
import static org.hamcrest.CoreMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import es.codeurjc.shop.domain.ShopException;
import es.codeurjc.shop.domain.customer.CustomerCreditLimitExceededException;
import es.codeurjc.shop.domain.customer.CustomerService;
import es.codeurjc.shop.domain.product.ProductService;
import es.codeurjc.shop.domain.product.ProductStockWithdrawExceededException;
import es.codeurjc.shop.domain.purchase.Purchase;
import es.codeurjc.shop.domain.purchase.PurchaseRepository;
import es.codeurjc.shop.domain.purchase.PurchaseService;
import es.codeurjc.shop.notification.NotificationService;


/*Se tiene que comprobar que la clase implementa correctamente las siguientes situaciones cuando se crea un pedido:
Para su implementación, dado que se trata de tests unitarios, se usarán dobles para todas las
dependencias de la clase: PurchaseRepository, CustomerService, ProductService y NotificationService.*/  


@RunWith(Parameterized.class)
@ExtendWith(Pedido.class)
public class pruebasUnitariasDobles {
	
	@Parameters
	public static Collection<Object[]> data(){
		testingScenarios s = new testingScenarios();
	    Object[][] data = {
	    	{ s.scenario1 },
	        { s.scenario2 },
	    	{ s.scenario3 }
	    };
	    
	    return Arrays.asList(data);
	}	
	@Parameter(0) public List<Pedido> pedidoGenerico;
	/* *********************************** */

	/*El pedido se realiza correctamente cuando el cliente tiene crédito y el producto stock.
	Además, la notificación se envía.*/
	
	@Test
	public void generico() throws ShopException  {
		
	//Given
		PurchaseRepository purchaseRep = mock(PurchaseRepository.class); //Este mock no se usa para nada, ya que las modificaciones al repo
																		//se hace mediante los Services
		CustomerService customerServ = mock(CustomerService.class);
		ProductService productServ = mock(ProductService.class);
		NotificationService notificationServ = mock(NotificationService.class);	
		
		PurchaseService purchaseServ = new PurchaseService(purchaseRep, customerServ, productServ, notificationServ);
		
		//Mock getProuctCost
		when(productServ.getProductCost(pedidoGenerico.get(0).getIdP())).thenReturn(pedidoGenerico.get(0).getCost());	//En cualquiera de los 3 casos

		//Mock withdrawProduct
		if(pedidoGenerico.get(0).getIdC()==3)
			doThrow(new ProductStockWithdrawExceededException()).when(productServ).withdrawProduct(pedidoGenerico.get(0).getIdP());
		else
			doThrow(new RuntimeException()).when(productServ).withdrawProduct(pedidoGenerico.get(0).getIdP());
		
		//Mock reserveCredit
		doThrow(new CustomerCreditLimitExceededException()).when(customerServ).reserveCredit(2,pedidoGenerico.get(0).getCost());
		if(pedidoGenerico.get(0).getIdC()!=2)
			doThrow(new RuntimeException()).when(customerServ).reserveCredit(pedidoGenerico.get(0).getIdC(),pedidoGenerico.get(0).getCost());

		//Mock notofy
		doThrow(new RuntimeException()).when(notificationServ).notify(pedidoGenerico.get(0).getMsg());
		
		//Mock save del repository
		//when(purchaseRep.save(any())).thenReturn(null);
		//HECAMBIADO LA VISIBILIDAD DEL CONSTRUCTOR DE "NADA" A PUBLICO
		Purchase p= new Purchase(pedidoGenerico.get(0).getIdC(),pedidoGenerico.get(0).getIdP());
		when(purchaseRep.save(p)).thenReturn(null);	//Devuelve null ya que es un purchase nuevo
		
	//When
		purchaseServ.createPurchase(pedidoGenerico.get(0).getIdC(), pedidoGenerico.get(0).getIdC());
		
	//Then
		verify(productServ, times(pedidoGenerico.get(0).getGetProductCost())).getProductCost(pedidoGenerico.get(0).getIdP());
		verify(productServ, times(pedidoGenerico.get(0).getWithdrawProduct())).withdrawProduct(pedidoGenerico.get(0).getIdP());
		verify(customerServ, times(pedidoGenerico.get(0).getReserveCredit())).reserveCredit(pedidoGenerico.get(0).getIdC(),pedidoGenerico.get(0).getCost());
		verify(notificationServ, times(pedidoGenerico.get(0).getNotify())).notify(pedidoGenerico.get(0).getMsg());
		verify(purchaseRep, times(pedidoGenerico.get(0).getSave())).save(p);
		//verify(purchaseRep, times(pedidoGenerico.getSave())).save(any());
			
			
			/*for(Pedido m: pedidosGenericos) {
				productServ.createProduct(m.productName, m.productPrice, m.productStock); //Creamos el producto
				verify(productServ, times(1)).createProduct(m.productName, m.productPrice, m.productStock); //Comprobamos que se llama x veces
				Product p = productServ.getProduct(1);
				customerServ.createCustomer(m.clientName, m.customerCredit);
				Customer c = customerServ.getCustomer(2); 
				verify(customerServ, times(1)).createCustomer(m.clientName, m.customerCredit); //Comprobamos que se llama x veces
				
				Purchase purchase = purchaseServ.createPurchase(2, 1);
				
				verify(purchaseRep, times(1)).save(purchase);

				
			}*/
			 
		}

		@Test
		public void genericoExcepciones() {
			//Given
			PurchaseRepository purchaseRep = mock(PurchaseRepository.class); //Este mock no se usa para nada, ya que las modificaciones al repo
																			//se hace mediante los Services
			CustomerService customerServ = mock(CustomerService.class);
			ProductService productServ = mock(ProductService.class);
			NotificationService notificationServ = mock(NotificationService.class);	
			
			PurchaseService purchaseServ = new PurchaseService(purchaseRep, customerServ, productServ, notificationServ);
			
		//	Exception e1 = assertThrows(CustomerNotFoundException.class,()-> customerServ.getCustomer(16123)); 
		//	assertEquals("8435011945389762511L", e1.getMessage()); 
		}
		

		
		/*PurchaseRepository purRep = mock(PurchaseRepository.class);
		CustomerService cService = mock(CustomerService.class);
		ProductService pService = mock(ProductService.class);
		NotificationService nService = mock(NotificationService.class);	
		
		@Test
		public void PurchaseService_CreditoYProducto() throws ShopException {
			Product p=pService.createProduct("Pan de Pipas",1,5);
			Customer c= cService.createCustomer("Pipo", 100);
			PurchaseService purService= new PurchaseService(purRep, cService, pService, nService);	
			purService.createPurchase(c.getId(), p.getId());
			
			assertThat("mensaje" ,pService.getProduct(p.getId()).getStock(), equalTo(4)); //is(4)
			assertThat(cService.getCustomer(c.getId()).getCredit(),  equalTo(99)); //is(99)
			verify(nService,times(1)).notify("Purchase: customer=" + c.getId() + ", product=" + c.getId());	//Comprobamos que se haya enviado la notificacion
			System.out.println(c.getId());
		}
		*/
		/*El pedido no se realiza cuando el cliente no tiene crédito. Se verifica que la excepción es la
		que corresponde a esta situación.*/
		/*public void PurchaseService_NoCredito() throws ProductNotFoundException, CustomerNotFoundException {
			Product p=pService.createProduct("Lasagna",20,10);
			Customer c= cService.createCustomer("Pepa", 3);
			PurchaseService purService= new PurchaseService(purRep, cService, pService, nService);	
			assertThrows(CustomerCreditLimitExceededException.class, () -> {purService.createPurchase(c.getId(), p.getId());});
			
			assertThat("mensaje", pService.getProduct(p.getId()).getStock(), equalTo(10));//is(10)
			assertThat("mensaje", cService.getCustomer(c.getId()).getCredit(), equalTo(3));//is(3)
			verify(nService, times(0)).notify("Purchase: customer=" + c.getId() + ", product=" + c.getId());
			//nService.notify("Purchase: customer=" + c.getId() + ", product=" + c.getId());	//Comprobamos que se haya enviado la notificacion
			
			
		}*/
		
		/*El pedido no se realiza cuando el producto no tiene stock. Se verifica que la excepción es la
		que corresponde a esta situación.*/
		/*public void PurchaseService_NoProducto() {
			
		}
		*/
	}
