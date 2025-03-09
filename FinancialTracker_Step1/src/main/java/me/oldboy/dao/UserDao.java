package me.oldboy.dao;

import me.oldboy.data_base.UserDB;
import me.oldboy.entity.User;
import me.oldboy.entity.feature.Auth;
import me.oldboy.exception.IllegalAuthParameterException;
import me.oldboy.exception.IllegalUserConditionException;
import me.oldboy.exception.NotUniqueEmailException;
import me.oldboy.exception.NotUniquePasswordException;

import java.util.List;
import java.util.Optional;

/**
 * Data Access Object (DAO) implementation of the CrudOperation for managing User entities.
 */
public class UserDao implements CrudOperation<Long, User>{

    private static UserDao INSTANCE;

    public static UserDao getInstance() {
        if(INSTANCE == null){
            INSTANCE = new UserDao();
        }
        return INSTANCE;
    }

    private UserDB userDB = UserDB.getInstance();

    @Override
    public List<User> findAll() {
        return userDB.getUserDb();
    }

    @Override
    public Optional<User> findById(Long id) {
        Optional<User> mayBeUser = findAll().stream()
                                            .filter(user -> user.getUserId().equals(id))
                                            .findAny();
        return mayBeUser;
    }

    public User findByEmailAndPassword(String email, String password){
        Optional<User> mayBeUser = findAll().stream()
                                            .filter(user -> user.getEmail().equals(email))
                                            .findAny();
        if(mayBeUser.isEmpty()){
            throw new IllegalAuthParameterException("Have no " + email + " in DB.");
        } else if(!mayBeUser.get().getPassword().equals(password)){
            throw new IllegalAuthParameterException("Wrong entered password.");
        } else if(isDeleted(mayBeUser.get()) || isBlocked(mayBeUser.get())){
            throw new IllegalUserConditionException("User is blocked or delete");
        }

        return mayBeUser.get();
    }

    @Override
    public boolean delete(Long id) {
        Optional<User> mayBeUser = findAll().stream()
                                            .filter(user -> user.getUserId().equals(id))
                                            .findAny();
        if(mayBeUser.isPresent()){
            userDB.getUserDb().remove(mayBeUser.get());
            return true;
        } else {
            return false;
        }
    }

    public boolean setDeleteStatus(Long id) {
        Optional<User> mayBeUser = findAll().stream()
                                            .filter(user -> user.getUserId().equals(id))
                                            .findAny();
        if(mayBeUser.isPresent()){
            int index = userDB.getUserDb().indexOf(mayBeUser.get());
            User deletedStatusUser = mayBeUser.get();
            deletedStatusUser.setAuth(Auth.IS_DELETE);
            userDB.getUserDb().set(index, deletedStatusUser);
            return true;
        } else {
            return false;
        }
    }

    public boolean setBlockedStatus(Long id) {
        Optional<User> mayBeUser = findAll().stream()
                .filter(user -> user.getUserId().equals(id))
                .findAny();
        if(mayBeUser.isPresent()){
            int index = userDB.getUserDb().indexOf(mayBeUser.get());
            User deletedStatusUser = mayBeUser.get();
            deletedStatusUser.setAuth(Auth.IS_BLOCKED);
            userDB.getUserDb().set(index, deletedStatusUser);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void update(User entity) {

        if(!isEmailUnique(entity)){
            throw new NotUniqueEmailException("Not unique entered Email");
        } else if (!isPasswordUnique(entity)) {
            throw new NotUniquePasswordException("Not unique entered Password");
        }

        Optional<User> mayBeUser = findById(entity.getUserId());

        if(mayBeUser.isPresent()){
            int index = findAll().indexOf(mayBeUser.get());
            findAll().set(index, entity);
        }
    }

    @Override
    public User save(User entity) {

        if(!isEmailUnique(entity)){
            throw new NotUniqueEmailException("Not unique entered Email");
        } else if (!isPasswordUnique(entity)) {
            throw new NotUniquePasswordException("Not unique entered Password");
        }

        Long currentId = userDB.getCurrentIdCount();
        Long newUserId = currentId + 1L;

        findAll().add(userBuilder(newUserId, entity));

        return findById(newUserId).get();
    }

    private boolean isEmailUnique(User entity){
        Optional<User> mayBeUser = findAll().stream()
                                            .filter(user -> user.getEmail().equals(entity.getEmail()))
                                            .findAny();

        return conditionQualifier(mayBeUser);
    }

    private boolean isPasswordUnique(User entity){
        Optional<User> mayBeUser = findAll().stream()
                                            .filter(user -> user.getPassword().equals(entity.getPassword()))
                                            .findAny();

        return conditionQualifier(mayBeUser);
    }

    private boolean isDeleted(User entity){
        Optional<User> mayBeUser = findById(entity.getUserId());
        if(mayBeUser.get().getAuth().equals(Auth.IS_DELETE)){
            return true;
        }
            return false;
    }

    private boolean isBlocked(User entity){
        Optional<User> mayBeUser = findById(entity.getUserId());
        if(mayBeUser.get().getAuth().equals(Auth.IS_BLOCKED)){
            return true;
        }
            return false;
    }
    
    private boolean conditionQualifier(Optional<User> mayBeUniqueUser){
        if(mayBeUniqueUser.isEmpty()){
            return true;
        } else {
            return false;
        }
    }

    private User userBuilder(Long userId, User entity){
        return User.builder()
                   .userId(userId)
                   .userName(entity.getUserName())
                   .email(entity.getEmail())
                   .password(entity.getPassword())
                   .role(entity.getRole())
                   .auth(entity.getAuth())
                   .build();
    }
}
