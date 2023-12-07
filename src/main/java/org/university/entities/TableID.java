package org.university.entities;

import org.jetbrains.annotations.NotNull;

public interface TableID extends Comparable<TableID> {
    Long getId();
    void setId(Long id);

    @Override
    default int compareTo(@NotNull TableID o){
        return this.getId().compareTo(o.getId());
    }
}
