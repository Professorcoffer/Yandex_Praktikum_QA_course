package model;

import model.constants.Discount;

public class Meat extends Food {
    public Meat (int amount, double price) {
        super(amount, price); //Использован конструктор класса-родителя
        this.isVegetarian = false;
    }

    @Override
    public double getDiscount() {
        return Discount.NO_DISCOUNT;
    }
}
