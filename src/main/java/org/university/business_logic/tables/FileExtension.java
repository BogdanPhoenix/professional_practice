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
@Table(name = "file_extension")
public class FileExtension implements TableID {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "index_extension")
    private Long indexExtension;

    @Column(name = "name_extension", length = 128, unique = true)
    @NotNull
    private String nameExtension;

    @Column(name = "current_data")
    private boolean currentData;

    @OneToMany(mappedBy = "fileExtension", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BinFileProject> binFileProjects;

    @OneToMany(mappedBy = "fileExtension", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Document> documents;

    @Override
    public Long getId() {
        return indexExtension;
    }
}
