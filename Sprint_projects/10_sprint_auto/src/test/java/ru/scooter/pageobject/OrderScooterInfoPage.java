package ru.scooter.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.assertTrue;

public class OrderScooterInfoPage {
    WebDriver driver;

    //Локаторы полей ввода
    private final By orderDateInput = By.xpath(".//input[@placeholder='* Когда привезти самокат']");
    private final By chosenDateButton = By.xpath(".//div[contains(@class, 'selected')]");
    private final By leaseTermDropdown = By.xpath(".//div[@class='Dropdown-control']");
    private final By scooterColorBlackInput = By.id("black");
    private final By scooterColorGreyInput = By.id("grey");
    private final By courierCommentaryInput = By.xpath(".//input[@placeholder='Комментарий для курьера']");

    //Локатор выбора длительности аренды "сутки"
    private final By leaseTermDropdownOption = By.xpath(".//div[@class='Dropdown-option' and text()='сутки']");

    //Локатор кнопки заказа
    private final By orderAcceptButton = By.xpath(".//button[contains(@class, 'Button_Middle__1CSJM') and text()='Заказать']");

    //Локатор кнопки подтверждения заказа
    private final By orderConfirmationButton = By.xpath(".//div[@class='Order_Buttons__1xGrp']/button[text()='Да']");

    //Локатор текста в окне подтверждённого заказа
    private final By finalOrderInfo = By.className("Order_ModalHeader__3FDaJ");

    public OrderScooterInfoPage(WebDriver driver) {
        this.driver = driver;
    }

    //Метод проверки появления текста создания заказа
    public void checkForOrderCompletion() {
        assertTrue("Заказ не был создан!",  driver.findElement(finalOrderInfo).getText().contains("Заказ оформлен"));
    }

    //Метод подтверждения заказа в всплывающем окне
    public void confirmOrder() {
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.elementToBeClickable(orderConfirmationButton));
        driver.findElement(orderConfirmationButton).click();
    }

    //Метод принятия заказа
    public void acceptOrder() {
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.elementToBeClickable(orderAcceptButton));
        driver.findElement(orderAcceptButton).click();
    }

    //Единый метод для заполнения данных в заказе
    public void fillOrderCredentials(String date, String color, String commentary) {
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.elementToBeClickable(orderDateInput));
        fillOrderDateInput(date);
        fillLeaseTermDropdown();
        fillScooterColorInput(color);
        fillCourierCommentaryInput(commentary);
    }

    //Методы заполнения полей
    public void fillOrderDateInput(String date) {
        driver.findElement(orderDateInput).clear();
        driver.findElement(orderDateInput).sendKeys(date);
        driver.findElement(chosenDateButton).click();
    }

    public void fillLeaseTermDropdown() {
        driver.findElement(leaseTermDropdown).click();
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.elementToBeClickable(leaseTermDropdownOption));
        driver.findElement(leaseTermDropdownOption).click();
    }

    public void fillScooterColorInput(String color) {
        if (color.equals("black")) {
            driver.findElement(scooterColorBlackInput).click();
        } else if (color.equals("grey")) {
            driver.findElement(scooterColorGreyInput).click();
        }
    }

    public void fillCourierCommentaryInput(String commentary) {
        driver.findElement(courierCommentaryInput).clear();
        driver.findElement(courierCommentaryInput).sendKeys(commentary);
    }
}
