package org.university.business_logic.tables;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;
import java.util.Set;

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
    private Long indexTask;

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
    private boolean currentData = true;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CheckList> checkLists;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TaskTesting> taskTestings;

    @Override
    public Long getId() {
        return indexTask;
    }
}
