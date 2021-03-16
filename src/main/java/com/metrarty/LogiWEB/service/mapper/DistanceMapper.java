package com.metrarty.LogiWEB.service.mapper;

import com.metrarty.LogiWEB.boundary.model.DistanceDto;
import com.metrarty.LogiWEB.repository.entity.Distance;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Distance mapper.
 */
@Component
@Log4j2
public class DistanceMapper {

    private final CityMapper cityMapper;

    @Autowired
    public DistanceMapper(CityMapper cityMapper) {
        this.cityMapper = cityMapper;
    }

    /**
     * Transfers data from distance to distance DTO
     * @param distance distance
     * @return distance DTO
     */
    public DistanceDto toDto(@NonNull Distance distance) {
        log.info("DistanceMapper.toDto was called with {}", distance);
        DistanceDto dto = new DistanceDto();
        dto.setId(distance.getId());
        dto.setCity1(cityMapper.toDto(distance.getCity1()));
        dto.setCity2(cityMapper.toDto(distance.getCity2()));
        dto.setDistance(distance.getDistance());
        return dto;
    }

    /**
     * Transfers data from distance DTO to distance
     * @param distanceDto distance DTO
     * @return distance
     */
    public Distance toEntity(@NonNull DistanceDto distanceDto) {
        log.info("DistanceMapper.toEntity was called with {}", distanceDto);
        Distance entity = new Distance();
        entity.setId(distanceDto.getId());
        entity.setCity1(cityMapper.toEntity(distanceDto.getCity1()));
        entity.setCity2(cityMapper.toEntity(distanceDto.getCity2()));
        entity.setDistance(distanceDto.getDistance());
        return entity;
    }
}
