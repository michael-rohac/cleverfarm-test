package cz.cleverfarm.mrtest.dao;

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

    @OneToMany(mappedBy = "id.field")
    @OrderBy("POINT_ORDER ASC")
    private List<FieldBoundaryPoint> geometry;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    public List<FieldBoundaryPoint> getGeometry() {
        return geometry;
    }

    public void setGeometry(List<FieldBoundaryPoint> geometry) {
        this.geometry = geometry;
    }
}
