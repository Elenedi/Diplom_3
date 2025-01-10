package org.example.page.object;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import io.github.bonigarcia.wdm.WebDriverManager;

public class RegisterPage {
    private final WebDriver webDriver;
    private final By inputsField = By.xpath(".//form[starts-with(@class, 'Auth_form')]//fieldset//div[@class='input__container']//input");
    private final By registerButton = By.xpath(".//form[starts-with(@class, 'Auth_form')]/button");
    private final By errorMessage = By.xpath(".//form[starts-with(@class, 'Auth_form')]//fieldset//div[@class='input__container']//p[starts-with(@class,'input__error')]");
    private final By title = By.xpath(".//main//h2");
    private final By authLink = By.xpath(".//a[starts-with(@class,'Auth_link')]");
    private final By modalOverlay = By.xpath(".//div[starts-with(@class, 'App_App')]/div[starts-with(@class, 'Modal_modal')]");

    public RegisterPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }
    @Step("Ввод данных в поле 'Имя'")
    public void setName(String name) {
        webDriver.findElements(inputsField).get(0).sendKeys(name);
    }
    @Step("Ввод данных в поле 'Email'")
    public void setEmail(String email) {
        webDriver.findElements(inputsField).get(1).sendKeys(email);
    }
    @Step("Ввод данных в поле 'Пароль'")
    public void setPassword(String password) {
        webDriver.findElements(inputsField).get(2).sendKeys(password);
    }

    @Step("Нажатие кнопки регистрации")
    public void clickRegisterButton() {
        waitWhenButtonIsClickable();
        webDriver.findElement(registerButton).click();
    }

    private void waitWhenButtonIsClickable() {
        new WebDriverWait(webDriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.invisibilityOf(webDriver.findElement(modalOverlay)));
    }
    public void waitFormIsSubmitted(String expectedTitle) {
        new WebDriverWait(webDriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.textToBe(title, expectedTitle));
    }
    public void waitTillErrorIsVisible() {
        new WebDriverWait(webDriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(webDriver.findElement(errorMessage)));
    }
    public String getErrorMessage() {
        return webDriver.findElement(errorMessage).getText();
    }
    public void clickAuthLink() {
        waitWhenButtonIsClickable();
        webDriver.findElement(authLink).click();
    }

}

