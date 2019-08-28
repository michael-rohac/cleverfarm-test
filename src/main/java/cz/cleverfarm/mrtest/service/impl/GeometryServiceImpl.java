package cz.cleverfarm.mrtest.service.impl;

import cz.cleverfarm.mrtest.dto.BoundaryPoint;
import cz.cleverfarm.mrtest.service.api.GeometryService;
import org.geotools.geometry.jts.GeometryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * © 2019 Michal Rohac, All Rights Reserved.
 */
@Service
public class GeometryServiceImpl implements GeometryService {

    public double calculateArea(List<BoundaryPoint> orderedBoundaryPoints) {
        if (orderedBoundaryPoints == null || !(orderedBoundaryPoints.size() > 0)) return 0;

        double[] coordinates = new double[orderedBoundaryPoints.size() * 2];
        GeometryBuilder gb = new GeometryBuilder();

        for (int idx = 0; idx < orderedBoundaryPoints.size(); idx++) {
            BoundaryPoint point = orderedBoundaryPoints.get(idx);
            coordinates[idx * 2] = point.getX();
            coordinates[idx * 2 + 1] = point.getY();
        }

        return gb.polygon(coordinates).getArea();
    }
}
