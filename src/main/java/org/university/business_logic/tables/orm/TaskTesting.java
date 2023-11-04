package org.university.business_logic.tables.orm;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "task_testing", uniqueConstraints = {@UniqueConstraint(columnNames = {"index_task", "name_task_testing"})})
public class TaskTesting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "index_task_testing")
    private int indexTaskTesting;

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
    private boolean currentData = true;

    @OneToMany(mappedBy = "taskTesting", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CommentTask> commentTasks;

}
