package es.codeurjc.testing;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import es.codeurjc.shop.Application;
import io.github.bonigarcia.wdm.WebDriverManager;

public class PruebasSistema {


    private List<WebDriver> drivers = new ArrayList<WebDriver>();

    @BeforeAll
    public static void setupClass() {
            WebDriverManager.chromedriver().setup();
            Application.start();
    }

    @BeforeAll
    public static void teardownClass() {
            Application.stop();
    }

    @BeforeEach
    public void setupTest() {
        drivers.add(new ChromeDriver());
        drivers.add(new ChromeDriver());
    }

    @AfterEach
    public void teardown() {
    	for(WebDriver driver : drivers) {
            if (driver != null) {
                    driver.quit();
            }
    	}
    	drivers.clear();
    }
    

	@Test
	public void Test() throws InterruptedException {
		for(WebDriver driver : drivers) {
			driver.get("http://localhost:8080");
		}
		drivers.get(0).findElement(By.id("product-3")).click();
		drivers.get(0).findElement(By.id("customer-id")).sendKeys("6");
		drivers.get(0).findElement(By.xpath("//input[@value='Purchase']")).click();
		System.out.println(drivers.get(0).findElement(By.id("message")).getText());
	}
}
