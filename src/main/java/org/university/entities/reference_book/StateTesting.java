package org.university.entities.reference_book;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.university.entities.TableID;
import org.university.entities.tables.TaskTesting;

import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@DynamicUpdate
@DynamicInsert
@Table(name = "state_testing")
public class StateTesting implements TableID {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "index_state")
    private Long id;

    @Column(name = "name_state", length = 128, unique = true)
    @NotNull
    private String nameState;

    @Column(name = "current_data")
    private boolean currentData;

    @OrderBy("id")
    @OneToMany(mappedBy = "stateTesting", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private List<TaskTesting> taskTestings;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StateTesting that = (StateTesting) o;

        if (currentData != that.currentData) return false;
        return nameState.equals(that.nameState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return nameState;
    }
}
