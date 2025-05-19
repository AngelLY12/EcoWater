package com.project.ecoWater.consumption.infraestructure;

import com.project.ecoWater.consumption.domain.WaterConsumption;
import com.project.ecoWater.consumption.domain.WaterConsumptionRepository;
import com.project.ecoWater.level.domain.WaterTankLevel;
import com.project.ecoWater.level.infraestrucutre.WaterTankLevelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class WaterConsumptionRepImpl implements WaterConsumptionRepository {
    private final WaterConsumptionJpaRep waterConsumptionJpaRep;


    @Override
    public WaterConsumption save(WaterConsumption wc) {
        WaterConsumptionEntity wcEntity=WaterConsumptionMapper.waterConsumptionToWaterConsumptionEntity(wc);
        WaterConsumptionEntity savedEntity=waterConsumptionJpaRep.save(wcEntity);
        return WaterConsumptionMapper.waterConsumptionEntityToWaterConsumption(savedEntity);
    }



    @Override
    public WaterConsumption findById(Long id) {
        return waterConsumptionJpaRep.findById(id).map(WaterConsumptionMapper::waterConsumptionEntityToWaterConsumption).orElse(null);
    }

    @Override
    public List<WaterConsumption> findAll(String email) {
        return waterConsumptionJpaRep.findConsumptionByEmail(email).stream().map(WaterConsumptionMapper::waterConsumptionEntityToWaterConsumption).collect(Collectors.toList());
    }

    @Override
    public List<WaterConsumption> findConsumptionByLocation(String email) {
        return waterConsumptionJpaRep.findConsumptionByLocation(email).stream().map(WaterConsumptionMapper::waterConsumptionEntityToWaterConsumption).collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Long id) {
        return waterConsumptionJpaRep.existsById(id);
    }

    @Override
    public Optional<WaterConsumption> findFirstMeasurementForMainTank(String email) {
        return waterConsumptionJpaRep.findFirstMeasurementForMainTank(email).map(WaterConsumptionMapper::waterConsumptionEntityToWaterConsumption);
    }

    @Override
    public List<WaterConsumption> findAllMainTankLevelsByUser(String email) {
        return waterConsumptionJpaRep.findAllMainTankLevelsByUser(email)
                .stream()
                .map(WaterConsumptionMapper::waterConsumptionEntityToWaterConsumption)
                .collect(Collectors.toList());
    }

    @Override
    public List<WaterConsumption> findAllMainTankLevelsByDate(String email, LocalDate date) {
        return waterConsumptionJpaRep.findMainTankLevelsByUserAndDate(email, date)
                .stream()
                .map(WaterConsumptionMapper::waterConsumptionEntityToWaterConsumption)
                .collect(Collectors.toList());
    }

    @Override
    public List<WaterConsumption> findAllMainTankLevelsByDateTime(String email, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return waterConsumptionJpaRep.findMainTankLevelsByUserAndDateTime(email, startDateTime, endDateTime)
                .stream()
                .map(WaterConsumptionMapper::waterConsumptionEntityToWaterConsumption)
                .collect(Collectors.toList());
    }
}
