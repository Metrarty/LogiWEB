package com.metrarty.LogiWEB.service.mapper;

import com.metrarty.LogiWEB.boundary.model.DistanceDto;
import com.metrarty.LogiWEB.repository.entity.Distance;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class DistanceMapper {
    public DistanceDto toDto(@NonNull Distance distance) {
        log.info("DistanceMapper.toDto was called with {}", distance);
        DistanceDto entity = new DistanceDto();
        entity.setId(distance.getId());
      //  entity.setCity1(distance.getCity1());
      //  entity.setCity2(distance.getCity2());
        entity.setDistance(distance.getDistance());
        return entity;
    }

    public Distance toEntity(@NonNull DistanceDto distanceDto) {
        log.info("DistanceMapper.toEntity was called with {}", distanceDto);
        Distance entity = new Distance();
        entity.setId(distanceDto.getId());
      //  entity.setCity1(distanceDto.getCity1());
      //  entity.setCity2(distanceDto.getCity2());
        entity.setDistance(distanceDto.getDistance());
        return entity;
    }
}
