package org.university.business_logic.tables.orm;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Data
@NoArgsConstructor
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "command_project", uniqueConstraints = {@UniqueConstraint(columnNames = {"index_project", "index_employee"})})
public class CommandProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "index_command_project")
    private int indexField;

    @ManyToOne
    @JoinColumn(name = "index_employee")
    @NonNull
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "index_project")
    @NonNull
    private Project project;

    @Column(name = "current_data")
    private boolean currentData = true;
}
