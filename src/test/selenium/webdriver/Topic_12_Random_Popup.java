package selenium.webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class Topic_12_Random_Popup {
    WebDriver driver;

    @BeforeClass
    public void beforeClass() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    }

    @Test
    public void TC_01_Random_Popup_InDOM_vnk() {

        driver.get("https://vnk.edu.vn/");

        if (driver.findElement(By.cssSelector("div.pum-content")).isDisplayed()) {
            //Close popup
            driver.findElement(By.cssSelector("button.pum-close")).click();
            sleepInSecond(3);
        };

        //Click vào mục Liên hệ
        driver.findElement(By.xpath("//a[text()='Liên hệ' and @class='mega-menu-link']")).click();

        //div.pum-content
    }

    @Test
    public void TC_02_Random_Popup_Not_InDOM_Dehieu() {
        //Hiên tại đang thuộc loại InDOM -> .size luôn >0
        driver.get("https://dehieu.vn/");
        sleepInSecond(5);

        By popup = By.cssSelector("div.modal-content");
        By closePopup = By.cssSelector("button.close");

        //Check popup hiển thị
        if (driver.findElements(popup).size() > 0 ) {
            //Close popup
            driver.findElement(closePopup).click();
            sleepInSecond(3);
        };
        //Verify popup không hiển thị
        Assert.assertEquals(driver.findElements(popup).size() , 0 );
        //Click vào mục Tất cả khóa học
        driver.findElement(By.xpath("//a[text()=' Tất cả khóa học']")).click();

    }

    @Test
    public void TC_03_Popup_Not_InDOM_Tiki() {
        driver.get("https://tiki.vn/");
        sleepInSecond(5);
        By popupBegin = By.cssSelector("div#VIP_BUNDLE");
        By closePopup = By.cssSelector("img[alt='close-icon']");
        By accountButton = By.xpath("//span[text()='Tài khoản']");
        By popupLogin = By.cssSelector("div.ReactModal__Content>div");

        //Check popup hiển thị
        if (driver.findElements(popupBegin).size() > 0 ) {
            //Close popup
            driver.findElement(closePopup).click();
        };
        //Verify popup không hiển thị
        Assert.assertEquals(driver.findElements(popupBegin).size() , 0 );

        //click button TaiKhoan
        driver.findElement(accountButton).click();
        sleepInSecond(3);

        //Verify popup Login hiển thị
        Assert.assertTrue(driver.findElement(popupLogin).isDisplayed());

        //Login with Email
        driver.findElement(By.cssSelector("p.login-with-email")).click();

        //Click button Dang Nhap
        driver.findElement(By.xpath("//button[text()='Đăng nhập']")).click();

        //Verify Error message
        Assert.assertTrue(driver.findElement(By.xpath("//span[text()='Email không được để trống']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//span[text()='Mật khẩu không được để trống']")).isDisplayed());

        //click  Close popup
        driver.findElement(By.cssSelector("button.btn-close")).click();

        //Verify popup Login không hiển thị
        Assert.assertEquals(driver.findElements(popupLogin).size() , 0 );



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
}
