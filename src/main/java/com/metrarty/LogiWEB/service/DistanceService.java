package com.metrarty.LogiWEB.service;

import com.metrarty.LogiWEB.boundary.model.CityDto;
import com.metrarty.LogiWEB.boundary.model.DistanceDto;
import com.metrarty.LogiWEB.repository.CityRepository;
import com.metrarty.LogiWEB.repository.DistanceRepository;
import com.metrarty.LogiWEB.repository.entity.City;
import com.metrarty.LogiWEB.repository.entity.Distance;
import com.metrarty.LogiWEB.service.mapper.DistanceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Log4j2
public class DistanceService {
    private final DistanceRepository distanceRepository;
    private final DistanceMapper distanceMapper;
    private final CityRepository cityRepository;

    public Distance createDistance(Long idCity1, Long idCity2, Long distance) {
        log.info("DistanceService.createDistance was called with {} {} {}", idCity1, idCity2, distance);
        Distance entity = new Distance();
        entity.setCity1(cityRepository.getOne(idCity1).getCityName());
        entity.setCity2(cityRepository.getOne(idCity2).getCityName());
        entity.setDistance(distance);
        distanceRepository.save(entity);
        return entity;
    }

    public List<DistanceDto> findAllDistances() {
        log.info("DistanceService.findAllDistances was called");
        List<Distance> entities = distanceRepository.findAll();
        List<DistanceDto> result = new ArrayList<>();
        for (Distance entity : entities) {
            DistanceDto distanceDto = distanceMapper.toDto(entity);
            result.add(distanceDto);
        }
        return result;
    }
}
