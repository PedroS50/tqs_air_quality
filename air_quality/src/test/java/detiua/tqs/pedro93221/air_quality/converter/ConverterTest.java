package detiua.tqs.pedro93221.air_quality.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.time.LocalDateTime;

class ConverterTest {
    private Converter converter;

    @BeforeEach
    void setUp() {
        converter = new Converter();
    }

    @Test
    void testConversionFromLDTToEpoch() {
        LocalDateTime timestamp = LocalDateTime.parse("2020-05-06T12:00:00");

        assertThat( converter.lDTtoEpoch(timestamp), is(1588766400L) );
    }

    @Test
    void testConversionFromEpochToLDT() {
        long timestamp = 1588766400L;

        assertThat( converter.epochtoLDT(timestamp), is(LocalDateTime.parse("2020-05-06T12:00:00")) );
    }
}
