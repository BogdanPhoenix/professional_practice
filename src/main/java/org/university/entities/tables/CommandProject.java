package org.university.entities.tables;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.university.entities.TableID;

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
    private Long id;

    @ManyToOne
    @JoinColumn(name = "index_employee")
    @NonNull
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "index_project")
    @NonNull
    private Project project;

    @Column(name = "current_data")
    private boolean currentData;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommandProject that = (CommandProject) o;

        if (currentData != that.currentData) return false;
        if (!employee.equals(that.employee)) return false;
        return project.equals(that.project);
    }

    @Override
    public int hashCode() {
        int result = employee.hashCode();
        result = 31 * result + project.hashCode();
        return result;
    }
}
