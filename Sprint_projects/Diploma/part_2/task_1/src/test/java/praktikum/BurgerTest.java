package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {
    @Mock
    Bun testBun;
    @Mock
    Ingredient testIngredientOne;
    @Mock
    Ingredient testIngredientTwo;

    Burger burger;

    int testIndex = 1;

    @Before
    public void startUp() {
        burger = new Burger();
    }

    @Test
    public void setBunsTest() {
        burger.setBuns(testBun);
        assertEquals("Булочка не была присвоена бургеру!", testBun, burger.bun);
    }


    @Test
    public void addIngredientTest() {
        burger.addIngredient(testIngredientOne);
        assertEquals("Ингредиент не был добавлен в бургер!", testIngredientOne, burger.ingredients.get(0));
    }

    @Test
    public void removeIngredientTest() {
        burger.addIngredient(testIngredientOne);
        burger.removeIngredient(0);
        assertTrue("Добавленный ингридиент не был удалён!", burger.ingredients.isEmpty());
    }

    @Test
    public void moveIngredientTest() {
        burger.addIngredient(testIngredientOne);
        burger.addIngredient(testIngredientTwo);
        burger.moveIngredient(0, testIndex);
        assertEquals("Ингредиент не был перемещён!", testIndex, burger.ingredients.indexOf(testIngredientOne));
    }
}
