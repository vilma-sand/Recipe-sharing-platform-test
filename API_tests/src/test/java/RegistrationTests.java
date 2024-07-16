import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class RegistrationTests {

    @Test
    void whenVisitorRegistersSuccessfully_thenReturn201AndResponseBody() {
        given().body(
                        """
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
                        "roles",
                        hasSize(1),
                        "roles[0].id",
                        equalTo(1),
                        "authorities",
                        hasSize(1),
                        "authorities[0].id",
                        equalTo(1),
                        "dateOfBirth",
                        equalTo("1903-01-01"),
                        "gender",
                        nullValue(),
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
                        equalTo(true));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/validGenders.csv")
    void whenVisitorChoosesGenderAndRegistersSuccessfully_thenReturn201AndResponseBody(String input) {

        given().body(
                        """
                {
                    "firstName": "Vardenis",
                    "lastName": "Pavardenis",
                    "country": "Lithuania",
                    "password": "Testas1*",
                    "displayName": "Mokytojas",
                    "roles": [
                        {"id": 1}
                    ],
                    "dateOfBirth": "1900-01-01",
                    "email": "vardens.pavardenis@techin.lt",
                    "gender": "%s"
                }
                """
                                .formatted(input))
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
                        equalTo("Vardenis"),
                        "lastName",
                        equalTo("Pavardenis"),
                        "displayName",
                        equalTo("Mokytojas"),
                        "email",
                        equalTo("vardens.pavardenis@techin.lt"),
                        "password",
                        not(equalTo("Testas1*")),
                        "dateOfBirth",
                        equalTo("1900-01-01"),
                        "gender",
                        equalTo(input),
                        "country",
                        equalTo("Lithuania"),
                        "roles",
                        hasSize(1),
                        "roles[0].id",
                        equalTo(1),
                        "authorities",
                        hasSize(1),
                        "authorities[0].id",
                        equalTo(1),
                        "username",
                        equalTo("vardens.pavardenis@techin.lt"),
                        "accountNonLocked",
                        equalTo(true),
                        "accountNonExpired",
                        equalTo(true),
                        "credentialsNonExpired",
                        equalTo(true),
                        "enabled",
                        equalTo(true));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/validGenders.csv")
    void whenVisitorChoosesGenderAndTwoRolesAndRegistersSuccessfully_thenReturn201AndResponseBody(String input) {

        given().body(
                        """
                {
                    "firstName": "Vardenis",
                    "lastName": "Pavardenis",
                    "country": "Lithuania",
                    "password": "Testas1*",
                    "displayName": "Mokytojas",
                    "roles": [
                        {"id": 1},
                        {"id": 2}
                    ],
                    "dateOfBirth": "1900-01-01",
                    "email": "vardens.pavardenis@techin.lt",
                    "gender": "%s"
                }
                """
                                .formatted(input))
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
                        equalTo("Vardenis"),
                        "lastName",
                        equalTo("Pavardenis"),
                        "displayName",
                        equalTo("Mokytojas"),
                        "email",
                        equalTo("vardens.pavardenis@techin.lt"),
                        "password",
                        not(equalTo("Testas1*")),
                        "dateOfBirth",
                        equalTo("1900-01-01"),
                        "gender",
                        equalTo(input),
                        "country",
                        equalTo("Lithuania"),
                        "roles",
                        hasSize(2),
                        "roles[0].id",
                        equalTo(1),
                        "roles[1].id",
                        equalTo(2),
                        "authorities",
                        hasSize(2),
                        "authorities[0].id",
                        equalTo(1),
                        "authorities[1].id",
                        equalTo(2),
                        "username",
                        equalTo("vardens.pavardenis@techin.lt"),
                        "accountNonLocked",
                        equalTo(true),
                        "accountNonExpired",
                        equalTo(true),
                        "credentialsNonExpired",
                        equalTo(true),
                        "enabled",
                        equalTo(true));
    }

    @BeforeEach
    void cleanUpDatabase() throws SQLException {
        Connection connection =
                DriverManager.getConnection("jdbc:h2:tcp://localhost/~/recipe-sharing-platform/backend/rsp", "sa", "");
        String deleteQuery = "DELETE FROM users_roles; DELETE FROM users;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
