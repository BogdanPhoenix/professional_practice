package org.university.entities.reference_book;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.university.entities.tables.Employee;
import org.university.entities.TableID;

import java.util.List;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "positions")
public class Position implements TableID {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "index_position")
    private Long id;

    @Column(name = "name_position", length = 128, unique = true)
    @NonNull
    private String namePosition;

    @Column(name = "current_data")
    private boolean currentData;

    @OrderBy("id")
    @OneToMany(mappedBy = "position", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Employee> employees;


    @Override
    public String toString() {
        return namePosition;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (currentData != position.currentData) return false;
        return namePosition.equals(position.namePosition);
    }
}
