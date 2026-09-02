package utility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverBuilder {
    public static final String URL = "https://stellarburgers.education-services.ru";

    public static WebDriver build() throws Exception {
        String browser = System.getProperty("browser", "chrome");

        switch (browser.toLowerCase()) {
            case "chrome":
                return buildChrome();

            case "yandex":
                return buildYandex();

            default:
                throw new Exception("Такой браузер не предусмотрен: " + browser);
        }
    }

    private static ChromeDriver buildChrome() {
        System.setProperty("webdriver.chrome.driver", "D:\\WebDriver\\bin\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage", "--headless", "--window-size=1920,1080");
        return new ChromeDriver(options);
    }

    private static ChromeDriver buildYandex() {
        System.setProperty("webdriver.chrome.driver", "D:\\WebDriver\\bin\\yandexdriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:\\Program Files\\Yandex\\YandexBrowser\\Application\\browser.exe");
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage", "--headless", "--window-size=1920,1080");
        return new ChromeDriver(options);
    }
}
