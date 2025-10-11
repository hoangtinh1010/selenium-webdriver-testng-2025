package selenium.webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class Topic_17_Wait_Part_II_FindElement {
    WebDriver driver;

    @BeforeClass
    public void initialBrowser() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://live.techpanda.org/index.php/customer/account/login/");

    }

    @Test
    public void TC_01_FindElement() {

        //Tìm thấy 1 element
        //Vào tìm thấy element ngay, không cần chờ hết timeout của implicit
        // driver.findElement(By.cssSelector("input#email"));

        //Tìm thấy 0 element
        //Vào sẽ không tìm thấy element -> sẽ tìm lại mỗi 0,5s 1 lần cho đến khi hết timeout của implicit là 10s=20 lần
        //Đánh fail testcase tại vị trí này và show ra l NoSuchElementException

        // Trong trường hợp không tìm thấy và vẫn tìm lại và thấy element -> sẽ pass bước này chạy tiếp các bước bên dưới
        // driver.findElement(By.cssSelector("input#emailAddress"));

        //Tìm thấy nhiều hơn 1 element
        //Nó sẽ luôn thao tác với element đầu tiên trong list
        driver.findElement(By.cssSelector("a[title='My Account']")).click();
    }

    @Test
    public void TC_02_FindElements() {
        //Tìm thấy 1 element
        // Tra về 1 element + không cần đợi hết timeout của implicit
        List<WebElement> elements = driver.findElements(By.cssSelector("input#email"));
        System.out.println("Number of element found = " + elements.size());

        //Tìm thấy 0 element
        // Vào sẽ không thấy element v sẽ tìm lại mỗi 0,5s 1 lần cho đến khi hết timeout của implicit là 10s=20 lần
        //Không đánh fail testcase mà trả v 1 list rỗng (empty) = 0 và chạy tiếp các bước bên dưới
        List <WebElement> elementList = driver.findElements(By.cssSelector("input#emailAddress"));
        System.out.println("Number of element found = " + elementList.size());

        //Tìm thấy nhiều hơn 1 element
        //Lấy hết tất cả và lưu vào List
        List<WebElement> elementList2 = driver.findElements(By.cssSelector("input:not([type='hidden'])"));
        System.out.println("Number of element found = " + elementList2.size());

        // Thao tác với element trong list
//        elementList2.get(1).sendKeys("Automation Testing");
        for (WebElement element : elementList2) {
            element.sendKeys("Automation Testing");
        }

    }

    @Test
    public void TC_03() {
    }

    @AfterClass
    public void clearBrowser() {
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
