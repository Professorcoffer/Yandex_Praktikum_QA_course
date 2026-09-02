package ru.scooter.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TrackOrderPage {
    private final WebDriver driver;

    //Локатор изображения о несуществующем заказе
    private final By orderNotFoundImage = By.xpath(".//img[@alt='Not found']");

    public TrackOrderPage(WebDriver driver) {
        this.driver = driver;
    }

    //Метод проверки наличия изображения о несуществующем заказе
    public void checkOrderNotFoundImage() {
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(orderNotFoundImage));
    }
}
