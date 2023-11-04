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
@Table(name = "file_extension")
public class FileExtension {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "index_extension")
    private int indexExtension;

    @Column(name = "name_extension", length = 128, unique = true)
    @NotNull
    private String nameExtension;

    @Column(name = "current_data")
    private boolean currentData = true;

    @OneToMany(mappedBy = "fileExtension", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BinFileProject> binFileProjects;

    @OneToMany(mappedBy = "fileExtension", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Document> documents;
}
