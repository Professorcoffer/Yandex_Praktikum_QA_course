package ru.educationservices.stellarburgers;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import static org.junit.Assert.assertTrue;

public class MainStellarBurgersPage {
    private final WebDriver driver;

    //Локаторы кнопок входа
    private final By headerLogInButton = By.xpath(".//a[@href='/account']");
    private final By orderLogInButton = By.xpath(".//button[@class='button_button__33qZ0 button_button_type_primary__1O7Bx button_button_size_large__G21Vg']");

    //Локатор кнопки заказа
    private final By confirmOrderButton = By.xpath(".//button[text() = 'Оформить заказ']");

    //Локаторы кнопок секций ингредиентов
    private final By bunsSectionButon = By.xpath(".//span[text()='Булки']/parent::div");
    private final By saucesSectionButton = By.xpath(".//span[text()='Соусы']/parent::div");
    private final By fillingsSectionButton = By.xpath(".//span[text()='Начинки']/parent::div");

    //Локаторы активных кнопок секций ингредиентов
    private final By bunsSectionButonActive = By.xpath(".//span[text()='Булки']/parent::div[contains(@class, 'tab_tab_type_current__2BEPc')]");
    private final By saucesSectionButtonActive = By.xpath(".//span[text()='Соусы']/parent::div[contains(@class, 'tab_tab_type_current__2BEPc')]");
    private final By fillingsSectionButtonActive = By.xpath(".//span[text()='Начинки']/parent::div[contains(@class, 'tab_tab_type_current__2BEPc')]");

    public MainStellarBurgersPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Проверка секции булочек")
    public void checkBunsSectionButton() {
        clickFillingsSectionButton();
        clickBunsSectionButton();
        checkBunsSectionActiveState();
    }

    @Step("Проверка секции соусов")
    public void checkSaucesSectionButton() {
        clickFillingsSectionButton();
        clickSaucesSectionButton();
        checkSaucesSectionActiveState();
    }

    @Step("Проверка секции начинок")
    public void checkFillingsSectionButton() {
        clickFillingsSectionButton();
        checkFillingsSectionActiveState();
    }

    @Step("Нажатие на кнопку секции булочек")
    public void clickBunsSectionButton() {
        driver.findElement(bunsSectionButon).click();
    }

    @Step("Нажатие на кнопку секции соусов")
    public void clickSaucesSectionButton() {
        driver.findElement(saucesSectionButton).click();
    }

    @Step("Нажатие на кнопку секции начинок")
    public void clickFillingsSectionButton() {
        driver.findElement(fillingsSectionButton).click();
    }

    @Step("Проверка активного состояния секции булочек")
    public void checkBunsSectionActiveState() {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(bunsSectionButonActive));
        assertTrue("Секция с булочками не отображается на экране!", driver.findElement(bunsSectionButonActive).isDisplayed());
    }

    @Step("Проверка активного состояния секции соусов")
    public void checkSaucesSectionActiveState() {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(saucesSectionButtonActive));
        assertTrue("Секция с соусами не отображается на экране!", driver.findElement(saucesSectionButtonActive).isDisplayed());
    }

    @Step("Проверка активного состояния секции начинок")
    public void checkFillingsSectionActiveState() {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(fillingsSectionButtonActive));
        assertTrue("Секция с начинками не отображается на экране!", driver.findElement(fillingsSectionButtonActive).isDisplayed());
    }

    @Step("Нажатие на кнопку входа в шапке сайта")
    public void clickHeaderLogInButton() {
        driver.findElement(headerLogInButton).click();
    }

    @Step("Нажатие на кнопку входа на месте кнопки заказа")
    public void clickOrderLogInButton() {
        driver.findElement(orderLogInButton).click();
    }

    @Step("Проверка появляения на экране кнопки подтверждения заказа после успешного входа в аккаунт")
    public void checkSuccessfulLogIn() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(confirmOrderButton));
        assertTrue("Переход на главную страницу после входа не был совершён!", driver.findElement(confirmOrderButton).isDisplayed());
    }
}
