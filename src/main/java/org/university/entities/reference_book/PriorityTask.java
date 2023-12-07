package org.university.entities.reference_book;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.university.entities.tables.CheckList;
import org.university.entities.TableID;
import org.university.entities.tables.Task;
import org.university.entities.tables.TaskTesting;

import java.util.List;
import java.util.Objects;

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
    private Long id;

    @Column(name = "name_priority", length = 128, unique = true)
    @NonNull
    private String namePriority;

    @Column(name = "current_data")
    private boolean currentData;

    @OrderBy("id")
    @OneToMany(mappedBy = "priority", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;

    @OrderBy("id")
    @OneToMany(mappedBy = "priority", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CheckList> checkLists;

    @OrderBy("id")
    @OneToMany(mappedBy = "priority", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskTesting> taskTestings;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PriorityTask that = (PriorityTask) o;

        if (currentData != that.currentData) return false;
        return namePriority.equals(that.namePriority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return namePriority;
    }
}
