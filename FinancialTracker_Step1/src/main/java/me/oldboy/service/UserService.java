package me.oldboy.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import me.oldboy.dao.UserDao;
import me.oldboy.entity.User;
import me.oldboy.entity.feature.Role;
import me.oldboy.exception.UserServiceException;

import java.util.Optional;

/**
 * Service for user-related functionality.
 *
 */
public class UserService {

    private static UserService INSTANCE;

    public static UserService getInstance() {
        if(INSTANCE == null){
            INSTANCE = new UserService();
        }
        return INSTANCE;
    }

    private UserDao userDao = UserDao.getInstance();

    public Role getUserRole(Long userId){
        Optional<User> mayBeUser = userDao.findById(userId);
        if (mayBeUser.isEmpty()){
            throw new UserServiceException("User with ID - " + userId + " not found");
        } else {
            return mayBeUser.get().getRole();
        }
    }
}
