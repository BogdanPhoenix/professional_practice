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
@Table(name = "priority_task")
public class PriorityTask implements TableID {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "index_priority")
    private Long indexPriority;

    @Column(name = "name_priority", length = 128, unique = true)
    @NonNull
    private String namePriority;

    @Column(name = "current_data")
    private boolean currentData = true;

    @OneToMany(mappedBy = "priority", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Task> tasks;

    @OneToMany(mappedBy = "priority", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CheckList> checkLists;

    @OneToMany(mappedBy = "priority", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TaskTesting> taskTestings;

    @Override
    public Long getId() {
        return indexPriority;
    }
}
