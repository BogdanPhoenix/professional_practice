package org.university.business_logic.tables;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@DynamicUpdate
@DynamicInsert
@Table(name = "authentication")
public class Authentication implements TableID {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "index_authentication")
    private Long indexAuthentication;

    @OneToOne
    @JoinColumn(name = "index_employee", unique = true)
    @NonNull
    private Employee employee;

    @Column(name = "login_user", length = 128, unique = true)
    @NonNull
    private String loginUser;

    @Column(name = "password_user")
    @NonNull
    private String passwordUser;

    @Column(name = "current_data")
    private boolean currentData = true;

    @Override
    public Long getId() {
        return indexAuthentication;
    }
}
