package me.oldboy.dao;

import me.oldboy.entity.User;
import me.oldboy.entity.feature.Auth;
import me.oldboy.entity.feature.Role;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    public UserDao userDao = UserDao.getInstance();

    @Test
    @Order(1)
    void shouldReturnSameNameForSaveUserTest() {
        User user = User.builder().userName("Bak").email("test@test.ru").password("1234").build();
        User saveUser = userDao.save(user);

        assertTrue(user.getUserName().equals(saveUser.getUserName()));
    }

    @Test
    @Order(2)
    void shouldReturnAllUsersTest() {
        Integer size = userDao.findAll().size();
        User user = User.builder().userName("tak").email("test2@test.ru").password("12345").build();
        User saveUser = userDao.save(user);
        assertTrue(userDao.findAll().size() == size + 1);
    }

    @Test
    @Order(3)
    void shouldReturnUserFindByIdTest() {
        User user = userDao.findById(1L).get();
        assertTrue(user.getRole().equals(Role.ADMIN));
    }

    @Test
    @Order(4)
    void shouldReturnUserFindByEmailAndPasswordTest() {
        User user = userDao.findByEmailAndPassword("user@user.ru", "user");
        assertTrue(user.getRole().equals(Role.USER));
    }

    @Test
    @Order(5)
    void shouldReturnTrueAfterDeleteTest() {
        User user = User.builder().userName("mak").email("2test2@test.ru").password("123456").build();
        User saveUser = userDao.save(user);
        Long userId = saveUser.getUserId();

        Boolean isDeleted = userDao.delete(userId);
        Boolean isEmpty = userDao.findById(userId).isEmpty();

        assertTrue(isDeleted);
        assertTrue(isEmpty);
    }

    @Test
    @Order(6)
    void shouldReturnTrueAfterSetDeleteStatusTest() {
        User user = User.builder().userName("dock").email("dock@test.ru").password("2123456").auth(Auth.IS_ACTIVE).build();
        User userSave = userDao.save(user);
        Long userId = userSave.getUserId();
        assertTrue(userSave.getAuth().equals(Auth.IS_ACTIVE));
        userDao.setDeleteStatus(userId);
        assertTrue(userSave.getAuth().equals(Auth.IS_DELETE));
    }

    @Test
    @Order(7)
    void shouldReturnTrueAfterSetBlockedStatusTest() {
        User user = User.builder().userName("o'rurk").email("o_rurk@test.ru").password("342123456").auth(Auth.IS_ACTIVE).build();
        User userSave = userDao.save(user);
        Long userId = userSave.getUserId();
        assertTrue(userSave.getAuth().equals(Auth.IS_ACTIVE));
        userDao.setBlockedStatus(userId);
        assertTrue(userSave.getAuth().equals(Auth.IS_BLOCKED));
    }

    @Test
    @Order(8)
    void shouldReturnTrueAfterUpdateUserTest() {
        String oldName = "izikava";
        String newName = "gonkucu";

        User user = User.builder().userName(oldName).email("kavaricu@de.ru").password("erew56").auth(Auth.IS_ACTIVE).build();
        User userSave = userDao.save(user);
        Long userId = userSave.getUserId();
        assertTrue(userSave.getUserName().equals(oldName));

        User userUpdate = User.builder().userId(userId).userName("gonkucu").email("bakaru@de.ru").password("432de").auth(Auth.IS_ACTIVE).build();
        userDao.update(userUpdate);

        User userFromBase = userDao.findById(userId).get();
        assertTrue(userFromBase.getUserName().equals(newName));
    }
}