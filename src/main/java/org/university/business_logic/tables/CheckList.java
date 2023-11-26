package org.university.business_logic.tables;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;

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
    private Long indexCheckList;

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
    private boolean currentData = true;

    @Override
    public Long getId() {
        return indexCheckList;
    }
}
