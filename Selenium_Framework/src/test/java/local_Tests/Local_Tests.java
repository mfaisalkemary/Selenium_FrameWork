package local_Tests;
import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.codoid.products.exception.FilloException;

import local_IO_Data_Conectivity.Excel_Sheet;
import pages_Of_System_Under_Test.Calculator;
public class Local_Tests {
	WebDriver Drive1;
	Calculator Cal1;
	Excel_Sheet Sheet;
	
	@DataProvider(name="numbers")
	public Object [][]GetData() throws IOException, FilloException{
		System.out.println("Before creating sheet");
		Sheet=new Excel_Sheet("C:\\Data_Test\\framework\\FrameWork.xlsx");
		System.out.println("After creating sheet and before getting data");
		//return Sheet.retrieveTestCaseData("Divide","Data 1 Column","Data 2 Column","CaseToRun","y");
		return Sheet.fillo();
		
	}
	
	@BeforeClass
	public void Setup(){
		
		System.setProperty("webdriver.chrome.driver", "C:\\Eclipse\\Chrome2\\chromedriver_win32\\chromedriver.exe");
		Drive1 = new ChromeDriver();
		
		/*System.setProperty("webdriver.gecko.driver", "C:\\Eclipse\\geckodriver\\geckodriver.exe");
		/*Drive1 = new FirefoxDriver();
		Cal1 = new Calculator(Drive1);
		*/
		Drive1.manage().window().maximize();
		Drive1.get("http://calculator-1.com/");
		//Drive1.navigate().to("http://calculator-1.com/");
		System.out.println("Cpmleting setup");
	}
	
	@Test(dataProvider="numbers")
	//@Test
	public void addition(String num1,String num2){
		System.out.println("Calling Test");
		Cal1.addition(num1,num2);
	}
	
	@AfterTest
	public void End(){
		Drive1.quit();
	}
	 
}
