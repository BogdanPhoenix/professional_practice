package org.university.business_logic.tables;

import jakarta.persistence.*;
import lombok.*;
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
@Table(name = "access_rights")
public class AccessRight implements TableID {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "index_right")
    private Long indexRight;

    @Column(name = "name_right", length = 128, unique = true)
    @NonNull
    private String nameRight;

    @Column(name = "current_data")
    private boolean currentData = true;

    @OneToMany(mappedBy = "accessRight", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EmployeeAccessRight> employeeAccessRights;

    @Override
    public Long getId() {
        return indexRight;
    }
}
