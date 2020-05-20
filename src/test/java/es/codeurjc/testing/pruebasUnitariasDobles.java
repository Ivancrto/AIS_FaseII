package es.codeurjc.testing;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import es.codeurjc.shop.domain.ShopException;
import es.codeurjc.shop.domain.customer.CustomerCreditLimitExceededException;
import es.codeurjc.shop.domain.customer.CustomerService;
import es.codeurjc.shop.domain.product.ProductService;
import es.codeurjc.shop.domain.product.ProductStockWithdrawExceededException;
import es.codeurjc.shop.domain.purchase.PurchaseRepository;
import es.codeurjc.shop.domain.purchase.PurchaseService;
import es.codeurjc.shop.notification.NotificationService;

/*Se tiene que comprobar que la clase implementa correctamente las siguientes situaciones cuando se crea un pedido:
Para su implementación, dado que se trata de tests unitarios, se usarán dobles para todas las
dependencias de la clase: PurchaseRepository, CustomerService, ProductService y NotificationService.*/

public class pruebasUnitariasDobles {

	public static Collection<Object[]> data() {

		TestingScenarios s = new TestingScenarios();
		Object[][] data = { { s.scenario1 }, { s.scenario2 }, { s.scenario3 } };

		return Arrays.asList(data);
	}

	/* *********************************** */

	@ParameterizedTest
	@MethodSource("data")
	public void generico(List<Pedido> pedidoGenerico) throws ShopException {
		// Given
		PurchaseRepository purchaseRep = mock(PurchaseRepository.class); // Este mock no se usa para nada, ya que las
																			// modificaciones al repo
																			// se hace mediante los Services
		CustomerService customerServ = mock(CustomerService.class);
		ProductService productServ = mock(ProductService.class);
		NotificationService notificationServ = mock(NotificationService.class);

		PurchaseService purchaseServ = new PurchaseService(purchaseRep, customerServ, productServ, notificationServ);

		// Mock getProuctCost
		when(productServ.getProductCost(pedidoGenerico.get(0).getIdP())).thenReturn(pedidoGenerico.get(0).getCost()); // En
																														// cualquiera
																														// de
																														// los
																														// 3
																														// casos

		// Mock withdrawProduct
		if (pedidoGenerico.get(0).getIdC() == 3)
			doThrow(new ProductStockWithdrawExceededException()).when(productServ)
					.withdrawProduct(pedidoGenerico.get(0).getIdP());

		// Mock reserveCredit
		else if (pedidoGenerico.get(0).getIdC() == 2) {
			doThrow(new CustomerCreditLimitExceededException()).when(customerServ).reserveCredit(2,
					pedidoGenerico.get(0).getCost());
		}
		// Mock save del repository
		when(purchaseRep.save(any())).thenReturn(null);

		// When
		if (pedidoGenerico.get(0).getIdC() == 3) {
			ProductStockWithdrawExceededException e1 = Assertions.assertThrows(
					ProductStockWithdrawExceededException.class,
					() -> purchaseServ.createPurchase(3, pedidoGenerico.get(0).getIdP()));
			// Preguntar al profesor si hay que hacer un assertEquals
		} else if (pedidoGenerico.get(0).getIdC() == 2) {
			CustomerCreditLimitExceededException e2 = Assertions.assertThrows(
					CustomerCreditLimitExceededException.class,
					() -> purchaseServ.createPurchase(2, pedidoGenerico.get(0).getIdP()));
			// Preguntar al profesor si hay que hacer un assertEquals
		} else {
			purchaseServ.createPurchase(pedidoGenerico.get(0).getIdC(), pedidoGenerico.get(0).getIdP());
		}

		// Then
		verify(productServ, times(pedidoGenerico.get(0).getGetProductCost()))
				.getProductCost(pedidoGenerico.get(0).getIdP());
		verify(productServ, times(pedidoGenerico.get(0).getWithdrawProduct()))
				.withdrawProduct(pedidoGenerico.get(0).getIdP());
		verify(customerServ, times(pedidoGenerico.get(0).getReserveCredit()))
				.reserveCredit(pedidoGenerico.get(0).getIdC(), pedidoGenerico.get(0).getCost());
		verify(notificationServ, times(pedidoGenerico.get(0).getNotify())).notify(pedidoGenerico.get(0).getMsg());
		verify(purchaseRep, times(pedidoGenerico.get(0).getSave())).save(any());

	}

}
