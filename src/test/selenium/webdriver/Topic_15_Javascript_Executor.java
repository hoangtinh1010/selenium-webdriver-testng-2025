package selenium.webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class Topic_15_Javascript_Executor {
    WebDriver driver;
    JavascriptExecutor jsExecutor;

    @BeforeClass
    public void beforeClass() {
        driver = new ChromeDriver();
        jsExecutor = (JavascriptExecutor) driver;
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    }

    @Test
    public void TC_Demo() {
        driver.get("https://live.techpanda.org/");

        //Click (WebElement)
//        driver.findElement(By.xpath("//a[@title='My Account']")).click();

        //Click (JavaScript)
        //Không quan tâm đến trạng thái của element (bị ẩn hoặc không hiển thị hoăc bị element khác che)
        // (VD: sub-menu,link,image,...),
        // chỉ quan tâm đến locator có trong DOM
        jsExecutor.executeScript("arguments[0].click();", driver.findElement(By.xpath("//a[@title='My Account']")));
        sleepInSecond(3);

        //CÁC HÀM JS HAY SỬ DỤNG : click() vào element , scroll tới element, highlight element ,
        // remove attribute, get HTML5 validation message, broken image (kiểm tra ảnh bị vỡ),
        WebElement element = driver.findElement(By.xpath("......"));
        //Scroll tới element
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
        //Highlight element (thêm viền đỏ vào element)
        jsExecutor.executeScript("arguments[0].style.border='3px solid red'", element);
        //Click vào element
        jsExecutor.executeScript("arguments[0].click();", element);
        //Remove attribute (bỏ thuộc tính readonly của element)
        jsExecutor.executeScript("arguments[0].removeAttribute('readonly')", element);

        //Get  URL page
        String homePageUrl = (String) jsExecutor.executeScript("return document.URL;");
        //Get title of page
        String homePageTitle = (String) jsExecutor.executeScript("return document.title;");
        //Get domain of page
        String homePageDomain = (String) jsExecutor.executeScript("return document.domain;");
        System.out.println("URL = " + homePageUrl);
        System.out.println("Title = " + homePageTitle);
        System.out.println("Domain = " + homePageDomain);
    }

    @Test
    public void TC_01_Techpanda() {
        navigateToUrlByJS("https://live.techpanda.org/");
        sleepInSecond(3);

        //Verify domain
        String homePageDomain = (String) executeForBrowser("return document.domain;");
        Assert.assertEquals(homePageDomain, "live.techpanda.org");
        //Verify URL
        String homePageUrl = (String) executeForBrowser("return document.URL;");
        Assert.assertEquals(homePageUrl, "https://live.techpanda.org/");

        //Verify title
        String homePageTitle =(String) executeForBrowser("return document.title;");
        Assert.assertEquals(homePageTitle, "Home page");

        //Step 02: Click vào MOBILE tab
        clickToElementByJS("//a[text()='Mobile']");
        sleepInSecond(3);

        //Step 03: Click Add to Cart trên sản phẩm SAMSUNG GALAXY
        clickToElementByJS("//a[@title='Samsung Galaxy']/parent::h2/following-sibling::div[@class='actions']/button");
        sleepInSecond(3);

        //Step 04: Verify message được hiển thị "Samsung Galaxy was added to your shopping cart." , sử dựng JS để getInner
        String shoppingCartText = getInnerText();
        Assert.assertTrue(shoppingCartText.contains("Samsung Galaxy was added to your shopping cart."));

        //Step 05: Open Customer Service Page
        clickToElementByJS("//a[text()='Customer Service']");
        sleepInSecond(3);

        //Step 06: Verify title Customer Service Page
        String customerServiceTitle = (String) executeForBrowser("return document.title;");
        Assert.assertEquals(customerServiceTitle, "Customer Service");

        //Step 07: Scroll to Newsletter textbox
        scrollToElementOnDown("//input[@id='newsletter']");
        sleepInSecond(3);

        //Step 08: Input email vào Newsletter textbox
        String emailAddress = "afc" + generateRandomNumber() + "@mail.vn";
        sendkeyToElementByJS("//input[@id='newsletter']", emailAddress);
        sleepInSecond(3);

        //Step 09: Click Subscribe button
        clickToElementByJS("//button[@title='Subscribe']");
        sleepInSecond(3);

        //Send  anyway
        clickToElementByJS("//button[@id='proceed-button']");
        sleepInSecond(3);


        //Step 10: Verify message "Thank you for your subscription." is displayed.
        Assert.assertTrue(isExpectedTextInInnerText("Thank you for your subscription."));

        //Step 11: Navigate to URL "https://www.facebook.com/"
        navigateToUrlByJS("https://www.facebook.com");
        sleepInSecond(3);
        //Step 12: Verify domain
        String facebookDomain = (String) executeForBrowser("return document.domain;");
        Assert.assertEquals(facebookDomain, "www.facebook.com");

    }

    @Test
    public void TC_02_HTML5_Validate_message() {
        driver.get("https://automationfc.github.io/html5/index.html");
        By submitButton = By.xpath("//input[@type='submit']");
        String textboxName= "//input[@id='fname']";
        String textboxPassword = "//input[@id='pass']";
        String texboxEmail = "//input[@id='em']";
       String texboxAddress ="//li/select";

        //Step 02: Click Submit button, verify message hiển thị tại field Name
        driver.findElement(submitButton).click();
        sleepInSecond(3);
        Assert.assertEquals(getElementValidationMessage(textboxName), "Please fill out this field.");

        //Step 03: Input "Afc" vào field Name và submit, verify message hiển thị tại field Password
        driver.findElement(By.xpath(textboxName)).sendKeys("Afc");
        driver.findElement(submitButton).click();
        Assert.assertEquals(getElementValidationMessage(textboxPassword), "Please fill out this field.");

        //Step 04: Input "123456" vào field Password và submit, verify message hiển thị tại field Email
        driver.findElement(By.xpath(textboxPassword)).sendKeys("123456");
        driver.findElement(submitButton).click();
        Assert.assertEquals(getElementValidationMessage(texboxEmail), "Please fill out this field.");

        //Step 05: Input invalid email (Afc@123#53) vào field Email và submit, verify message hiển thị tại field Email
        driver.findElement(By.xpath(texboxEmail)).sendKeys("Afc123#53");
        driver.findElement(submitButton).click();
        Assert.assertEquals(getElementValidationMessage(texboxEmail), "Please include an '@' in the email address. 'Afc123#53' is missing an '@'.");

        //Step 06: input valid email vào field Email,  và submit, verify message hiển thị tại field Address
        driver.findElement(By.xpath(texboxEmail)).clear();
        driver.findElement(By.xpath(texboxEmail)).sendKeys("Afc" + generateRandomNumber() + "@mail.vn");
        driver.findElement(submitButton).click();
        Assert.assertEquals(getElementValidationMessage(texboxAddress), "Please select an item in the list.");





    }


    //Các hàm js chung  dùng cho tất cả các project
    public Object executeForBrowser(String javaScript) {
        return jsExecutor.executeScript(javaScript);
    }

    public String getInnerText() {
        return (String) jsExecutor.executeScript("return document.documentElement.innerText;");
    }

    public boolean isExpectedTextInInnerText(String textExpected) {
        String textActual = (String) jsExecutor.executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0];");
        return textActual.equals(textExpected);
    }

    public void scrollToBottomPage() {
        jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
    }


    public void navigateToUrlByJS(String url) {
        jsExecutor.executeScript("window.location = '" + url + "'");
        sleepInSecond(3);
    }

    public void highlightElement(String locator) {
        WebElement element = getElement(locator);
        String originalStyle = element.getAttribute("style");
        jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", element, "border: 2px solid red; border-style: dashed;");
        sleepInSecond(2);
        jsExecutor.executeScript("arguments[0].setAttribute('style', arguments[1])", element, originalStyle);
    }

    public void clickToElementByJS(String locator) {
        jsExecutor.executeScript("arguments[0].click();", getElement(locator));
        sleepInSecond(3);
    }

    public String getElementTextByJS(String locator) {
        return (String) jsExecutor.executeScript("return arguments[0].textContent;", getElement(locator));
    }

    public void scrollToElementOnTop(String locator) {
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getElement(locator));
    }

    public void scrollToElementOnDown(String locator) {
        jsExecutor.executeScript("arguments[0].scrollIntoView(false);", getElement(locator));
    }

    public void setAttributeInDOM(String locator, String attributeName, String attributeValue) {
        jsExecutor.executeScript("arguments[0].setAttribute('" + attributeName + "', '" + attributeValue +"');", getElement(locator));
    }

    public void removeAttributeInDOM(String locator, String attributeRemove) {
        jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", getElement(locator));
    }

    public void sendkeyToElementByJS(String locator, String value) {
        jsExecutor.executeScript("arguments[0].setAttribute('value', '" + value + "')", getElement(locator));
    }

    public String getAttributeInDOM(String locator, String attributeName) {
        return (String) jsExecutor.executeScript("return arguments[0].getAttribute('" + attributeName + "');", getElement(locator));
    }

    public String getElementValidationMessage(String locator) {
        return (String) jsExecutor.executeScript("return arguments[0].validationMessage;", getElement(locator));
    }

    public boolean isImageLoaded(String locator) {
        boolean status = (boolean) jsExecutor.executeScript(
                "return arguments[0].complete && typeof arguments[0].naturalWidth != 'undefined' && arguments[0].naturalWidth > 0", getElement(locator));
        return status;
    }

    public WebElement getElement(String locator) {
        return driver.findElement(By.xpath(locator));
    }

    @AfterClass
    public void afterClass() {
//        driver.quit();
    }

    public int generateRandomNumber() {
        return (int) (Math.random() * 9999);
    }


    public void sleepInSecond(long timeInSecond){
        try {
            Thread.sleep(timeInSecond * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
