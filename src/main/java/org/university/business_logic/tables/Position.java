package org.university.business_logic.tables;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "positions")
public class Position implements TableID {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "index_position")
    private Long indexPosition;

    @Column(name = "name_position", length = 128, unique = true)
    @NonNull
    private String namePosition;

    @Column(name = "current_data")
    private boolean currentData;

    @OneToMany(mappedBy = "position", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Employee> employees;

    @Override
    public Long getId() {
        return indexPosition;
    }
}
