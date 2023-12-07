package org.university.entities.tables;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.university.entities.TableID;
import org.university.entities.reference_book.Position;

import java.util.List;

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
    private Long id;

    @Column(name = "login_user", length = 128, nullable = false, unique = true)
    @NonNull
    private String loginUser;

    @Column(name = "password_user", length = 1000, nullable = false)
    @NonNull
    private String passwordUser;

    @ManyToOne
    @JoinColumn(name = "index_position", nullable = false)
    @NonNull
    private Position position;

    @Column(name = "first_name", length = 128, nullable = false)
    @NonNull
    private String firstName;

    @Column(name = "name_user", length = 128, nullable = false)
    @NonNull
    private String nameUser;

    @Column(name = "phone_number", length = 20, nullable = false, unique = true)
    @NonNull
    private String phoneNumber;

    @Column(name = "image_user", unique = true)
    private byte[] imageUser;

    @Column(name = "description", length = 2500)
    @NonNull
    private String description;

    @Column(name = "current_data")
    private boolean currentData;

    @OrderBy("id")
    @OneToMany(mappedBy = "intermediary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Project> projectIntermediaries;

    @OrderBy("id")
    @OneToMany(mappedBy = "performer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;

    @OrderBy("id")
    @OneToMany(mappedBy = "performer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CheckList> checkLists;

    @OrderBy("id")
    @OneToMany(mappedBy = "performer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BinFileProject> binFileProjects;

    @OrderBy("id")
    @OneToMany(mappedBy = "performer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Document> documents;

    @OrderBy("id")
    @OneToMany(mappedBy = "performer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskTesting> taskTestings;

    @OrderBy("id")
    @OneToMany(mappedBy = "performer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentTask> commentTasks;

    @OrderBy("id")
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmployeeAccessRight> employeeAccessRights;

    @ManyToMany
    @JoinTable(
            name = "command_project",
            joinColumns = @JoinColumn(name = "index_employee"),
            inverseJoinColumns = @JoinColumn(name = "index_project")
    )
    private List<Project> projects;

    @Override
    public String toString() {
        return String.format("%s %s", firstName, nameUser);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (currentData != employee.currentData) return false;
        if (!loginUser.equals(employee.loginUser)) return false;
        if (!firstName.equals(employee.firstName)) return false;
        return nameUser.equals(employee.nameUser);
    }

    @Override
    public int hashCode() {
        int result = loginUser.hashCode();
        result = 31 * result + firstName.hashCode();
        result = 31 * result + nameUser.hashCode();
        return result;
    }
}
