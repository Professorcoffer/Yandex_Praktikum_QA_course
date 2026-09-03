package service;

import model.Food;

public class ShoppingCart {
    Food[] food;

    public ShoppingCart(Food[] food) {
        this.food = food;
    }

    public double getAllAmount() {
        double sum = 0;

        for (Food value : food) {
            sum += value.getAmount() * value.getPrice();
        }

        return sum;
    }

    public double getAllDiscountedAmount() {
        double discountSum = 0;

        for (Food value : food) { //Теперь используется улучшенный for
            discountSum += (value.getAmount() * value.getPrice()) * ((100 - value.getDiscount()) / 100); //Способ расчёта суммы со скидкой изменён
        }

        return discountSum;
    }

    public double getAllVegetarianUndiscountedAmount() {
        double vegSum = 0;

        for (Food value : food) {
            if (value.isVegetarian()) {
                vegSum += value.getAmount() * value.getPrice();
            }
        }

        return vegSum;
    }
}
