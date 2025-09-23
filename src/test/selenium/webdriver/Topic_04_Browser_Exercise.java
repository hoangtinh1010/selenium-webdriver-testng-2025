package selenium.webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class Topic_04_Browser_Exercise {
    WebDriver driver;

    @BeforeClass
    public void beforeClass() {
        driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//        driver.manage().window().maximize();

    }

    @Test
    public void TC_01_Url() {
        driver.get("https://live.techpanda.org/");

        //Click vào My Account ở dưới footer
        driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();

        //Verify url của page hiện tại = https://live.techpanda.org/index.php/customer/account/login/
        String loginPageUrl = driver.getCurrentUrl();
        Assert.assertEquals(loginPageUrl, "https://live.techpanda.org/index.php/customer/account/login/");

        //Click vào Create an Account button
        driver.findElement(By.xpath("//a[@title='Create an Account']")).click();

        //Verify url của Register Page hiện tại = https://live.techpanda.org/index.php/customer/account/create/
        String registerPageUrl = driver.getCurrentUrl();
        Assert.assertEquals(registerPageUrl, "https://live.techpanda.org/index.php/customer/account/create/");

    }

    @Test
    public void TC_02_Title() {
        driver.get("https://live.techpanda.org/");

        //Click vào My Account ở dưới footer
        driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();

        //Verify title của page hiện tại = Customer Login
        String loginPageTitle = driver.getTitle();
        Assert.assertEquals(loginPageTitle, "Customer Login");

        //Click vào Create an Account button
        driver.findElement(By.xpath("//a[@title='Create an Account']")).click();

        //Verify title của Register Page hiện tại = Create New Customer Account
        String registerPageTitle = driver.getTitle();
        Assert.assertEquals(registerPageTitle, "Create New Customer Account");
    }

    @Test
    public void TC_03_Navigation() {
        driver.get("https://live.techpanda.org/");

        //Click vào My Account ở dưới footer
        driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();

        //Click vào Create an Account button
        driver.findElement(By.xpath("//a[@title='Create an Account']")).click();

        //Back lại trang Login
        driver.navigate().back();

        //Verify url của page hiện tại = https://live.techpanda.org/index.php/customer/account/login/
        String loginPageUrl = driver.getCurrentUrl();
        Assert.assertEquals(loginPageUrl, "https://live.techpanda.org/index.php/customer/account/login/");

        //Forward tới trang Register
        driver.navigate().forward();
        //Verify title của Register Page hiện tại = https://live.techpanda.org/index.php/customer/account/create/
        String registerTitle = driver.getTitle();
        Assert.assertEquals(registerTitle, "Create New Customer Account");


    }

    @Test
    public void TC_04_Page_Source() {
        driver.get("https://live.techpanda.org/");

        //Click vào My Account ở dưới footer
        driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();

        //Verify page source của page hiện tại chứa text "Login or Create an Account"
        String loginPageSource = driver.getPageSource();
        Assert.assertTrue(loginPageSource.contains("Login or Create an Account"));

        //Click vào Create an Account button
        driver.findElement(By.xpath("//a[@title='Create an Account']")).click();

        //Verify page source của page hiện tại chứa text "Create an Account"
        String registerPageSource = driver.getPageSource();
        Assert.assertTrue(registerPageSource.contains("Create an Account"));

    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
