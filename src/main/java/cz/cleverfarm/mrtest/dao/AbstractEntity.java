package cz.cleverfarm.mrtest.dao;

import java.io.Serializable;
import java.util.Objects;

/**
 * © 2019 Michal Rohac, All Rights Reserved.
 */
public abstract class AbstractEntity <T extends Serializable> {

    public abstract T getId();

    public abstract void setId(T id);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractEntity<?> that = (AbstractEntity<?>) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
