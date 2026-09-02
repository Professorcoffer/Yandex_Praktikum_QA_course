package ru.educationservices.stellarburgers;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import static org.junit.Assert.assertTrue;

public class LogInPage {
    private final WebDriver driver;

    //Локаторы кнопок
    private final By registerButton = By.xpath(".//a[@href='/register']");
    private final By logInButton = By.xpath(".//button[@class='button_button__33qZ0 button_button_type_primary__1O7Bx button_button_size_medium__3zxIa']");
    private final By recoverPasswordButton = By.xpath(".//a[@href='/forgot-password']");

    //Локаторы полей ввода
    private final By emailInput = By.name("name");
    private final By passwordInput = By.name("Пароль");

    public LogInPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Вход в аккаунт")
    public void logIn(String email, String password) {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(registerButton));
        fillEmailInput(email);
        fillPasswordInput(password);
        clickLogInButton();
    }

    @Step("Нажатие кнопки регистрации")
    public void clickRegisterButton() {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(registerButton));
        driver.findElement(registerButton).click();
    }

    @Step("Нажатие на кнопку восстановления пароля")
    public void clickRecoverPasswordButton() {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(registerButton));
        driver.findElement(recoverPasswordButton).click();
    }

    @Step("Проверка появления кнопки регистрации после успешной регистрации")
    public void checkSuccessfulRegistration() {
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.urlContains("/login"));
        assertTrue("Переход на страницу входа после регистрации не был совершён!", driver.findElement(registerButton).isDisplayed());
    }

    @Step("Заполнение поля ввода почты")
    public void fillEmailInput(String email) {
        driver.findElement(emailInput).sendKeys(email);
    }

    @Step("Заполнение поля ввода пароля")
    public void fillPasswordInput(String password) {
        driver.findElement(passwordInput).sendKeys(password);
    }

    @Step("Нажатие на кнопку входа")
    public void clickLogInButton() {
        driver.findElement(logInButton).click();
    }
}
