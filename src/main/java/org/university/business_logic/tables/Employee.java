package org.university.business_logic.tables;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@DynamicUpdate
@DynamicInsert
@Table(name = "employees", uniqueConstraints = {@UniqueConstraint(columnNames = {"first_name", "name_user"})})
public class Employee implements TableID {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "index_employee")
    private Long indexEmployee;

    @ManyToOne
    @JoinColumn(name = "index_position")
    @NonNull
    private Position position;

    @Column(name = "first_name", length = 128)
    @NonNull
    private String firstName;

    @Column(name = "name_user", length = 128)
    @NonNull
    private String nameUser;

    @Column(name = "phone_number", length = 20, unique = true)
    @NonNull
    private String phoneNumber;

    @Column(name = "image_user", unique = true)
    private byte[] imageUser;

    @Column(name = "description", length = 2500)
    @NonNull
    private String description;

    @Column(name = "current_data")
    private boolean currentData = true;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private Authentication authentication;

    @OneToMany(mappedBy = "intermediary", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Project> projectIntermediaries;

    @OneToMany(mappedBy = "performer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Task> tasks;

    @OneToMany(mappedBy = "performer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CheckList> checkLists;

    @OneToMany(mappedBy = "performer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<HistoryChange> historyChanges;

    @OneToMany(mappedBy = "performer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BinFileProject> binFileProjects;

    @OneToMany(mappedBy = "performer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Document> documents;

    @OneToMany(mappedBy = "performer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TaskTesting> taskTestings;

    @OneToMany(mappedBy = "performer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CommentTask> commentTasks;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EmployeeAccessRight> employeeAccessRights;

    @ManyToMany
    @JoinTable(
            name = "command_project",
            joinColumns = @JoinColumn(name = "index_employee"),
            inverseJoinColumns = @JoinColumn(name = "index_project")
    )
    private Set<Project> projects;

    @Override
    public Long getId() {
        return indexEmployee;
    }
}
