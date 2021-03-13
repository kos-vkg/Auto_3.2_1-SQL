package ru.netology.manager;

import lombok.NoArgsConstructor;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import ru.netology.data.AuthCode;
import ru.netology.data.DataHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@NoArgsConstructor

public class DbManager {

    public String getSmsCode(String userId) throws SQLException {
        String usersSQL = "SELECT * FROM auth_codes WHERE user_id = ?;";
        var runner = new QueryRunner();

        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                )
        ) {
            AuthCode first = runner.query(conn, usersSQL, userId, new BeanHandler<>(AuthCode.class));
            System.out.println(first.getCode());
            return first.getCode();
        }
    }

    public void setUp() throws SQLException {
        val runner = new QueryRunner();
        val dataSQL = "INSERT INTO users(id,login, password,status) VALUES (?, ?, ?, ?);";

        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                )

        ) {
            // обычная вставка
            runner.update(conn, dataSQL, DataHelper.getAuthInfo().getId(), DataHelper.getAuthInfo().getLogin(), DataHelper.getAuthInfo().getPassCode(), "active");
            runner.update(conn, dataSQL, DataHelper.getOtherAuthInfo().getId(), DataHelper.getOtherAuthInfo().getLogin(), DataHelper.getOtherAuthInfo().getPassCode(), "active");
        }
    }


    public void clearTables() throws SQLException {
        val runner = new QueryRunner();
        String delUsersSQL = "DELETE FROM users;";
        String delCardsSQL = "DELETE FROM cards;";
        String delAuthSQL = "DELETE FROM auth_codes;";
        String delTransSQL = "DELETE FROM card_transactions;";

        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                )
        ) {
            // очистка таблиц
            runner.update(conn, delTransSQL);
            runner.update(conn, delCardsSQL);
            runner.update(conn, delAuthSQL);
            runner.update(conn, delUsersSQL);
        }
    }
}
