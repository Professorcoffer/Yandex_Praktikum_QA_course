package ru.educationservices.stellarburgers;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import static org.junit.Assert.assertTrue;

public class RegisterPage {
    private final WebDriver driver;

    //Локаторы кнопок
    public final By registerButton = By.xpath(".//button[text()='Зарегистрироваться']");
    public final By logInButton = By.xpath(".//a[@href='/login']");

    //Локаторы полей ввода
    public final By nameInput = By.xpath(".//label[text()='Имя']/following-sibling::input");
    public final By emailInput = By.xpath(".//label[text()='Email']/following-sibling::input");
    public final By passwordInput = By.xpath(".//label[text()='Пароль']/following-sibling::input");

    //Локатор текста об ошибке пароля
    public final By passwordInputError = By.xpath(".//p[@class='input__error text_type_main-default']");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Регистрация валидного пользователя")
    public void registerValidly(String name, String email, String password) {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(nameInput));
        fillNameInput(name);
        fillEmailInput(email);
        fillPasswordInput(password);
        clickRegisterButton();
    }

    @Step("Проверка появления текста ошибки пароля")
    public void registerWithInvalidPassword() {
        fillPasswordInput("123");
        clickRegisterButton();
        checkPasswordError();
    }

    @Step("Заполнение поля ввода имени")
    public void fillNameInput(String name) {
        driver.findElement(nameInput).sendKeys(name);
    }

    @Step("Заполнение поля ввода почты")
    public void fillEmailInput(String email) {
        driver.findElement(emailInput).sendKeys(email);
    }

    @Step("Заполнение поля ввода пароля")
    public void fillPasswordInput(String password) {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
        driver.findElement(passwordInput).sendKeys(password);
    }

    @Step("Нажатие на кнопку регистрации")
    public void clickRegisterButton() {
        driver.findElement(registerButton).click();
    }

    @Step("Нажатие на кнопку входа")
    public void clickLogInButton() {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(logInButton));
        driver.findElement(logInButton).click();
    }

    @Step("Проверка появления текста ошибки в пароле")
    public void checkPasswordError() {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(passwordInputError));
        assertTrue("Сообщение об ошибке для поля пароль не появилось!", driver.findElement(passwordInputError).isDisplayed());
    }
}
