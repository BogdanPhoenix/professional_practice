package org.university.entities.tables;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.university.entities.TableID;
import org.university.entities.reference_book.AccessRight;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@DynamicUpdate
@DynamicInsert
@Table(name = "employee_access_rights", uniqueConstraints = {@UniqueConstraint(columnNames = {"index_project", "index_employee", "index_right"})})
public class EmployeeAccessRight implements TableID {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "index_employee_access_right")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "index_project")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "index_employee")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "index_right", referencedColumnName = "index_right")
    private AccessRight accessRight;

    @Column(name = "current_data")
    private boolean currentData;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmployeeAccessRight that = (EmployeeAccessRight) o;

        if (currentData != that.currentData) return false;
        if (!project.equals(that.project)) return false;
        if (!employee.equals(that.employee)) return false;
        return accessRight.equals(that.accessRight);
    }

    @Override
    public int hashCode() {
        int result = project.hashCode();
        result = 31 * result + employee.hashCode();
        result = 31 * result + accessRight.hashCode();
        return result;
    }
}