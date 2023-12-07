package org.university.entities.reference_book;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.university.entities.tables.CheckList;
import org.university.entities.tables.Project;
import org.university.entities.tables.Sprint;
import org.university.entities.tables.Task;
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
@Table(name = "execution_status")
public class ExecutionStatus implements TableID {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "index_status")
    private Long id;

    @Column(name = "name_execution", unique = true)
    @NonNull
    private String nameExecution;

    @Column(name = "current_data")
    private boolean currentData;

    @OrderBy("id")
    @OneToMany(mappedBy = "executionStatus", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Project> projects;

    @OrderBy("id")
    @OneToMany(mappedBy = "executionStatus", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Sprint> sprints;

    @OrderBy("id")
    @OneToMany(mappedBy = "executionStatus", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Task> tasks;

    @OrderBy("id")
    @OneToMany(mappedBy = "executionStatus", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private List<CheckList> checkLists;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExecutionStatus that = (ExecutionStatus) o;

        if (currentData != that.currentData) return false;
        return nameExecution.equals(that.nameExecution);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return nameExecution;
    }
}
