package org.university.business_logic.attribute_name;

import lombok.Getter;
import org.university.entities.TableID;

import java.util.Objects;

@Getter
public class AttributeNameComboBox extends AttributeName {
    private final Class<? extends TableID> nameClass;

    public AttributeNameComboBox(int id, String nameForUser, String nameForSystem, Class<? extends TableID> nameClass) {
        super(id, nameForUser, nameForSystem);
        this.nameClass = nameClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AttributeNameComboBox that = (AttributeNameComboBox) o;
        return Objects.equals(nameClass, that.nameClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), nameClass);
    }
}
