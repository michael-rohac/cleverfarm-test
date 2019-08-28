package cz.cleverfarm.mrtest.dao;

import com.google.common.base.Preconditions;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Holds coordinates expected to be in S-JTSK coordinate system.
 *
 * © 2019 Michal Rohac, All Rights Reserved.
 */
@Entity
@Table(name = "FIELD_BOUNDARY_POINT")
public class FieldBoundaryPoint extends AbstractEntity<FieldBoundaryPoint.Id> {

    @EmbeddedId
    private Id id;

    @Column(name = "SJTSK_X", nullable = false, columnDefinition = "decimal(10,3)")
    private Double x;

    @Column(name = "SJTSK_Y", nullable = false, columnDefinition = "decimal(10,3)")
    private Double y;

    public FieldBoundaryPoint() {
    }

    public FieldBoundaryPoint(Id id) {
        this.id = id;
    }

    @Override
    public Id getId() {
        return id;
    }

    @Override
    public void setId(Id id) {
        this.id = id;
    }

    public Double getX() {
        return x;
    }

    public FieldBoundaryPoint setX(Double x) {
        this.x = Preconditions.checkNotNull(x);
        return this;
    }

    public Double getY() {
        return y;
    }

    public FieldBoundaryPoint setY(Double y) {
        this.y = Preconditions.checkNotNull(y);
        return this;
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

        public Id setField(Field field) {
            this.field = field;
            return this;
        }

        public Integer getOrder() {
            return order;
        }

        public Id setOrder(Integer order) {
            this.order = order;
            return this;
        }
    }

}
