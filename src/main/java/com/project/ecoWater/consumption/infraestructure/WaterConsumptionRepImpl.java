package com.project.ecoWater.consumption.infraestructure;

import com.project.ecoWater.consumption.domain.WaterConsumption;
import com.project.ecoWater.consumption.domain.WaterConsumptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
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
    public List<WaterConsumption> findAll() {
        return waterConsumptionJpaRep.findAll().stream().map(WaterConsumptionMapper::waterConsumptionEntityToWaterConsumption).collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Long id) {
        return waterConsumptionJpaRep.existsById(id);
    }
}
