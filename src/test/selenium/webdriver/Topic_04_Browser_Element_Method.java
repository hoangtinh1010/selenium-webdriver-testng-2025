package selenium.webdriver;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class Topic_04_Browser_Element_Method {
    WebDriver driver;
    WebElement element;

    @BeforeClass
    public void beforeClass() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    }

    @Test
    public void TC_01_Browser() {
        //Các hàm để thao tác Browser thông qua biến driver
        //Biến driver sẽ đại diện cho trình duyệt
        //Các hàm để thao tác với element tông qua bin element
        //Action lên browser

        // Mở ra 1 URL
        driver.get("https://www.facebook.com/");//**

        // Close tab/ window hiện tại
        driver.close();

        // Đóng browser
        driver.quit();

        // Tìm 1 element trên page
        // Trả  về data type là WebElement
        WebElement email =driver.findElement(By.id("email")); //**

        // Tìm nhiều element trên page
        // Trả về data type là List<WebElement>
        driver.findElements(By.xpath("//a"));

        //Không nên lưu thành 1 bieenss - tương ta trực tiếp luôn
        driver.findElement(By.id("email.com")).click();

        //Nên lưu thành biến - tung tác sử dụng lại nhiều lần (nếu dùng ít nh 2 lần)
        WebElement txtEmailTextbox = driver.findElement(By.id("email"));
        txtEmailTextbox.clear();
        txtEmailTextbox.sendKeys();
        txtEmailTextbox.click();

        List<WebElement> links = driver.findElements(By.xpath("//a"));

        // Trả về URL của page hiện tại
        String loginPageUrl = driver.getCurrentUrl();
        System.out.println("Login page URL = " + loginPageUrl);
        // Verify URL đúng như mong đợi/ chứa 1 chuỗi nào đó
        Assert.assertEquals(loginPageUrl, "https://www.facebook.com/");

        // Lấy source code của trang hiện tại (HTML/CSS/JS/JQuery...)
        // Dungf để verify tương đối 1 giá nào đó trong trang
        String loginPageSource = driver.getPageSource();
        Assert.assertTrue(loginPageSource.contains("facebook"));

        // Lấy title của page hiện tại
        String loginPageTitle = driver.getTitle();
        Assert.assertEquals(loginPageTitle, "Facebook - Đăng nhập hoặc đăng ký");

        // WebDriver API - Window/ Tab
        // Trả về 1 ID của tab/ window hiện tại (1)
        String loginPageWindowID = driver.getWindowHandle();

        // Trả về ID của tất cả các tab/ window đang có (n)
        Set<String> allTabID = driver.getWindowHandles();

       //Xử lý cookie (Framework)
        driver.manage().getCookies();

        // Lấy log của browser ra (Framework)
        driver.manage().logs();

        // Time của viêc findElement/ findElements (Framework)
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); //**

        // Time chờ page load thành công (Framework)
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));

        // Time chờ cho việc JavaScript Executor (Framework)
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(10));

        // Quản lý window/ tab (Framework)
        // User hay dùng
        driver.manage().window().maximize();

        // TEST GUI

        //Lấy ra vị trí của brower so voi độ phân gia của màn hình
        Point browserPosition = driver.manage().window().getPosition();

        //Set vị trí cua browser tại đểm 0x250
        driver.manage().window().setPosition(new Point(0,250));

        //Lấy ra kích thước(chiều rộng/ chiều cao) của browser
        Dimension browserSize = driver.manage().window().getSize();

        //Set kích thước browser
        //Test Responsive
        driver.manage().window().setSize(new Dimension(1920,1080));
        driver.manage().window().setSize(new Dimension(1366,768));

        //Back/ Forward/ Refresh
        driver.navigate().back();
        driver.navigate().forward();
        driver.navigate().refresh();
        driver.navigate().to("https://www.facebook.com/");

        //WebDriver API - Alert/ Frame/ Window
        driver.switchTo().alert(); //**
        driver.switchTo().frame(0); //**
        driver.switchTo().window(""); //**






        // Cookie/ Cache
        driver.manage().deleteAllCookies();
        // Timeout
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));



    }

    @Test
    public void TC_02() {
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
