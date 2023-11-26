package org.university.business_logic.tables;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@DynamicUpdate
@DynamicInsert
@Table(name = "projects")
public class Project implements TableID {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "index_project")
    private Long indexProject;

    @ManyToOne
    @JoinColumn(name = "index_execution_status")
    @NonNull
    private ExecutionStatus executionStatus;

    @ManyToOne
    @JoinColumn(name = "index_intermediary")
    @NonNull
    private Employee intermediary;

    @Column(name = "name_project", length = 128, unique = true)
    @NonNull
    private String nameProject;

    @Column(name = "date_time_start")
    @NonNull
    private Timestamp dateTimeStart;

    @Column(name = "planned_completion_date")
    @NonNull
    private Timestamp plannedCompletionDate;

    @Column(name = "date_time_end")
    private Timestamp dateTimeEnd;

    @Column(name = "budget")
    @NonNull
    private BigDecimal budget;

    @Column(name = "image_project", unique = true)
    private byte @NonNull [] imageProject;

    @Column(name = "description", length = 2500)
    private String description;

    @Column(name = "client_info", length = 2500)
    private String clientInfo;

    @Column(name = "current_data")
    private boolean currentData = true;

    @ManyToMany(mappedBy = "projects")
    private Set<Employee> employees;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Sprint> sprints;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<HistoryChange> historyChanges;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BinFileProject> binFileProjects;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Document> documents;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EmployeeAccessRight> employeeAccessRights;

    @Override
    public Long getId() {
        return indexProject;
    }
}
