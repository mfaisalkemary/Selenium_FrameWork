package pages_Of_System_Under_Test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Calculator {
public WebDriver Driver;
public WebDriverWait Wait;
public Actions actions;

public Calculator(WebDriver driver){
	this.Driver=driver;
	PageFactory.initElements(driver, this);
}


@FindBy(id="Btn0")
public WebElement Number0;

@FindBy(id="Btn1")
public WebElement Number1;

@FindBy(id="Btn2")
public WebElement Number2;

@FindBy(id="Btn3")
public WebElement Number3;

@FindBy(id="Btn4")
public WebElement Number4;

@FindBy(id="Btn5")
public WebElement Number5;

@FindBy(id="Btn6")
public WebElement Number6;

@FindBy(id="Btn7")
public WebElement Number7;

@FindBy(id="Btn8")
public WebElement Number8;

@FindBy(id="Btn9")
public WebElement Number9;

@FindBy(id="BtnClear")
public WebElement Cancel;

@FindBy(id="input")
public WebElement Result;

@FindBy(id="BtnPlus")
public WebElement Sumation;

@FindBy(id="BtnMinus")
public WebElement Minus;

@FindBy(id="BtnMult")
public WebElement Multiply;

@FindBy(id="BtnDiv")
public WebElement Divide;

@FindBy(id="BtnCalc")
public WebElement Equal;

@FindBy(xpath = "//*[@id='calccontainer']/div[2]")
public WebElement pageidentifier;



public void waitforpagetoload(){
    Wait = new WebDriverWait(Driver, 20);
	Wait.until(ExpectedConditions.visibilityOf(pageidentifier));
}

public void addition(String num1,String num2){
	actions = new Actions(Driver);
	actions.moveToElement(Result).sendKeys(num1);
	actions.moveToElement(Sumation).click();
	actions.moveToElement(Result).sendKeys(num2);
	actions.moveToElement(Equal).click();
	actions.moveToElement(Result);
	actions.build().perform();
	//int i = Integer.parseInt(Result.getText());
	//return i;
}


public int multiply(int num1,int num2){
	actions = new Actions(Driver);
	actions.moveToElement(Result).sendKeys(Integer.toString(num1));
	actions.moveToElement(Multiply).click();
	actions.moveToElement(Result).sendKeys(Integer.toString(num2));
	actions.moveToElement(Equal).click();
	actions.moveToElement(Result);
	actions.build().perform();
	 int I=Integer.parseInt(Result.getText());
	 return I;
}





}
