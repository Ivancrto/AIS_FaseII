package es.codeurjc.testing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
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
