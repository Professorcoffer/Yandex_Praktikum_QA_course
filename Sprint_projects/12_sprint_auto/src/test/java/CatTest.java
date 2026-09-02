import com.example.Cat;
import com.example.Feline;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CatTest {
    @Mock
    Feline predator;

    Cat cat;

    List<String> expectedCatFood = List.of("Рыбка", "Корм", "Мыши");

    @Before
    public void startUp() {
        cat = new Cat(predator);
    }

    @Test
    public void getSoundTest() {
        assertEquals("Кошка издаёт неверный звук!", "Мяу", cat.getSound());
    }

    @Test
    public void getFoodResultTest() throws Exception {
        Mockito.when(predator.eatMeat()).thenReturn(expectedCatFood);
        assertEquals("Возвращён неверный список еды кошки!", expectedCatFood, cat.getFood());
    }

    @Test
    public void getFoodBehaviorTest() throws Exception {
        Mockito.when(predator.eatMeat()).thenReturn(expectedCatFood);
        cat.getFood();
        Mockito.verify(predator).eatMeat();
    }
}
