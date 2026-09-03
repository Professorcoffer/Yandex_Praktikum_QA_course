package utility;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverBuilder {
    public static final String URL = "https://qa-scooter.praktikum-services.ru";

    public static ChromeDriver buildChrome() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage", "--headless", "--window-size=1920,1080");
        return new ChromeDriver(options);
    }
    public static FirefoxDriver buildFirefox() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage", "--headless", "--width=1920", "--height=1080");
        return new FirefoxDriver(options);
    }
}
