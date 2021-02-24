package com.metrarty.LogiWEB.service;

import com.metrarty.LogiWEB.boundary.model.DistanceDto;
import com.metrarty.LogiWEB.repository.DistanceRepository;
import com.metrarty.LogiWEB.repository.entity.Distance;
import com.metrarty.LogiWEB.service.exception.DistanceNotFoundException;
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

    public Distance createDistance(DistanceDto distanceDto) {
        log.info("DistanceService.createDistance was called with {}", distanceDto);
        Distance entity = distanceMapper.toEntity(distanceDto);
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

    public DistanceDto editDistance(DistanceDto distanceDto, Long id) {
        log.info("DistanceService.editDistance was called with {}", id);
        Distance distance = distanceMapper.toEntity(distanceDto);
        Distance entity = distanceRepository.findById(id)
                .orElseThrow(()-> new DistanceNotFoundException("Distance with ID " + id + " is not found"));
        distance.setId(entity.getId());
        Distance saved = distanceRepository.save(distance);
        return distanceMapper.toDto(saved);
    }

    public void deleteDistanceById(Long id) {
        log.info("DistanceService.deleteDistanceById was called with {}", id);
        distanceRepository.deleteById(id);
    }
}
