package tests_To_Be_Executed;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import java.net.MalformedURLException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.Parameters;

import com.beust.jcommander.Parameter;

import pages_Of_System_Under_Test.Calculator;

public class Tests {

	
	public WebDriver Driver;
	Calculator calculator;
	//WebDriverWait Wait;
	public String Node; 
	
	@Parameters("Browser")
	@BeforeClass
	public void startup(String Browser ) throws MalformedURLException{
		if (Browser.equals("chrome")){
    Node = "http://localhost:5555/wd/hub/";
    DesiredCapabilities DC = DesiredCapabilities.chrome();
    DC.setBrowserName("chrome");
    Driver = new RemoteWebDriver(new java.net.URL(Node),DC);
    calculator = new Calculator(Driver);
    System.out.println("hello from chrome");
		}
		
		/*
    else if (Browser.equalsIgnoreCase("fireFox")){
		   Node = "http://localhost:4446/wd/hub/";
		    DesiredCapabilities DC = DesiredCapabilities.firefox();
		    DC.setBrowserName("firefox");
		    Driver = new RemoteWebDriver(new java.net.URL(Node),DC);
		    System.out.println("hello from firefox");
	 }
    */
    
    //Wait.until(ExpectedConditions.visibilityOf(element))
	}
	@DataProvider
	@Test
	public void Addition(){
		Driver.manage().window().maximize();
		Driver.get("http://calculator-1.com/");
		//Assert.assertEquals(calculator.addition(1,2), 3);
	}
	
	@Test
	public void Multiply() {
		Driver.manage().window().maximize();
		Driver.get("http://calculator-1.com/");
		Assert.assertEquals(calculator.multiply(11, 12), 132);
	}
	
	@AfterTest
	public void end(){
		Driver.quit();
	}
	
	
}
