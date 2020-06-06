package es.codeurjc.shop;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import es.codeurjc.shop.Application;

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
		Object[][] data = { { s.scenario1API }, { s.scenario2API }, { s.scenario3API } };

		return Arrays.asList(data);
	}


@ParameterizedTest
	@MethodSource("data")
    public void test(List<Pedido> pedidoGenerico) {
        //el 3 y el statusCode se debe plantear en los escenarios como una sola variable

		if(pedidoGenerico.get(0).getStatusAPI()==200) {
			given().
			contentType("application/json").
			body("{\"productId\":\""+pedidoGenerico.get(0).getIdP()+"\",\"customerId\":"+ pedidoGenerico.get(0).getIdC()+"}").
	    	when().
	    	post("http://localhost:8081/api/purchases/").
	    	then().assertThat().statusCode(equalTo(pedidoGenerico.get(0).getStatusAPI())).
	    	body(pedidoGenerico.get(0).getMessageAPI().get(0), equalTo(pedidoGenerico.get(0).getValueID()));
		}
		else {
			given().
			contentType("application/json").
			body("{\"productId\":\""+pedidoGenerico.get(0).getIdP()+"\",\"customerId\":"+ pedidoGenerico.get(0).getIdC()+"}").
	    	when().
	    	post("http://localhost:8081/api/purchases/").
	    	then().assertThat().statusCode(equalTo(pedidoGenerico.get(0).getStatusAPI())).
	    	body(pedidoGenerico.get(0).getMessageAPI().get(0),equalTo(pedidoGenerico.get(0).getMessageAPI().get(1))	);
		}
    }
	
}