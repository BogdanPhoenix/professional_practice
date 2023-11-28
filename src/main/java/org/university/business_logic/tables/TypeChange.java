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
@Table(name = "type_change")
public class TypeChange implements TableID {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "index_type_change")
    private Long indexTypeChange;

    @Column(name = "name_type_change", length = 128, unique = true)
    @NotNull
    private String nameTypeChange;

    @Column(name = "current_data")
    private boolean currentData;

    @OneToMany(mappedBy = "typeChange", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<HistoryChange> historyChanges;

    @Override
    public Long getId() {
        return indexTypeChange;
    }
}
