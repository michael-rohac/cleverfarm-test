package cz.cleverfarm.mrtest.dao;

import com.google.common.collect.Lists;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * © 2019 Michal Rohac, All Rights Reserved.
 */
@Entity
@Table(name = "FIELD")
public class Field extends GeneratedLongIdEntity {

    @Column(name = "NAME")
    @NotBlank(message = "Field name may not be blank")
    private String name;

    @ManyToOne
    @JoinColumn(name = "FARM_ID")
    private Farm farm;

    @OneToMany(mappedBy = "id.field", cascade = CascadeType.REMOVE)
    @OrderBy("POINT_ORDER ASC")
    private List<FieldBoundaryPoint> boundary = Lists.newArrayList();

    public String getName() {
        return name;
    }

    public Field setName(String name) {
        this.name = name;
        return this;
    }

    public Farm getFarm() {
        return farm;
    }

    public Field setFarm(Farm farm) {
        this.farm = farm;
        return this;
    }

    public List<FieldBoundaryPoint> getBoundary() {
        return boundary;
    }

    public void setBoundary(List<FieldBoundaryPoint> boundary) {
        this.boundary = boundary;
    }
}
