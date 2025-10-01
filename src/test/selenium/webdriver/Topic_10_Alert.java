package selenium.webdriver;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class Topic_10_Alert {
    WebDriver driver;
    Alert alert;

    @BeforeClass
    public void beforeClass() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    }

    @Test
    public void TC_01_Accept_Alert() {
        driver.get("https://automationfc.github.io/basic-form/index.html");

        driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();

        //Switch to:
        //Alert
        alert = driver.switchTo().alert();
        Assert.assertEquals(alert.getText(),"I am a JS Alert");
        sleepInSecond(3);

        alert.accept();
        sleepInSecond(3);


        Assert.assertEquals(driver.findElement(By.cssSelector("p#result")).getText(),"You clicked an alert successfully");
    }

    @Test
    public void TC_02_Confirm_Alert() {
        driver.get("https://automationfc.github.io/basic-form/index.html");
        driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();

        //Switch qua Alert
        alert = driver.switchTo().alert();
        Assert.assertEquals(alert.getText(),"I am a JS Confirm");
        sleepInSecond(3);

        //Cancel an Alert
        alert.dismiss();
        sleepInSecond(3);
        Assert.assertEquals(driver.findElement(By.cssSelector("p#result")).getText(),"You clicked: Cancel");
    }

    @Test
    public void TC_03_Prompt_Alert() {
        driver.get("https://automationfc.github.io/basic-form/index.html");

        String name = "Automation FC";
        driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();

        //Switch qua Alert
        alert = driver.switchTo().alert();
        Assert.assertEquals(alert.getText(),"I am a JS prompt");

        alert.sendKeys(name);

        alert.accept();
        sleepInSecond(3);

        Assert.assertEquals(driver.findElement(By.cssSelector("p#result")).getText(),"You entered: " + name);
    }

    @Test
    public void TC_04_Authenication_Alert_I() {
        String username = "admin";
        String password = "admin";
        driver.get("http://" + username + ":" + password + "@" + "the-internet.herokuapp.com/basic_auth");
        Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(),'Congratulations! You must have the proper credentials.')]")).isDisplayed());
    }

    @Test
    public void TC_04_Authenication_Alert_II() {
        String username = "admin";
        String password = "admin";

        driver.get("http://the-internet.herokuapp.com");
        String basicAuthenLink = driver.findElement(By.xpath("//a[text()='Basic Auth']")).getAttribute("href");
        System.out.println(basicAuthenLink);
//        https://the-internet.herokuapp.com/basic_auth

        String[] basicAuth = basicAuthenLink.split("//");
        //http:
        //the-internet.herokuapp.com/basic_auth

        basicAuthenLink = basicAuth[0] + "//" + username + ":" + password + "@" + basicAuth[1];
        System.out.println(basicAuthenLink);

        driver.get(basicAuthenLink);
        sleepInSecond(3);

        Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(),'Congratulations! You must have the proper credentials.')]")).isDisplayed());
    }
    @AfterClass
    public void afterClass() {
        driver.quit();
    }

    public void sleepInSecond(long timeInSecond){
        try {
            Thread.sleep(timeInSecond * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
