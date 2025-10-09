package selenium.webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.time.Duration;
import java.util.List;

public class Topic_16_Upload_File {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String photo1FileName="photo1.jpg";
    String photo2FileName="photo2.jpg";
    String photo3FileName="photo3.jpg";
    String photo4FileName="photo4.jpg";

    //Support cho các máy Windows (do trong window, / bị chuyển thành \\
    String photo1FilePath= projectPath + File.separator + "uploadFiles"+ File.separator+ photo1FileName;
    String photo2FilePath = projectPath + File.separator + "uploadFiles"+ File.separator + photo2FileName;
    String photo3FilePath = projectPath + File.separator + "uploadFiles"+ File.separator + photo3FileName;
    String photo4FilePath = projectPath + File.separator + "uploadFiles"+ File.separator + photo4FileName;

    @BeforeClass
    public void beforeClass() {
        driver = new ChromeDriver();
//        driver = new EdgeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void TC_00_Knowledge() {
        //4 cách upload file
        //1 - Selenium Sendkey method (Trick)
        // Truyền vào thẻ inpu
        // t type = file
        // Không cần phải bật dialog lên
        // Dùng được hầu hết các trình duyệt, các OS
        // Chaỵ được cho run UI và Non UI

        //2 - AutoIT (Chỉ chạy được trên Windows OS, không chạy được trên MacOS/Linux)
           //Các browser cần viết script riêng
        //3 - Java Robot (Chạy được taats cả OS, nhưng không chạy được trên MAC/Linux)
        //4 - Sikuli (Dùng hình ảnh để thao tác)
    }
    @Test
    public void TC_01_One_File_One_Time() {
        driver.get("https://blueimp.github.io/jQuery-File-Upload/");
        //Upload file by sendkey methods
        //Note: Khong click vào button upload
        // Không được sendkey vào các thẻ khác ngoài thẻ input
        By uploadFile = By.cssSelector("input[type='file']");

        //Load file
        driver.findElement(uploadFile).sendKeys(photo1FilePath);
        driver.findElement(uploadFile).sendKeys(photo2FilePath);
        driver.findElement(uploadFile).sendKeys(photo3FilePath);
        driver.findElement(uploadFile).sendKeys(photo4FilePath);

        //Verify files are loaded successfully
        assert driver.findElement(By.xpath("//p[@class='name' and text()='"+photo1FileName+"']")).isDisplayed();
        assert driver.findElement(By.xpath("//p[@class='name' and text()='"+photo2FileName+"']")).isDisplayed();
        assert driver.findElement(By.xpath("//p[@class='name' and text()='"+photo3FileName+"']")).isDisplayed();
        assert driver.findElement(By.xpath("//p[@class='name' and text()='"+photo4FileName+"']")).isDisplayed();

        //Click start button to upload
        List<WebElement> uploadButtons = driver.findElements(By.cssSelector("table button.start"));
        for (WebElement button : uploadButtons) {
            button.click();
            sleepInSecond(2);
        }

        //Verify files are uploaded successfully
        assert driver.findElement(By.xpath("//p[@class='name']/a[@title='"+ photo1FileName+ "']")).isDisplayed();
        assert driver.findElement(By.xpath("//p[@class='name']/a[@title='"+ photo2FileName+ "']")).isDisplayed();
        assert driver.findElement(By.xpath("//p[@class='name']/a[@title='"+ photo3FileName+ "']")).isDisplayed();
        assert driver.findElement(By.xpath("//p[@class='name']/a[@title='"+ photo4FileName+ "']")).isDisplayed();

    }
    @Test
    public void TC_02_Multiple_File_One_Time() {
        driver.get("https://blueimp.github.io/jQuery-File-Upload/");
        //Upload file by sendkey methods
        //Note: Khong click vào button upload
        // Không được sendkey vào các thẻ khác ngoài thẻ input
        By uploadFile = By.cssSelector("input[type='file']");

        //Load 3 files
        driver.findElement(uploadFile).sendKeys(photo1FilePath + "\n" + photo2FilePath + "\n" + photo3FilePath + "\n" + photo4FilePath);
        sleepInSecond(2);

        //Verify files are loaded successfully
        assert driver.findElement(By.xpath("//p[@class='name' and text()='"+photo1FileName+"']")).isDisplayed();
        assert driver.findElement(By.xpath("//p[@class='name' and text()='"+photo2FileName+"']")).isDisplayed();
        assert driver.findElement(By.xpath("//p[@class='name' and text()='"+photo3FileName+"']")).isDisplayed();
        assert driver.findElement(By.xpath("//p[@class='name' and text()='"+photo4FileName+"']")).isDisplayed();

        //Click start button to upload
        List<WebElement> uploadButtons = driver.findElements(By.cssSelector("table button.start"));
        for (WebElement button : uploadButtons) {
            button.click();
            sleepInSecond(2);
        }

        //Verify files are uploaded successfully
        assert driver.findElement(By.xpath("//p[@class='name']/a[@title='"+ photo1FileName+ "']")).isDisplayed();
        assert driver.findElement(By.xpath("//p[@class='name']/a[@title='"+ photo2FileName+ "']")).isDisplayed();
        assert driver.findElement(By.xpath("//p[@class='name']/a[@title='"+ photo3FileName+ "']")).isDisplayed();
        assert driver.findElement(By.xpath("//p[@class='name']/a[@title='"+ photo4FileName+ "']")).isDisplayed();


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
