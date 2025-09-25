package selenium.webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.idealized.Javascript;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Random;

public class Topic_08_Textbox_TextArea {
    WebDriver driver;
    JavascriptExecutor jsExecutor;
    String loginPageUrl,homepagePageUrl,userID, userPassword,customerID;
    String customerName, gender, dateOfBirth, addressInput,addressOutput, city, state, pin, phoneNumber, email, password;

    String editAddressInput,editAddressOutput, editCity, editState, editPin, editPhoneNumber, editEmail;

    @BeforeClass
    public void beforeClass() {
        driver = new ChromeDriver();

        //Ép kiểu tường minh
        jsExecutor = (JavascriptExecutor) driver;

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        loginPageUrl ="https://demo.guru99.com/V1/index.php";
        homepagePageUrl="https://demo.guru99.com/V1/html/Managerhomepage.php";

        customerName="Json";
        gender = "male";
        dateOfBirth = "1950-01-01";
        addressInput = "123 London Road\nLondon";
        addressOutput ="123 London Road London";
        city = "NewYork";
        state = "California";
        pin = "234566";
        phoneNumber = "0123456789";
        email = "json" + getRandomNumber() + "@mail.net";
        password = "123456";

        editAddressInput = "123 Cali 2\nLondon";
        editAddressOutput ="123 Cali 2 London";
        editCity = "NewYork";
        editState = "California";
        editPin = "123456";
        editPhoneNumber = "0123456000";
        editEmail = "json edit" + getRandomNumber() + "@mail.net";


        driver.get(loginPageUrl);

    }

    @Test
    public void TC_01_Register() {
        driver.findElement(By.xpath("//a[text()='here']")).click();


        driver.findElement(By.xpath("//input[@name='emailid']")).sendKeys("brian" + getRandomNumber() + "@mail.net");
        driver.findElement(By.xpath("//input[@name='btnLogin']")).click();

        userID = driver.findElement(By.xpath("//td[text()='User ID :']/following-sibling::td")).getText();
        userPassword = driver.findElement(By.xpath("//td[text()='Password :']/following-sibling::td")).getText();


    }

    @Test
    public void TC_02_Login() {

        driver.get(loginPageUrl);

        driver.findElement(By.xpath("//input[@name='uid']")).sendKeys(userID);
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys(userPassword);

        driver.findElement(By.xpath("//input[@name='btnLogin']")).click();

        //Verify login success

        Assert.assertEquals(driver.findElement(By.cssSelector("marquee.heading3")).getText(), "Welcome To Manager's Page of GTPL Bank");

    }

    @Test
    public void TC_03_Create_Customer() {
        driver.findElement(By.xpath("//a[text()='New Customer']")).click();

        driver.findElement(By.name("name")).sendKeys(customerName);


        WebElement dobTextbox = driver.findElement(By.name("dob"));
        jsExecutor.executeScript("arguments[0].removeAttribute('type')", dobTextbox);
        sleepInSecond(3);
        dobTextbox.sendKeys(dateOfBirth);

        driver.findElement(By.name("addr")).sendKeys(addressInput);
        driver.findElement(By.name("city")).sendKeys(city);
        driver.findElement(By.name("state")).sendKeys(state);
        driver.findElement(By.name("pinno")).sendKeys(pin);
        driver.findElement(By.name("telephoneno")).sendKeys(phoneNumber);
        driver.findElement(By.name("emailid")).sendKeys(email);

        driver.findElement(By.name("sub")).click();

        //Verify customer created successfully
        Assert.assertEquals(driver.findElement(By.cssSelector("p.heading3")).getText(), "Customer Registered Successfully!!!");
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Customer Name']/following-sibling::td")).getText(), customerName);
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Gender']/following-sibling::td")).getText(), gender);
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Birthdate']/following-sibling::td")).getText(), dateOfBirth);
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td")).getText(), addressOutput);
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='City']/following-sibling::td")).getText(), city);
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='State']/following-sibling::td")).getText(), state);
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Pin']/following-sibling::td")).getText(), pin);
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Mobile No.']/following-sibling::td")).getText(), phoneNumber);
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Email']/following-sibling::td")).getText(), email);


        //Lấy Customer ID để phục vụ cho việc edit sau này
        String customerID = driver.findElement(By.xpath("//td[text()='Customer ID']/following-sibling::td")).getText();

    }

    @Test
    public void TC_04_Edit_Customer() {
        driver.get(homepagePageUrl);

        driver.findElement(By.xpath("//a[text()='Edit Customer']")).click();

        driver.findElement(By.name("cusid")).sendKeys(customerID);
        driver.findElement(By.name("AccSubmit")).click();

        //Verify thông tin hiển thị đúng với thông tin đã nhập ở bước tạo mới
        Assert.assertEquals(driver.findElement(By.name("name")).getAttribute("value"), customerName);
        Assert.assertEquals(driver.findElement(By.name("gender")).getAttribute("value"), gender);
        Assert.assertEquals(driver.findElement(By.name("dob")).getAttribute("value"), dateOfBirth);
        Assert.assertEquals(driver.findElement(By.name("addr")).getAttribute("value"), addressInput);
        Assert.assertEquals(driver.findElement(By.name("city")).getAttribute("value"), city);
        Assert.assertEquals(driver.findElement(By.name("pinno")).getAttribute("value"), pin);
        Assert.assertEquals(driver.findElement(By.name("telephonno")).getAttribute("value"), phoneNumber);
        Assert.assertEquals(driver.findElement(By.name("email")).getAttribute("value"), email);

        //Edit thông tin
        driver.findElement(By.name("addr")).clear();
        driver.findElement(By.name("addr")).sendKeys(editAddressInput);
        driver.findElement(By.name("city")).clear();
        driver.findElement(By.name("city")).sendKeys(editCity);
        driver.findElement(By.name("state")).clear();
        driver.findElement(By.name("state")).sendKeys(editState);
        driver.findElement(By.name("pinno")).clear();
        driver.findElement(By.name("pinno")).sendKeys(editPin);
        driver.findElement(By.name("telephonno")).clear();
        driver.findElement(By.name("telephonno")).sendKeys(editPhoneNumber);
        driver.findElement(By.name("email")).clear();
        driver.findElement(By.name("email")).sendKeys(editEmail);
        driver.findElement(By.name("sub")).click();

        //Verify edit thành công
        Assert.assertEquals(driver.findElement(By.cssSelector("p.heading3")).getText(), "Customer details updated Successfully!!!");
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Customer Name']/following-sibling::td")).getText(), customerName);
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Gender']/following-sibling::td")).getText(), gender);
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Birthdate']/following-sibling::td")).getText(), dateOfBirth);

        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td")).getText(), editAddressOutput);
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='City']/following-sibling::td")).getText(), editCity);
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='State']/following-sibling::td")).getText(), editState);
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Pin']/following-sibling::td")).getText(), editPin);
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Mobile No.']/following-sibling::td")).getText(), editPhoneNumber);
        Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Email']/following-sibling::td")).getText(), editEmail);


    }

    @Test
    public void TC_05() {

    }

    @AfterClass
    public void afterClass() {
//        driver.quit();
    }

    public void sleepInSecond(long timeInSecond){
        try {
            Thread.sleep(timeInSecond * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public int getRandomNumber(){
        Random rand = new Random();
        return rand.nextInt(9999);

    }
}
