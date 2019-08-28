package cz.cleverfarm.mrtest.service.api;

import cz.cleverfarm.mrtest.dto.BoundaryPoint;

import java.util.List;

/**
 * © 2019 Michal Rohac, All Rights Reserved.
 */
public interface GeometryService {
    double calculateArea(List<BoundaryPoint> orderedBoundaryPoints);
}
