package cz.cleverfarm.mrtest.converters;

import cz.cleverfarm.mrtest.dao.Farm;
import cz.cleverfarm.mrtest.dao.Field;
import cz.cleverfarm.mrtest.dto.FarmDto;
import cz.cleverfarm.mrtest.dto.FieldDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * © 2019 Michal Rohac, All Rights Reserved.
 */
@Component
public class FarmToFarmDto implements Converter<Farm, FarmDto> {

    @Override
    public FarmDto convert(Farm farm) {
        FarmDto result = new FarmDto()
                .setEntityId(farm.getId())
                .setName(farm.getName())
                .setNote(farm.getNote());
        result.getFields().addAll(farm.getFields().stream()
                .map(field -> new FieldDto()
                        .setName(field.getName())
                )
                .collect(Collectors.toList())
        );
        return result;

    }
}
