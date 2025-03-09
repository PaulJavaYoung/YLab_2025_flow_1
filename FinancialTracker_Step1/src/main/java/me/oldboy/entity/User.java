package me.oldboy.entity;

import lombok.*;
import me.oldboy.entity.feature.Auth;
import me.oldboy.entity.feature.Role;

/**
 * Represents a user with information such as a unique identifier, username, email, password, role, and auth admission.
 *
 */
import java.util.Objects;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class User {
    private Long userId;
    private String userName;
    private String email;
    private String password;
    private Role role;
    private Auth auth;

    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.role = Role.USER;
        this.auth = Auth.IS_ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) && Objects.equals(userName, user.userName) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && role == user.role && auth == user.auth;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName, email, password, role, auth);
    }

    @Override
    public String toString() {
        return "User {" +
                "userId = " + userId +
                ", userName = '" + userName + '\'' +
                ", email = '" + email + '\'' +
                ", role = " + role +
                ", auth = " + auth +
                '}';
    }
}
