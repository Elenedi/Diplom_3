import io.qameta.allure.Allure;
import com.github.javafaker.Faker;
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

@DisplayName("Авторизация пользователя")
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
    @Step("Запуск браузера, подготовка тестовых данных")
    public void startUp() {
        String browserName = System.getProperty("browser", "chrome");
        webDriver = Browsers.createDriver(browserName);
        webDriver.get(Constants.MAIN_PAGE_URL);

        authPage = new AuthorisationPage(webDriver);
        homePage = new HomePage(webDriver);
        regPage = new RegisterPage(webDriver);
        lostPasswordPage = new LostPasswordPage(webDriver);

        name = faker.name().firstName();
        email = faker.internet().safeEmailAddress();
        password = faker.letterify("???????");

        Allure.addAttachment("Имя", name);
        Allure.addAttachment("Email", email);
        Allure.addAttachment("Пароль", password);

        new UserOperators().createUser(name, email, password);
    }

    @After
    @Step("Закрытие браузера, очистка тестовых данных")
    public void tearDown() {
        webDriver.quit();
        new UserOperators().deleteUser(email, password);
    }

    @Step("Авторизация пользователя")
    private void authUser() {
        authPage.setEmail(email);
        authPage.setPassword(password);
        authPage.clickAuthButton();
        authPage.waitFormIsSubmitted();
    }

    @Test
    @DisplayName("Вход через клик по кнопке 'Войти в аккаунт' на главной")
    public void authHomePageButtonIsSuccess() {
        Allure.parameter("Браузер", System.getProperty("browser", "chrome"));

        homePage.clickAuthButton();
        authPage.waitAuthFormVisible();
        authUser();

        MatcherAssert.assertThat(
                "Текст на кнопке 'Войти в аккаунт' должен поменяться на 'Оформить заказ'",
                homePage.getAuthButtonText(),
                equalTo("Оформить заказ")
        );
    }

    @Test
    @DisplayName("Вход через клик по кнопке 'Личный Кабинет' в хеддере страницы")
    public void authProfileButtonIsSuccess() {
        Allure.parameter("Браузер", System.getProperty("browser", "chrome"));

        homePage.clickLinkToProfile();
        authPage.waitAuthFormVisible();
        authUser();

        MatcherAssert.assertThat(
                "Текст на кнопке 'Войти в аккаунт' должен поменяться на 'Оформить заказ'",
                homePage.getAuthButtonText(),
                equalTo("Оформить заказ")
        );
    }

    @Test
    @DisplayName("Вход через формy восстановления пароля")
    public void authLinkLostPasswordFormIsSuccess() {
        Allure.parameter("Браузер", System.getProperty("browser", "chrome"));

        webDriver.get(Constants.FORGOT_PASSWORD_URL);

        regPage.clickAuthLink();
        authPage.waitAuthFormVisible();
        authUser();

        MatcherAssert.assertThat(
                "Текст на кнопке 'Войти в аккаунт' должен поменяться на 'Оформить заказ'",
                homePage.getAuthButtonText(),
                equalTo("Оформить заказ")
        );
    }
}


