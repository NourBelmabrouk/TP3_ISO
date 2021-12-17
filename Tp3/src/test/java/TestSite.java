import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

class TestSite {
    private static ChromeDriver driver;

    @BeforeAll
    public  static  void setup(){
        WebDriverManager.chromedriver().setup();
    }
    @BeforeEach
    public void  init(){
        driver =new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
        driver.manage().timeouts().scriptTimeout(Duration.ofMinutes(2));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
    }
    @AfterEach
    public void quitDriver() throws InterruptedException {
        driver.quit();
    }

    private void chooseTechnology(String technology) throws InterruptedException {
        //click on the technology link
        WebElement technologyLink= driver.findElement(By.linkText(technology));
        technologyLink.click();
        Thread.sleep(5000);
    }
    private void add(String line){
        WebElement newElement = driver.findElement(By.className("new-todo"));
        newElement.sendKeys(line);
        newElement.sendKeys(Keys.RETURN);
    }
    private void testAssert(String expectedNumberLeft){
        WebElement numberOfItemsLeft= driver.findElement(By.cssSelector("body > section > footer > span > strong"));
        ExpectedConditions.textToBePresentInElement(numberOfItemsLeft, expectedNumberLeft);
    }
    @Test
    void doTest()  throws InterruptedException{
        driver.get("https://todomvc.com/");
        Thread.sleep(5000);
        chooseTechnology("Backbone.js");
        add("Meet a Friend");
        add("Buy Meat");
        add("clean the car");
        WebElement removeditem= driver.findElement(By.cssSelector("body > section > section > ul > li:nth-child(1) > div > input"));
        removeditem.click();
        Thread.sleep(5000);
        testAssert("2");
    }

    @ParameterizedTest
    @ValueSource(strings = {"Backbone.js",
            "AngularJS",
            "Dojo",
            "React"})
    public void todosTestCase(String technology) throws InterruptedException {
        driver.get("https://todomvc.com/");
        Thread.sleep(5000);
        chooseTechnology(technology);
        add("Meet a Friend");
        add("Buy Meat");
        add("clean the car");
        WebElement removedItem = driver.findElement(By.cssSelector("li:nth-child(2) .toggle"));
        removedItem.click();
        Thread.sleep(5000);

        testAssert("2");

    }
}
