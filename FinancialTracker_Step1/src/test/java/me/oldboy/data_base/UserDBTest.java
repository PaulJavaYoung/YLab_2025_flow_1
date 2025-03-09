package me.oldboy.data_base;

import me.oldboy.entity.User;
import me.oldboy.entity.feature.Auth;
import me.oldboy.entity.feature.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserDBTest {

    public UserDB userDB;

    @BeforeEach
    public void initBase(){
        userDB = new UserDB();
    }

    @AfterEach
    public void killBase(){
        userDB.getUserDb().clear();
    }

    @Test
    void shouldReturnCurrentUserCountTest() {
        User user = new User();
        User user2 = new User();
        userDB.getUserDb().add(user);
        userDB.getUserDb().add(user2);

        assertThat(userDB.getCurrentIdCount()).isEqualTo(2);
    }

    @Test
    void shouldReturnCurrentUserContainInDB() {
        User user = User.builder().userId(1L).userName("Mal").email("qwer@wer.ru").password("122").auth(Auth.IS_ACTIVE).role(Role.USER).build();
        User admin = User.builder().userId(2L).userName("Pal").email("rewqw@rewe.com").password("321").auth(Auth.IS_ACTIVE).role(Role.ADMIN).build();
        userDB.getUserDb().add(user);
        userDB.getUserDb().add(admin);


        assertThat(user.getRole()).isEqualTo(userDB.getUserDb().get(0).getRole());
        assertThat(admin.getRole()).isEqualTo(userDB.getUserDb().get(1).getRole());
    }

    @Test
    void shouldReturnInitUserContainInDB() {
        UserDB userDB_2 = UserDB.getInstance();

        assertThat(userDB_2.getUserDb().get(0).getUserId()).isEqualTo(1L);
    }
}