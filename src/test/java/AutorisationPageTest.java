import io.qameta.allure.Allure;
import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.example.endpoints.UserOperators;
import org.example.references.Browsers;
import org.example.references.Constants;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import static org.hamcrest.Matchers.equalTo;

import org.example.page.object.AuthorisationPage;
import org.example.page.object.RegisterPage;
import org.example.page.object.HomePage;
import org.example.page.object.LostPasswordPage;

@DisplayName("Авторизация")
public class AutorisationPageTest {
    private WebDriver webDriver;
    private AuthorisationPage authPage;
    private HomePage homePage;
    private RegisterPage regPage;
    private LostPasswordPage lostPasswordPage;
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
        regPage = new RegisterPage(webDriver);
        lostPasswordPage = new LostPasswordPage(webDriver);

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

    @Test
    @DisplayName("Вход по кнопке 'Войти в аккаунт' на главной")
    @Description("Проверка авторизации по кнопке на главной странице")
    public void authHomePageButtonIsSuccessTest() {
        Allure.parameter("Браузер", System.getProperty("browser", "chrome"));

        homePage.clickAuthButton();
        authPage.waitAuthFormVisible();
        authPage.authUser(email, password);

        MatcherAssert.assertThat(
                "Текст на кнопке должен поменяться на 'Оформить заказ'",
                homePage.getAuthButtonText(),
                equalTo("Оформить заказ")
        );
    }

    @Test
    @DisplayName("Вход по кнопке 'Личный Кабинет' вверху страницы")
    @Description("Успешный вход по кнопке вверху страницы")
    public void authProfileButtonIsSuccessTest() {
        Allure.parameter("Браузер", System.getProperty("browser", "chrome"));

        homePage.clickLinkToProfile();
        authPage.waitAuthFormVisible();
        authPage.authUser(email, password);

        MatcherAssert.assertThat(
                "Текст 'Войти в аккаунт' должен поменяться на 'Оформить заказ'",
                homePage.getAuthButtonText(),
                equalTo("Оформить заказ")
        );
    }

    @Test
    @DisplayName("Вход по форме восстановления пароля")
    @Description("Успешный вход по форме восстанновления пароля")
    public void authLinkLostPassFormIsSuccessTest() {
        Allure.parameter("Браузер", System.getProperty("browser", "chrome"));

        webDriver.get(Constants.LOST_PASSWORD_URL);

        regPage.clickAuthLink();
        authPage.waitAuthFormVisible();
        authPage.authUser(email, password);

        MatcherAssert.assertThat(
                "Текст на кнопке 'Войти в аккаунт' меняется на 'Оформить заказ'",
                homePage.getAuthButtonText(),
                equalTo("Оформить заказ")
        );
    }

    @Test
    @DisplayName("Вход по кнопке в форме регистрации")
    @Description("Успешная авторизация по кнопке в форме регистрации")
    public void authThroughRegisterButtonIsSuccessTest() {
        Allure.parameter("Браузер", System.getProperty("browser", "chrome"));
        webDriver.get(Constants.REGISTER_PAGE_URL);

        regPage.setName(name);
        regPage.setEmail(email);
        regPage.setPassword(password);

        regPage.clickRegisterButton();
        authPage.waitAuthFormVisible();
        authPage.authUser(email, password);

        MatcherAssert.assertThat(
                "Текст на кнопке должен меняться на 'Оформить заказ'",
                homePage.getAuthButtonText(),
                equalTo("Оформить заказ")
        );
    }
}


