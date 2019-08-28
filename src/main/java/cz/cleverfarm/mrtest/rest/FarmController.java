package cz.cleverfarm.mrtest.rest;

import com.google.common.collect.Lists;
import cz.cleverfarm.mrtest.dao.Farm;
import cz.cleverfarm.mrtest.dao.Field;
import cz.cleverfarm.mrtest.dao.FieldBoundaryPoint;
import cz.cleverfarm.mrtest.dto.BoundaryPoint;
import cz.cleverfarm.mrtest.dto.FarmDto;
import cz.cleverfarm.mrtest.dto.FieldDto;
import cz.cleverfarm.mrtest.repo.FarmRepository;
import cz.cleverfarm.mrtest.repo.FieldBoundaryPointRepository;
import cz.cleverfarm.mrtest.repo.FieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;
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

    @Autowired
    private FieldBoundaryPointRepository pointRepository;

    @GetMapping(UrlMappings.FARMS_ENDPOINT)
    public List<FarmDto> listAllFarms() {
        return farmRepository.findAll().stream()
                .map(farm -> addFarmLinks(conversionService.convert(farm, FarmDto.class)))
                .collect(Collectors.toList());
    }

    @Transactional
    @PostMapping(UrlMappings.FARMS_ENDPOINT)
    public ResponseEntity createFarm(
            @RequestBody @Valid FarmDto farmDto,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        Farm farm = farmRepository.save(new Farm()
                .setName(farmDto.getName())
                .setNote(farmDto.getNote())
        );

        return new ResponseEntity(conversionService.convert(farm, FarmDto.class), HttpStatus.CREATED);
    }

    @GetMapping(UrlMappings.FARM_ENDPOINT)
    public FarmDto getFarm(@PathVariable(UrlMappings.FARM_ID) Long farmId) {
        Optional<Farm> optFarm = farmRepository.findById(farmId);
        return optFarm.isPresent() ? addFarmLinks(conversionService.convert(optFarm.get(), FarmDto.class)) : null;
    }

    @Transactional
    @PutMapping(UrlMappings.FARM_ENDPOINT)
    public ResponseEntity updateFarm(
            @PathVariable(UrlMappings.FARM_ID) Long farmId,
            @RequestBody @Valid FarmDto farmDto,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        Optional<Farm> optFarm = farmRepository.findById(farmId);
        if (!optFarm.isPresent()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        Farm farm = optFarm.get();
        farm.setName(farmDto.getName());
        farm.setNote(farmDto.getNote());
        farm = farmRepository.save(farm);
        return new ResponseEntity(conversionService.convert(farm, FarmDto.class), HttpStatus.CREATED);
    }

    @Transactional
    @DeleteMapping(UrlMappings.FARM_ENDPOINT)
    public ResponseEntity deleteFarm(@PathVariable(UrlMappings.FARM_ID) Long farmId) {
        Optional<Farm> optFarm = farmRepository.findById(farmId);
        if (!optFarm.isPresent()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        farmRepository.delete(optFarm.get());
        return ResponseEntity.ok().build();
    }

    @GetMapping(UrlMappings.FIELDS_ENDPOINT)
    public List<FieldDto> listFarmFields(@PathVariable(UrlMappings.FARM_ID) Long farmId) {
        Optional<Farm> optFarm = farmRepository.findById(farmId);
        return !optFarm.isPresent() ? Lists.newArrayList() : optFarm.get().getFields().stream()
                .map(field -> addFieldLinks(farmId, addFieldLinks(farmId, conversionService.convert(field, FieldDto.class))))
                .collect(Collectors.toList());
    }

    @Transactional
    @PostMapping(UrlMappings.FIELDS_ENDPOINT)
    public ResponseEntity addFarmField(
            @PathVariable(UrlMappings.FARM_ID) Long farmId,
            @RequestBody @Valid FieldDto fieldDto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        Optional<Farm> optFarm = farmRepository.findById(farmId);
        if (!optFarm.isPresent()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        // TODO - perform extra polygon validation - i.e. check boundary points validity

        Field farmField = fieldRepository.save(new Field()
                .setFarm(optFarm.get())
                .setName(fieldDto.getName()));

        for (int idx = 0; idx < fieldDto.getBoundary().size(); idx++) {
            BoundaryPoint point = fieldDto.getBoundary().get(idx);
            farmField.getBoundary().add(
                    new FieldBoundaryPoint(new FieldBoundaryPoint.Id()
                            .setField(farmField)
                            .setOrder(idx)
                    ).setX(point.getX()).setY(point.getY())
            );
        }
        pointRepository.saveAll(farmField.getBoundary());
        return new ResponseEntity(addFieldLinks(farmId, conversionService.convert(farmField, FieldDto.class)), HttpStatus.CREATED);
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

    @Transactional
    @PutMapping(UrlMappings.FIELD_ENDPOINT)
    public ResponseEntity updateFarmField(
            @PathVariable(UrlMappings.FARM_ID) Long farmId,
            @PathVariable(UrlMappings.FIELD_ID) Long fieldId,
            @RequestBody @Valid FieldDto fieldDto,
            BindingResult bindingResult
    ) {
        Optional<Field> optField = fieldRepository.findById(fieldId);
        if (!optField.isPresent() || !optField.get().getFarm().getId().equals(farmId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Field farmField = optField.get();

        pointRepository.deleteAll(farmField.getBoundary());
        farmField.setName(fieldDto.getName());
        List<FieldBoundaryPoint> newBoundary = Lists.newArrayList();

        for (int idx = 0; idx < fieldDto.getBoundary().size(); idx++) {
            BoundaryPoint point = fieldDto.getBoundary().get(idx);
            newBoundary.add(
                    new FieldBoundaryPoint(new FieldBoundaryPoint.Id()
                            .setField(farmField)
                            .setOrder(idx)
                    ).setX(point.getX()).setY(point.getY())
            );
        }
        farmField.setBoundary(pointRepository.saveAll(newBoundary));
        return ResponseEntity.ok(addFieldLinks(farmId, conversionService.convert(fieldRepository.save(farmField), FieldDto.class)));
    }

    @Transactional
    @DeleteMapping(UrlMappings.FIELD_ENDPOINT)
    public ResponseEntity deleteFarmField(
            @PathVariable(UrlMappings.FARM_ID) Long farmId,
            @PathVariable(UrlMappings.FIELD_ID) Long fieldId
    ) {
        Optional<Field> optField = fieldRepository.findById(fieldId);
        if (!optField.isPresent() || !optField.get().getFarm().getId().equals(farmId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        fieldRepository.delete(optField.get());
        return ResponseEntity.ok().build();
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
