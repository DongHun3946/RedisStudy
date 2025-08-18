package org.example.redisstudy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.redisstudy.entity.enums.Role;

import java.time.ZonedDateTime;

import static jakarta.persistence.EnumType.STRING;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@Table(name = "users")
public class User extends BaseEntity {
    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    private boolean isUsed;

    private boolean isDeleted;

    @Enumerated(STRING)
    private Role role;

    private ZonedDateTime lastLoginAt;

    public static User create(Long id, String userId, String password, String nickname, Role role) {
        return new User(id, userId, password, nickname, true, false, role, null);
    }
}
