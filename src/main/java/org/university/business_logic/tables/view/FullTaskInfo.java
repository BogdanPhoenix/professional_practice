package org.university.business_logic.tables.view;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "full_task_info")
public class FullTaskInfo {
    @Id
    @Column(name = "name_project", length = 128)
    @NonNull
    private String nameProject;

    @Column(name = "name_sprint", length = 128)
    @NonNull
    private String nameSprint;

    @Column(name = "name_task", length = 128)
    @NonNull
    private String nameTask;

    @Column(name = "full_name_user")
    @NonNull
    private String fullNameUser;

    @Column(name = "name_execution", length = 128)
    @NonNull
    private String nameExecution;

    @Column(name = "name_priority", length = 128)
    @NonNull
    private String namePriority;

    @Column(name = "name_complexity", length = 128)
    @NonNull
    private String nameComplexity;

    @Column(name = "date_time_create")
    @NonNull
    private Timestamp dateTimeCreate;

    @Column(name = "planned_completion_date")
    @NonNull
    private Timestamp plannedCompletionDate;

    @Column(name = "date_time_end")
    @NonNull
    private Timestamp dateTimeEnd;

    @Column(name = "description", length = 2500)
    @NonNull
    private String description;
}
