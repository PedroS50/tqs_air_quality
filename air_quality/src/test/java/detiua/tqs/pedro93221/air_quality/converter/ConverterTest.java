package detiua.tqs.pedro93221.air_quality.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.time.LocalDateTime;

public class ConverterTest {
    private Converter converter;

    @BeforeEach
    public void setUp() {
        converter = new Converter();
    }

    @Test
    public void testConversionFromLDTToEpoch() {
        LocalDateTime timestamp = LocalDateTime.parse("2020-05-06T12:00:00");

        assertThat( converter.LDTtoEpoch(timestamp), is(1588766400L) );
    }

    @Test
    public void testConversionFromEpochToLDT() {
        long timestamp = 1588766400L;

        assertThat( converter.EpochtoLDT(timestamp), is(LocalDateTime.parse("2020-05-06T12:00:00")) );
    }
}
