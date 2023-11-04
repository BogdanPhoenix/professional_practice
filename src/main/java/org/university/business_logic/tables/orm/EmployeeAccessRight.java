package org.university.business_logic.tables.orm;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Data
@NoArgsConstructor
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "employee_access_rights", uniqueConstraints = {@UniqueConstraint(columnNames = {"index_project", "index_employee", "index_right"})})
public class EmployeeAccessRight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "index_employee_access_right")
    private int indexField;

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
}