package org.university.entities.reference_book;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.university.entities.tables.EmployeeAccessRight;
import org.university.entities.TableID;

import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@DynamicUpdate
@DynamicInsert
@Table(name = "access_rights")
public class AccessRight implements TableID {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "index_right")
    private Long id;

    @Column(name = "name_right", length = 128, unique = true)
    @NonNull
    private String nameRight;

    @Column(name = "current_data")
    private boolean currentData;

    @OrderBy("id")
    @OneToMany(mappedBy = "accessRight", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private List<EmployeeAccessRight> employeeAccessRights;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccessRight that = (AccessRight) o;

        if (currentData != that.currentData) return false;
        return nameRight.equals(that.nameRight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return nameRight;
    }
}
