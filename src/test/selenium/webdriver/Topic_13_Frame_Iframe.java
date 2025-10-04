package selenium.webdriver;

import com.google.common.base.Verify;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class Topic_13_Frame_Iframe {
    WebDriver driver;
    Select select;
    JavascriptExecutor jsExecutor;

    @BeforeClass
    public void beforeClass() {
        driver = new ChromeDriver();
        jsExecutor = (JavascriptExecutor) driver;
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    }

    @Test
    public void TC_01_iframe_Toidicodedao() {

        //A (toidicodedao)
        driver.get("https://toidicodedao.com/");


        //Switch vào frame/iframe trước khi thao tác với element bên trong nó
        //A -> B(facebook)
        driver.switchTo().frame(driver.findElement(By.cssSelector("aside#facebook-likebox-3 iframe")));

        //Verify số lượng like trên facebook
        Assert.assertTrue(driver.findElement(By.xpath("//a[text()='Tôi đi code dạo']")).isDisplayed());

        // Switch về main page A
      driver.switchTo().defaultContent();
        //driver.switchTo().parentFrame();

    }

    @Test
    public void TC_02_iframe_Formsite() {
//        Step 1: Truy cập vào trang https://www.formsite.com/templates/education/campus-safety-survey/
        driver.get("https://www.formsite.com/templates/education/campus-safety-survey/");
        //Switch vào iframe

        //Click form Preview Background
        By surveyFormBackground  = By.cssSelector("div#imageTemplateContainer");
        driver.findElement(surveyFormBackground).click();
        sleepInSecond(3);

        //Switch vào iframe
        By surveyFormFrame = By.cssSelector("div#formTemplateContainer>iframe");
        driver.switchTo().frame(driver.findElement(surveyFormFrame));

        //Nhập First name/ Last name/ Address/ Email/ Phone

        //Step 2: Chọn Year, Residence và Gender và click button Submit
        select = new Select(driver.findElement(By.id("RESULT_RadioButton-2")));
        select.selectByVisibleText("Junior");

        select = new Select(driver.findElement(By.id("RESULT_RadioButton-3")));
        select.selectByVisibleText("North Dorm");

        //Chọn Gender
        WebElement MaleRadio = driver.findElement(By.xpath("//label[text()='Male']/preceding-sibling::input"));
        jsExecutor.executeScript("arguments[0].click();",MaleRadio);

        driver.findElement(By.id("FSsubmit")).click();
//
//        //Switch về main page
        driver.switchTo().defaultContent();

        //Step 3: Click Login
        By loginButton = By.xpath("//nav[@class='header header--desktop']//a[@title='Log in']");
        jsExecutor.executeScript("arguments[0].click();",driver.findElement(loginButton));
        sleepInSecond(2);

        //Step 4:Tại form Login, không nhập gì và click button Login
        driver.findElement(By.id("login")).click();

        //Step 5: Verify error message xuất hiện tại Username và Password
        Assert.assertEquals(driver.findElement(By.cssSelector("div#message-error")).getText(),"Username and password are both required.");

    }

    @Test
    public void TC_03_Iframe_hdfcbank() {

        //A
        driver.get("https://netbanking.hdfcbank.com/netbanking/");

        //B (Login)
        driver.switchTo().frame("login_page");
        //B:
        driver.findElement(By.name("fldLoginUserId")).sendKeys("automationfc");
        driver.findElement(By.cssSelector("a.login-btn")).click();
        sleepInSecond(5);

        //C: Verify password textbox;
        driver.switchTo().defaultContent();
        Assert.assertTrue(driver.findElement(By.cssSelector("input[type='password']")).isDisplayed());
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
