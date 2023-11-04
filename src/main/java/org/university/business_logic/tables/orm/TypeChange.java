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
@Table(name = "type_change")
public class TypeChange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "index_type_change")
    private int indexTypeChange;

    @Column(name = "name_type_change", length = 128, unique = true)
    @NotNull
    private String nameTypeChange;

    @Column(name = "current_data")
    private boolean currentData = true;

    @OneToMany(mappedBy = "typeChange", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<HistoryChange> historyChanges;
}
