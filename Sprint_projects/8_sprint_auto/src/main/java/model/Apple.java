package model;

import model.constants.Colour;
import model.constants.Discount;

public class Apple extends Food {
    private final String colour;

    public Apple (int amount, double price, String colour) {
        super(amount, price); //Использован конструктор класса-родителя
        this.colour = colour;
        this.isVegetarian = true;
    }

    @Override
    public double getDiscount() {
        return (colour.equals(Colour.RED_APPLE_COLOUR)) ? Discount.RED_APPLE_DISCOUNT : Discount.NO_DISCOUNT; //Сравнение теперь через equals, также запись укорочена с помощью тернарного оператора
    }
}
