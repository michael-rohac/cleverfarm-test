package cz.cleverfarm.mrtest.rest;

import com.google.common.collect.Lists;
import cz.cleverfarm.mrtest.dao.Farm;
import cz.cleverfarm.mrtest.dao.Field;
import cz.cleverfarm.mrtest.dto.FarmDto;
import cz.cleverfarm.mrtest.dto.FieldDto;
import cz.cleverfarm.mrtest.repo.FarmRepository;
import cz.cleverfarm.mrtest.repo.FieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * © 2019 Michal Rohac, All Rights Reserved.
 */
@RestController
public class FarmController {

    @Autowired
    private FarmRepository farmRepository;

    @Autowired
    private FieldRepository fieldRepository;

    @Autowired
    private ConversionService conversionService;

    @GetMapping(UrlMappings.FARMS_ENDPOINT)
    public List<FarmDto> listAllFarms() {
        return farmRepository.findAll().stream()
                .map(farm -> addFarmLinks(conversionService.convert(farm, FarmDto.class)))
                .collect(Collectors.toList());
    }

    @GetMapping(UrlMappings.FARM_ENDPOINT)
    public FarmDto getFarm(@PathVariable(UrlMappings.FARM_ID) Long farmId) {
        Optional<Farm> optFarm = farmRepository.findById(farmId);
        return optFarm.isPresent() ? conversionService.convert(optFarm.get(), FarmDto.class) : null;
    }

    @GetMapping(UrlMappings.FIELDS_ENDPOINT)
    public List<FieldDto> listFarmFields(@PathVariable(UrlMappings.FARM_ID) Long farmId) {
        Optional<Farm> optFarm = farmRepository.findById(farmId);
        return !optFarm.isPresent() ? Lists.newArrayList() : optFarm.get().getFields().stream()
                .map(field -> addFieldLinks(farmId, addFieldLinks(farmId, conversionService.convert(field, FieldDto.class))))
                .collect(Collectors.toList());
    }

    @GetMapping(UrlMappings.FIELD_ENDPOINT)
    public FieldDto getFarmField(
            @PathVariable(UrlMappings.FARM_ID) Long farmId,
            @PathVariable(UrlMappings.FIELD_ID) Long fieldId
    ) {
        Optional<Field> optField = fieldRepository.findById(fieldId);
        if (!optField.isPresent() || !optField.get().getFarm().getId().equals(farmId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return addFieldLinks(farmId, conversionService.convert(optField.get(), FieldDto.class));
    }

    private FarmDto addFarmLinks(FarmDto farmDto) {
        try {
            farmDto.add(linkTo(FarmController.class, FarmController.class.getMethod("getFarm", Long.class), farmDto.getEntityId()).withSelfRel());
            for (FieldDto farmField : farmDto.getFields()) {
                farmDto.add(linkTo(FarmController.class, FarmController.class.getMethod("getFarmField", Long.class, Long.class),
                        farmDto.getEntityId(), farmField.getEntityId()).withRel("farmField"));
            }

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return farmDto;
    }

    private FieldDto addFieldLinks(Long farmId, FieldDto fieldDto) {
        try {
            fieldDto.add(linkTo(FarmController.class, FarmController.class.getMethod("getFarmField", Long.class, Long.class),
                    farmId, fieldDto.getEntityId()).withSelfRel());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return fieldDto;
    }

}
