package detiua.tqs.pedro93221.air_quality.cache;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CacheDetailsUnitTest {
    CacheDetails cacheDetails;
    
    @BeforeEach
    public void setUp() {
        cacheDetails = new CacheDetails("current");
    }

    @AfterEach
    public void cleanUp() {

    }

    @Test
    public void whenInitialized_valuesAreZero() {
        assertThat( cacheDetails.getHits(), is(0) );
        assertThat( cacheDetails.getMisses(), is(0) );
        assertThat( cacheDetails.getNRequests(), is(0) );
    }

    @Test
    public void whenAddHit_thenHitNumberIncreasesByOne() {

        int initial = cacheDetails.getHits();
        cacheDetails.addHit();
        
        assertThat( cacheDetails.getHits(), is(initial+1) );
    }

    @Test
    public void whenAddMiss_thenMissNumberIncreasesByOne() {

        int initial = cacheDetails.getMisses();
        cacheDetails.addMiss();
        
        assertThat( cacheDetails.getMisses(), is(initial+1) );
    }

    @Test
    public void whenAddRequest_thenRequestNumberIncreasesByOne() {

        int initial = cacheDetails.getNRequests();
        cacheDetails.addRequest();
        
        assertThat( cacheDetails.getNRequests(), is(initial+1) );
    }
}
