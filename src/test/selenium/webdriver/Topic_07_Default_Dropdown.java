package selenium.webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class Topic_07_Default_Dropdown {
    WebDriver driver;

    JavascriptExecutor jsExecutor;
    WebDriverWait explicitWait;
    Actions action;
    Select select;

    @BeforeClass
    public void beforeClass() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

        //JavaScript Executor/webDriverWait/Actions/...
        jsExecutor = (JavascriptExecutor) driver;
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(3));
        action = new Actions(driver);


    }

    @Test
    public void TC_01_Rode() {
        driver.get("https://rode.com/en-au/support/where-to-buy");

        // Khởi tạo khi sử dung (element xuất hiện)
        // Khởi tạo select de thao tác với element dropdown
        select = new Select(driver.findElement(By.id("country")));

        //Kiểm tra dropdown là single dropdown/không support multiple select
        Assert.assertFalse(select.isMultiple());

        //Chọn giá trị Vietnam trong dropdown
        select.selectByVisibleText("Vietnam");

        //verify selected value is Vietnam
        Assert.assertEquals(select.getFirstSelectedOption().getText(),"Vietnam");

        driver.findElement(By.xpath("//button[@class='btn btn-default']")).click();
        sleepInSecond(5);

        List<WebElement> storeName = driver.findElements(By.xpath("//h4[@class='text-left']"));
        for (WebElement store : storeName) {
            System.out.println(store.getText());
        }





        //Verify
        //Kiểm tra dropdown là single dropdown
        //Kiểm tra dropdown đã được chọn đúng giá trị
    }

    @Test
    public void TC_02_Nopcommerce() {

        String firstName = "Automation";
        String lastName = "FC";
        String emailAddress = "afc" + randomNumber() + "@mailinator.com";
        String companyName = "Automation FC";
        String passWord = "123456";

        driver.get("https://demo.nopcommerce.com/register");

        driver.findElement(By.id("FirstName")).sendKeys(firstName);
        driver.findElement(By.id("LastName")).sendKeys(lastName);
        driver.findElement(By.id("Email")).sendKeys(emailAddress);
        driver.findElement(By.id("Company")).sendKeys(companyName);
        driver.findElement(By.id("Password")).sendKeys(passWord);
        driver.findElement(By.id("ConfirmPassword")).sendKeys(passWord);

        driver.findElement(By.id("register-button")).click();

        //Verify
        Assert.assertEquals(driver.findElement(By.className("result")).getText(),"Your registration completed");




    }

    @AfterClass
    public void afterClass() {
//        driver.quit();
    }

    public void sleepInSecond(long timeInSecond){
        try {
            Thread.sleep(timeInSecond * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public int randomNumber(){
        Random rand = new Random();
        return rand.nextInt(9999);
    }
}
