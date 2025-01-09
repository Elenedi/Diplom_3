import io.qameta.allure.Step;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.openqa.selenium.WebDriver;
import static org.hamcrest.Matchers.equalTo;

import org.example.references.Constants;
import org.example.references.Browsers;
import org.example.page.object.HomePage;

@DisplayName("Проверка конструктора (главной страницы)")
public class HomePageTest {
        private WebDriver webDriver;
        private HomePage homePage;

        @Before
        @Step("Запуск браузера")
        public void startUp() {
            String browserName = System.getProperty("browser", "chrome");
            webDriver = Browsers.createDriver(browserName);
            webDriver.get(Constants.MAIN_PAGE_URL);
            homePage = new HomePage(webDriver);
        }
        @After
        @Step("Закрытие браузера")
        public void tearDown() {
            webDriver.quit();
        }

        @Test
        @Step("Нажатие на вкладку Булочки")
        @DisplayName("Проверка работы вкладки Булочки в разделе с ингредиентами")
        public void checkScrollToBunIsSuccess() {
            Allure.parameter("Браузер", System.getProperty("browser", "chrome"));

            homePage.clickFillingButton();
            homePage.clickBunButton();
            homePage.scrollAndWait(homePage.getBunTypes());

            MatcherAssert.assertThat(
                    "Список 'Булочки' не видно на странице",
                    webDriver.findElement(homePage.getBunTypes()).isDisplayed(),
                    equalTo(true)
            );
        }

        @Test
        @Step("Нажатие на вкладку Соусы")
        @DisplayName("Проверка работы вкладки Соусы в разделе с ингредиентами")
        public void checkScrollToSauceIsSuccess() {
            Allure.parameter("Браузер", System.getProperty("browser", "chrome"));

            homePage.clickSauceButton();
            homePage.scrollAndWait(homePage.getSauceTypes());

            MatcherAssert.assertThat(
                    "Список 'Соусы' не видно на странице",
                    webDriver.findElement(homePage.getSauceTypes()).isDisplayed(),
                    equalTo(true)
            );
        }

        @Test
        @Step("Нажатие на вкладку Начинки")
        @DisplayName("Проверка работы вкладки Начинки в разделе с ингредиентами")
        public void checkScrollToFillingIsSuccess() {
            Allure.parameter("Браузер", System.getProperty("browser", "chrome"));

            homePage.clickFillingButton();
            homePage.scrollAndWait(homePage.getFillingTypes());

            MatcherAssert.assertThat(
                    "Список 'Начинки' не видно на странице",
                    webDriver.findElement(homePage.getFillingTypes()).isDisplayed(),
                    equalTo(true)
            );
        }
    }
