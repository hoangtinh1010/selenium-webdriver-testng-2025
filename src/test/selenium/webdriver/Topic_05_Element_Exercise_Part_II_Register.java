package selenium.webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.time.Duration;

public class Topic_05_Element_Exercise_Part_II_Register {
    WebDriver driver;


    By txtFullName = By.id("txtFirstname");
    By txtEmail = By.id("txtEmail");
    By txtCfEmail = By.id("txtCEmail");
    By txtPassword = By.id("txtPassword");
    By txtCfPassword = By.id("txtCPassword");
    By txtPhoneNumber = By.id("txtPhone");
    By registerButton = By.xpath("//button[@type='submit' and text()='ĐĂNG KÝ']");
    By checkBox = By.xpath("//input[@id='chkRight']");
    By btnRegister = By.xpath("//button[@type='submit' and text()='ĐĂNG KÝ']");

    //Message error
    By errorFullName = By.id("txtFirstname-error");
    By errorEmail = By.id("txtEmail-error");
    By errorCfEmail = By.id("txtCEmail-error");
    By errorPassword = By.id("txtPassword-error");
    By errorCfPassword = By.id("txtCPassword-error");
    By errorPhoneNumber = By.id("txtPhone-error");


    @BeforeClass
    public void beforeClass() {

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    }

    @BeforeMethod
    public void beforeMethod() {
        driver.get("https://alada.vn/tai-khoan/dang-ky.html");
    }

    @Test
    public void TC01_register_with_empty_data() {
        driver.navigate().refresh();
        // Steps to perform the test case
        driver.findElement(registerButton).click();
        // Add assertions to verify error messages for each required field
        assert driver.findElement(errorFullName).getText().equals("Vui lòng nhập họ tên");
        assert driver.findElement(errorEmail).getText().equals("Vui lòng nhập email");
        assert driver.findElement(errorCfEmail).getText().equals("Vui lòng nhập lại địa chỉ email");
        assert driver.findElement(errorPassword).getText().equals("Vui lòng nhập mật khẩu");
        assert driver.findElement(errorCfPassword).getText().equals("Vui lòng nhập lại mật khẩu");
        assert driver.findElement(errorPhoneNumber).getText().equals("Vui lòng nhập số điện thoại.");

    }

    @Test
    public void TC02_register_with_invalid_email() {
        driver.navigate().refresh();

        // Steps to perform the test case
        driver.findElement(txtEmail).sendKeys("invalid-email");
        driver.findElement(txtCfEmail).sendKeys("invalid-email");
        driver.findElement(txtFullName).sendKeys("Test User");
        driver.findElement(txtPassword).sendKeys("Password123");
        driver.findElement(txtCfPassword).sendKeys("Password123");
        driver.findElement(txtPhoneNumber).sendKeys("0123456789");
        //
        driver.findElement(registerButton).click();

        // Add assertions to verify error messages for each required field
        assert driver.findElement(errorEmail).getText().equals("Vui lòng nhập email hợp lệ");
        assert driver.findElement(errorCfEmail).getText().equals("Email nhập lại không đúng");
    }
        @Test
    public void TC03_register_with_incorrect_confirm_emall() {
            driver.navigate().refresh();
        // Steps to perform the test case
            driver.findElement(txtFullName).sendKeys("Test User");
            driver.findElement(txtEmail).sendKeys("email@email.com");
            driver.findElement(txtCfEmail).sendKeys("email123@email.com");
            driver.findElement(txtPassword).sendKeys("Password123");
            driver.findElement(txtCfPassword).sendKeys("Password123");
            driver.findElement(txtPhoneNumber).sendKeys("0123456789");
            //
            driver.findElement(registerButton).click();
            // Add assertions to verify error messages for each required field
            assert driver.findElement(errorCfEmail).getText().equals("Email nhập lại không đúng");

        }

        @Test
    public void TC04_register_with_invalid_password() {

            driver.navigate().refresh();

            // Steps to perform the test case
            driver.findElement(txtFullName).sendKeys("Test User");
            driver.findElement(txtEmail).sendKeys("email@email.com");
            driver.findElement(txtCfEmail).sendKeys("email@email.com");
//            driver.findElement(txtPassword).clear();
            driver.findElement(txtPassword).sendKeys("1234");
            driver.findElement(txtCfPassword).sendKeys("1234");
            driver.findElement(txtPhoneNumber).sendKeys("0123456789");
            //
            driver.findElement(registerButton).click();
            // Add assertions to verify error messages for each required field
            assert driver.findElement(errorPassword).getText().equals("Mật khẩu phải có ít nhất 6 ký tự");
        }

        @Test
    public void TC05_register_with_incorrect_confirm_password() {
            driver.navigate().refresh();

            driver.findElement(txtFullName).sendKeys("Test User");
            driver.findElement(txtEmail).sendKeys("email@email.com");
            driver.findElement(txtCfEmail).sendKeys("email@email.com");
            driver.findElement(txtPassword).sendKeys("123456");
            driver.findElement(txtCfPassword).sendKeys("654321");
            driver.findElement(txtPhoneNumber).sendKeys("0123456789");
            //
            driver.findElement(registerButton).click();
            // Add assertions to verify error messages for each required field
            assert driver.findElement(errorCfPassword).getText().equals("Mật khẩu bạn nhập không khớp");

        }

    @Test
    public void TC06_register_with_invalid_phone_number() {
        driver.navigate().refresh();

        driver.findElement(txtFullName).sendKeys("Test User");
        driver.findElement(txtEmail).sendKeys("email@email.com");
        driver.findElement(txtCfEmail).sendKeys("email@email.com");
        driver.findElement(txtPassword).sendKeys("123456");
        driver.findElement(txtCfPassword).sendKeys("123456");
        driver.findElement(txtPhoneNumber).sendKeys("03456789");
        //
        driver.findElement(registerButton).click();
        // Add assertions to verify error messages for each required field
        assert driver.findElement(errorPhoneNumber).getText().equals("Số điện thoại phải từ 10-11 số.");

        driver.findElement(txtPhoneNumber).clear();
        driver.findElement(txtPhoneNumber).sendKeys("4567899999");
        driver.findElement(registerButton).click();
        assert driver.findElement(errorPhoneNumber).getText().equals("Số điện thoại bắt đầu bằng: 09 - 03 - 012 - 016 - 018 - 019 - 088 - 03 - 05 - 07 - 08");

    }



    @AfterTest
    public void teardown() {
        driver.quit();
    }

}
