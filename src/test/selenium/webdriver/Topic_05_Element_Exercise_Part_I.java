package selenium.webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class Topic_05_Element_Exercise_Part_I {
    WebDriver driver;

    @BeforeClass
    public void beforeClass() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    }

    @Test
    public void TC_01() {
        driver.get("https://live.techpanda.org/index.php/customer/account/login/");

        String searchPlaceholder =driver.findElement(By.id("search")).getAttribute("placeholder");
        System.out.println(searchPlaceholder);

        String instructionText = driver.findElement(By.cssSelector("p.form-instructions")).getText();
        System.out.println(instructionText);

    }

    @Test
    public void TC_02() {
        driver.get("https://www.facebook.com/");
        WebElement emailTextbox = driver.findElement(By.id("email"));

        String bgColorTextbox = emailTextbox.getCssValue("background-color");
        String fontSizeTextbox = emailTextbox.getCssValue("font-size");
        System.out.println(bgColorTextbox);
        System.out.println(fontSizeTextbox);

    }

    @Test
    public void TC_03() {
        driver.get("https://www.facebook.com/");
        WebElement loginButton = driver.findElement(By.name("login"));
        String loginButtonTagName = loginButton.getTagName();
        //others step..

        //Lấy ra của step này sẽ là đầu bào của step kia
        loginButton = driver.findElement(By.xpath("//" + loginButtonTagName + "[@name='login']"));
        //Nối chuỗi

    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
