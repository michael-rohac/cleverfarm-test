package cz.cleverfarm.mrtest.repo;

import cz.cleverfarm.mrtest.dao.Farm;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * © 2019 Michal Rohac, All Rights Reserved.
 */
public interface FarmRepository extends JpaRepository<Farm, Long> {
}
