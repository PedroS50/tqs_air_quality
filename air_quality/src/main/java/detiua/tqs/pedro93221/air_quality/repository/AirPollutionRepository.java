package detiua.tqs.pedro93221.air_quality.repository;

import detiua.tqs.pedro93221.air_quality.model.AirPollution;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface AirPollutionRepository extends JpaRepository<AirPollution, Long>  {

}