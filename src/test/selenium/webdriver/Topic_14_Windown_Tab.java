package selenium.webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Set;

public class Topic_14_Windown_Tab {
    WebDriver driver;

    @BeforeClass
    public void beforeClass() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    }

    @Test
    public void TC_01_Automationfc_Tab_ID_I() {
        //Trang A
        driver.get("https://automationfc.github.io/basic-form/index.html");

        driver.findElement(By.xpath("//a[text()='GOOGLE']")).click();
        String homePageWindownID= driver.getWindowHandle();
        System.out.println("Automationfc HomePage ID = " + homePageWindownID);

        Set<String> allWindownIDs = driver.getWindowHandles();

        //Click vào GOOGLE (trang B)
        //Trong trường hợp chi có 2 tab/ windown thì có thể dùng ID của Tab/Windown này để switch qua lại
        System.out.println("All windown ID = " + allWindownIDs);
        for (String id : allWindownIDs) {
            //Nếu ID nào khác với ID của trang GOOGLE thì switch qua
            if (!id.equals(homePageWindownID)) {
                driver.switchTo().window(id);
            }
        }

        // Sau khi switch qua tab/Windown B (trang GOOGLE)
        //Verify title của trang GOOGLE
        System.out.println("Tab B: " + driver.getTitle());

        // Hiện tại đang đứng  tab/windown B rồi
        String googlePageWindownID = driver.getWindowHandle();

        allWindownIDs =driver.getWindowHandles();
        System.out.println("Set Size: " + allWindownIDs.size());
        for (String id : allWindownIDs) {
            //Nếu ID nào khác với ID của trang GOOGLE thì switch qua
            if (!id.equals(googlePageWindownID)) {
                //Switch lại về tab A
                driver.switchTo().window(id);
            }
        }

        //Sau khi switch qua tab A
        System.out.println("Tab A: " + driver.getTitle());

    }

    @Test
    public void TC_01_Automationfc_Tab_ID() {
        //Cách 2: Gọi hàm viết sẵn

        //Trang A
        driver.get("https://automationfc.github.io/basic-form/index.html");

        driver.findElement(By.xpath("//a[text()='GOOGLE']")).click();
        String homePageWindownID= driver.getWindowHandle();
        switchToWindowByID(homePageWindownID);

        //Trang B
        System.out.println("Tab B: " + driver.getTitle());
        String googlePageWindownID = driver.getWindowHandle();

        //Switch sang lại trang A
        switchToWindowByID(googlePageWindownID);

        //Trang A
        System.out.println("Tab A: " + driver.getTitle());
    }

    @Test
    public void TC_02_Automationfc_Tab_Title() {
        //Trang A
        driver.get("https://automationfc.github.io/basic-form/index.html");
        String titleParentPage =driver.getTitle();
        String homePageWindownID= driver.getWindowHandle();


        //Step2: Click GOOGLE
        driver.findElement(By.xpath("//a[text()='GOOGLE']")).click();

        switchToWindowByTitle("Google");

        // Step3: Verify title của trang GOOGLE
        Assert.assertEquals(driver.getTitle(),"Google");

        //Step4: Switch về trang automationfc
        switchToWindowByTitle(titleParentPage);

        //Step5: Click Fabebook
        driver.findElement(By.xpath("//a[text()='FACEBOOK']")).click();
        switchToWindowByTitle("Facebook – log in or sign up");

        //Step6: Verify title của trang FACEBOOK
        Assert.assertEquals(driver.getTitle(),"Facebook – log in or sign up");

        //Step7: Switch về trang automationfc
        switchToWindowByTitle(titleParentPage);
        Assert.assertEquals(driver.getTitle(),titleParentPage);

        //Step8: Click TIKI
        driver.findElement(By.xpath("//a[text()='TIKI']")).click();
        switchToWindowByTitle("Tiki - Mua hàng online giá tốt, hàng chuẩn, ship nhanh");

        //Step9: Verify title của trang TIKI
        Assert.assertEquals(driver.getTitle(),"Tiki - Mua hàng online giá tốt, hàng chuẩn, ship nhanh");

        //Step10: Close tất cả cửa sổ tab, ngoại trừa parent windown (trang autumation face)
        closeAllWindowWithoutParent(homePageWindownID);

        //Step 11: Kiểm tra quay về màn hình chính thành công
        Assert.assertEquals(driver.getTitle(),titleParentPage);
    }

    @Test
    public void TC_03_Dictionary_Window_Title() {
        //Step 1: Truy cập trang techpanda
        driver.get("https://dictionary.cambridge.org/vi/");
        String idHomePage = driver.getWindowHandle();

        //Step 2: Click vào  Đăng nhập link
        driver.findElement(By.cssSelector("span.cdo-login-button")).click();
        sleepInSecond(2);

        //Step 3: Switch window Đăng nhập
        switchToWindowByTitle("Login");
        System.out.println("Title: "+ driver.getTitle());

        //Step 4: Click Login button
        driver.findElement(By.xpath("//input[@value='Log in']")).click();

        //Step 5: Verify error message xuất hiện tại 2 file Email và Password

        Assert.assertEquals(driver.findElement(By.cssSelector("span#gigya-error-msg-gigya-login-form-loginID")).getText(),"This field is required");
        Assert.assertEquals(driver.findElement(By.cssSelector("span#gigya-error-msg-gigya-login-form-password")).getText(),"This field is required");

        //Step 6: Close cửa sổ Login và Switch về trang home trước đó
        closeAllWindowWithoutParent(idHomePage);

        //Step 7: Nhập từ khóa bất kỳ vào ô Search và click Search button
        driver.findElement(By.cssSelector("input#searchword")).sendKeys("automation");
        driver.findElement(By.cssSelector("button[type=submit][aria-label=Search]")).click();

        //Step 8: Verify kết quả hiển thị đúng với từ khóa đã nhập
        Assert.assertTrue(driver.findElement(By.xpath("//div[@data-id='cald4']//span[@class='hw dhw']")).getText().contains("automation"));
    }

    // CÓ TỂ VIẾT THÀNH 1 HÀM KHI NÀO MUỐN DÙNG THÌ GỌI HÀM ĐÓ RA
    // TH này chỉ dùng cho 2 tab/Window duy nhất
    public void switchToWindowByID(String currentWindowID){
        //Lấy ra tất cả ID đang có
        Set<String> allWindownIDs = driver.getWindowHandles();
        //Dùng vòng lặp để duyệt qua từng ID
        for (String id : allWindownIDs) {
            //Nếu ID nào khác với ID của trang hiện tại tại thì switch qua
            if (!id.equals(currentWindowID)) {
                driver.switchTo().window(id);
                break;
            }
        }
    }
    //Cách này dùng cho nhiều hơn 2 tab/windown
    public void switchToWindowByTitle(String expectedPageTitle){
        //Lấy ra tất cả ID đang có
        Set<String> allWindownIDs = driver.getWindowHandles();
        //Dùng vòng lặp để duyệt qua từng ID
        for (String id : allWindownIDs) {

            //Switch qua từng ID trước rồi getTitle
            driver.switchTo().window(id);

            //Get title của ID đó ra
            String actualPageTitle = driver.getTitle();

            //So sánh với title mong muốn
            if (actualPageTitle.equals(expectedPageTitle)) {
                break;
            }
        }
    }

    public void closeAllWindowWithoutParent(String parentWindowID){
        //Lấy ra tất cả ID đang có
        Set<String> allWindowIDs = driver.getWindowHandles();
        //Dùng vòng lặp để duyệt qua từng ID
        for (String id : allWindowIDs) {
            //Nếu ID nào khác với ID của trang hiện tại tại thì switch qua
            if (!id.equals(parentWindowID)) {
                driver.switchTo().window(id);
                driver.close();
            }
        }
        driver.switchTo().window(parentWindowID);
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
