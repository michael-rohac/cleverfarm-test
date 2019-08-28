package cz.cleverfarm.mrtest.dao;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * © 2019 Michal Rohac, All Rights Reserved.
 */
@MappedSuperclass
public abstract class GeneratedLongIdEntity extends AbstractEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", columnDefinition = "numeric(10,0)")
    @NotNull
    private Long id;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

}
