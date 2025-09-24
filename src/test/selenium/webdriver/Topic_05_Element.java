package selenium.webdriver;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.nio.channels.CancelledKeyException;
import java.time.Duration;

public class Topic_05_Element {
    WebDriver driver;

    @BeforeClass
    public void beforeClass() {
        driver = new ChromeDriver();
        driver.get("https://www.facebook.com/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    }

    @Test
    public void TC_01() {

        WebElement element = driver.findElement(By.id("email"));

        //xóa dữ liệu trong textbox, textarea, dropdown editable (cho phép edit n/ nập liê
        element.clear();

        //Nhập dữ liệu vào textbox, textarea, dropdown editable (cho phép edit/nhập lệu)
        element.sendKeys("");

        //Click vào button, link, checkbox, radio button, image, dropdown (không phải editable)
        element.click();

        //Lấy dữ liệu hiển thị trên element (text/ value/ attribute/ css/...)
        element.getText();

        // Lấy ra gi trị của attribute
        element.getAttribute("");

        element.getCssValue("background-color");

        element.getLocation();
        element.getRect();
        Dimension elementSize = element.getSize();

        //chụp hình của element rồi lưu lại dưới dạngj nào đó file/ base64/ bytes
        String bese64Image = element.getScreenshotAs(OutputType.BASE64);
        element.getScreenshotAs(OutputType.BYTES);
        element.getScreenshotAs(OutputType.FILE);

        element.getTagName();

        //Kiểm tra trạng thái của element (displayed/ enabled/ selected/...)
        //Người dùng có thể nhìn thấy được

        //Mong đợi nó hiển thị
        Assert.assertTrue(element.isDisplayed());
        element.isDisplayed();

        //Mong đợi nó không hiển thị
        Assert.assertFalse(element.isDisplayed());

        //Kiểm tra 1 element có thể thao tác được hay không
        element.isEnabled();

        // Mong đợi nó có thể thao tác được
        Assert.assertTrue(element.isEnabled());
        // Mong đợi nó không thể thao tác được (read only)
        Assert.assertFalse(element.isEnabled());

        //Kiểm tra 1 element đã được chọn hay chưa (checkbox/ radio/ dropdown editable)
        element.isSelected();

        //Mong đợi nó đ ược chọn rồi
        Assert.assertTrue(element.isSelected());
        //Mong đợi nó chưa được chọn
        Assert.assertFalse(element.isSelected());

        //Tương ứng với hành động nhấn phím ENTER trên bàn phím
        element.submit();


    }

    @Test
    public void TC_02() {
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
