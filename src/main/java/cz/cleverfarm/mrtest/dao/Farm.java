package cz.cleverfarm.mrtest.dao;

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

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "FARM_ID")
    private Set<Field> fields;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Set<Field> getFields() {
        return fields;
    }

    public void setFields(Set<Field> fields) {
        this.fields = fields;
    }
}