package cz.cleverfarm.mrtest.rest;

import cz.cleverfarm.mrtest.dao.Farm;
import cz.cleverfarm.mrtest.dto.FarmDto;
import cz.cleverfarm.mrtest.repo.FarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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


    private FarmDto addFarmLinks(FarmDto farmDto) {
        try {
            farmDto.add(linkTo(FarmController.class, FarmController.class.getMethod("getFarm", Long.class), farmDto.getEntityId()).withSelfRel());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return farmDto;
    }

}
