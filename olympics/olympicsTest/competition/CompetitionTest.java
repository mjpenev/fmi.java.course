package bg.sofia.uni.fmi.mjt.olympics.competition;

import bg.sofia.uni.fmi.mjt.olympics.competitor.Athlete;
import bg.sofia.uni.fmi.mjt.olympics.competitor.Competitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

public class CompetitionTest {
    private Competitor athlete = new Athlete("id1", "John", "USA");

    @BeforeEach
    void setup() {
        System.out.println("Competition test executed!");
    }

    @Test
    void testConstructorThrowsWhenNameIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new Competition(null, "Swimming", Set.of(athlete)),
                "Constructor does not throw error for an empty argument.");
    }
    @Test
    void testConstructorThrowsWhenDisciplineIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new Competition("Martin", null, Set.of(athlete)),
                "Constructor does not throw error for an empty argument.");
    }
    @Test
    void testConstructorThrowsWhenCompetitorsEmpty() {
        assertThrows(IllegalArgumentException.class,
                () -> new Competition("Martin", "Running", Set.of()),
                "Should throw when competitors is empty");
    }
    @Test
    void testConstructorThrowsWhenCompetitorsAreNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new Competition("Martin", "Swimming", null),
                "Constructor does not throw error for an empty argument.");
    }
    @Test
    void testConstructorThrowsWhenCompetitionIsFine() {
        Competition c1 = new Competition("Martin", "Swimming", Set.of(athlete));
        assertEquals(c1, c1,"Constructor does not create competition properly.");
    }
    @Test
    void testCompetitors() {
        Competition c1 = new Competition("Martin", "Football", Set.of(athlete));
        Competition c2 = new Competition("Martin", "Football", Set.of(athlete));

        assertEquals(c1.competitors(), c2.competitors(), "Competitors function failed.");
    }
    @Test
    void testEqualsWhenCompetitionsSame() {
        Competition c1 = new Competition("Martin", "Football", Set.of(athlete));

        assertTrue(c1.equals(c1), "Competition does not equal itself.");
    }
    @Test
    void testEqualsWhenNull() {
        Competition c1 = new Competition("Martin", "Football", Set.of(athlete));

        assertFalse(c1.equals(null), "Equals comparison with null does not returned false.");
    }
    @Test
    void testEqualsWhenCompetitionsDifferent() {
        Competition c1 = new Competition("Martin", "Football", Set.of(athlete));
        Competition c2 = new Competition("Petar", "Basketball", Set.of(athlete));

        assertFalse(c1.equals(c2), "Different competitors were not counted as different.");
    }
    @Test
    void testEqualsDifferentClass() {
        Competition c1 = new Competition("Martin", "Football", Set.of(athlete));
        String name = "Martin";
        assertFalse(c1.equals(name), "Different class comparison was not caught in equals function.");
    }
    @Test
    void testHashCode() {
        Competition c1 = new Competition("Martin", "Football", Set.of(athlete));
        Competition c2 = new Competition("Martin", "Football", Set.of(athlete));

        assertEquals(c1.hashCode(), c2.hashCode(), "HashCode does not return expected value.");
    }
    @Test
    void testEqualsSameObjects() {
        Competition c1 = new Competition("Martin", "Football", Set.of(athlete));
        Competition c2 = new Competition("Martin", "Football", Set.of(athlete));

        assertTrue(c1.equals(c2), "Same objects count as different when equals is called.");
    }
}
