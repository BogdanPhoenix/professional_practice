package org.university.business_logic.tables;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@DynamicUpdate
@DynamicInsert
@Table(name = "history_changes")
public class HistoryChange implements TableID {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "index_change")
    private Long indexChange;

    @ManyToOne
    @JoinColumn(name = "index_project")
    @NonNull
    private Project project;

    @ManyToOne
    @JoinColumn(name = "index_type_change")
    @NonNull
    private TypeChange typeChange;

    @ManyToOne
    @JoinColumn(name = "index_performer")
    @NonNull
    private Employee performer;

    @Column(name = "name_change", length = 2500)
    @NonNull
    private String nameChange;

    @Column(name = "date_time_create")
    @NonNull
    private Timestamp dateTimeCreate;

    @Column(name = "current_data")
    private boolean currentData = true;

    @Override
    public Long getId() {
        return indexChange;
    }
}
