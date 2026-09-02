package ru.educationservices.stellarburgers;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PasswordRecoverPage {
    private final WebDriver driver;

    //Локатор кнопки перехода на страницу входа
    private final By logInButton = By.xpath(".//a[@href='/login']");

    public PasswordRecoverPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Нажатие на кнопку перехода на страницу входа")
    public void clickLogInButton() {
        driver.findElement(logInButton).click();
    }
}
