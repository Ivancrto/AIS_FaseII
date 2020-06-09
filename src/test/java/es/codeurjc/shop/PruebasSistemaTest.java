package es.codeurjc.shop;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import es.codeurjc.shop.Application;
import io.github.bonigarcia.wdm.WebDriverManager;


public class PruebasSistemaTest {


	public static Collection<Object[]> data() {
		TestingScenarios scenarios = new TestingScenarios();
		Object[][] data = { { scenarios.scenario1S }, { scenarios.scenario2S }, { scenarios.scenario3S } };
		return Arrays.asList(data);
	}


	private List<WebDriver> drivers = new ArrayList<WebDriver>();

	@BeforeAll
	public static void setupClass() {
		WebDriverManager.chromedriver().setup();
		Application.start();//Levantamos la aplicacion
	}

	@AfterAll
	public static void teardownClass() {
		Application.stop();//Paramos la aplicacion
	}

	@BeforeEach
	public void setupTest() {
		drivers.add(new ChromeDriver()); //Creamos los navegadores
	}

	@AfterEach
	public void teardown() {
		for (WebDriver driver : drivers) {
			if (driver != null) {
				driver.quit();//Cerramos los navegadores
			}
		}
		drivers.clear();
	}

	//En un primer momento empezaba desde 
	
	@ParameterizedTest
	@MethodSource("data")
	public void Test(ArrayList<Pedido> pedidoGenerico) throws InterruptedException {

		if (pedidoGenerico.get(0).getIdP() != 1) {
			inicializar(pedidoGenerico.get(0).getIdP());
			drivers.get(0).findElement(By.id("customer-id")).sendKeys(String.valueOf(pedidoGenerico.get(0).getIdC())); // numero
																														// del
																														// customer
			drivers.get(0).findElement(By.xpath("//input[@value='Purchase']")).click(); // click para realizar la compra
			WebDriverWait wait = new WebDriverWait(drivers.get(0), 30);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("message"))).getText();
			String mensaje =  drivers.get(0).findElement(By.id("message")).getText(); /*
								 * Pasamos al siguiente html donde nos encontramos con el mensaje, que tendremos
								 * que validar
								 */
			assertThat(mensaje).isEqualTo(pedidoGenerico.get(0).getMsg()); // Comprobamos si el mensaje es el que se esperaba
		} else {
			drivers.add(new ChromeDriver());//Creamos otro para el otro usuario
			inicializar(pedidoGenerico.get(0).getIdP());
			drivers.get(0).findElement(By.id("customer-id")).sendKeys(String.valueOf(pedidoGenerico.get(0).getIdC())); // numero
																														// cliente
			drivers.get(1).findElement(By.id("customer-id")).sendKeys(String.valueOf(pedidoGenerico.get(1).getIdC())); // numero
																														// cliente
			drivers.get(0).findElement(By.xpath("//input[@value='Purchase']")).click(); // click para realizar la compra
			// Thread.sleep(1000);
			drivers.get(1).findElement(By.xpath("//input[@value='Purchase']")).click(); // click para realizar la compra
			WebDriverWait wait0 = new WebDriverWait(drivers.get(0), 30);
			WebDriverWait wait1 = new WebDriverWait(drivers.get(1), 30);
			wait0.until(ExpectedConditions.presenceOfElementLocated(By.id("message"))).getText();//0 Esperamos por si la pagina no se cargo
			wait1.until(ExpectedConditions.presenceOfElementLocated(By.id("message"))).getText();//1 Esperamos por si la pagina no se cargo
			String mensaje0 =  drivers.get(0).findElement(By.id("message")).getText();//Obtenemos el mensaje del primer navegador
			String mensaje1 =  drivers.get(1).findElement(By.id("message")).getText();//Obtenemos el mensaje del segundo navegador
			assertThat(mensaje0).isEqualTo(pedidoGenerico.get(0).getMsg()); // Comprobamos si el mensaje es el que se esperaba, si se realiza
			assertThat(mensaje1).isEqualTo(pedidoGenerico.get(1).getMsg()); // Comprobamos si el mensaje es el que se esperaba, no se realiza

		}

	}
	
	public void inicializar(long n) {
		for (WebDriver driver : drivers) {
			driver.get("http://localhost:8081/product/" + n); //Le decimos la url al navegador
		}
		
	}

}
