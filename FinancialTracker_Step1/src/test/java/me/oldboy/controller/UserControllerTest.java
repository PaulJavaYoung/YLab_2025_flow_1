package me.oldboy.controller;

import me.oldboy.dao.UserDao;
import me.oldboy.entity.User;
import me.oldboy.entity.feature.Auth;
import me.oldboy.entity.feature.Role;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Optional;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @Mock
    private UserDao userDao;
    @InjectMocks
    private UserController userController;

    private ByteArrayInputStream inToScanner;
    private ByteArrayOutputStream outStringData;
    private Scanner scanner;
    private String updateEmail;
    private String updatePassword;
    private String updateName;
    private String answerYes;
    private Long userId;
    private User userForUpdate;

    @BeforeEach
    public void setUp(){
        answerYes = "yes";
        userId = 10L;
        updateEmail = "updateMail@drt.ru";
        updatePassword = "028476";
        updateName = "updateName";
        userForUpdate = User.builder()
                .userId(userId)
                .userName(updateName)
                .email(updateEmail)
                .password(updatePassword)
                .auth(Auth.IS_ACTIVE)
                .role(Role.USER)
                .build();
        MockitoAnnotations.openMocks(this);
    }

    @AfterAll
    public static void systemOutRestoringSettings() {
        System.setOut(System.out);
    }

    @Test
    void update() throws IOException {
        String enterLoginPasswordMail = updateEmail + "\n" + updatePassword + "\n" + updateName;

        inToScanner = new ByteArrayInputStream(enterLoginPasswordMail.getBytes());

        outStringData = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outStringData));

        scanner = new Scanner(inToScanner);

        when(userDao.findById(userId)).thenReturn(Optional.of(userForUpdate));
        userController.update(userId, scanner);

        outStringData.flush();
        String allWrittenLines = new String(outStringData.toByteArray());

        assertTrue(allWrittenLines.contains("Данные обновлены."));
    }

    @Test
    void setDeleteStatus() {
        String enterAnswer = answerYes;

        inToScanner = new ByteArrayInputStream(enterAnswer.getBytes());

        outStringData = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outStringData));

        scanner = new Scanner(inToScanner);

        when(userDao.setDeleteStatus(userId)).thenReturn(true);

        boolean isUpdated = userController.setDeleteStatus(userId, scanner);

        assertTrue(isUpdated);
    }
}