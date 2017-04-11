package hello;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Fail.fail;

import static org.junit.Assert.assertEquals;

import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.But;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@SuppressWarnings("deprecation")
@ContextConfiguration(classes = Application.class, loader = SpringApplicationContextLoader.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@org.springframework.boot.test.IntegrationTest
public class SearchSteps {

	private static String user;
	private static WebDriver driver;
	private Object currentPage;

	@Before({ "@requires_browser" })
	public void buildDriver() {
		System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
		driver = new ChromeDriver();
	}

	@After({ "@requires_browser" })
	public void destroyDriver() {
		driver.quit();
	}

	@Given("^User is on Homepage$")
	public void A_Google_search_page() throws Throwable {
		currentPage = SearchQueryPage.loadUsing(driver);
	}

	@When("^User Navigate to Login Page$")
	public void gotoLoginPage() throws Throwable {
		driver.findElement(By.xpath("/html/body/div[2]/div/a")).click();
	}

	@And("^User enters Username as \"([^\"]*)\" and Password as \"([^\"]*)\"$")
	public void enterCredentials(String username, String password) throws Throwable {
		driver.findElement(By.xpath("/*//*[@id=\"username\"]")).sendKeys(username);
		driver.findElement(By.xpath("/*//*[@id=\"password\"]")).sendKeys(password);
		user = username;
	}
	
	@And("^User submit the credentials by pressing SignIn$")
	public void clickSignIn() throws Throwable {
		driver.findElement(By.xpath("/*//*[@id=\"submit\"]")).click();
	}
	
	@Then("^Message displayed Welcome")
	public void welcomePage() throws Throwable {
		assertEquals(driver.findElement(By.xpath("/html/body/div[2]/h1")).getText(),"Hello "+user);
	}

	private void verifyCurrentPage(Class pageClass) {
		if (!currentPage.getClass().equals(pageClass)) {
			fail(String.format("Expected current page to have type %s - actual type is %s", pageClass.getSimpleName(),
					currentPage.getClass().getSimpleName()));
		}
	}
}
