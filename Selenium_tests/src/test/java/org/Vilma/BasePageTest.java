package org.Vilma;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BasePageTest {
    WebDriver driver;

    @BeforeEach
    void cleanUpDatabaseAndSetUpEnvironment() throws SQLException {
        Connection connection = DriverManager.getConnection(
                "jdbc:h2:tcp://localhost/~/recipe-sharing-platform/backend/rsp", "sa", "");

        try {
            // First delete from users_roles
            String deleteUsersRolesQuery = "DELETE FROM users_roles";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteUsersRolesQuery)) {
                preparedStatement.executeUpdate();
            }

            // Then delete from users
            String deleteUsersQuery = "DELETE FROM users";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteUsersQuery)) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        //driver.manage().window().setSize(new Dimension(375, 667));
        driver.get("http://localhost:5173/");
    }


    @AfterEach
    void tearDown() {
        driver.quit();
    }

}
