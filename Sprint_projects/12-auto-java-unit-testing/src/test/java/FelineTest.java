import com.example.Feline;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class FelineTest {
    @Spy
    Feline feline;

    List<String> expectedFelineFood = List.of("Грызуны", "Птички");


    @Test
    public void eatMeatResultTest() throws Exception {
        Mockito.doReturn(expectedFelineFood).when(feline).getFood("Хищник");
        assertEquals("Возвращен неверный список еды кошачьего!", feline.eatMeat(), expectedFelineFood);
    }

    @Test
    public void eatMeatBehaviorTest() throws Exception {
        Mockito.doReturn(expectedFelineFood).when(feline).getFood("Хищник");
        feline.eatMeat();
        Mockito.verify(feline).getFood("Хищник");
    }

    @Test
    public void getFamilyTest(){
        assertEquals("Возвращено неверное семейство кошачьих!","Кошачьи", feline.getFamily());
    }

    @Test
    public void getKittensNoArgsResultTest() {
        assertEquals("Количество котят без аргументов неверное!", 1, feline.getKittens());
    }

    @Test
    public void getKittensNoArgsBehaviorTest() {
        feline.getKittens();
        Mockito.verify(feline).getKittens(1);
    }

    @Test
    public void getKittensWithArgsTest() {
        assertEquals("Количество котят с аргументами неверное!", 10, feline.getKittens(10));
    }

}
