package org.university.entities.tables;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.university.entities.TableID;
import org.university.entities.reference_book.ExecutionStatus;
import org.university.entities.reference_book.TypeComplexity;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@DynamicUpdate
@DynamicInsert
@Table(name = "sprints", uniqueConstraints = {@UniqueConstraint(columnNames = {"index_project", "name_sprint"})})
public class Sprint implements TableID {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "index_sprint")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "index_project")
    @NonNull
    private Project project;

    @ManyToOne
    @JoinColumn(name = "index_execution_status")
    @NonNull
    private ExecutionStatus executionStatus;

    @ManyToOne
    @JoinColumn(name = "index_complexity")
    @NonNull
    private TypeComplexity complexity;

    @Column(name = "name_sprint", length = 128)
    @NonNull
    private String nameSprint;

    @Column(name = "description", length = 2500)
    private String description;

    @Column(name = "date_time_create")
    @NonNull
    private Timestamp dateTimeCreate;

    @Column(name = "planned_completion_date")
    @NonNull
    private Timestamp plannedCompletionDate;

    @Column(name = "date_time_end")
    private Timestamp dateTimeEnd;

    @Column(name = "current_data")
    private boolean currentData;

    @OrderBy("id")
    @OneToMany(mappedBy = "sprint", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sprint sprint = (Sprint) o;

        if (currentData != sprint.currentData) return false;
        if (!project.equals(sprint.project)) return false;
        return nameSprint.equals(sprint.nameSprint);
    }

    @Override
    public int hashCode() {
        int result = project.hashCode();
        result = 31 * result + nameSprint.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return nameSprint;
    }
}
