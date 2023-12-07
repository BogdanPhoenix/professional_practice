package org.university.entities.tables;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.university.entities.TableID;
import org.university.entities.reference_book.ExecutionStatus;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

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
    private Long id;

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

    @Column(name = "description", length = 2500)
    private String description;

    @Column(name = "client_info", length = 2500)
    private String clientInfo;

    @Column(name = "current_data")
    private boolean currentData;

    @ManyToMany(mappedBy = "projects")
    private List<Employee> employees;

    @OrderBy("id")
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sprint> sprints;

    @OrderBy("id")
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BinFileProject> binFileProjects;

    @OrderBy("id")
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Document> documents;

    @OrderBy("id")
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmployeeAccessRight> employeeAccessRights;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project project = (Project) o;

        if (currentData != project.currentData) return false;
        return nameProject.equals(project.nameProject);
    }

    @Override
    public int hashCode() {
        return nameProject.hashCode();
    }

    @Override
    public String toString() {
        return nameProject;
    }
}
