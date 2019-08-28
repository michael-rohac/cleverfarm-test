package cz.cleverfarm.mrtest.dao;

import cz.cleverfarm.mrtest.repo.FarmRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * © 2019 Michal Rohac, All Rights Reserved.
 */
@Component
public class DBInitializer implements InitializingBean {

    @Autowired
    private FarmRepository farmRepository;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (farmRepository.count() == 0) {
            farmRepository.save(
                    new Farm()
                            .setName("Testing Farm")
                            .setNote("Farm for demonstrating purpose initialized automatically only when DB is empty")
            );
        }
    }
}
