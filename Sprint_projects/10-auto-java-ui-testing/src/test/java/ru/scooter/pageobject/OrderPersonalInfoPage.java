package ru.scooter.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OrderPersonalInfoPage {
    WebDriver driver;

    //Локаторы полей ввода
    private final By nameInput = By.xpath(".//input[@placeholder='* Имя']");
    private final By surnameInput = By.xpath(".//input[@placeholder='* Фамилия']");
    private final By addressInput = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By phoneInput = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");
    private final By metroInput = By.xpath(".//input[@placeholder='* Станция метро']");

    //Локатор варианта выбора первой станции
    private final By metroStationButton = By.xpath(".//ul[@class='select-search__options']/li[@data-value='1']");

    //Локатор кнопки продолжения оформления заказа
    private final By orderProceedButton = By.cssSelector(".Button_Button__ra12g.Button_Middle__1CSJM");

    //Для доп задания!
    //Локаторы сообщений об ошибке
    private final By nameInputError = By.xpath("(.//div[@class='Input_InputContainer__3NykH']/div)[2]");
    private final By surnameInputError = By.xpath("(.//div[@class='Input_InputContainer__3NykH']/div)[3]");
    private final By addressInputError = By.xpath("(.//div[@class='Input_InputContainer__3NykH']/div)[4]");
    private final By phoneInputError = By.xpath("(.//div[@class='Input_InputContainer__3NykH']/div)[5]");
    private final By metroInputError = By.xpath(".//div[@class='Order_MetroError__1BtZb']");

    public OrderPersonalInfoPage(WebDriver driver) {
        this.driver = driver;
    }

    //Единый метод для заполнения данных о заказе
    public void fillOrderCredentials(String name, String surname, String address, String phone) {
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.elementToBeClickable(nameInput));
        fillNameInput(name);
        fillSurnameInput(surname);
        fillAddressInput(address);
        fillMetroInput();
        fillPhoneInput(phone);
    }

    //Методы заполнения полей ввода
    public void fillNameInput(String name) {
        driver.findElement(nameInput).clear();
        driver.findElement(nameInput).sendKeys(name);
    }

    public void fillSurnameInput(String surname) {
        driver.findElement(surnameInput).clear();
        driver.findElement(surnameInput).sendKeys(surname);
    }

    public void fillAddressInput(String address) {
        driver.findElement(addressInput).clear();
        driver.findElement(addressInput).sendKeys(address);
    }

    public void fillMetroInput() {
        driver.findElement(metroInput).click();
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.elementToBeClickable(metroStationButton));
        driver.findElement(metroStationButton).click();
    }

    public void fillPhoneInput(String phone) {
        driver.findElement(phoneInput).clear();
        driver.findElement(phoneInput).sendKeys(phone);
    }

    //Метод нажатия на кнопку заказа
    public void clickOrderProceedButton() {
        driver.findElement(orderProceedButton).click();
    }

    //Единый метод для проверки сообщений об ошибках
    public void checkInputsErrorMessages() {
        clickOrderProceedButton();
        checkNameInputErrorMessage();
        checkSurnameInputErrorMessage();
        checkAddressInputErrorMessage();
        checkMetroInputErrorMessage();
        checkPhoneInputErrorMessage();
    }

    //Методы проверки сообщений об ошибке полей
    public void checkNameInputErrorMessage() {
        assertTrue("Сообщение об ошибке в поле имя не отобразилось!", driver.findElement(nameInputError).isDisplayed());
        assertEquals("Сообщение об ошибке в поле имя неверное!", "Введите корректное имя", driver.findElement(nameInputError).getText());
    }

    public void checkSurnameInputErrorMessage() {
        assertTrue("Сообщение об ошибке в поле фамилия не отобразилось!", driver.findElement(surnameInputError).isDisplayed());
        assertEquals("Сообщение об ошибке в поле фамилия неверное!", "Введите корректную фамилию", driver.findElement(surnameInputError).getText());
    }

    public void checkAddressInputErrorMessage() {
        assertTrue("Сообщение об ошибке в поле адрес не отобразилось!", driver.findElement(addressInputError).isDisplayed());
        assertEquals("Сообщение об ошибке в поле адрес неверное!", "Введите корректный адрес", driver.findElement(addressInputError).getText());
    }

    public void checkMetroInputErrorMessage() {
        assertTrue("Сообщение об ошибке в поле метро не отобразилось!", driver.findElement(metroInputError).isDisplayed());
        assertEquals("Сообщение об ошибке в поле метро неверное!", "Выберите станцию", driver.findElement(metroInputError).getText());
    }

    public void checkPhoneInputErrorMessage() {
        assertTrue("Сообщение об ошибке в поле телефон не отобразилось!", driver.findElement(phoneInputError).isDisplayed());
        assertEquals("Сообщение об ошибке в поле телефон неверное!", "Введите корректный номер", driver.findElement(phoneInputError).getText());
    }
}