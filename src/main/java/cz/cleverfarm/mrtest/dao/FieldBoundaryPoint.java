package cz.cleverfarm.mrtest.dao;

import javax.persistence.*;
import java.io.Serializable;

/**
 * © 2019 Michal Rohac, All Rights Reserved.
 */
@Entity
@Table(name = "FIELD_BOUNDARY_POINT")
public class FieldBoundaryPoint extends AbstractEntity<FieldBoundaryPoint.Id> {

    @EmbeddedId
    private Id id;

    @Column(nullable = false, columnDefinition = "decimal(11,8)")
    private Double lon;

    @Column(nullable = false, columnDefinition = "decimal(10,8)")
    private Double lat;

    @Override
    public Id getId() {
        return id;
    }

    @Override
    public void setId(Id id) {
        this.id = id;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    @Embeddable
    public static class Id implements Serializable {

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "FIELD_ID", nullable = false)
        private Field field;

        @Column(name = "POINT_ORDER", nullable = false)
        private Integer order;

        public Field getField() {
            return field;
        }

        public void setField(Field field) {
            this.field = field;
        }

        public Integer getOrder() {
            return order;
        }

        public void setOrder(Integer order) {
            this.order = order;
        }
    }

}
