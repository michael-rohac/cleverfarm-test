package cz.cleverfarm.mrtest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

/**
 * © 2019 Michal Rohac, All Rights Reserved.
 */
public class FieldDto extends ResourceSupport {

    @JsonProperty("id")
    private Long entityId;

    private String name;

    private Double area;

    private final List<BoundaryPoint> boundary = Lists.newArrayList();

    public FieldDto() {
    }

    public FieldDto(Long entityId) {
        this.entityId = entityId;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getName() {
        return name;
    }

    public FieldDto setName(String name) {
        this.name = name;
        return this;
    }

    public Double getArea() {
        return area;
    }

    public FieldDto setArea(Double area) {
        this.area = area;
        return this;
    }

    public List<BoundaryPoint> getBoundary() {
        return boundary;
    }
}
