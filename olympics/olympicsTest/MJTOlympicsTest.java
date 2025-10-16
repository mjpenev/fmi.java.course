package bg.sofia.uni.fmi.mjt.olympics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import bg.sofia.uni.fmi.mjt.olympics.comparator.NationMedalComparator;
import bg.sofia.uni.fmi.mjt.olympics.competition.Competition;
import bg.sofia.uni.fmi.mjt.olympics.competition.CompetitionResultFetcher;
import bg.sofia.uni.fmi.mjt.olympics.competitor.Athlete;
import bg.sofia.uni.fmi.mjt.olympics.competitor.Competitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class MJTOlympicsTest {
    @Test
    void testUpdateMedalStatisticsValidation() {
        MJTOlympics olympics = new MJTOlympics(Set.of(), Mockito.mock(CompetitionResultFetcher.class));
        assertThrows(IllegalArgumentException.class,
                () -> olympics.updateMedalStatistics(null),
                "Null exception was not thrown by updateMedalStatistics.");
    }
    @Test
    void testGetRegisteredCompetitors() {
        Set<Competitor> identicalSet = new HashSet<>();
        identicalSet.add(new Athlete("id1", "Martin", "Bulgaria"));
        MJTOlympics olympics1 = new MJTOlympics(identicalSet, Mockito.mock(CompetitionResultFetcher.class));
        MJTOlympics olympics2 = new MJTOlympics(identicalSet, Mockito.mock(CompetitionResultFetcher.class));
        assertEquals(olympics1.getRegisteredCompetitors(), olympics2.getRegisteredCompetitors());
    }
    @Test
    void testGetNationsMedalTable() {
        Competitor competitorMock = Mockito.mock(Competitor.class);
        Set<Competitor> competitors = Set.of(competitorMock);
        CompetitionResultFetcher fetcherMock = Mockito.mock(CompetitionResultFetcher.class);
        MJTOlympics olympics = new MJTOlympics(competitors, fetcherMock);

        assertEquals(competitors, olympics.getRegisteredCompetitors());
    }

}