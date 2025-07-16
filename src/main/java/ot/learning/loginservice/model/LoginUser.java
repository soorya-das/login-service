package ot.learning.loginservice.model;

import jakarta.persistence.*;

import lombok.*;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "login_user")
public class LoginUser {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String userName;
    private String password;
    private String role; // e.g., EMPLOYEE or USER
}
