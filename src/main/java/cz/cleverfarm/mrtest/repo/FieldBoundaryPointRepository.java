package cz.cleverfarm.mrtest.repo;

import cz.cleverfarm.mrtest.dao.FieldBoundaryPoint;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * © 2019 Michal Rohac, All Rights Reserved.
 */
public interface FieldBoundaryPointRepository extends JpaRepository<FieldBoundaryPoint, FieldBoundaryPoint.Id> {
}
