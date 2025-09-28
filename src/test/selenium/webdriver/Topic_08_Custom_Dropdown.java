package selenium.webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class Topic_08_Custom_Dropdown {
    WebDriver driver;
    WebDriverWait explicitWait;
    JavascriptExecutor jsExecutor;

    @BeforeClass
    public void beforeClass() {
        driver = new ChromeDriver();

        //wait cho các trạng thái của element: visible, presence, invisible, staleness
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(15));

        //Ép kiểu tường minh
        jsExecutor = (JavascriptExecutor) driver;

        //Wait cho viêc tìm element (findElement/ findElements)
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

    }

    @Test
    public void TC_01_Jquery() {
        driver.get("https://jqueryui.com/resources/demos/selectmenu/default.html");
        selectItemCustomDropdownList("span#number-button>span.ui-selectmenu-icon","ul#number-menu div","5");
        Assert.assertEquals(driver.findElement(By.cssSelector("span#number-button>span.ui-selectmenu-text")).getText(),"5");

        selectItemCustomDropdownList("span#number-button>span.ui-selectmenu-icon","ul#number-menu div","15");
        Assert.assertEquals(driver.findElement(By.cssSelector("span#number-button>span.ui-selectmenu-text")).getText(),"15");

        selectItemCustomDropdownList("span#number-button>span.ui-selectmenu-icon","ul#number-menu div","19");
        Assert.assertEquals(driver.findElement(By.cssSelector("span#number-button>span.ui-selectmenu-text")).getText(),"19");

//
//
    }

    @Test
    public void TC_02_React() {
        driver.get("https://react.semantic-ui.com/maximize/dropdown-example-selection/");
        selectItemCustomDropdownList("i.dropdown","div.item>span.text","Jenny Hess");
        Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(),"Jenny Hess");

        selectItemCustomDropdownList("i.dropdown","div.item>span.text","Elliot Fu");
        Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(),"Elliot Fu");

        selectItemCustomDropdownList("i.dropdown","div.item>span.text","Stevie Feliciano");
        Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(),"Stevie Feliciano");
    }

    @Test
    public void TC_03_VueJS() {
        driver.get("https://mikerodham.github.io/vue-dropdowns/");
        selectItemCustomDropdownList("span.caret","ul.dropdown-menu a","First Option");
        Assert.assertEquals(driver.findElement(By.cssSelector("li.dropdown-toggle")).getText(),"First Option");

        selectItemCustomDropdownList("span.caret","ul.dropdown-menu a","Second Option");
        Assert.assertEquals(driver.findElement(By.cssSelector("li.dropdown-toggle")).getText(),"Second Option");

        selectItemCustomDropdownList("span.caret","ul.dropdown-menu a","Third Option");
        Assert.assertEquals(driver.findElement(By.cssSelector("li.dropdown-toggle")).getText(),"Third Option");
    }

    @Test
    public void TC_04_EnterReact(){
        driver.get("https://react.semantic-ui.com/maximize/dropdown-example-search-selection/");

        enterToCustomDropdownList("input.search","div.item>span.text","Algeria");
        Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(),"Algeria");

        enterToCustomDropdownList("input.search","div.item>span.text","Andorra");
        Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(),"Andorra");

        enterToCustomDropdownList("input.search","div.item>span.text","Angola");
        Assert.assertEquals(driver.findElement(By.cssSelector("div.divider.text")).getText(),"Angola");
    }

    public void selectItemCustomDropdownList(String parentLocator, String childLocator, String expectedTextItem) {

//        Step1: Click vào dropdown cho nó xổ hết ra các item
        driver.findElement(By.cssSelector(parentLocator)).click();
        sleepInSecond(2);

//        Step2: Chờ tất cả các item được load ra (
//        Lưu y: locator chua heets tất cả item
//        Lưu ý 2: Locator phải đi tơí item cuối cùng
        explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(childLocator)));
//
//        Step3: Tìm item mong muốn
//        + Nếu item cần chọn hiển thị thì không cần scroll
//        + Nếu item cần chọn không hiển thị thì cần scroll đến item đó

        //Lấy tất cả các element (item) ra sau đó duyệt qua từng item
        List<WebElement> allItems = driver.findElements(By.cssSelector(childLocator));

        //Duyệt qua từng item getText() của item ra
        for (WebElement item : allItems) {
            String actualText = item.getText();
            System.out.println("Actual text: " + actualText);

            //So sánh với item mong muốn, nếu actualText = item mong muốn -> click
            if (actualText.equals(expectedTextItem)) {

                jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
                sleepInSecond(2);

//         Step4: Click vào item mong muốn
                item.click();
                sleepInSecond(2);
                //Thoát khỏi vòng lặp không có kiểm tra element tiếp theo nữa
                break;
            }

        }

    }

    public void enterToCustomDropdownList(String parentLocator, String childLocator, String expectedTextItem) {

//        Step1: Phải lấy đến thẻ input (textbox) để sendkey vào
        driver.findElement(By.cssSelector(parentLocator)).sendKeys(expectedTextItem);
        sleepInSecond(2);

//        Step2: Chờ tất cả các item được load ra (
//        Lưu y: locator chua heets tất cả item
//        Lưu ý 2: Locator phải đi tơí item cuối cùng
        explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(childLocator)));
//
//        Step3: Tìm item mong muốn
//        + Nếu item cần chọn hiển thị thì không cần scroll
//        + Nếu item cần chọn không hiển thị thì cần scroll đến item đó

        //Lấy tất cả các element (item) ra sau đó duyệt qua từng item
        List<WebElement> allItems = driver.findElements(By.cssSelector(childLocator));

        //Duyệt qua từng item getText() của item ra
        for (WebElement item : allItems) {
            String actualText = item.getText();
            System.out.println("Actual text: " + actualText);

            //So sánh với item mong muốn, nếu actualText = item mong muốn -> click
            if (actualText.equals(expectedTextItem)) {

          // Scroll đến item đó
                jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
                sleepInSecond(2);

//         Step4: Click vào item mong muốn
                item.click();
                sleepInSecond(2);
                //Thoát khỏi vòng lặp không có kiểm tra element tiếp theo nữa
                break;
            }

        }

    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }

    public void sleepInSecond(long timeInSecond) {
        try {
            Thread.sleep(timeInSecond * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
