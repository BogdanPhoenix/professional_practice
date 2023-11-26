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
@Table(name = "sprints", uniqueConstraints = {@UniqueConstraint(columnNames = {"index_project", "name_sprint"})})
public class Sprint implements TableID {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "index_sprint")
    private Long indexSprint;

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
    private boolean currentData = true;

    @OneToMany(mappedBy = "sprint", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Task> tasks;

    @Override
    public Long getId() {
        return indexSprint;
    }
}
