package org.university.entities.tables;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.university.entities.TableID;
import org.university.entities.reference_book.PriorityTask;
import org.university.entities.reference_book.StateTesting;

import java.sql.Timestamp;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@DynamicUpdate
@DynamicInsert
@Table(name = "task_testing", uniqueConstraints = {@UniqueConstraint(columnNames = {"index_task", "name_task_testing"})})
public class TaskTesting implements TableID {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "index_task_testing")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "index_task")
    @NonNull
    private Task task;

    @ManyToOne
    @JoinColumn(name = "index_state_testing")
    @NonNull
    private StateTesting stateTesting;

    @ManyToOne
    @JoinColumn(name = "index_priority")
    @NonNull
    private PriorityTask priority;

    @ManyToOne
    @JoinColumn(name = "index_performer")
    @NonNull
    private Employee performer;

    @Column(name = "name_task_testing", length = 128)
    @NonNull
    private String nameTaskTesting;

    @Column(name = "date_time_create")
    @NonNull
    private Timestamp dateTimeCreate;

    @Column(name = "planned_completion_date")
    @NonNull
    private Timestamp plannedCompletionDate;

    @Column(name = "date_time_end")
    private Timestamp dateTimeEnd;

    @Column(name = "description", length = 2500)
    private String description;

    @Column(name = "current_data")
    private boolean currentData;

    @OrderBy("id")
    @OneToMany(mappedBy = "taskTesting", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CommentTask> commentTasks;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskTesting that = (TaskTesting) o;

        if (currentData != that.currentData) return false;
        if (!task.equals(that.task)) return false;
        return nameTaskTesting.equals(that.nameTaskTesting);
    }

    @Override
    public int hashCode() {
        int result = task.hashCode();
        result = 31 * result + nameTaskTesting.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return nameTaskTesting;
    }
}
