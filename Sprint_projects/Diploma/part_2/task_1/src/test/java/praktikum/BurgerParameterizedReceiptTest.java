package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class BurgerParameterizedReceiptTest {
    private final int ingredientsNumber;
    private final String expectedResult;

    Bun testBun = Mockito.mock(Bun.class);
    Ingredient testIngredient = Mockito.mock(Ingredient.class);
    Burger burger;

    public BurgerParameterizedReceiptTest(int ingredientsNumber, String expectedResult) {
        this.ingredientsNumber = ingredientsNumber;
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters
    public static Object[][] getParameters() {
        return new Object[][] {
                {0, String.format("(==== Sweet Bun ====)%n" +
                        "(==== Sweet Bun ====)%n%n" +
                        "Price: 20,000000%n")},
                {1, String.format("(==== Sweet Bun ====)%n" +
                        "= sauce ketchup =%n" +
                        "(==== Sweet Bun ====)%n%n" +
                        "Price: 21,000000%n")},
                {5, String.format("(==== Sweet Bun ====)%n" +
                        "= sauce ketchup =%n" +
                        "= sauce ketchup =%n" +
                        "= sauce ketchup =%n" +
                        "= sauce ketchup =%n" +
                        "= sauce ketchup =%n" +
                        "(==== Sweet Bun ====)%n%n" +
                        "Price: 25,000000%n")}
        };
    }

    @Before
    public void startUp() {
        MockitoAnnotations.initMocks(this);
        burger = new Burger();
    }

    @Test
    public void getReceiptTest() {
        Mockito.when(testBun.getName()).thenReturn("Sweet Bun");
        Mockito.when(testBun.getPrice()).thenReturn(10f);
        Mockito.when(testIngredient.getType()).thenReturn(IngredientType.SAUCE);
        Mockito.when(testIngredient.getName()).thenReturn("ketchup");
        Mockito.when(testIngredient.getPrice()).thenReturn(1f);

        burger.setBuns(testBun);

        for (int i = 0; i < ingredientsNumber; i++) {
            burger.addIngredient(testIngredient);
        }

        assertEquals("Рецепт составлен неверно!", expectedResult, burger.getReceipt());
    }
}
