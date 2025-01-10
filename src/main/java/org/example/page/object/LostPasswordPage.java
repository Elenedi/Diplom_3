package org.example.page.object;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import io.github.bonigarcia.wdm.WebDriverManager;

public class LostPasswordPage {
    private WebDriver webDriver;
    private final By authLink = By.xpath(".//a[starts-with(@class,'Auth_link')]");
    private final By modalOverlay = By.xpath(".//div[starts-with(@class, 'App_App')]/div[starts-with(@class, 'Modal_modal')]");

    public LostPasswordPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }
    public void clickAuthLink() {
        waitWhenButtonIsClickable();
        webDriver.findElement(authLink).click();
    }
    private void waitWhenButtonIsClickable() {
        new WebDriverWait(webDriver, Duration.ofSeconds(20))
                .until(ExpectedConditions.invisibilityOf(webDriver.findElement(modalOverlay)));
    }
}

