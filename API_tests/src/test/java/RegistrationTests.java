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

    @Test
    void whenVisitorEntersInvalidFormatFirstName_theReturn400AndResponseBody() {

        given().body(
                        """
                {
                    "firstName": "testas",
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
                .statusCode(400)
                .body(
                        "size()",
                        is(1),
                        "firstName",
                        equalTo(
                                "You can only enter letters. First letter must be capital. At least 2 characters long"));
    }

    @Test
    void whenVisitorEntersEmptyFirstName_theReturn400AndResponseBody() {

        given().body(
                        """
                {
                    "firstName": "",
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
                .statusCode(400)
                .body("size()", is(1), "firstName", equalTo("Cannot be null or empty"));
    }

    @Test
    void whenFieldFirstNameNotExists_theReturn400AndResponseBody() {

        given().body(
                        """
                {
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
                .statusCode(400)
                .body("size()", is(1), "firstName", equalTo("Cannot be null or empty"));
    }

    @Test
    void whenVisitorEntersFirstNameWithNumber_theReturn400AndResponseBody() {

        given().body(
                        """
                {
                    "firstName": "Toma3",
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
                .statusCode(400)
                .body(
                        "size()",
                        is(1),
                        "firstName",
                        equalTo(
                                "You can only enter letters. First letter must be capital. At least 2 characters long"));
    }

    @Test
    void whenVisitorEntersOneLetterFirstName_theReturn400AndResponseBody() {

        given().body(
                        """
                {
                    "firstName": "T",
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
                .statusCode(400)
                .body(
                        "size()",
                        is(1),
                        "firstName",
                        equalTo(
                                "You can only enter letters. First letter must be capital. At least 2 characters long"));
    }

    @Test
    void whenVisitorEntersSmallLettersLastName_theReturn400AndResponseBody() {

        given().body(
                        """
                {
                    "firstName": "Testas",
                    "lastName": "testukaitis",
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
                .statusCode(400)
                .body(
                        "size()",
                        is(1),
                        "lastName",
                        equalTo(
                                "You can only enter letters. First letter must be capital. At least 2 characters long"));
    }

    @Test
    void whenVisitorEntersLastNameWithNumbers_theReturn400AndResponseBody() {

        given().body(
                        """
                {
                    "firstName": "Testas",
                    "lastName": "Testukaitis3",
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
                .statusCode(400)
                .body(
                        "size()",
                        is(1),
                        "lastName",
                        equalTo(
                                "You can only enter letters. First letter must be capital. At least 2 characters long"));
    }

    @Test
    void whenVisitorEntersOneSymbolLastName_theReturn400AndResponseBody() {

        given().body(
                        """
                {
                    "firstName": "Testas",
                    "lastName": "T",
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
                .statusCode(400)
                .body(
                        "size()",
                        is(1),
                        "lastName",
                        equalTo(
                                "You can only enter letters. First letter must be capital. At least 2 characters long"));
    }

    @Test
    void whenVisitorEntersEmptyLastName_theReturn400AndResponseBody() {

        given().body(
                        """
                {
                    "firstName": "Testas",
                    "lastName": "",
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
                .statusCode(400)
                .body("size()", is(1), "lastName", equalTo("Cannot be null or empty"));
    }

    @Test
    void whenFieldLastNameDoesNotExist_theReturn400AndResponseBody() {

        given().body(
                        """
                {
                    "firstName": "Testas",
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
                .statusCode(400)
                .body("size()", is(1), "lastName", equalTo("Cannot be null or empty"));
    }

    @Test
    void whenVisitorEntersPasswordWithoutNumber_theReturn400AndResponseBody() {

        given().body(
                        """
                {
                    "firstName": "Testas",
                    "lastName": "Testukaitis",
                    "country": "Lithuania",
                    "password": "Testukas*",
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
                .statusCode(400)
                .body(
                        "size()",
                        is(1),
                        "password",
                        equalTo(
                                "Must contain at least one uppercase letter, one lowercase letter, one number, and any one of these special symbols: !@#$%^&*"));
    }

    @Test
    void whenVisitorEntersPasswordWithoutUppercase_theReturn400AndResponseBody() {

        given().body(
                        """
                {
                    "firstName": "Testas",
                    "lastName": "Testukaitis",
                    "country": "Lithuania",
                    "password": "testukas*3",
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
                .statusCode(400)
                .body(
                        "size()",
                        is(1),
                        "password",
                        equalTo(
                                "Must contain at least one uppercase letter, one lowercase letter, one number, and any one of these special symbols: !@#$%^&*"));
    }

    @Test
    void whenVisitorEntersPasswordWithoutLowercase_theReturn400AndResponseBody() {

        given().body(
                        """
                {
                    "firstName": "Testas",
                    "lastName": "Testukaitis",
                    "country": "Lithuania",
                    "password": "TESTUKAS*3",
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
                .statusCode(400)
                .body(
                        "size()",
                        is(1),
                        "password",
                        equalTo(
                                "Must contain at least one uppercase letter, one lowercase letter, one number, and any one of these special symbols: !@#$%^&*"));
    }

    @Test
    void whenVisitorEntersPasswordWithoutSpecialSymbols_theReturn400AndResponseBody() {

        given().body(
                        """
                {
                    "firstName": "Testas",
                    "lastName": "Testukaitis",
                    "country": "Lithuania",
                    "password": "Testukas3",
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
                .statusCode(400)
                .body(
                        "size()",
                        is(1),
                        "password",
                        equalTo(
                                "Must contain at least one uppercase letter, one lowercase letter, one number, and any one of these special symbols: !@#$%^&*"));
    }

    //    @Test
    //    void whenVisitorEntersEmptyPassword_theReturn400AndResponseBody() {
    //
    //        given().body(
    //                        """
    //                {
    //                    "firstName": "Testas",
    //                    "lastName": "Testukaitis",
    //                    "country": "Lithuania",
    //                    "password": "",
    //                    "displayName": "Jukava",
    //                    "roles": [
    //                        {
    //                            "id": 1
    //                        }
    //                    ],
    //                    "dateOfBirth": "1903-01-01",
    //                    "email": "jukava@testas.lt"
    //                }
    //                """)
    //                .contentType(ContentType.JSON)
    //                .when()
    //                .request("POST", "/register")
    //                .then()
    //                .assertThat()
    //                .statusCode(400)
    //                .body("size()", is(1), "password", equalTo("Cannot be null or empty"));
    //    }

    @Test
    void whenPasswordFieldDoesNotExist_theReturn400AndResponseBody() {

        given().body(
                        """
                {
                    "firstName": "Testas",
                    "lastName": "Testukaitis",
                    "country": "Lithuania",
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
                .statusCode(400)
                .body("size()", is(1), "password", equalTo("Cannot be null or empty"));
    }

    @Test
    void whenVisitorEntersDisplayNameWithSpecialSymbol_theReturn400AndResponseBody() {

        given().body(
                        """
                {
                    "firstName": "Testas",
                    "lastName": "Testukaitis",
                    "country": "Lithuania",
                    "password": "Testukas123*",
                    "displayName": "J3*",
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
                .statusCode(400)
                .body(
                        "size()",
                        is(1),
                        "displayName",
                        equalTo(
                                "You can only enter letters or numbers. At least 1 character long. Cannot begin or end with a space. No more than one space between words"));
    }

    @Test
    void whenDisplayNameBeginsWithSpace_theReturn400AndResponseBody() {

        given().body(
                        """
                {
                    "firstName": "Testas",
                    "lastName": "Testukaitis",
                    "country": "Lithuania",
                    "password": "Testukas123*",
                    "displayName": " J3",
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
                .statusCode(400)
                .body(
                        "size()",
                        is(1),
                        "displayName",
                        equalTo(
                                "You can only enter letters or numbers. At least 1 character long. Cannot begin or end with a space. No more than one space between words"));
    }

    @Test
    void whenDisplayNameEndsWithSpace_theReturn400AndResponseBody() {

        given().body(
                        """
                {
                    "firstName": "Testas",
                    "lastName": "Testukaitis",
                    "country": "Lithuania",
                    "password": "Testukas123*",
                    "displayName": "J3 ",
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
                .statusCode(400)
                .body(
                        "size()",
                        is(1),
                        "displayName",
                        equalTo(
                                "You can only enter letters or numbers. At least 1 character long. Cannot begin or end with a space. No more than one space between words"));
    }

    @Test
    void whenVisitorEntersEmptyDisplayName_theReturn400AndResponseBody() {

        given().body(
                        """
                {
                    "firstName": "Testas",
                    "lastName": "Testukaitis",
                    "country": "Lithuania",
                    "password": "Testukas123*",
                    "displayName": "",
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
                .statusCode(400)
                .body("size()", is(1), "displayName", equalTo("Cannot be null or empty"));
    }

    @Test
    void whenDisplayNameFieldDoesNotExist_theReturn400AndResponseBody() {

        given().body(
                        """
                {
                    "firstName": "Testas",
                    "lastName": "Testukaitis",
                    "country": "Lithuania",
                    "password": "Testukas123*",
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
                .statusCode(400)
                .body("size()", is(1), "displayName", equalTo("Cannot be null or empty"));
    }

    @Test
    void whenDisplayNameHasMoreThanOneSpaceBetweenWords_theReturn400AndResponseBody() {

        given().body(
                        """
                {
                    "firstName": "Testas",
                    "lastName": "Testukaitis",
                    "country": "Lithuania",
                    "password": "Testukas123*",
                    "displayName": "J  3",
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
                .statusCode(400)
                .body(
                        "size()",
                        is(1),
                        "displayName",
                        equalTo(
                                "You can only enter letters or numbers. At least 1 character long. Cannot begin or end with a space. No more than one space between words"));
    }

    @Test
    void whenVisitorEntersTodayDateOfBirth_theReturn400AndResponseBody() {

        given().body(
                        """
                {
                    "firstName": "Testas",
                    "lastName": "Testukaitis",
                    "country": "Lithuania",
                    "password": "Testukas123*",
                    "displayName": "J3",
                    "roles": [
                        {
                            "id": 1
                        }
                    ],
                    "dateOfBirth": "2024-07-16",
                    "email": "jukava@testas.lt"
                }
                """)
                .contentType(ContentType.JSON)
                .when()
                .request("POST", "/register")
                .then()
                .assertThat()
                .statusCode(400)
                .body(
                        "size()",
                        is(1),
                        "dateOfBirth",
                        equalTo("Cannot be older than the year 1900, or younger than 13 years old"));
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
