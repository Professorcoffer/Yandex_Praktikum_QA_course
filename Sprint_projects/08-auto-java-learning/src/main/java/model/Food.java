package model;

public abstract class Food implements Discountable{
    protected int amount;
    protected double price;
    protected boolean isVegetarian;

    public Food (int amount, double price) { //Добавлен конструктор
        this.amount = amount;
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public double getPrice() {
        return price;
    }

    public boolean isVegetarian() {
        return isVegetarian;
    }
}
