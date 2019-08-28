package cz.cleverfarm.mrtest.rest;

import cz.cleverfarm.mrtest.dao.Farm;
import cz.cleverfarm.mrtest.repo.FarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * © 2019 Michal Rohac, All Rights Reserved.
 */
@RestController
public class FarmController {

    @Autowired
    private FarmRepository farmRepository;

    @GetMapping(UrlMappings.FARMS_ENDPOINT)
    public List<Farm> listAllFarms() {
        return farmRepository.findAll();
    }
}
