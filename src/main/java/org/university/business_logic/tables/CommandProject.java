package org.university.business_logic.tables;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@DynamicUpdate
@DynamicInsert
@Table(name = "command_project", uniqueConstraints = {@UniqueConstraint(columnNames = {"index_project", "index_employee"})})
public class CommandProject implements TableID {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "index_command_project")
    private Long indexField;

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

    @Override
    public Long getId() {
        return indexField;
    }
}
