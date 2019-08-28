package cz.cleverfarm.mrtest.converters;

import cz.cleverfarm.mrtest.dao.Field;
import cz.cleverfarm.mrtest.dto.BoundaryPoint;
import cz.cleverfarm.mrtest.dto.FieldDto;
import cz.cleverfarm.mrtest.service.api.GeometryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * © 2019 Michal Rohac, All Rights Reserved.
 */
@Component
public class FieldToFieldDto implements Converter<Field, FieldDto> {

    @Autowired
    private GeometryService geometryService;

    @Override
    public FieldDto convert(Field field) {
        FieldDto result = new FieldDto(field.getId()).setName(field.getName());
        result.getBoundary().addAll(
                field.getBoundary().stream()
                        .map(point -> new BoundaryPoint(point.getX(), point.getY()))
                        .collect(Collectors.toList())
        );
        result.setArea(geometryService.calculateArea(result.getBoundary()));
        return result;
    }
}
