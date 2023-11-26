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
@Table(name = "execution_status")
public class ExecutionStatus implements TableID {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "index_status")
    private Long indexStatus;

    @Column(name = "name_execution", unique = true)
    @NonNull
    private String nameExecution;

    @Column(name = "current_data")
    private boolean currentData = true;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "executionStatus", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Project> projects;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "executionStatus", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Sprint> sprints;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "executionStatus", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Task> tasks;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "executionStatus", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CheckList> checkLists;

    @Override
    public Long getId() {
        return indexStatus;
    }
}
