package org.university.business_logic.tables;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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
    private Long indexField;

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
    private boolean currentData = true;

    @Override
    public Long getId() {
        return indexField;
    }
}