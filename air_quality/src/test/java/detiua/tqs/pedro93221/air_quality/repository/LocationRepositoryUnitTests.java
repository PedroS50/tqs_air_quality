package detiua.tqs.pedro93221.air_quality.repository;

import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import detiua.tqs.pedro93221.air_quality.model.Location;

@ExtendWith(MockitoExtension.class)
class LocationRepositoryUnitTests {
    @InjectMocks
    private LocationRepository locationRepository;

    private String aveiro, invalidAddress;

    @BeforeEach
    void setUp() {
        aveiro = "Portugal, Aveiro";
        invalidAddress = "999999999999999999999999999999";
    }

    @Test
    void whenValidAddress_thenReturnValidLocation() {
        Location result = locationRepository.getLocation(aveiro);

        assertThat( result.getAddress(), is(aveiro) );
    }

    @Test
    void whenInvalidAddress_thenThrowResponseStatusException() {
        assertThrows( ResponseStatusException.class, () -> { locationRepository.getLocation(invalidAddress); } );
    }

}
