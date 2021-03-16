package ru.netology.manager;


import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import ru.netology.data.AuthCode;
import ru.netology.data.DataHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public final class DbManager {


    public static String getSmsCode(String userId) {
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
        } catch (SQLException e) {
            System.out.println("Не удалось получить код доступа к базе данных");
        }
        return "";
    }

    public static void setUp() {
        var runner = new QueryRunner();
        String dataSQL = "INSERT INTO users(id,login, password,status) VALUES (?, ?, ?, ?);";

        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                )

        ) {
            // обычная вставка
            runner.update(conn, dataSQL, DataHelper.getAuthInfo().getId(), DataHelper.getAuthInfo().getLogin(), DataHelper.getAuthInfo().getPassCode(), "active");
            runner.update(conn, dataSQL, DataHelper.getOtherAuthInfo().getId(), DataHelper.getOtherAuthInfo().getLogin(), DataHelper.getOtherAuthInfo().getPassCode(), "active");
        } catch (SQLException e) {
            System.out.println("Не удалось загрузить данные в таблищы базы данных");
        }
    }


    public static void clearTables() {
        var runner = new QueryRunner();
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
        } catch (SQLException e) {
            System.out.println("Не удалось очистить таблицы базы данных");
        }
    }
}
