package sauceDemo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class loginPage {
	
	WebDriver driver = null ;
	WebElement userName = null ;
	WebElement password = null ;
	WebElement loginButton = null ;
	SoftAssert softAssert = new SoftAssert() ;
	
	
	
	
  @BeforeMethod
  public void preConditions() {
	
	  //initiate chrome driver
	  driver = new ChromeDriver() ;
	  driver.get("https://www.saucedemo.com");
	  driver.manage().window().maximize() ;
	  
  }





//Check if the username and password fields are on the main screen of the application

@Test (priority = 1) 
public void check_of_loginpage_Attributes() 
{
	userName = driver.findElement(By.id("user-name")) ;
	softAssert.assertTrue(userName.isDisplayed());
	
	password = driver.findElement(By.id("password")) ;
	softAssert.assertTrue(password.isDisplayed());
	
	loginButton = driver.findElement(By.id("login-button")) ;
	softAssert.assertTrue(loginButton.isDisplayed());
	
	 
	  
}



//Check if the given valid credentials work
//Notice i used "dependsOnMethods" because if the login attributes not appear, so these tests will be useless

@Test (priority = 2, dependsOnMethods = "check_of_loginpage_Attributes") 
public void valid_Login() 
{
	//Provide the valid data
	userName = driver.findElement(By.id("user-name")) ;
	userName.sendKeys("standard_user");
	
	password = driver.findElement(By.id("password")) ;
	password.sendKeys("secret_sauce");
	
	loginButton = driver.findElement(By.id("login-button")) ;
	loginButton.click(); 
	
	//Validate the display of "Swag Labs" Text
	WebElement successfulLogin = driver.findElement(By.xpath("//div[text()='Swag Labs']")) ;
	softAssert.assertTrue(successfulLogin.isDisplayed()) ;
	
	//Validate the correctness of "Swag Labs" Text
	String actualResult = successfulLogin.getText() ;
	String expectedResult = "Swag Labs" ;
	softAssert.assertEquals(actualResult, expectedResult) ;
	
	 
	  
}


//Check if the given wrong credentials work
//Notice i used "dependsOnMethods" because if the login attributes not appear, so these tests will be useless

@Test (priority = 3, dependsOnMethods = "check_of_loginpage_Attributes") 
public void invalid_Login() 
{
	//Provide the invalid data
	userName = driver.findElement(By.id("user-name")) ;
	userName.sendKeys("standard_user1");
	
	password = driver.findElement(By.id("password")) ;
	password.sendKeys("secret_sauce1");
	
	loginButton = driver.findElement(By.id("login-button")) ;
	loginButton.click(); 
	
	//Validate the display of the error message "Epic sadface" Text
	WebElement unsuccessfulLogin = driver.findElement(By.tagName("h3")) ;
	softAssert.assertTrue(unsuccessfulLogin.isDisplayed()) ;
	
	//Validate the correctness of the error message "Epic sadface" Text 
	String actualResult = unsuccessfulLogin.getText() ;
	String expectedResult = "Epic sadface: Username and password do not match any user in this service" ;
	softAssert.assertEquals(actualResult, expectedResult) ;
	
	 
	  
}



//Check for empty credentials
//Notice i used "dependsOnMethods" because if the login attributes not appear, so these tests will be useless


@Test (priority = 4, dependsOnMethods = "check_of_loginpage_Attributes" , dataProvider = "testData") 
public void empty_Credentials_Login(String Username, String Password) throws InterruptedException 
{
	//Provide the invalid data
	userName = driver.findElement(By.id("user-name")) ;
	userName.sendKeys(Username);
	
	password = driver.findElement(By.id("password")) ;
	password.sendKeys(Password);
	
	loginButton = driver.findElement(By.id("login-button")) ;
	loginButton.click(); 
	
	
	//Validate the display of the error message "Epic sadface"
	WebElement unsuccessfulLogin = driver.findElement(By.tagName("h3")) ;
	softAssert.assertTrue(unsuccessfulLogin.isDisplayed()) ;
	
	if (Username == "") 
	{
		//Validate the correctness of the error message text 
		String actualResult = unsuccessfulLogin.getText() ;
		String expectedResult = "Epic sadface: Username is required" ;
		softAssert.assertEquals(actualResult, expectedResult) ;
		
	}
	
	else 
	{
		//Validate the correctness of the error message text 
		String actualResult = unsuccessfulLogin.getText() ;
		String expectedResult = "Epic sadface: Password is required" ;
		softAssert.assertEquals(actualResult, expectedResult) ;
		
		//This sleep just to give u a time to see the error message when appearing before providing the other data 
		Thread.sleep(1000);
	}
	

}



//This method to provide the empty username and password for "empty_Credentials_Login" method

@DataProvider (name = "testData")
public Object[][] empty_Data_Providers()
{
	return new Object[][] { {"standard_user", ""}, 
		                    {"","secret_sauce"}} ;
	
}


@AfterMethod
public void close_Browser()
{
	driver.close();
}

}