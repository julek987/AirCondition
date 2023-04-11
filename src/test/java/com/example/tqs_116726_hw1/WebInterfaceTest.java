package com.example.tqs_116726_hw1;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WebInterfaceTest {

    private static final String CHROMEDRIVER_PATH = System.getProperty("webdriver.chrome.driver");

    private WebDriver driver;

    @BeforeClass
    public static void setupClass() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
    }

    @Before
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:8080/");
    }

    @Test
    public void testLocationSelection() {
        WebElement locationField = waitUntilPresent(By.id("location"));
        locationField.sendKeys("Porto");
        WebElement submitButton = waitUntilClickable(By.id("submit"));
        submitButton.click();

        WebElement locationName = waitUntilPresent(By.id("locationName"));
        assertEquals("Porto", locationName.getText());

    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private WebElement waitUntilPresent(By locator) {
        Duration duration = Duration.ofSeconds(10);
        return new WebDriverWait(driver, duration).until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    private WebElement waitUntilClickable(By locator) {
        Duration duration = Duration.ofSeconds(10);
        return new WebDriverWait(driver, duration).until(ExpectedConditions.elementToBeClickable(locator));
    }
}
