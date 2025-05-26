package com.project.ecoWater.filling.infraestructure;

import com.project.ecoWater.filling.domain.TankFilling;
import com.project.ecoWater.filling.domain.TankFillingRepository;
import com.project.ecoWater.level.infraestrucutre.WaterTankLevelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class TankFillingRepImpl implements TankFillingRepository {
    private final TankFillingJpaRep tankFillingJpaRep;
    @Override
    public TankFilling save(TankFilling tankFilling) {
        TankFillingEntity tankFillingEntity= TankFillingMapper.tankFillingToTankFillingEntity(tankFilling);
        TankFillingEntity savedTankFilling= tankFillingJpaRep.save(tankFillingEntity);
        return TankFillingMapper.tankFillingEntityToTankFilling(savedTankFilling);
    }

    @Override
    public TankFilling findById(Long id) {
        return tankFillingJpaRep.findById(id).map(TankFillingMapper::tankFillingEntityToTankFilling).orElse(null);
    }

    @Override
    public List<TankFilling> findAll() {
        return tankFillingJpaRep.findAll().stream().map(TankFillingMapper::tankFillingEntityToTankFilling).collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Long id) {
        return tankFillingJpaRep.existsById(id);
    }

    @Override
    public List<TankFilling> findMainTankFillingsByUserAndDate(String email, LocalDate date) {
        return tankFillingJpaRep.findMainTankFillingsByUserAndDate(email, date)
                .stream()
                .map(TankFillingMapper::tankFillingEntityToTankFilling)
                .collect(Collectors.toList());
    }

    @Override
    public List<TankFilling> findMainTankFillingsByUserAndDateTime(String email, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return tankFillingJpaRep.findMainTankFillingsByUserAndDateTime(email, startDateTime, endDateTime)
                .stream()
                .map(TankFillingMapper::tankFillingEntityToTankFilling)
                .collect(Collectors.toList());
    }

    @Override
    public List<TankFilling> findByEmailAndDate(String email, LocalDateTime startedDate) {
        return tankFillingJpaRep
                .findTankFillingByEmailAndDate(email,startedDate)
                .stream()
                .map(TankFillingMapper::tankFillingEntityToTankFilling)
                .collect(Collectors.toList());
    }

    @Override
    public List<TankFilling> findByEmailAndCurrent(String email) {
        return tankFillingJpaRep
                .findTankFillingByEmailAndCurrent(email)
                .stream()
                .map(TankFillingMapper::tankFillingEntityToTankFilling)
                .collect(Collectors.toList());
    }
}
