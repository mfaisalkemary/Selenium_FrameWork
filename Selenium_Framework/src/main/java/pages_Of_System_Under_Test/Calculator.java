package pages_Of_System_Under_Test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Calculator {
public WebDriver Driver;

public Calculator(WebDriver driver){
	this.Driver=driver;
	PageFactory.initElements(driver, this);
}

@FindBy(id="nmr_25")
public WebElement Number0;

@FindBy(id="nmr_19")
public WebElement Number1;

@FindBy(id="nmr_20")
public WebElement Number2;

@FindBy(id="nmr_21")
public WebElement Number3;

@FindBy(id="nmr_13")
public WebElement Number4;

@FindBy(id="nmr_14")
public WebElement Number5;

@FindBy(id="nmr_15")
public WebElement Number6;

@FindBy(id="nmr_7")
public WebElement Number7;

@FindBy(id="nmr_8")
public WebElement Number8;

@FindBy(id="nmr_9")
public WebElement Number9;

@FindBy(id="nmr_24")
public WebElement Cancel;

@FindBy(id="display")
public WebElement Result;

@FindBy(id="nmr_22")
public WebElement Sumation;

@FindBy(id="nmr_17")
public WebElement Minus;

@FindBy(id="nmr_16")
public WebElement Multiply;

@FindBy(id="nmr_10")
public WebElement Divide;

@FindBy(id="nmr_23")
public WebElement Equal;


}
