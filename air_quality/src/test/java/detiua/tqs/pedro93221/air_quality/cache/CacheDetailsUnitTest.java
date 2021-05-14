package detiua.tqs.pedro93221.air_quality.cache;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class CacheDetailsUnitTest {
    CacheDetails cacheDetails;
    
    @BeforeEach
    void setUp() {
        cacheDetails = new CacheDetails("current");
    }

    @AfterEach
    void cleanUp() {

    }

    @Test
    void whenInitialized_valuesAreZero() {
        assertThat( cacheDetails.getHits(), is(0) );
        assertThat( cacheDetails.getMisses(), is(0) );
        assertThat( cacheDetails.getNRequests(), is(0) );
    }

    @Test
    void whenAddHit_thenHitNumberIncreasesByOne() {

        int initial = cacheDetails.getHits();
        cacheDetails.addHit();
        
        assertThat( cacheDetails.getHits(), is(initial+1) );
    }

    @Test
    void whenAddMiss_thenMissNumberIncreasesByOne() {

        int initial = cacheDetails.getMisses();
        cacheDetails.addMiss();
        
        assertThat( cacheDetails.getMisses(), is(initial+1) );
    }

    @Test
    void whenAddRequest_thenRequestNumberIncreasesByOne() {

        int initial = cacheDetails.getNRequests();
        cacheDetails.addRequest();
        
        assertThat( cacheDetails.getNRequests(), is(initial+1) );
    }
}
