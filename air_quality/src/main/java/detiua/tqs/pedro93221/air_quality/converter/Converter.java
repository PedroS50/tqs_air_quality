package detiua.tqs.pedro93221.air_quality.converter;

import java.time.ZoneOffset;
import java.time.LocalDateTime;

public class Converter {
    public long lDTtoEpoch(LocalDateTime timestamp) {
        return timestamp.toEpochSecond(ZoneOffset.UTC);
    }

    public LocalDateTime epochtoLDT(long timestamp) {
        return LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.UTC);
    }
}