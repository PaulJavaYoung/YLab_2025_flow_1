package me.oldboy.data_base;

import lombok.Getter;
import lombok.Setter;
import me.oldboy.entity.User;
import me.oldboy.entity.feature.Auth;
import me.oldboy.entity.feature.Role;

import java.util.ArrayList;
import java.util.List;

/**
 * User database imitation.
 */
public class UserDB {

    private static UserDB INSTANCE;

    public static UserDB getInstance() {
        if(INSTANCE == null){
            INSTANCE = new UserDB();
            initialSetup();
        }
        return INSTANCE;
    }

    @Setter
    private Long userIdCount;

    @Getter
    private List<User> userDb = new ArrayList<>();

    public Long getCurrentIdCount() {
        this.userIdCount = Long.valueOf(userDb.size());
        return userIdCount;
    }

    private static void initialSetup(){
        User firstAdmin = User.builder()
                .userId(1L)
                .email("admin@admin.ru")
                .password("admin")
                .userName("Ada")
                .role(Role.ADMIN)
                .auth(Auth.IS_ACTIVE)
                .build();

        User firstUser = User.builder()
                .userId(2L)
                .email("user@user.ru")
                .password("user")
                .userName("Usa")
                .role(Role.USER)
                .auth(Auth.IS_ACTIVE)
                .build();

        INSTANCE.getUserDb().add(firstAdmin);
        INSTANCE.getUserDb().add(firstUser);
    }
}