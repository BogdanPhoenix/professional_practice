package org.university.entities.reference_book;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.university.entities.TableID;
import org.university.entities.tables.Sprint;
import org.university.entities.tables.Task;

import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@DynamicUpdate
@DynamicInsert
@Table(name = "types_complexity")
public class TypeComplexity implements TableID {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "index_complexity")
    private Long id;

    @Column(name = "name_complexity", length = 128, unique = true)
    @NonNull
    private String nameComplexity;

    @Column(name = "number_value", unique = true)
    @NonNull
    private Integer numberValue;

    @Column(name = "current_data")
    private boolean currentData;

    @OrderBy("id")
    @OneToMany(mappedBy = "complexity", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Sprint> sprints;

    @OrderBy("id")
    @OneToMany(mappedBy = "complexity", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Task> tasks;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TypeComplexity that = (TypeComplexity) o;

        if (currentData != that.currentData) return false;
        if (!nameComplexity.equals(that.nameComplexity)) return false;
        return numberValue.equals(that.numberValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return nameComplexity;
    }
}
