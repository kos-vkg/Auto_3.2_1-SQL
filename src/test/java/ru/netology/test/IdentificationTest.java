package ru.netology.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.manager.DbManager;
import ru.netology.page.LoginPage;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;

public class IdentificationTest {

    DbManager manager = new DbManager();

    @BeforeEach
    void setUp() throws SQLException {
        manager.clearTables();
        manager.setUp();
    }

    @Test
    void shouldInitCardsPage() throws SQLException {
        open("http://localhost:9999");
        String userId = DataHelper.getAuthInfo().getId();
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = manager.getSmsCode(userId);   //DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }

    @AfterEach
    void deleteTabs() throws SQLException {
        manager.clearTables();
    }
}
