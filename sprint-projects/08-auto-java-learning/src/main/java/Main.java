import model.Apple;
import model.Food;
import model.Meat;
import model.constants.Colour;
import service.ShoppingCart;

public class Main {
    public static void main(String[] args) {
        Meat steak = new Meat(5, 100);
        Apple redApple = new Apple(10, 50, Colour.RED_APPLE_COLOUR);
        Apple greenApple = new Apple(8, 60, Colour.GREEN_APPLE_COLOUR);

        Food[] foodList = {steak, redApple, greenApple};

        ShoppingCart cart = new ShoppingCart(foodList);

        System.out.println("Общая сумма товаров без скидки: " + cart.getAllAmount());
        System.out.println("Общая сумма товаров со скидкой: " + cart.getAllDiscountedAmount());
        System.out.println("Сумма всех вегетарианских товаров без скидки: " + cart.getAllVegetarianUndiscountedAmount()); //Теперь дополнительно выводится текст со значениями
    }
}
