import com.example.AlexLion;
import com.example.IFeline;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class AlexLionTest {
    AlexLion alex;

    @Mock
    IFeline feline;

    List<String> expectedAlexFriends = List.of("Марти", "Глория", "Мелман");
    String expectedAlexPlaceOfLiving = "Нью-Йоркский зоопарк";

    @Before
    public void startUp() throws Exception {
        alex = new AlexLion(feline);
    }

    @Test
    public void alexSexTest() {
        assertTrue("У Алекса неверный пол!", alex.doesHaveMane());
    }

    @Test
    public void getFriendsTest() {
        assertEquals("Неверный список друзей Алекса!", expectedAlexFriends, alex.getFriends());
    }

    @Test
    public void getPlaceOfLivingTest() {
        assertEquals("Неверное место жительства Алекса!", expectedAlexPlaceOfLiving, alex.getPlaceOfLiving());
    }

    @Test
    public void getKittensTest() {
        assertEquals("У Алекса не может быть котят!", 0, alex.getKittens());
    }
}
