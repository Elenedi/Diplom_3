package org.example.references;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.*;

public class Browsers {
    public static ChromeDriver createDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome": {
                ChromeOptions options = new ChromeOptions();
                options.addArguments(
                        "--no-sandbox",
                        "--headless",
                        "--disable-dev-shm-usage",
                        "--disable-gpu"
                );
                WebDriverManager.chromedriver().driverVersion("131.0.6778.85")
                        .setup();
                return new ChromeDriver(options);
            }

            case "yandex": {
                String yandexBinaryPath = System.getenv("YANDEX_BROWSER_PATH");
                if (yandexBinaryPath == null || yandexBinaryPath.isEmpty()) {
                    throw new IllegalArgumentException("Переменная окружения YANDEX_BROWSER_PATH не задана.");
                }

                ChromeOptions options = new ChromeOptions();
                options.setBinary(yandexBinaryPath);
                options.addArguments(
                        "--no-sandbox",
                        "--headless",
                        "--disable-dev-shm-usage",
                        "--disable-gpu"
                );
                WebDriverManager.chromedriver()
                        .driverVersion("128.0.6613.67")
                        .setup();
                return new ChromeDriver(options);
            }

            default:
                throw new IllegalArgumentException("Неизвестный браузер: " + browser);
        }
    }
}



