package es.codeurjc.testing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import es.codeurjc.shop.domain.ShopException;
import es.codeurjc.shop.domain.customer.Customer;
import es.codeurjc.shop.domain.customer.CustomerCreditLimitExceededException;
import es.codeurjc.shop.domain.customer.CustomerNotFoundException;
import es.codeurjc.shop.domain.customer.CustomerService;
import es.codeurjc.shop.domain.product.Product;
import es.codeurjc.shop.domain.product.ProductNotFoundException;
import es.codeurjc.shop.domain.product.ProductService;
import es.codeurjc.shop.domain.purchase.PurchaseRepository;
import es.codeurjc.shop.domain.purchase.PurchaseService;
import es.codeurjc.shop.notification.NotificationService;


/*Se tiene que comprobar que la clase implementa correctamente las siguientes situaciones cuando se crea un pedido:
Para su implementación, dado que se trata de tests unitarios, se usarán dobles para todas las
dependencias de la clase: PurchaseRepository, CustomerService, ProductService y NotificationService.*/  
public class pruebasUnitariasDobles {

	PurchaseRepository purRep = mock(PurchaseRepository.class);
	CustomerService cService = mock(CustomerService.class);
	ProductService pService = mock(ProductService.class);
	NotificationService nService = mock(NotificationService.class);	

	/*El pedido se realiza correctamente cuando el cliente tiene crédito y el producto stock.
	Además, la notificación se envía.*/
	
	@Test
	public void generico()  {
		
		//Given
		PurchaseRepository purchaseRep = mock(PurchaseRepository.class); //Este mock no se usa para nada, ya que las modificaciones al repo
																		//se hace mediante los Services
		CustomerService customerServ = mock(CustomerService.class);
		ProductService productServ = mock(ProductService.class);
		NotificationService notificationServ = mock(NotificationService.class);	
		
		PurchaseService purchaseServ = new PurchaseService(purchaseRep, customerServ, productServ, notificationServ);
		
		//Comprobamos si existe dicho comprador
		 Exception e1 = assertThrows(CustomerNotFoundException.class,()-> customerServ.getCustomer(6)); //el 6 se pasaria por parametro
		 assertEquals(null, e1.getMessage()); //el null se pasaria por parametro, asi comprobamos si funciona
		 //Comprobamos si existe dicho producto
		 Exception e2 = assertThrows(ProductNotFoundException.class,()-> productServ.getProduct(3)); 
		 assertEquals(null, e2.getMessage()); 
		 //Obtenems coste del producto
		 Exception e3 = assertThrows(ProductNotFoundException.class,()-> productServ.getProductCost(3)); 
		 assertEquals(null, e3.getMessage()); 
		 //UPDATEPRODUC de productServ 
		// Product p = productServ.getProduct(3);
		 //productServ.updateProduct(p.getId(), p.getName(), p.getCost(), p.getStock()-1);
		 //CREATEPURCHASE de purchaseServi, al usar este metodo se actualiza el monedero del customer
		 Exception e4 = assertThrows( ShopException.class, ()-> purchaseServ.createPurchase(6, 3));
		 assertEquals(null, e4.getMessage()); 
		 //POR ULTIMO COMPROBAMO EL PRURCHASE CON GET PURCHASE 
		 
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
		
		Exception e1 = assertThrows(CustomerNotFoundException.class,()-> customerServ.getCustomer(16123)); 
		assertEquals("8435011945389762511L", e1.getMessage()); 
	}
	
	
	
	
	@Test
	public void PurchaseService_CreditoYProducto() throws ShopException {
		Product p=pService.createProduct("Pan de Pipas",1,5);
		Customer c= cService.createCustomer("Pipo", 100);
		PurchaseService purService= new PurchaseService(purRep, cService, pService, nService);	
		purService.createPurchase(c.getId(), p.getId());
		
		assertThat("mensaje" ,pService.getProduct(p.getId()).getStock(), equalTo(4)); //is(4)
		assertThat(cService.getCustomer(c.getId()).getCredit(),  equalTo(99)); //is(99)
		verify(nService,times(1)).notify("Purchase: customer=" + c.getId() + ", product=" + c.getId());	//Comprobamos que se haya enviado la notificacion
	
	}
	
	/*El pedido no se realiza cuando el cliente no tiene crédito. Se verifica que la excepción es la
	que corresponde a esta situación.*/
	public void PurchaseService_NoCredito() throws ProductNotFoundException, CustomerNotFoundException {
		Product p=pService.createProduct("Lasagna",20,10);
		Customer c= cService.createCustomer("Pepa", 3);
		PurchaseService purService= new PurchaseService(purRep, cService, pService, nService);	

		assertThrows(CustomerCreditLimitExceededException.class, () -> {purService.createPurchase(c.getId(), p.getId());});
		
		assertThat("mensaje", pService.getProduct(p.getId()).getStock(), equalTo(10));//is(10)
		assertThat("mensaje", cService.getCustomer(c.getId()).getCredit(), equalTo(3));//is(3)
		verify(nService, times(0)).notify("Purchase: customer=" + c.getId() + ", product=" + c.getId());
		//nService.notify("Purchase: customer=" + c.getId() + ", product=" + c.getId());	//Comprobamos que se haya enviado la notificacion
		
		
	}
	
	/*El pedido no se realiza cuando el producto no tiene stock. Se verifica que la excepción es la
	que corresponde a esta situación.*/
	public void PurchaseService_NoProducto() {
		
	}
	


}
