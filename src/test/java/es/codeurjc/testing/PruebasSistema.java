package es.codeurjc.testing;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import es.codeurjc.shop.Application;
import io.github.bonigarcia.wdm.WebDriverManager;

@RunWith(Parameterized.class)
public class PruebasSistema {

	@Parameters
	public static Collection<Object[]> data() {

		TestingScenarios scenarios = new TestingScenarios();

		Object[][] data = { { scenarios.scenario1W }, { scenarios.scenario2W }, { scenarios.scenario3W } };

		return Arrays.asList(data);
	}

	@Parameter(0)
	public List<Pedido> pedidoGenerico;

	private List<WebDriver> drivers = new ArrayList<WebDriver>();

	@BeforeClass
	public static void setupClass() {
		WebDriverManager.chromedriver().setup();
		Application.start();
	}

	@AfterClass
	public static void teardownClass() {
		Application.stop();
	}

	@Before
	public void setupTest() {
		drivers.add(new ChromeDriver());
		drivers.add(new ChromeDriver());
	}

	@After
	public void teardown() {
		for (WebDriver driver : drivers) {
			if (driver != null) {
				driver.quit();
			}
		}
		drivers.clear();
	}

	@Test
	public void Test() throws InterruptedException {
		for (WebDriver driver : drivers) {
			driver.get("http://localhost:8080");
		}
		if (pedidoGenerico.get(0).getIdP() != 1) {
			drivers.get(0).findElement(By.id("product-" + pedidoGenerico.get(0).getIdP())).click(); // numero del
																									// producto
			drivers.get(0).findElement(By.id("customer-id")).sendKeys(String.valueOf(pedidoGenerico.get(0).getIdC())); // numero
																														// del
																														// customer
			drivers.get(0).findElement(By.xpath("//input[@value='Purchase']")).click(); // click para realizar la compra
			String mensaje = drivers.get(0).findElement(By.id("message"))
					.getText(); /*
								 * Pasamos al siguiente html donde nos encontramos con el mensaje, que tendremos
								 * que validar
								 */
			assertThat(mensaje).isEqualTo(pedidoGenerico.get(0).getMsg()); // Comprobamos si el mensaje es el que
		} else {
			drivers.get(0).findElement(By.id("product-" + pedidoGenerico.get(0).getIdP())).click();
			drivers.get(1).findElement(By.id("product-" + pedidoGenerico.get(1).getIdP())).click();
			drivers.get(0).findElement(By.id("customer-id")).sendKeys(String.valueOf(pedidoGenerico.get(0).getIdC())); // numero
																														// cliente
			drivers.get(1).findElement(By.id("customer-id")).sendKeys(String.valueOf(pedidoGenerico.get(1).getIdC())); // numero
																														// cliente
			drivers.get(0).findElement(By.xpath("//input[@value='Purchase']")).click(); // click para realizar la compra
			// Thread.sleep(1000);
			drivers.get(1).findElement(By.xpath("//input[@value='Purchase']")).click(); // click para realizar la compra
			String mensaje0 = drivers.get(0).findElement(By.id("message")).getText();
			assertThat(mensaje0).isEqualTo(pedidoGenerico.get(0).getMsg()); // Comprobamos si el mensaje es el que
			String mensaje1 = drivers.get(1).findElement(By.id("message")).getText();
			assertThat(mensaje1).isEqualTo(pedidoGenerico.get(1).getMsg()); // Comprobamos si el mensaje es el que

		}

	}

}
