package org.university.business_logic.tables.orm;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "bin_files_projects", uniqueConstraints = {@UniqueConstraint(columnNames = {"index_project", "name_file", "bit_file"})})
public class BinFileProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "index_file")
    private int indexFile;

    @ManyToOne
    @JoinColumn(name = "index_project")
    @NonNull
    private Project project;

    @ManyToOne
    @JoinColumn(name = "index_extension")
    @NonNull
    private FileExtension fileExtension;

    @ManyToOne
    @JoinColumn(name = "index_performer")
    @NonNull
    private Employee performer;

    @Column(name = "name_file", length = 128)
    @NonNull
    private String nameFile;

    @Column(name = "description", length = 2500)
    private String description;

    @Column(name = "bit_file")
    private byte[] bitFile;

    @Column(name = "date_time_down")
    @NonNull
    private Timestamp dateTimeDown;

    @Column(name = "current_data")
    private boolean currentData = true;
}
