package org.university.business_logic.tables.orm;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "access_rights")
public class AccessRight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "index_right")
    private int indexRight;

    @Column(name = "name_right", length = 128, unique = true)
    @NonNull
    private String nameRight;

    @Column(name = "current_data")
    private boolean currentData = true;

    @OneToMany(mappedBy = "accessRight", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EmployeeAccessRight> employeeAccessRights;
}
