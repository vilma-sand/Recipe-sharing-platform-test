
import io.restassured.RestAssured;

import io.restassured.http.ContentType;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

public class RegistrationTests {

    @Test

    void whenVisitorRegistersSuccessfully_thenReturn201AndResponseBody() {

        given()

                .body("""

                {

                    "firstName": "Testas",

                    "lastName": "Testukaitis",

                    "country": "Lithuania",

                    "password": "Testas123*",

                    "displayName": "Jukava",

                    "roles": [

                        {

                            "id": 1

                        }

                    ],

                    "dateOfBirth": "1903-01-01",

                    "email": "jukava@testas.lt"

                }

                """)

                .contentType(ContentType.JSON)

                .when()

                .request("POST", "/register")

                .then()

                .assertThat()

                .statusCode(201)
                .body(
                        "id",
                        not(equalTo(0)),
                        "firstName",
                        equalTo("Testas"),
                        "lastName",
                        equalTo("Testukaitis"),
                        "country",
                        equalTo("Lithuania"),
                        "password",
                        not(equalTo("Testas123*")),
                        "displayName",
                        equalTo("Jukava"),
                        "roles.id",
                        contains(1),
                        "authorities.id",
                        contains(1),
                        "dateOfBirth",
                        equalTo("1903-01-01"),
                        "gender",
                        equalTo(null),
                        "username",
                        equalTo("jukava@testas.lt"),
                        "email",
                        equalTo("jukava@testas.lt"),
                        "enabled",
                        equalTo(true),
                        "accountNonLocked",
                        equalTo(true),
                        "accountNonExpired",
                        equalTo(true),
                        "credentialsNonExpired",
                        equalTo(true)


                );

    }
    @BeforeEach
    void cleanUpDatabase() throws SQLException {
        Connection connection = DriverManager.getConnection(
                "jdbc:h2:tcp://localhost/~/recipe-sharing-platform/backend/rsp", "sa", "");
        String deleteQuery = "DELETE FROM users_roles; DELETE FROM users;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



