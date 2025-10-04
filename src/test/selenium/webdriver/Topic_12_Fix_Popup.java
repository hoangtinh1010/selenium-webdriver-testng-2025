package selenium.webdriver;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class Topic_12_Fix_Popup {
    WebDriver driver;
    Alert alert;


    @BeforeClass
    public void beforeClass() {
        ChromeOptions options = new ChromeOptions();

        // Disable notification popup
        options.addArguments("--disable-notifications");

        // Khởi tạo driver với options
       driver = new ChromeDriver(options);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    }

    @Test
    public void TC_01_Ngoaingu24h_InDOM() {
        driver.get("https://ngoaingu24h.vn/");
        sleepInSecond(3);


        By loginPopup = By.xpath("//div[@role='dialog']");
        

        //click button Đăng nhập
       driver.findElement(By.xpath("//button[text()='Đăng nhập']")).click();
        sleepInSecond(3);

        //Verify popup hiển thị
        Assert.assertTrue(driver.findElement(loginPopup).isDisplayed());

        driver.findElement(By.cssSelector("input[autocomplete='username']")).sendKeys("automationfc");
        driver.findElement(By.cssSelector("input[autocomplete='new-password']")).sendKeys("automationfc");

        driver.findElement(By.xpath("//div[@class='auth-form']//button[@type='submit']")).click();
        sleepInSecond(2);

        //Verify error message hiển thị
        String errorMessage = driver.findElement(By.xpath("//div[@id='notistack-snackbar']")).getText();
        Assert.assertEquals(errorMessage,"Bạn đã nhập sai tài khoản hoặc mật khẩu!");

    }

    @Test
    public void TC_02_Kyna_InDOM() {
        driver.get("https://skills.kynaenglish.vn/dang-nhap");
        sleepInSecond(2);
        By loginPopup = By.cssSelector("div.k-popup-account-mb-content");

        //Verify popup Login hiển thị
        Assert.assertTrue(driver.findElement(loginPopup).isDisplayed());

        //Nhập thông tin đăng nhập
        driver.findElement(By.cssSelector("input#user-login")).sendKeys("automation@gmail.com");
        driver.findElement(By.cssSelector("input#user-password")).sendKeys("123456");
        driver.findElement(By.cssSelector("button#btn-submit-login")).click();
        sleepInSecond(2);

        //Verify thông báo lỗi hiển thị "Sai tên ....."

        Assert.assertEquals(driver.findElement(By.xpath("//div[@id='password-form-login-message")).getText(),"Sai tên đăng nhập hoặc mật khẩu");



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
