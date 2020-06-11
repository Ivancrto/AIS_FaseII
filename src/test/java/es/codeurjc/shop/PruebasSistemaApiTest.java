package es.codeurjc.shop;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import es.codeurjc.shop.Application;

@DisplayName("Pruebas de sistema de pedidos mediante la API REST")
public class PruebasSistemaApiTest {


	@BeforeAll
	public static void setupClass() {
		Application.start();
	}
	@AfterAll
	public static void teardownClass() {
		Application.stop();
	
	}
	public static Collection<Object[]> data() {
		TestingScenarios s = new TestingScenarios();
		Object[][] data = { { s.scenario1API, " Caso correcto" }, { s.scenario2API, " Sin credito" }, { s.scenario3API, " Sin stock" } };
		return Arrays.asList(data);
	}

	@DisplayName("Test genérico")
	@ParameterizedTest(name = "{index}: {1}")
	@MethodSource("data")
    public void test(List<Pedido> pedidoGenerico, String c) {

		if(pedidoGenerico.get(0).getStatusAPI()==200) {	//Caso el cual se realice el pedido con exito
			given().
				contentType("application/json").
				body("{\"productId\":\""+pedidoGenerico.get(0).getIdP()+"\",\"customerId\":"+ pedidoGenerico.get(0).getIdC()+"}"). //informacion que irá en el body
	    	when().
	    		post("http://localhost:8080/api/purchases/").	//Acción http que se va a realizar
	    	then().
	    		assertThat().statusCode(equalTo(pedidoGenerico.get(0).getStatusAPI())).	//Se comprueba el status de la respuesta
	    		body(pedidoGenerico.get(0).getMessageAPI().get(0), equalTo(pedidoGenerico.get(0).getValueID()));	//Información que ira en el body de la respuesta
		}
		else {		//Cuando, ya sea porque no haya stock o ya sea porque el usuairo no tenga credito, la consulta falla
			given().
				contentType("application/json").
				body("{\"productId\":\""+pedidoGenerico.get(0).getIdP()+"\",\"customerId\":"+ pedidoGenerico.get(0).getIdC()+"}").	//informacion que irá en el body
	    	when().
	    		post("http://localhost:8080/api/purchases/").	//Acción http que se va a realizar
	    	then().
	    		assertThat().statusCode(equalTo(pedidoGenerico.get(0).getStatusAPI())).	//Se comprueba el status de la respuesta
	    		body(pedidoGenerico.get(0).getMessageAPI().get(0),equalTo(pedidoGenerico.get(0).getMessageAPI().get(1)));	//Información que ira en el body de la respuesta
		}
    }
	
}
