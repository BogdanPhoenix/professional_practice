package org.university.entities.tables;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.university.entities.TableID;
import org.university.entities.reference_book.ExecutionStatus;
import org.university.entities.reference_book.PriorityTask;
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
@Table(name = "task", uniqueConstraints = {@UniqueConstraint(columnNames = {"index_sprint", "name_task"})})
public class Task implements TableID {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "index_task")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "index_sprint")
    @NonNull
    private Sprint sprint;

    @ManyToOne
    @JoinColumn(name = "index_execution_status")
    @NonNull
    private ExecutionStatus executionStatus;

    @ManyToOne
    @JoinColumn(name = "index_priority")
    @NonNull
    private PriorityTask priority;

    @ManyToOne
    @JoinColumn(name = "index_performer")
    @NonNull
    private Employee performer;

    @ManyToOne
    @JoinColumn(name = "index_complexity")
    @NonNull
    private TypeComplexity complexity;

    @Column(name = "name_task", length = 128)
    @NonNull
    private String nameTask;

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
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CheckList> checkLists;

    @OrderBy("id")
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskTesting> taskTestings;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (currentData != task.currentData) return false;
        if (!sprint.equals(task.sprint)) return false;
        return nameTask.equals(task.nameTask);
    }

    @Override
    public int hashCode() {
        int result = sprint.hashCode();
        result = 31 * result + nameTask.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return nameTask;
    }
}
