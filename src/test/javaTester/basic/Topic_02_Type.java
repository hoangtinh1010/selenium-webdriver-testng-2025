package javaTester.basic;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;

public class Topic_02_Type {
    //Kiểu dữ liệu nguyên thủy - Primitive (6 kiểu)
    //Số nguyên: int, long, short, byte
    //Số thực: float, double
    //Ký tự: char
    //Logic: boolean

    //Kiểu dữ liệu tham chiếu - Reference (2 kiểu)
    //Chuỗi: String
    //Tập hợp: Array, Class, Interface

    //Kiểu dữ liệu nguyên thủy - Primitive (6 kiểu)
    byte bNumber = 8; //1 byte
    short sNumber = 16; //2 bytes
    int iNumber = 32; //4 bytes
    long lNumber = 64; //8 bytes

    float fNumber = 12.5f; //4 bytes
    double dNumber = 15.99d; //8 bytes

    char cCharacter = 'A'; //2 bytes
    boolean bStatus = true; //1 bit

    //Kiểu dữ liệu tham chiếu - Reference (2 kiểu)
    String address = "Ho Chi Minh";

    //Khai báo Object và khởi tạo giá trị
    Object student = new Object();

    //Khai báo mảng và khởi tạo giá trị
    int[] studentNumbers = {1, 2, 3, 4, 5};//Khai báo mảng

    //Khai báo List và khởi tao giá trị
    List<String> studentNames = List.of("John", "Jane", "Jack", "Jill", "James");

    WebDriver driver;
    WebElement element;

    @Test
    public void defaultValue() {
        System.out.println(bNumber);
        System.out.println(sNumber);
        System.out.println(iNumber);
        System.out.println(lNumber);
        System.out.println(fNumber);
        System.out.println(dNumber);
        System.out.println(cCharacter);
        System.out.println(bStatus);
        System.out.println(address);
        System.out.println(student);
        System.out.println(studentNumbers);
        System.out.println(studentNames);
        System.out.println(driver);
        System.out.println(element);



}}
