package tests_To_Be_Executed;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.Parameters;

import com.beust.jcommander.Parameter;

import pages_Of_System_Under_Test.Calculator;

public class Tests {

	Calculator calculator;
	WebDriver Driver;
	WebDriverWait Wait;
	String Node, URL; 
	
	@Parameters("Browser")
	@BeforeClass
	public void startup() throws MalformedURLException{
    Node = "http://localhost:5555/wd/hub/";
    DesiredCapabilities DC = DesiredCapabilities.chrome();
    DC.setBrowserName("chrome");
    Driver = new RemoteWebDriver(new java.net.URL(Node),DC);
   
    //Wait.until(ExpectedConditions.visibilityOf(element))
	}
	
	@Test
	public void addition(){
		System.out.println("hello");
		 Driver.get("http://calculator-1.com/");
	}
	
	@AfterTest
	public void end(){
		Driver.quit();
	}
	
	public String browertype(){
		String browser = "chrome";
		return browser;
	}
}
