import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import org.example.endpoints.UserOperators;
import org.example.references.Browsers;
import org.example.references.Constants;
import org.hamcrest.MatcherAssert;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import org.example.page.object.ProfilePage;
import org.example.page.object.AuthorisationPage;
import org.example.page.object.HomePage;

@DisplayName("Проверка профиля пользователя")
public class ProfilePageTest {
    private WebDriver webDriver;
    private AuthorisationPage authPage;
    private HomePage homePage;
    private ProfilePage profilePage;
    private String name;
    private String email;
    private String password;
    Faker faker = new Faker();

    @Before
    @Step("Запуск браузера")
    public void startUp() {
        String browserName = System.getProperty("browser", "chrome");
        webDriver = Browsers.createDriver(browserName);
        webDriver.get(Constants.HOME_PAGE_URL);

        authPage = new AuthorisationPage(webDriver);
        homePage = new HomePage(webDriver);
        profilePage = new ProfilePage(webDriver);

        name = faker.name().firstName();
        email = faker.internet().safeEmailAddress();
        password = faker.letterify("12345678");

        Allure.addAttachment("Имя", name);
        Allure.addAttachment("Email", email);
        Allure.addAttachment("Пароль", password);

        new UserOperators().createUser(name, email, password);
    }
    @After
    @Step("Закрытие браузера")
    public void tearDown() {
        webDriver.quit();
        new UserOperators().deleteUser(email, password);
    }

    @Step("Авторизация")
    private void authUser() {
        authPage.setEmail(email);
        authPage.setPassword(password);

        authPage.clickAuthButton();
        authPage.waitFormIsSubmitted();
    }

    @Step("Переход в профиль")
    private void goToProfile() {
        webDriver.get(Constants.LOG_IN_PAGE_URL);
        authPage.waitAuthFormVisible();
        authUser();

        homePage.clickLinkToProfile();
        profilePage.waitWhenAuthFormIsVisible();
    }
    @Test
    @DisplayName("Проверка перехода в 'Личный кабинет'")
    public void checkLinkToProfileIsSuccess() {
        Allure.parameter("Браузер", System.getProperty("browser", "chrome"));
        goToProfile();
        MatcherAssert.assertThat(
                "Некорректный URL страницы Личного кабинета",
                webDriver.getCurrentUrl(),
                containsString("/account/profile")
        );
    }

    @Test
    @DisplayName("Проверка перехода по клику по 'Конструктор'")
    public void checkLinkToConstructorIsSuccess() {
        Allure.parameter("Браузер", System.getProperty("browser", "chrome"));

        goToProfile();

        profilePage.clickConstructorButton();
        homePage.waitHeaderIsVisible();

        MatcherAssert.assertThat(
                "Текст 'Войти в аккаунт' меняется на 'Оформить заказ'",
                homePage.getAuthButtonText(),
                equalTo("Оформить заказ")
        );
    }
    @Test
    @DisplayName("Проверка перехода по клику логотипа Stellar Burgers")
    public void checkLinkOnLogoIsSuccess() {
        Allure.parameter("Браузер", System.getProperty("browser", "chrome"));

        goToProfile();

        profilePage.clickLinkOnLogo();
        homePage.waitHeaderIsVisible();

        MatcherAssert.assertThat(
                "Текст 'Войти в аккаунт' меняется на 'Оформить заказ'",
                homePage.getAuthButtonText(),
                equalTo("Оформить заказ")
        );
    }
    @Test
    @DisplayName("Проверка выхода из профиля по клику кнопки 'Выйти'")
    public void checkLinkLogOutIsSuccess() {
        Allure.parameter("Браузер", System.getProperty("browser", "chrome"));

        goToProfile();

        profilePage.clickLogoutButton();
        authPage.waitAuthFormVisible();

        MatcherAssert.assertThat(
                "Некорректный URL страницы Авторизации",
                webDriver.getCurrentUrl(),
                containsString("/login")
        );
    }
}

