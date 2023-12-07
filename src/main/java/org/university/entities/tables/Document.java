package org.university.entities.tables;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.university.entities.TableID;
import org.university.entities.reference_book.FileExtension;

import java.sql.Timestamp;
import java.util.Arrays;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@DynamicUpdate
@DynamicInsert
@Table(name = "documents", uniqueConstraints = {@UniqueConstraint(columnNames = {"index_project", "name_file", "bit_file"})})
public class Document implements TableID {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "index_file")
    private Long id;

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
    private boolean currentData;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Document document = (Document) o;

        if (currentData != document.currentData) return false;
        if (!project.equals(document.project)) return false;
        if (!nameFile.equals(document.nameFile)) return false;
        return Arrays.equals(bitFile, document.bitFile);
    }

    @Override
    public int hashCode() {
        int result = project.hashCode();
        result = 31 * result + nameFile.hashCode();
        result = 31 * result + Arrays.hashCode(bitFile);
        return result;
    }

    @Override
    public String toString() {
        return nameFile;
    }
}
