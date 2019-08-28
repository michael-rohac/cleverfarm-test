package cz.cleverfarm.mrtest.dao;

import com.google.common.collect.Sets;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

/**
 * © 2019 Michal Rohac, All Rights Reserved.
 */
@Entity
@Table(name = "FARM")
public class Farm extends GeneratedLongIdEntity {

    @Column(name = "NAME")
    @NotBlank(message = "Farm name may not be blank")
    private String name;

    @Column(name = "NOTE")
    private String note;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "FARM_ID")
    private Set<Field> fields = Sets.newHashSet();

    public String getName() {
        return name;
    }

    public Farm setName(String name) {
        this.name = name;
        return this;
    }

    public String getNote() {
        return note;
    }

    public Farm setNote(String note) {
        this.note = note;
        return this;
    }

    public Set<Field> getFields() {
        return fields;
    }

    public Farm setFields(Set<Field> fields) {
        this.fields = fields;
        return this;
    }
}
