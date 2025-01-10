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
import org.example.objects.AcceptUser;
import org.example.objects.User;
import io.github.bonigarcia.wdm.*;

@DisplayName("Проверка главной страницы")
public class HomePageTest {
        private WebDriver webDriver;
        private HomePage homePage;

        @Before
        @Step("Запуск браузера")
        public void startUp() {
            String browserName = System.getProperty("browser", "chrome");
            webDriver = Browsers.createDriver(browserName);
            webDriver.get(Constants.HOME_PAGE_URL);
            homePage = new HomePage(webDriver);
        }
        @After
        @Step("Закрытие браузера")
        public void tearDown() {
            webDriver.quit();
        }

        @Test
        @Step("Нажатие опции Булки")
        @DisplayName("Проверка работы опции Булки")
        public void checkScrollToBunIsSuccess() {
            Allure.parameter("Браузер", System.getProperty("browser", "chrome"));

            homePage.clickFillingsButton();
            homePage.clickBunsButton();
            homePage.scrollAndWait(homePage.getBunsTypes());

            MatcherAssert.assertThat(
                    "Список 'Булки' не отображается на странице",
                    webDriver.findElement(homePage.getBunsTypes()).isDisplayed(),
                    equalTo(true)
            );
        }

        @Test
        @Step("Нажатие опции Соусы")
        @DisplayName("Проверка работы опции Соусы")
        public void checkScrollToSauceIsSuccess() {
            Allure.parameter("Браузер", System.getProperty("browser", "chrome"));

            homePage.clickSaucesButton();
            homePage.scrollAndWait(homePage.getSaucesTypes());

            MatcherAssert.assertThat(
                    "Список 'Соусы' не отображается на странице",
                    webDriver.findElement(homePage.getSaucesTypes()).isDisplayed(),
                    equalTo(true)
            );
        }

        @Test
        @Step("Нажатие опции Начинки")
        @DisplayName("Проверка работы опции Начинки")
        public void checkScrollToFillingIsSuccess() {
            Allure.parameter("Браузер", System.getProperty("browser", "chrome"));

            homePage.clickFillingsButton();
            homePage.scrollAndWait(homePage.getFillingsTypes());

            MatcherAssert.assertThat(
                    "Список 'Начинки' не отображается на странице",
                    webDriver.findElement(homePage.getFillingsTypes()).isDisplayed(),
                    equalTo(true)
            );
        }
    }
