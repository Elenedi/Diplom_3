package org.example.page.object;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import lombok.Getter;
import io.github.bonigarcia.wdm.WebDriverManager;

public class HomePage {
    private WebDriver webDriver;

    private final By constructorButton = By.xpath(".//p[starts-with(@class,'AppHeader_header__linkText') and text()='Конструктор']");
    private final By orderFeedButton = By.xpath(".//p[starts-with(@class,'AppHeader_header__linkText') and text()='Лента заказов']");
    private final By profileButton = By.xpath(".//p[starts-with(@class,'AppHeader_header__linkText') and text()='Личный Кабинет']");

    private final By bunsButton = By.xpath(".//section[starts-with(@class, 'BurgerIngredients_ingredients')]/div/div/span[text()='Булки']");
    private final By saucesButton = By.xpath(".//section[starts-with(@class, 'BurgerIngredients_ingredients')]/div/div/span[text()='Соусы']");
    private final By fillingsButton = By.xpath(".//section[starts-with(@class, 'BurgerIngredients_ingredients')]/div/div/span[text()='Начинки']");

    @Getter
    private final By bunsTypes = By.xpath(".//div[starts-with(@class, 'BurgerIngredients_ingredients__menuContainer')]//h2[text()='Булки']");
    @Getter
    private final By saucesTypes = By.xpath(".//div[starts-with(@class, 'BurgerIngredients_ingredients__menuContainer')]//h2[text()='Соусы']");
    @Getter
    private final By fillingsTypes = By.xpath(".//div[starts-with(@class, 'BurgerIngredients_ingredients__menuContainer')]//h2[text()='Начинки']");

    private final By authButton = By.xpath(".//div[starts-with(@class,'BurgerConstructor_basket__container')]/button");

    private final By header = By.xpath(".//main//h1[text()='Соберите бургер']");
    private final By modalOverlay = By.xpath(".//div[starts-with(@class, 'App_App')]/div/div[starts-with(@class, 'Modal_modal_overlay')]");


    public HomePage (WebDriver webDriver) {
        this.webDriver = webDriver;
    }
    @Step
    public void clickAuthButton() {
        waitWhenButtonIsClickable();
        webDriver.findElement(authButton).click();
    }

    @Step
    public boolean isBunsDisplayed() {
        return webDriver.findElement(getBunsTypes()).isDisplayed();
    }

    @Step
    public void waitWhenButtonIsClickable() {
        new WebDriverWait(webDriver, Duration.ofSeconds(30))
                .until(ExpectedConditions.invisibilityOf(webDriver.findElement(modalOverlay)));
    }

    @Step
    public void waitHeaderIsVisible() {
        new WebDriverWait(webDriver, Duration.ofSeconds(30))
                .until(ExpectedConditions.visibilityOfElementLocated(header));
    }
    @Step
    public void scrollAndWait(By elementLocator) {
        WebElement element = new WebDriverWait(webDriver, Duration.ofSeconds(30))
                .until(ExpectedConditions.visibilityOfElementLocated(elementLocator));

        new Actions(webDriver)
                .moveToElement(element)
                .perform();

        new WebDriverWait(webDriver, Duration.ofSeconds(20))
                .until(ExpectedConditions.visibilityOf(element));
    }

    @Step
    public void clickConstructorButton() {
        waitWhenButtonIsClickable();
        webDriver.findElement(constructorButton).click();
    }

    @Step
    public void clickOrderFeedButton() {
        waitWhenButtonIsClickable();
        webDriver.findElement(orderFeedButton).click();
    }

    @Step
    public void clickLinkToProfile() {
        waitWhenButtonIsClickable();
        webDriver.findElement(profileButton).click();
    }

    public String getAuthButtonText() {
        return webDriver.findElement(authButton).getText();
    }

    @Step
    public void clickBunsButton() {
        waitWhenButtonIsClickable();
        webDriver.findElement(bunsButton).click();
        scrollAndWait(bunsTypes);
    }

    @Step
    public void clickSaucesButton() {
        waitWhenButtonIsClickable();
        webDriver.findElement(saucesButton).click();
        scrollAndWait(saucesTypes);
    }

    @Step
    public void clickFillingsButton() {
        waitWhenButtonIsClickable();
        webDriver.findElement(fillingsButton).click();
        scrollAndWait(fillingsTypes);
    }
}
