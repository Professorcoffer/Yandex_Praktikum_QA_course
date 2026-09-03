package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class BurgerParameterizedPriceTest {
    private final int ingredientsNumber;
    private final float expectedResult;

    Bun testBun = Mockito.mock(Bun.class);
    Ingredient testIngredient = Mockito.mock(Ingredient.class);
    Burger burger;

    public BurgerParameterizedPriceTest(int ingredientsNumber, float expectedResult) {
        this.ingredientsNumber = ingredientsNumber;
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters
    public static Object[][] getParameters() {
        return new Object[][] {
                {0, 20f},
                {1, 21f},
                {5, 25f}
        };
    }

    @Before
    public void startUp() {
        MockitoAnnotations.initMocks(this);
        burger = new Burger();
    }

    @Test
    public void getPriceTest() {
        Mockito.when(testBun.getPrice()).thenReturn(10f);
        Mockito.when(testIngredient.getPrice()).thenReturn(1f);

        burger.setBuns(testBun);

        for (int i = 0; i < ingredientsNumber; i++) {
            burger.addIngredient(testIngredient);
        }

        assertEquals("Цена бургера рассчитана неверно!", expectedResult, burger.getPrice(), 0.001);
    }
}
