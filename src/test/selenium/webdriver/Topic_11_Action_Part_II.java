package selenium.webdriver;

import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.awt.event.InputEvent;
import java.awt.AWTException;;
import java.awt.Robot;
import java.time.Duration;


import java.util.List;

public class Topic_11_Action_Part_II {
    WebDriver driver;
    Actions action;
    JavascriptExecutor jsExecutor;
    WebDriverWait explicitWait;

    @BeforeClass
    public void beforeClass() {

//        driver = new ChromeDriver();
        driver = new FirefoxDriver();
        jsExecutor = (JavascriptExecutor) driver;
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(10));

        //1 - Khởi tạo Action lên
        action = new Actions(driver);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

    }

    @Test
    public void TC_01_Right_Click() {
        //ContextMenu = Menu chuột phải
        driver.get("https://swisnl.github.io/jQuery-contextMenu/demo.html");
        action.contextClick(driver.findElement(By.cssSelector("span.context-menu-one"))).perform();
        sleepInSecond(3);

        //Verify Paste được hiển thị
        WebElement pasteElement = driver.findElement(By.cssSelector("li.context-menu-icon-paste"));
        Assert.assertTrue((pasteElement).isDisplayed());

        //Hover chuột vào element Paste
        action.moveToElement(pasteElement).perform();
        sleepInSecond(3);

        //Click vào Paste
        action.click(pasteElement).perform();
        sleepInSecond(3);

        //Accept alert and accept alert
        explicitWait.until(ExpectedConditions.alertIsPresent()).accept();



    }

    @Test
    public void TC_02_Double_Click() {
        driver.get("https://automationfc.github.io/basic-form/index.html");
        WebElement doubleClickButton = driver.findElement(By.xpath("//button[text()='Double click me']"));
        //Scroll xuống element trước
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);",doubleClickButton);

        //Double click
        action.doubleClick(doubleClickButton).perform();
        sleepInSecond(3);

        Assert.assertEquals(driver.findElement(By.id("demo")).getText(),"Hello Automation Guys!");
    }

    @Test
    public void TC_03_Drag_And_Drop_HTML4() {
        driver.get("https://automationfc.github.io/kendo-drag-drop/");
        WebElement sourceElement = driver.findElement(By.id("draggable"));
        WebElement targetElement = driver.findElement(By.id("droptarget"));
        action.dragAndDrop(sourceElement,targetElement).perform();
        sleepInSecond(3);

        //Verify text changed
        Assert.assertEquals(targetElement.getText(),"You did great!");
        //Verify background color changed
        String hexaColor = Color.fromString(targetElement.getCssValue("background-color")).asHex().toLowerCase() ;
        Assert.assertEquals(hexaColor,"#03a9f4");

        System.out.println(targetElement.getCssValue("background-color"));
        System.out.println(Color.fromString(targetElement.getCssValue("background-color")).asHex());
        System.out.println(hexaColor);
    }

    @Test
    public void TC_04_Drag_And_Drop_HTML5() throws AWTException {
        driver.get("https://automationfc.github.io/drag-drop-html5/");

       //Drag A to B
        dragAndDropHTML5ByXpath("//div[@id='column-a']","//div[@id='column-b']");
        sleepInSecond(3);

        //verify
        Assert.assertEquals(driver.findElement(By.xpath("//div[@id='column-a']/header")).getText(),"B");
        Assert.assertEquals(driver.findElement(By.xpath("//div[@id='column-b']/header")).getText(),"A");
        sleepInSecond(2);

        //Drag B to A
        dragAndDropHTML5ByXpath("//div[@id='column-a']","//div[@id='column-b']");
        sleepInSecond(2);
        //verify
        Assert.assertEquals(driver.findElement(By.xpath("//div[@id='column-a']/header")).getText(),"A");
        Assert.assertEquals(driver.findElement(By.xpath("//div[@id='column-b']/header")).getText(),"B");


    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }

    public void dragAndDropHTML5ByXpath(String sourceLocator, String targetLocator) throws AWTException {

        WebElement source = driver.findElement(By.xpath(sourceLocator));
        WebElement target = driver.findElement(By.xpath(targetLocator));

        // Setup robot
        Robot robot = new Robot();
        robot.setAutoDelay(500);

        // Get size of elements
        Dimension sourceSize = source.getSize();
        Dimension targetSize = target.getSize();

        // Get center distance
        int xCentreSource = sourceSize.width / 2;
        int yCentreSource = sourceSize.height / 2;
        int xCentreTarget = targetSize.width / 2;
        int yCentreTarget = targetSize.height / 2;

        Point sourceLocation = source.getLocation();
        Point targetLocation = target.getLocation();

        // Make Mouse coordinate center of element
//        sourceLocation.x += 20 + xCentreSource;
//        sourceLocation.y += 110 + yCentreSource;
//        targetLocation.x += 20 + xCentreTarget;
//        targetLocation.y += 110 + yCentreTarget;

        // Move mouse to drag from location
        robot.mouseMove(sourceLocation.x, sourceLocation.y);

        // Click and drag
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseMove(((sourceLocation.x - targetLocation.x) / 2) + targetLocation.x, ((sourceLocation.y - targetLocation.y) / 2) + targetLocation.y);

        // Move to final position
        robot.mouseMove(targetLocation.x, targetLocation.y);

        // Drop
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    public void sleepInSecond(long timeInSecond){
        try {
            Thread.sleep(timeInSecond * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
