
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import org.example.endpoints.UserOperators;
import org.example.references.Browsers;
import org.example.references.Constants;
import org.openqa.selenium.WebDriver;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;

import org.example.page.object.RegisterPage;

import static org.hamcrest.Matchers.equalTo;

@DisplayName("Регистрация нового пользователя")
public class RegisterPageTest {
    private WebDriver webDriver;
    private RegisterPage registerPage;
    private String name;
    private String email;
    private String password;
    Faker faker = new Faker();

    @Before
    @Step("Запуск браузера, подготовка тестовых данных")
    public void startUp() {
        String browserName = System.getProperty("browser", "chrome");
        webDriver = Browsers.createDriver(browserName);
        webDriver.get(Constants.REGISTER_PAGE_URL);
        registerPage = new RegisterPage(webDriver);


        name = faker.name().firstName();
        email = faker.internet().safeEmailAddress();
        password = faker.letterify("????????");

        Allure.addAttachment("Имя", name);
        Allure.addAttachment("Email", email);
        Allure.addAttachment("Пароль", password);
    }

    @After
    @Step("Закрытие браузера, очистка тестовых данных")
    public void tearDown() {
        webDriver.quit();
        new UserOperators().deleteUser(email, password);
    }

    @Test
    @DisplayName("Регистрация нового пользователя с валидными данными")
    public void registerUserIsSuccess() {
        Allure.parameter("Браузер", System.getProperty("browser", "chrome"));


        registerPage.setName(name);
        registerPage.setEmail(email);
        registerPage.setPassword(password);

        registerPage.clickRegisterButton();

        registerPage.waitFormIsSubmitted("Вход");
    }

    @Test
    @DisplayName("Регистрация нового пользователя с коротким паролем (4 символа)")
    public void registerUserIncorrectPasswordFailed() {
        Allure.parameter("Браузер", System.getProperty("browser", "chrome"));


        registerPage.setName(name);
        registerPage.setEmail(email);
        registerPage.setPassword(faker.letterify("????"));

        registerPage.clickRegisterButton();

        registerPage.waitErrorIsVisible();

        checkErrorMessage();
    }


    @Step("Проверка появления сообщения об ошибке")
    private void checkErrorMessage() {
        MatcherAssert.assertThat(
                "Некорректное сообщение об ошибке",
                registerPage.getErrorMessage(),
                equalTo("Некорректный пароль")
        );
    }
}


