import com.example.IFeline;
import com.example.Lion;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(MockitoJUnitRunner.class)
public class LionTest {
    @Mock
    IFeline feline;
    Lion lion;
    List<String> expectedLionFood = List.of("Зебра", "Лань");

    @Test
    public void invalidSexTest() throws Exception {
        try {
            lion = new Lion("Вертолёт-апач", feline);
            fail("Ожидалось исключение");
        } catch (Exception e) {
            assertEquals("Неверное сообщение о неправильном значении пола!", "Используйте допустимые значения пола животного - самец или самка", e.getMessage());
        }
    }

    @Test
    public void getKittensResultTest() throws Exception {
        Mockito.when(feline.getKittens()).thenReturn(4);
        lion = new Lion("Самка", feline);
        assertEquals("Возвращено неверное количество котят льва!", 4, lion.getKittens());
    }

    @Test
    public void getKittensBehaviorTest() throws Exception {
        Mockito.when(feline.getKittens()).thenReturn(4);
        lion = new Lion("Самка", feline);
        lion.getKittens();
        Mockito.verify(feline).getKittens();
    }

    @Test
    public void getFoodResultTest() throws Exception {
        Mockito.when(feline.getFood("Хищник")).thenReturn(expectedLionFood);
        lion = new Lion("Самец", feline);
        assertEquals("Возвращен неверный список еды льва!", expectedLionFood, lion.getFood());
    }

    @Test
    public void getFoodBehaviorTest() throws Exception {
        Mockito.when(feline.getFood("Хищник")).thenReturn(expectedLionFood);
        lion = new Lion("Самец", feline);
        lion.getFood();
        Mockito.verify(feline).getFood("Хищник");
    }
}
