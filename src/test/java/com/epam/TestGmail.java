package com.epam;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class TestGmail {

    public WebDriver driver;
    public String LOGIN_IDENTIFIER="//*[@id='identifierId']";
    public String LOGIN_IDENTIFIER_SUBMIT="//div[@id='identifierNext']";
    public String LOGIN_PSW="input[type='password']";
    public String LOGIN_PSW_SUBMIT="//div[@id='passwordNext']";
    public  String WRITE_BUTTON = "//div[@class='z0']//div[@role='button']";
    public String USER_TO_SEND_EMAIL = "textarea[id=':pz']";
    public  String THEME_EAMIL = "input[id=':ph']";
    public  String TEXT_EMAIL = "div[id=':qm']";
    public  String SEND_EMAIL = "//div[@id=':p7' and @role='button']";
    public String ALERT_SEND_EMAIL = "//span[@class='bAq']";
    @BeforeTest
    public void setBaseURL()
    {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver  =new ChromeDriver();
        driver.get(" https://www.gmail.com");
    }


    @Test
    public  void verifyGmailLoginTest() {
         driver.findElement(By.xpath(LOGIN_IDENTIFIER)).sendKeys("testng.userdrive@gmail.com");
         driver.findElement(By.xpath(LOGIN_IDENTIFIER_SUBMIT)).click();
         (new WebDriverWait(driver,30)).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='password']")));
         driver.findElement(By.cssSelector(LOGIN_PSW)).sendKeys("1111test");
         driver.findElement(By.xpath(LOGIN_PSW_SUBMIT)).click();
        String expectedTitle = "Gmail";
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, expectedTitle);
    }

    @Test(	dependsOnMethods = "verifyGmailLoginTest")
    public  void verifySendEmailTest( ) {
         WebElement element=null;
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS) ;
        driver.findElement(By.xpath(WRITE_BUTTON)).click();
        driver.findElement(By.cssSelector(USER_TO_SEND_EMAIL)).sendKeys("natali.salo101@gmail.com");
        driver.findElement(By.cssSelector(THEME_EAMIL)).sendKeys("some text");
        driver.findElement(By.cssSelector(TEXT_EMAIL)).sendKeys("Hi");
        element = driver.findElement(By.xpath(SEND_EMAIL));
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("arguments[0].click();", element);
        String textExpected = "Надсилання…";
        String textActual = (new WebDriverWait(driver, 10)).until(msg->msg.findElement(By.xpath(ALERT_SEND_EMAIL)).getText());
        Assert.assertEquals( textExpected, textActual );
    }

    @AfterTest
    public void endSession(){
        driver.quit();

    }
}
