import static org.mockito.Mockito.mock;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import es.codeurjc.shop.domain.customer.Customer;
import es.codeurjc.shop.domain.customer.CustomerCreditLimitExceededException;
import es.codeurjc.shop.domain.customer.CustomerService;
import es.codeurjc.shop.domain.product.Product;
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
	public void PurchaseService_CreditoYProducto() {
		Product p=pService.createProduct("Pan de Pipas",1,5);
		Customer c= cService.createCustomer("Pipo", 100);
		PurchaseService purService= new PurchaseService(purRep, cService, pService, nService);	
		purService.createPurchase(c.getId(), p.getId());
		
		assertThat(pService.getProduct(p.getId()).getStock(),is(4));
		assertThat(cService.getCustomer(c.getId()).getCredit(),is(99));
		verify(nService,times(1)).nService.notify("Purchase: customer=" + c.getId() + ", product=" + c.getId());	//Comprobamos que se haya enviado la notificacion
	
	}
	
	/*El pedido no se realiza cuando el cliente no tiene crédito. Se verifica que la excepción es la
	que corresponde a esta situación.*/
	public void PurchaseService_NoCredito() {
		Product p=pService.createProduct("Lasagna",20,10);
		Customer c= cService.createCustomer("Pepa", 3);
		PurchaseService purService= new PurchaseService(purRep, cService, pService, nService);	
		
		Assertions.assertThrows(CustomerCreditLimitExceededException.class, () -> {purService.createPurchase(c.getId(), p.getId());});
		
		assertThat(pService.getProduct(p.getId()).getStock(),is(10));
		assertThat(cService.getCustomer(c.getId()).getCredit(),is(3));
		verify(nService,times(0)).nService.notify("Purchase: customer=" + c.getId() + ", product=" + c.getId());	//Comprobamos que se haya enviado la notificacion
		
		
	}
	
	/*El pedido no se realiza cuando el producto no tiene stock. Se verifica que la excepción es la
	que corresponde a esta situación.*/
	public void PurchaseService_NoProducto() {
		
	}
	


}
