package org.university.business_logic.tables.orm;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Data
@NoArgsConstructor
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "authentication")
public class Authentication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "index_authentication")
    private int indexAuthentication;

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
}
