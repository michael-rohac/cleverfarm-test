package cz.cleverfarm.mrtest.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

/**
 * © 2019 Michal Rohac, All Rights Reserved.
 */
public class FarmDto extends ResourceSupport {

    @JsonProperty("id")
    private Long entityId;

    private String name;

    private String note;

    private final List<FieldDto> fields = Lists.newArrayList();

    public Long getEntityId() {
        return entityId;
    }

    public FarmDto setEntityId(Long entityId) {
        this.entityId = entityId;
        return this;
    }

    public String getName() {
        return name;
    }

    public FarmDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getNote() {
        return note;
    }

    public FarmDto setNote(String note) {
        this.note = note;
        return this;
    }

    public List<FieldDto> getFields() {
        return fields;
    }

}
