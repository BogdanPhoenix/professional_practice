package org.university.business_logic.attribute_name;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@Getter
public abstract class AttributeName implements Comparable<AttributeName>{
    protected final String nameForSystem;
    protected final String nameForUser;
    protected final int id;

    protected AttributeName(int id, String nameForUser, String nameForSystem) {
        this.id = id;
        this.nameForSystem = nameForSystem;
        this.nameForUser = nameForUser;
    }

    @Override
    public String toString() {
        return nameForUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttributeName that = (AttributeName) o;
        return Objects.equals(nameForSystem, that.nameForSystem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameForSystem);
    }

    @Override
    public int compareTo(@NotNull AttributeName o) {
        return nameForSystem.compareTo(o.nameForSystem);
    }
}
