package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class FirstProjectAmazon {
    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver", "chromedriver");

        WebDriver driver = new ChromeDriver();

        //Launch Amazon.in
        driver.get("https://www.amazon.in/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(7000, TimeUnit.MILLISECONDS);
        //Getting Parent Window Handler
        String ParentWin = driver.getWindowHandle();
        //Search for Samsung
        WebElement SearchInput = driver.findElement(By.id("twotabsearchtextbox"));
        SearchInput.sendKeys("Samsung");

        //Click Search Button
        WebElement SearchButton = driver.findElement(By.id("nav-search-submit-button"));
        SearchButton.click();

        //Print Product Details and Price
        //div[@data-component-type='s-search-result']//h2  or   //div[@class='a-section']//span[starts-with(text(),'Samsung ')]

        List<WebElement> ProductDesc = driver.findElements(By.xpath("//div[@data-component-type='s-search-result']//h2/a"));
        System.out.println("Total num of links are " + ProductDesc.size());
        List<WebElement> ProductPrice = driver.findElements(By.xpath("//div[@data-component-type='s-search-result']//span[@class='a-price']"));

        for(int index=0;index<ProductDesc.size();index++) {

            System.out.print(ProductDesc.get(index).getText() + "Price : Rupees ");
            System.out.println(ProductPrice.get(index).getText());
        }

        // Click on First Product Link
        ProductDesc.get(0).click();
        String toValidate = ProductDesc.get(0).getText();
        System.out.println(toValidate);

        //Validation on parent and child windows

        Set<String> allWinHan = driver.getWindowHandles();
        System.out.println("Before Clicking Tab button the win is " + ParentWin);

        for(String win:allWinHan){
            if(!win.equals(ParentWin)){
                driver.switchTo().window(win);
            }
        }
        WebElement HeadingOnNewTab = driver.findElement(By.xpath("//div[@id='title_feature_div']//span"));
        String headerString = HeadingOnNewTab.getText();
        System.out.println(headerString);

        if(headerString.equals(toValidate)){
            System.out.println("TC Passed");
        }else {
            System.out.println("TC Failed");

        }
    }
}