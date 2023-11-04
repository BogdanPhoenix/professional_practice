package org.university.business_logic.tables.orm.view;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "full_project_info")
public class FullProjectInfo {
    @Id
    @Column(name = "name_project", length = 128)
    @NonNull
    private String nameProject;

    @Column(name = "full_user_name")
    @NonNull
    private String fullUserName;

    @Column(name = "name_execution", length = 128)
    @NonNull
    private String nameExecution;

    @Column(name = "date_time_start")
    @NonNull
    private Timestamp dateTimeStart;

    @Column(name = "planned_completion_date")
    @NonNull
    private Timestamp plannedCompletionDate;

    @Column(name = "date_time_end")
    @NonNull
    private String dateTimeEnd;

    @Column(name = "budget")
    @NonNull
    private BigDecimal budget;

    @Column(name = "client_info", length = 2500)
    @NonNull
    private String clientIndo;

    @Column(name = "description", length = 2500)
    @NonNull
    private String description;
}
