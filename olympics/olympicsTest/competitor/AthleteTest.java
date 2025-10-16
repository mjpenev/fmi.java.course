package bg.sofia.uni.fmi.mjt.olympics.competitor;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class AthleteTest {
    @Test
    void testAddMedal() {
        Medal medal = Medal.GOLD;
        Athlete athlete1 = new Athlete("id1", "Martin", "Bulgaria");
        athlete1.addMedal(medal);
        assertFalse(athlete1.getMedals().isEmpty(), "Add medal func didn't properly add medals.");
    }
    @Test
    void testValidateMedal() {
        Athlete athlete1 = new Athlete("id1", "Martin", "Bulgaria");
        assertThrows(IllegalArgumentException.class,
                () -> athlete1.addMedal(null),
                "Validate medal didn't throw null exception.");
    }
    @Test
    void testGetIdentifier() {
        Athlete athlete1 = new Athlete("id1", "Martin", "Bulgaria");
        assertEquals("id1", athlete1.getIdentifier());
    }
    @Test
    void testGetName() {
        Athlete athlete1 = new Athlete("id1", "Martin", "Bulgaria");
        assertEquals("Martin", athlete1.getName());
    }
    @Test
    void testGetNationality() {
        Athlete athlete1 = new Athlete("id1", "Martin", "Bulgaria");
        assertEquals("Bulgaria", athlete1.getNationality());
    }
    @Test
    void testGetMedals() {
        Athlete athlete1 = new Athlete("id1", "Martin", "Bulgaria");
        athlete1.addMedal(Medal.GOLD);
        assertEquals(Set.of(Medal.GOLD), athlete1.getMedals());
    }
    @Test
    void testHashCode() {
        Athlete athlete1 = new Athlete("id1", "Martin", "Bulgaria");
        Athlete athlete2 = new Athlete("id2", "Martin", "Bulgaria");
        assertEquals(athlete1.hashCode(), athlete2.hashCode());
    }
    @Test
    void testEqualsTrue() {
        Athlete athlete1 = new Athlete("id1", "Martin", "Bulgaria");
        assertTrue(athlete1.equals(athlete1), "Failed equals for self.");
    }
    @Test
    void testEqualsFalse() {
        Athlete athlete1 = new Athlete("id1", "Martin", "Bulgaria");
        assertFalse(athlete1.equals(null), "Failed equals for null.");
    }
    @Test
    void testEqualsFalseForClass() {
        Athlete athlete1 = new Athlete("id1", "Martin", "Bulgaria");
        String athlete2 = "Pesho";
        assertFalse(athlete1.equals(athlete2), "Failed equals for missmatch class.");
    }
    @Test
    void testEqualsForNotEqualCompetitors() {
        Athlete athlete1 = new Athlete("id1", "Martin", "Bulgaria");
        Athlete athlete2 = new Athlete("id2", "Petar", "Bulgaria");
        assertFalse(athlete1.equals(athlete2));
    }
    @Test
    void testEqualsForEqualCompetitors() {
        Athlete athlete1 = new Athlete("id1", "Martin", "Bulgaria");
        Athlete athlete2 = new Athlete("id1", "Martin", "Bulgaria");
        assertTrue(athlete1.equals(athlete2));
    }

}
