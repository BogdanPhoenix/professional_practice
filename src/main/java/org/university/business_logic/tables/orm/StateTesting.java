package org.university.business_logic.tables.orm;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "state_testing")
public class StateTesting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "index_state")
    private int indexState;

    @Column(name = "name_state", length = 128, unique = true)
    @NotNull
    private String nameState;

    @Column(name = "current_data")
    private boolean currentData = true;

    @OneToMany(mappedBy = "stateTesting", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TaskTesting> taskTestings;
}
