package org.university.business_logic.tables.orm;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "types_complexity")
public class TypeComplexity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "index_complexity")
    private int indexComplexity;

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
}
