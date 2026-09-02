import com.example.IFeline;
import com.example.Lion;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class LionParameterizedSexTest {
    private final String lionSex;
    private final boolean hasLionMane;

    IFeline feline = Mockito.mock(IFeline.class);
    Lion lion;

    public LionParameterizedSexTest(String lionSex, boolean hasLionMane) {
        this.lionSex = lionSex;
        this.hasLionMane = hasLionMane;
    }

    @Parameterized.Parameters
    public static Object[][] getParameters() {
        return new Object[][] {
                {"Самец", true},
                {"Самка", false}
        };
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void lionSexTest() throws Exception {
        lion = new Lion(lionSex, feline);
        assertEquals("У льва был установлен неверный пол!", hasLionMane, lion.doesHaveMane());
    }
}
