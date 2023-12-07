package org.university.entities.tables;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.university.entities.TableID;
import org.university.entities.reference_book.ExecutionStatus;
import org.university.entities.reference_book.PriorityTask;

import java.sql.Timestamp;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@DynamicUpdate
@DynamicInsert
@Table(name = "check_list", uniqueConstraints = {@UniqueConstraint(columnNames = {"index_task", "name_task"})})
public class CheckList implements TableID {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "index_check_list")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "index_task")
    @NonNull
    private Task task;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CheckList checkList = (CheckList) o;

        if (currentData != checkList.currentData) return false;
        if (!task.equals(checkList.task)) return false;
        return nameTask.equals(checkList.nameTask);
    }

    @Override
    public int hashCode() {
        int result = task.hashCode();
        result = 31 * result + nameTask.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return nameTask;
    }
}
