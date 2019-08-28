package cz.cleverfarm.mrtest.dao;

import com.google.common.collect.Lists;
import cz.cleverfarm.mrtest.repo.FarmRepository;
import cz.cleverfarm.mrtest.repo.FieldBoundaryPointRepository;
import cz.cleverfarm.mrtest.repo.FieldRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * © 2019 Michal Rohac, All Rights Reserved.
 */
@Component
@SuppressWarnings("unchecked")
public class DBInitializer implements InitializingBean {

    private static final String SAMPLE_DATA_FILE = "sample_data.csv";
    private static final int IDX_FARM = 0;
    private static final int IDX_FIELD = 1;
    private static final int IDX_SJTSK_Y = 2;
    private static final int IDX_SJTSK_X = 3;

    @Autowired
    private FarmRepository farmRepository;

    @Autowired
    private FieldRepository fieldRepository;

    @Autowired
    private FieldBoundaryPointRepository pointRepository;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (farmRepository.count() == 0) {
            populateSampleData();
        }
    }

    private static final List<List<Double>> FIELD_COORDINATES = Lists.<List<Double>>newArrayList(
            Lists.<Double>newArrayList(575866.54, 1156567.08, 575848.31, 1156552.85, 575838.46, 1156565.46, 575857.72, 1156580.5),
            Lists.<Double>newArrayList(575857.72, 1156580.5, 575838.46, 1156565.46, 575828.62, 1156578.08, 575848.15, 1156593.31),
            Lists.<Double>newArrayList(575837.42, 1156605.25, 575848.15, 1156593.31, 575828.62, 1156578.08, 575818.77, 1156590.69)
    );

    private void populateSampleData() {
        Farm farm = farmRepository.save(new Farm()
                .setName("Testing Farm")
                .setNote("Farm for demonstrating purpose initialized automatically only when DB is empty")
        );

        for (int fieldIdx = 0; fieldIdx < FIELD_COORDINATES.size(); fieldIdx++) {
            Field field = fieldRepository.save(new Field()
                    .setFarm(farm)
                    .setName("Testing field " + (fieldIdx + 1))
            );
            for (int pointIdx = 0; pointIdx < FIELD_COORDINATES.get(fieldIdx).size(); pointIdx += 2) {
                Double y = FIELD_COORDINATES.get(fieldIdx).get(pointIdx);
                Double x = FIELD_COORDINATES.get(fieldIdx).get(pointIdx + 1);
                pointRepository.save(new FieldBoundaryPoint(new FieldBoundaryPoint.Id()
                                .setField(field)
                                .setOrder(pointIdx / 2)
                        ).setX(x).setY(y)
                );
            }
        }
    }

}
