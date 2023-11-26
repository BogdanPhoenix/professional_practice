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
@Table(name = "types_complexity")
public class TypeComplexity implements TableID {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "index_complexity")
    private Long indexComplexity;

    @Column(name = "name_complexity", length = 128, unique = true)
    @NonNull
    private String nameComplexity;

    @Column(name = "number_value", unique = true)
    @NonNull
    private Integer numberValue;

    @Column(name = "current_data")
    private boolean currentData = true;

    @OneToMany(mappedBy = "complexity", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Sprint> sprints;

    @OneToMany(mappedBy = "complexity", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Task> tasks;

    @Override
    public Long getId() {
        return indexComplexity;
    }
}
