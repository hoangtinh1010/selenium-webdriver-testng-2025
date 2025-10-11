package selenium.webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class Topic_17_Wait_Part_I_Element_Status {
    WebDriver driver;
    WebDriverWait explicitWait;

    @BeforeClass
    public void initialBrowser() {
        driver = new ChromeDriver();

        explicitWait =new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    }

    @Test
    public void TC_00_Knowledge() {
        // Có 3 loại wait trong Selenium
        // 1 - Implicit Wait (Timeouts)
        // 2 - Explicit Wait (WebDriverWait)
        // 3 - Fluent Wait (FluentWait)
        // Chia làm 2 loại: Static wait (Sleep) và Dynamic wait (3 loại trên)
        //wait của Java: Static wait (Thread.sleep)

        //có 3 loại trạng thái element
        //1 - Visible/ Displayed: Element hiển thị trên UI (hiển thị/ HTML có)
        //2 - Invisible/ Undisplayed: Element không hiển thị trên UI (HTML có/ không hiển thị)
        //3 - Presence: Element có trong DOM (DOM có/ không hiển thị)
        //4 - Staleness: Element tại thời điểm A có trong HTML (present), thời điểm B thì không (không còn trong HTML)
    }
    @Test
    public void TC_01_Visible() {
        driver.get("https://tiki.vn/");
        sleepInSecond(5);
        //Điều kiện 1: Element hiển thị trên UI có trong cây HTML (hiển thị/ HTML/DOM có)
        // Element hiển thị trên UI thì 100% có trong HTML -> Element đó gọi  là Hiển thị (Visible/ Displayed)
        //Visible means that the element is not only displayed on the page but also has a height and width greater than 0.

        //Check popup và Close popup
        if (driver.findElements(By.cssSelector("div#VIP_BUNDLE")).size() > 0 ) {
            //Close popup
            driver.findElement(By.cssSelector("img[alt='close-icon']")).click();
        };

        //Click vào Đăng nhập/ Đăng ký
        driver.findElement(By.cssSelector("div[data-view-id=header_header_account_container]")).click();
        //Wait cho Element hiển thị (visible/ displayed)ư
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='tel']")));

    }
    @Test
    public void TC_02_Invisible_HTML() throws InterruptedException {
        driver.get("https://live.techpanda.org/");
        //Điều kiện 2: Element không hiển thị trên UI nhưng có trong cây HTML (không hiển thị/ HTML/DOM có)
        //Element không hiển thị trên UI nhưng có trong HTML thì gọi là không hiển thị (Invisible/ Undisplayed) => Không có trên UI

        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div#header-account a[title='My Account']")));

    }

    @Test
    public void TC_02_Invisible_Not_HTML() throws InterruptedException {
        driver.get("https://tiki.vn/");
        sleepInSecond(5);
        //Điều kiện 1: Element hiển thị trên UI có trong cây HTML (hiển thị/ HTML/DOM có)
        // Element hiển thị trên UI thì 100% có trong HTML -> Element đó gọi  là Hiển thị (Visible/ Displayed)
        //Visible means that the element is not only displayed on the page but also has a height and width greater than 0.

        //Check popup và Close popup
        if (driver.findElements(By.cssSelector("div#VIP_BUNDLE")).size() > 0 ) {
            //Close popup
            driver.findElement(By.cssSelector("img[alt='close-icon']")).click();
        };

        //Click vào Đăng nhập/ Đăng ký
        driver.findElement(By.cssSelector("div[data-view-id=header_header_account_container]")).click();

        driver.findElement(By.cssSelector("p.login-with-email")).click();

        //Wait cho Element không hiển thị (Invisible/ Undisplayed)
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("input[name='tel']")));
        // Vì sao nó chạy lâu
        // Phải đi tìm element trước: findElement => không có trong HTML => Tìm lâu và tìm lại cho ến khi hết timeout
    }
    @Test
    public void TC_03_Present() throws InterruptedException {
        driver.get("https://live.techpanda.org/");
        sleepInSecond(3);
        //Present: Nếu Element thỏa mãn 1 trong 2 điều kiện sau  thì được gọi là có trong DOM (Present)

        //Điều kiện 1: Element hiển thị trên UI có trong cây HTML (UI hiển thị +  HTML/DOM có)
        explicitWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div#header-account a[title='My Account']")));

        //Điều kiện 2: Element không hiển thị trên UI nhưng có trong cây HTML (UI không hiển thị + HTML/DOM có)

        //Wait cho Element có trong DOM (Presence)
        explicitWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div#header-account a[title='My Account']")));

    }

    @Test
    public void TC_04_Staleness() throws InterruptedException {
        driver.get("https://tiki.vn/");
        sleepInSecond(5);

        //Staleness: Element tại thời điểm A có trong HTML (present), thời điểm B thì không (không còn trong HTML)
        // Điều kiện 3: Không Hiển thị và không có trong HTML
        // Điều kiện cần: Invisible + Not in HTML (not present)
        // Điều kiện đủ: Element tại thời điểm A có trong HTML (present), thời điểm B thì không (không còn trong HTML)
        //Check popup và Close popup
        if (driver.findElements(By.cssSelector("div#VIP_BUNDLE")).size() > 0 ) {
            //Close popup
            driver.findElement(By.cssSelector("img[alt='close-icon']")).click();
        };

        driver.findElement(By.cssSelector("div[data-view-id=header_header_account_container]")).click();
        //phone textbox hiển thị - Thời điểm A
        By phoneTextbox = By.cssSelector("input[name='tel']");

        driver.findElement(By.cssSelector("p.login-with-email")).click();

        // Tới đây phone textbox không còn hiển thị và không có trong HTML - Thời điểm B
        explicitWait.until(ExpectedConditions.stalenessOf(driver.findElement(phoneTextbox)));
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
