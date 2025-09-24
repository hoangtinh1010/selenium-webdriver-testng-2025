package selenium.webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Random;

public class Topic_05_Element_Exercise_Part_III_Login {
    WebDriver driver;
    String firstName, lastName, fullName, emailAddress, password,nonExitEmailAddress;
    By emailTextboxBy = By.id("email");
    By passwordTextboxBy = By.id("pass");
    By loginButtonBy = By.name("send");

    @BeforeClass
    public void beforeClass() {

        driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        firstName = "Steve";
        lastName = "Job";
        fullName = firstName + " " + lastName;
        emailAddress = "afc" + randomNumber() + "@mailinator.com";
        nonExitEmailAddress = "mailtest" + randomNumber() + "@mailinator.com";
        password ="123123";


    }


    @BeforeMethod
    public void beforeMethod() {
        driver.get("https://live.techpanda.org/");
        driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
    }

    @Test
    public void Login_TC01_Empty_Email_Password() {
        driver.findElement(emailTextboxBy).sendKeys("");
        driver.findElement(passwordTextboxBy).sendKeys("");
        driver.findElement(loginButtonBy).click();

        //Verify
        Assert.assertEquals(driver.findElement(By.id("advice-required-entry-email")).getText(), "This is a required field.");
        Assert.assertEquals(driver.findElement(By.id("advice-required-entry-pass")).getText(), "This is a required field.");

    }

    @Test
    public void Login_TC02_Invalid_Email() {
        driver.findElement(emailTextboxBy).sendKeys("test@123123");
        driver.findElement(passwordTextboxBy).sendKeys("123456");
        driver.findElement(loginButtonBy).click();

        Assert.assertEquals(driver.findElement(By.id("advice-validate-email-email")).getText(),"Please enter a valid email address. For example johndoe@domain.com.");

    }

    @Test
    public void Login_TC03_Invalid_Password() {
        driver.findElement(emailTextboxBy).sendKeys("automationfc@gmail.com");
        driver.findElement(passwordTextboxBy).sendKeys("123");
        driver.findElement(loginButtonBy).click();

        //Verify
        Assert.assertEquals(driver.findElement(By.id("advice-validate-password-pass")).getText(),"Please enter 6 or more characters without leading or trailing spaces.");


    }

    @Test
    public void Login_TC04_Create_New_Account_Success() {
        //Existed Email
        driver.findElement(By.xpath("//a[@title='Create an Account']")).click();

        driver.findElement(By.id("firstname")).sendKeys(firstName);
        driver.findElement(By.id("lastname")).sendKeys(lastName);
        driver.findElement(By.id("email_address")).sendKeys(emailAddress);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("confirmation")).sendKeys(password);

        driver.findElement(By.xpath("//button[@title='Register']")).click();

        //chấp nhận security do http
        sleepInSecond(2);
        driver.findElement(By.xpath("//button[text()='Send anyway']")).click();



        //Verify
        Assert.assertEquals(driver.findElement(By.cssSelector("div.page-title>h1")).getText(),"MY DASHBOARD");
        Assert.assertEquals(driver.findElement(By.cssSelector("li.success-msg span")).getText(),"Thank you for registering with Main Website Store.");


        String contactInformation = driver.findElement(By.cssSelector("div.box-content p")).getText();
        Assert.assertTrue(contactInformation.contains(fullName));
        Assert.assertTrue(contactInformation.contains(emailAddress));

        //Logout
        driver.findElement(By.xpath("//header//span[text()='Account']")).click();
        driver.findElement(By.xpath("//a[@title='Log Out']")).click();

        Assert.assertTrue(driver.findElement(By.xpath("//a[@class='logo']")).isDisplayed());

    }

    @Test
    public void Login_TC05_Invalid_Email_Or_Password() {
        //Existed Email + incorrect Password -> Unsuccess login
        driver.findElement(emailTextboxBy).sendKeys(emailAddress);
        driver.findElement(passwordTextboxBy).sendKeys("123456");
        driver.findElement(loginButtonBy).click();

        //chấp nhận security do http
        sleepInSecond(2);
        driver.findElement(By.xpath("//button[text()='Send anyway']")).click();

        Assert.assertEquals(driver.findElement(By.cssSelector("li.error-msg span")).getText(),"Invalid login or password.");

        // Non-existed Email + valit/correct Password -> Unsuccess login
        driver.findElement(emailTextboxBy).clear();
        driver.findElement(emailTextboxBy).sendKeys(nonExitEmailAddress);

        driver.findElement(passwordTextboxBy).clear();
        driver.findElement(passwordTextboxBy).sendKeys(password);
        driver.findElement(loginButtonBy).click();

        //chấp nhận security alert nếu có
        sleepInSecond(2);
        driver.findElement(By.xpath("//button[text()='Send anyway']")).click();

        Assert.assertEquals(driver.findElement(By.cssSelector("li.error-msg span")).getText(),"Invalid login or password.");

    }



    @Test
    public void Login_TC06_Valid_Email_And_Password() {
        // Valid Email + Valit Password -> success login
        driver.findElement(emailTextboxBy).sendKeys(emailAddress);
        driver.findElement(passwordTextboxBy).sendKeys(password);
        driver.findElement(loginButtonBy).click();

        //chấp nhận security alert nếu có
        sleepInSecond(2);
        driver.findElement(By.xpath("//button[text()='Send anyway']")).click();

        //Verify
        Assert.assertEquals(driver.findElement(By.cssSelector("div.page-title>h1")).getText(),"MY DASHBOARD");

        String contactInformation = driver.findElement(By.cssSelector("div.box-content p")).getText();
        Assert.assertTrue(contactInformation.contains(fullName));
        Assert.assertTrue(contactInformation.contains(emailAddress));

    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }

    public void sleepInSecond(long timeInSecond) {
        try {
            Thread.sleep(timeInSecond * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int randomNumber() {
        Random rand = new Random();
        return rand.nextInt(9999);
    }
}
