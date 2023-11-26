package org.university.business_logic.tables;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Table(name = "state_testing")
public class StateTesting implements TableID {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "index_state")
    private Long indexState;

    @Column(name = "name_state", length = 128, unique = true)
    @NotNull
    private String nameState;

    @Column(name = "current_data")
    private boolean currentData = true;

    @OneToMany(mappedBy = "stateTesting", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TaskTesting> taskTestings;

    @Override
    public Long getId() {
        return indexState;
    }
}
