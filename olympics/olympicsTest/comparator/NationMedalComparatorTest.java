package bg.sofia.uni.fmi.mjt.olympics.comparator;

import bg.sofia.uni.fmi.mjt.olympics.MJTOlympics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class NationMedalComparatorTest {

    private MJTOlympics olympicsMock;
    private NationMedalComparator comparator;

    @BeforeEach
    void setUp() {
        olympicsMock = Mockito.mock(MJTOlympics.class);
        comparator = new NationMedalComparator(olympicsMock);
    }

    @Test
    void testCompareWhenFirstNationHasMoreMedals() {
        Mockito.when(olympicsMock.getTotalMedals("Bulgaria")).thenReturn(5);
        Mockito.when(olympicsMock.getTotalMedals("Germany")).thenReturn(3);

        int result = comparator.compare("Bulgaria", "Germany");

        assertTrue(result < 0, "Expected Bulgaria to come before Germany (more medals).");
    }

    @Test
    void testCompareWhenSecondNationHasMoreMedals() {
        Mockito.when(olympicsMock.getTotalMedals("Bulgaria")).thenReturn(2);
        Mockito.when(olympicsMock.getTotalMedals("Germany")).thenReturn(4);

        int result = comparator.compare("Bulgaria", "Germany");

        assertTrue(result > 0, "Expected Germany to come before Bulgaria (more medals).");
    }

    @Test
    void testCompareWhenEqualMedals() {
        Mockito.when(olympicsMock.getTotalMedals("Bulgaria")).thenReturn(3);
        Mockito.when(olympicsMock.getTotalMedals("Germany")).thenReturn(3);

        int result = comparator.compare("Bulgaria", "Germany");

        assertTrue(result < 0, "Expected Bulgaria to come before Germany alphabetically.");
    }

    @Test
    void testCompareWhenEqualMedalsAndSameName() {
        Mockito.when(olympicsMock.getTotalMedals("Italy")).thenReturn(2);
        Mockito.when(olympicsMock.getTotalMedals("Italy")).thenReturn(2);

        int result = comparator.compare("Italy", "Italy");

        assertEquals(0, result, "Expected result 0 when comparing the same nation.");
    }

    @Test
    void testCompareCallsGetTotalMedalsForBothNations() {
        Mockito.when(olympicsMock.getTotalMedals("France")).thenReturn(1);
        Mockito.when(olympicsMock.getTotalMedals("Spain")).thenReturn(2);

        comparator.compare("France", "Spain");

        Mockito.verify(olympicsMock).getTotalMedals("France");
        Mockito.verify(olympicsMock).getTotalMedals("Spain");
    }
}
