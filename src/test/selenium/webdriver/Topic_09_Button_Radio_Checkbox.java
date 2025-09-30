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
import java.util.List;

public class Topic_09_Button_Radio_Checkbox {
    WebDriver driver;
    JavascriptExecutor jsExecutor;

    @BeforeClass
    public void beforeClass() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        jsExecutor = (JavascriptExecutor) driver;

    }

    @Test
    public void TC_01_Button() {
        driver.get("https://www.fahasa.com/customer/account/create");

       driver.findElement(By.cssSelector("li.popup-login-tab-login")).click();

       By loginButtonBy = By.cssSelector("button.fhs-btn-login");

       //Verify button is disable
        Assert.assertFalse(driver.findElement(loginButtonBy).isEnabled());

         //Input email/ password
        driver.findElement(By.cssSelector("input#login_username")).sendKeys("fc_automation@mail.com");
        driver.findElement(By.cssSelector("input#login_password")).sendKeys("123456");

        //Verify button is enable
        Assert.assertTrue(driver.findElement(loginButtonBy).isEnabled());

        //Verify background-color button = Red
        //Get background-color
        String loginButtonBackgroundColor = driver.findElement(loginButtonBy).getCssValue("background-color");
        System.out.println("Login button background color: " + loginButtonBackgroundColor);

        //Verify =RGB
//        Assert.assertEquals(loginButtonBackgroundColor,"rgba(201, 33, 39)";

        //convert sang Hex
        String loginButtonBackgroundColorHex = org.openqa.selenium.support.Color.fromString(loginButtonBackgroundColor).asHex().toUpperCase();

        Assert.assertEquals(loginButtonBackgroundColorHex,"#C92127");

    }

    @Test
    public void TC_02_Default_Radio() {
        driver.get("https://demos.telerik.com/kendo-ui/radiobutton/index");

        By oneDotEightPetrolRadioBy = By.xpath("//label[text()='1.8 Petrol, 118kW']/preceding-sibling::span/input");
        By twoDotZeroDieselRadioBy = By.xpath("//label[text()='2.0 Diesel, 103kW']/preceding-sibling::span/input");
        By threeDotSixPetrolRadioBy = By.xpath("//label[text()='3.6 Petrol, 191kW']/preceding-sibling::span/input");

        //Verify radio button is unselected
        Assert.assertFalse(driver.findElement(oneDotEightPetrolRadioBy).isSelected());

        //Click to radio button
        driver.findElement(oneDotEightPetrolRadioBy).click();

        //Verify radio button is selected
        Assert.assertTrue(driver.findElement(oneDotEightPetrolRadioBy).isSelected());

        //Verify radio button  2.0 Diesel is not selected
        Assert.assertFalse(driver.findElement(twoDotZeroDieselRadioBy).isSelected());

        //Verify radio button 3.6 Petrol is disable = Read only
        Assert.assertFalse(driver.findElement(threeDotSixPetrolRadioBy).isEnabled());



    }


    @Test
    public void TC_03_Default_Checkbox() {
        driver.get("https://demos.telerik.com/kendo-ui/checkbox/index");

        By heatedCheckBoxBy = By.xpath("//label[text()='Heated front and rear seats']/preceding-sibling::span/input");
        By luggageCheckBoxBy = By.xpath("//label[text()='Luggage compartment cover']/preceding-sibling::span/input");
        By leatherCheckBoxBy = By.xpath("//label[text()='Leather trim']/preceding-sibling::span/input");
        By towbarCheckBoxBy = By.xpath("//label[text()='Towbar preparation']/preceding-sibling::span/input");

        //Select
        checkToCheckbox(heatedCheckBoxBy);
        checkToCheckbox(luggageCheckBoxBy);

        //Verify selected
        Assert.assertTrue(isElementSelected(heatedCheckBoxBy));
        Assert.assertTrue(isElementSelected(luggageCheckBoxBy));
        Assert.assertTrue(isElementSelected(leatherCheckBoxBy));

        //Disable
        Assert.assertFalse(isElementEnabled(leatherCheckBoxBy));
        Assert.assertFalse(isElementEnabled(towbarCheckBoxBy));

        //De-Select
        uncheckToCheckbox(heatedCheckBoxBy);
        uncheckToCheckbox(luggageCheckBoxBy);

        //Verify de-selected
        Assert.assertFalse(isElementSelected(heatedCheckBoxBy));
        Assert.assertFalse(isElementSelected(luggageCheckBoxBy));
        Assert.assertFalse(isElementSelected(towbarCheckBoxBy));
    }

    @Test
    public void TC_04_Default_Multi_Checkbox() {
        driver.get("https://automationfc.github.io/multiple-fields/");
        List<WebElement> allCheckboxes = driver.findElements(By.cssSelector("input.form-checkbox"));

        //Action: Select all
        for (WebElement checkbox : allCheckboxes) {
            if(!checkbox.isSelected())
            checkbox.click();
//            sleepInSecond(1);
        }
        //Verify: All selected
        for (WebElement checkbox : allCheckboxes) {
            Assert.assertTrue(checkbox.isSelected());
        }

        //Action: DeSelect all
        for (WebElement checkbox : allCheckboxes) {
            if(checkbox.isSelected())
                checkbox.click();
//            sleepInSecond(1);
        }
        //Verify: All de-selected
        for (WebElement checkbox : allCheckboxes) {
            Assert.assertFalse(checkbox.isSelected());
        }

    }

    @Test
    public void TC_05_Custom_Radio() {
        driver.get("https://login.ubuntu.com");

        // Case 3:
        //Dùng thẻ label - click()
        //dùng thẻ input -  verify isSelected()
        // 1 element phải dùng 2 locator
        //Dễ bị nhầm lẫn
        //Bảo trì khó
        By iDontRadioLableBy = By.cssSelector("label.new-user");
        By iDontRadioBy = By.cssSelector("input#id_new_user");

        driver.findElement(iDontRadioLableBy).click();
        Assert.assertTrue(driver.findElement(iDontRadioBy).isSelected());

        // Case 4:Dùng thẻ input
        //JavascriptExecutor - click() (không quan tâm đến ẩn hiện)
    }

    @Test
    public void TC05_Custom_Radio_2() {
        driver.get("https://login.ubuntu.com");

        By iDontRadioBy = By.cssSelector("input#id_new_user");
        clickByJavascript(iDontRadioBy);
        Assert.assertTrue(isElementSelected(iDontRadioBy));

    }

    @Test
    public void TC06_Custom_Checkbox() {
        driver.get("https://login.ubuntu.com");
        By staySignedCheckboxBy = By.cssSelector("input[type=checkbox]");

        //Checked
        if (!isElementSelected(staySignedCheckboxBy)){
            clickByJavascript(staySignedCheckboxBy);
        }
        Assert.assertTrue(isElementSelected(staySignedCheckboxBy));

        //UnChecked
        if (isElementSelected(staySignedCheckboxBy)){
            clickByJavascript(staySignedCheckboxBy);
        }
        Assert.assertFalse(isElementSelected(staySignedCheckboxBy));
    }

    @Test
    public void TC07_Custom_Radio_GoogleDocs_3(){
        driver.get("https://docs.google.com/forms/d/e/1FAIpQLSfiypnd69zhuDkjKgqvpID9kwO29UCzeCVrGGtbNPZXQok0jA/viewform");
        By conThoRadioBy = By.xpath("//div[@data-value='Cần Thơ']");
        clickByJavascript(conThoRadioBy);
        sleepInSecond(1);

        Assert.assertEquals(
                driver.findElement(conThoRadioBy).getAttribute("aria-checked"),
                "true"
        );
    }

    public void clickByJavascript(By by) {
        jsExecutor.executeScript("arguments[0].click();", driver.findElement(by));
    }


    public void checkToCheckbox(By by) {
        if (!driver.findElement(by).isSelected()){
            driver.findElement(by).click();
        }
    }

    public  void uncheckToCheckbox(By by) {
        if (driver.findElement(by).isSelected()){
            driver.findElement(by).click();
        }
    }

    public boolean isElementSelected(By by) {
        if (driver.findElement(by).isSelected()){
            return true;
        } else {
            return false;
        }
    }

    public boolean isElementEnabled(By by) {
        if (driver.findElement(by).isEnabled()){
            return true;
        } else {
            return false;
        }
    }

    public void sleepInSecond(long timeInSecond){
        try {
            Thread.sleep(timeInSecond * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
