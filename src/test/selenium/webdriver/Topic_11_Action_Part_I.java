package selenium.webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class Topic_11_Action_Part_I {
    WebDriver driver;
    Actions action;

    @BeforeClass
    public void beforeClass() {

        driver = new ChromeDriver();

        //1 - Khởi tạo Action lên
        action = new Actions(driver);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    }

    @Test
    public void TC_01_Hover() {
        driver.get("https://automationfc.github.io/jquery-tooltip/");

        WebElement ageTextbox = driver.findElement(By.id("age"));

        //Hover to Age Textbox
        action.moveToElement(ageTextbox).perform();
        sleepInSecond(3);

        Assert.assertEquals(driver.findElement(By.cssSelector("div.ui-tooltip-content")).getText(),"We ask for your age only for statistical purposes.");


    }

    @Test
    public void TC_02_Hover() {
        driver.get("https://www.myntra.com/");

        //2 - Gọi hm cần dùng ra
        //3 - Gọi cái perform() cuối cùng

        action.moveToElement(driver.findElement(By.xpath("//header//a[text()='Kids']"))).perform();
        sleepInSecond(3);

        //Click vào Home & Bath
        action.click(driver.findElement(By.xpath("//header//a[text()='Home & Bath']"))).perform();
        sleepInSecond(3);

//        Assert.assertEquals(driver.getCurrentUrl(),"https://www.myntra.com/kids-home-bath");
        Assert.assertEquals(driver.findElement(By.cssSelector("span.breadcrumbs-crumb")).getText(),"Kids Home Bath");
    }

    @Test
    public void TC_03_Click_And_Hold() {
        driver.get("https://automationfc.github.io/jquery-selectable/");

        //Lấy hết tất cả các item
        //Chọn 1-4
        //Click and hold chuột vào element đầu tiên
        //Move to element cuối cùng
        //Release chuột ra

        //Lấy hết tất cả các item
        List<WebElement> allItems = driver.findElements(By.cssSelector("ol#selectable>li"));

        action.clickAndHold(allItems.get(0)).moveToElement(allItems.get(3)).release().perform();
        sleepInSecond(3);

        //Verify 4 item được chọn
        List<WebElement> itemSelected = driver.findElements(By.cssSelector("ol#selectable>li.ui-selected"));
        Assert.assertEquals(itemSelected.size(),4);

    }

    @Test
    public void TC_04_Click_And_Hold_Random() {
        driver.get("https://automationfc.github.io/jquery-selectable/");

        //Lấy hết tất cả các item
        List<WebElement> allItems = driver.findElements(By.cssSelector("ol#selectable>li"));

        //Chọn 1 5 và 11
        //Nhấn phím Ctrl xuống (Chưa nha ra)
        //click vào 1
        //click vào 5
        //click vo 11
        //Thực thi các câu lệnh
        //Nhả phím Ctrl  ra
        action.keyDown(Keys.CONTROL).perform();
        action.click(allItems.get(0)).click(allItems.get(4)).click(allItems.get(10)).perform();
        action.keyUp(Keys.CONTROL).perform();

        List<WebElement> itemSelected = driver.findElements(By.cssSelector("ol#selectable>li.ui-selected"));
        Assert.assertEquals(itemSelected.size(),3);


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
