package org.example.page.object;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import io.github.bonigarcia.wdm.WebDriverManager;

public class AuthorisationPage {
    private WebDriver webDriver;
    private final By inputsField = By.xpath(".//form[starts-with(@class, 'Auth_form')]//fieldset//div[@class='input__container']//input");
    private final By authButton = By.xpath(".//form[starts-with(@class, 'Auth_form')]/button");
    private final By title = By.xpath(".//main//h2");
    private final By modalOverlay = By.xpath(".//div[starts-with(@class, 'App_App')]/div/div[starts-with(@class, 'Modal_modal_overlay')]");
    private final By header = By.tagName("h1");

    public AuthorisationPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @Step("Авторизация")
    public void authUser(String email, String password) {
        setEmail(email);
        setPassword(password);
        clickAuthButton();
        waitFormIsSubmitted();
    }

    @Step("Ввод данных в поле 'Email'")
    public void setEmail(String email) {
        webDriver.findElements(inputsField).get(0).sendKeys(email);
    }
    @Step("Ввод данных в поле 'Пароль'")
    public void setPassword(String password) {
        webDriver.findElements(inputsField).get(1).sendKeys(password);
    }

    @Step("Нажатие кнопки авторизации")
    public void clickAuthButton() {
        waitWhenButtonIsClickable();
        webDriver.findElement(authButton).click();
    }
    @Step("Ожидание пока кнопка не станет кликабельной")
    private void waitWhenButtonIsClickable() {
        new WebDriverWait(webDriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.invisibilityOf(webDriver.findElement(modalOverlay)));
    }
    @Step("Ожидание отправки формы авторизации")
    public void waitFormIsSubmitted() {
        new WebDriverWait(webDriver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(header));
    }
    @Step("Ожидание пока форма авторизации не станет видимой")
    public void waitAuthFormVisible() {
        new WebDriverWait(webDriver, Duration.ofSeconds(30))
                .until(ExpectedConditions.textToBe(title, "Вход"));
    }
}

