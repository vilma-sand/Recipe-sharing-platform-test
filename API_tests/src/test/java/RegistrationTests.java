import io.restassured.http.ContentType;
import io.restassured.response.Response;
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
                        "size()",
                        is(16),
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
                        "size()",
                        is(16),
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
                        "size()",
                        is(16),
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
    void whenVisitorSkipsGenderField_theReturn201AndResponseBody() {

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
                    "dateOfBirth": "2001-07-01",
                    "gender": null,
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
                        "size()",
                        is(16),
                        "id",
                        not(equalTo(0)),
                        "firstName",
                        equalTo("Testas"),
                        "lastName",
                        equalTo("Testukaitis"),
                        "displayName",
                        equalTo("Jukava"),
                        "email",
                        equalTo("jukava@testas.lt"),
                        "password",
                        not(equalTo("Testas1*")),
                        "dateOfBirth",
                        equalTo("2001-07-01"),
                        "gender",
                        equalTo(null),
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
                        equalTo("jukava@testas.lt"),
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
    @CsvFileSource(resources = "/invalidGenders.csv")
    void whenVisitorEntersGenderWithFirstLowercase_theReturn400AndResponseBody(String input) {

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
                .statusCode(400)
                .body("size()", is(1), "gender", equalTo("Must be Female, Male, or Other"));
    }

    @Test
    void whenVisitorEntersTwoSameRoles_theReturn400AndResponseBody() {

        given().body(
                        """
                    {
                        "firstName": "Testas",
                        "lastName": "Testukaitis",
                        "country": "Lithuania",
                        "password": "Testas123*",
                        "displayName": "Jukava",
                        "roles": [
                            {"id": 1},
                            {"id": 1}
                        ],
                        "dateOfBirth": "1903-01-01",
                        "gender": null,
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
                        "size()",
                        is(16),
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

    @Test
    void whenVisitorEntersLowercaseFirstName_theReturn400AndResponseBody() {

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
                                "You can only enter English letters. First letter must be capital. At least 2 characters long"));
    }

    @Test
    void whenVisitorEntersFirstNameWithLithuanianLetters_theReturn400AndResponseBody() {

        given().body(
                        """
                {
                    "firstName": "testaš",
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
                                "You can only enter English letters. First letter must be capital. At least 2 characters long"));
    }

    @Test
    void whenVisitorEnters135LettersFirstName_theReturn400AndResponseBody() {

        given().body(
                        """
                        {
                            "firstName":
     "Adrfghjkiuadrfghjkiuadrfghjkiuadrfghjkiuadrfghjkiuadrfghjkiuadrfghjkiuadrfghjkiuadrfghjkiuadrfghjkiuadrfghjkiuadrfghjkiuadrfghjklokiuklihg",
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
                .body("size()", is(1), "firstName", equalTo("Maximum length is 135 characters"));
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
                                "You can only enter English letters. First letter must be capital. At least 2 characters long"));
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
                                "You can only enter English letters. First letter must be capital. At least 2 characters long"));
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
                                "You can only enter English letters. First letter must be capital. At least 2 characters long"));
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
                                "You can only enter English letters. First letter must be capital. At least 2 characters long"));
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
                                "You can only enter English letters. First letter must be capital. At least 2 characters long"));
    }

    @Test
    void whenVisitorEntersEmptyLastName_theReturn400AndResponseBody() {

        given().body(
                        """
                {
                    "firstName": "Testas",
                    "lastName": "Vgtfrdgtfdvgtfrdgtfdvgtfrdgtfdvgtfrdgtfdvgtfrdgtfdvnjhgtfrdgtfdvgtfrdgtfdvgtfrdgtfdvgtfrdgtfdvgtfrdgtfd",
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
                .body("size()", is(1), "lastName", equalTo("Maximum length is 100 characters"));
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
    void whenvisitorEntersLastNameWithLithuanianLetters_theReturn400AndResponseBody() {

        given().body(
                        """
                {
                    "firstName": "Testas",
                    "lastName": "Lahbaš",
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
                                "You can only enter English letters. First letter must be capital. At least 2 characters long"));
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

    @Test
    void whenVisitorEntersPasswordWithMoreThan255Symbols_theReturn400AndResponseBody() {

        given().body(
                        """
                    {
                        "firstName": "Testas",
                        "lastName": "Testukaitis",
                        "country": "Lithuania",
                        "password": "T*1hfgdfstergfdsgtjghfdgtijdhfgdfstergfdsgtkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkjghfdgtijdhfgdfstergfdsgtjghfdgtijdhfgdfstergfdsgtjghfdgtijdhfgdfstergfdsgtjghfdgtijdhfgdfstergfdsgtjghfdgtijdhfgdfstergfdsgtjghfdgtijdhfgdfstergfdsgtjghfdgtijdhfgdfstergfdsgtjghfdgtijdhfgdfstergfdsgtjghfdgtijdhfgdfstergfdsgtjghfdgtij",
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
                        equalTo("Minimum length 8 characters, maximum length 255 characters"));
    }

    @Test
    void whenVisitorEntersPasswordWithLithuanianLetters_theReturn400AndResponseBody() {

        given().body(
                        """
                    {
                        "firstName": "Testas",
                        "lastName": "Testukaitis",
                        "country": "Lithuania",
                        "password": "d*1hfgdfsttjghfdgtijššš",
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
                                "You can only enter English letters or numbers. At least 1 character long. Cannot begin or end with a space. No more than one space between words"));
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
                                "You can only enter English letters or numbers. At least 1 character long. Cannot begin or end with a space. No more than one space between words"));
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
                                "You can only enter English letters or numbers. At least 1 character long. Cannot begin or end with a space. No more than one space between words"));
    }

    @Test
    void whenVisitorEntersDisplayNameWithMoreThan255Symbols_theReturn400AndResponseBody() {

        given().body(
                        """
                {
                    "firstName": "Testas",
                    "lastName": "Testukaitis",
                    "country": "Lithuania",
                    "password": "Testukas123*",
                    "displayName": "hfgtdshfuhgtdjkfjygfdhnhgjhfgtdshfuhgtdjkfjygfdhnhgjhfgtdshfuhgtdjkfjygfdhnhgjhfgtdshfuhgtdjkfjygfdhnhgjhfgtdshfuhgtdjkfjygfdhnhgjvvvhfgtdshfuhgtdjkfjygfdhnhgjhfgtdshfuhgtdjkfjygfdhnhgjhfgtdshfuhgtdjkfjygfdhnhgjhfgtdshfuhgtdjkfjygfdhnhgjhfgtdshfuhgtdjkfjygfdhnhgjhfgtdshfuhgtdjkfjygfdhnhgj",
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
                .body("size()", is(1), "displayName", equalTo("Maximum length is 255 characters"));
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
                                "You can only enter English letters or numbers. At least 1 character long. Cannot begin or end with a space. No more than one space between words"));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/swearWords.csv")
    void whenDisplayNameContainsInappropriateLanguage_theReturn400AndResponseBody(String input) {

        given().body(
                        """
                {
                    "firstName": "Vardenis",
                    "lastName": "Pavardenis",
                    "country": "Lithuania",
                    "password": "Testas1*",
                    "displayName": "%s",
                    "roles": [
                        {"id": 1}
                    ],
                    "dateOfBirth": "1900-01-01",
                    "email": "vardens.pavardenis@techin.lt",
                    "gender": "Female"
                }
                """
                                .formatted(input))
                .contentType(ContentType.JSON)
                .when()
                .request("POST", "/register")
                .then()
                .assertThat()
                .statusCode(400)
                .body("size()", is(1), "displayName", equalTo("Display name contains inappropriate language"));
    }

    @Test
    void testDisplayNameIsUnique_theReturn400AndResponseBody() {
        given().body(
                        """
                        {
                            "firstName": "Testas",
                            "lastName": "Testukaitis",
                            "country": "Lithuania",
                            "password": "Testukas123*",
                            "displayName": "Jonasas",
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
                .post("/register")
                .then()
                .assertThat()
                .statusCode(201);

        Response response = given().contentType(ContentType.JSON)
                .body(
                        """
                        {
                            "firstName": "Testas",
                            "lastName": "Testukaitis",
                            "country": "Lithuania",
                            "password": "Testukas123*",
                            "displayName": "Jonasas",
                            "roles": [
                                {
                                    "id": 1
                                }
                            ],
                            "dateOfBirth": "1903-01-01",
                            "email": "jukava@testas.lt"
                        }
                        """)
                .when()
                .post("/register");

        response.then().statusCode(400).body("displayName", equalTo("Already exists"));
    }

    @Test
    void whenVisitorEntersLaterThan1900DateOfBirth_theReturn400AndResponseBody() {

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
                    "dateOfBirth": "1800-07-16",
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

    @Test
    void whenVisitorEntersFutureDateOfBirth_theReturn400AndResponseBody() {

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
                    "dateOfBirth": "2026-07-01",
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

    @Test
    void whenDateOfBirthFieldDoesNotExist_theReturn400AndResponseBody() {

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
                    "email": "jukava@testas.lt"
                }
                """)
                .contentType(ContentType.JSON)
                .when()
                .request("POST", "/register")
                .then()
                .assertThat()
                .statusCode(400)
                .body("size()", is(1), "dateOfBirth", equalTo("Cannot be null or empty"));
    }

    @Test
    void whenVisitorEntersNotPosibleDateOfBirth_theReturn400AndResponseBody() {

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
                    "dateOfBirth": "2026-07-00",
                    "email": "jukava@testas.lt"
                }
                """)
                .contentType(ContentType.JSON)
                .when()
                .request("POST", "/register")
                .then()
                .assertThat()
                .statusCode(400)
                .body("size()", is(1), "dateOfBirth", equalTo("Cannot be null or empty"));
    }

    @Test
    void whenVisitorEntersInvalidDateOfBirthDateFormat_theReturn400AndResponseBody() {

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
                    "dateOfBirth": "201307-05",
                    "email": "jukava@testas.lt"
                }
                """)
                .contentType(ContentType.JSON)
                .when()
                .request("POST", "/register")
                .then()
                .assertThat()
                .statusCode(400)
                .body("size()", is(1), "dateOfBirth", equalTo("Cannot be null or empty"));
    }

    @Test
    void whenVisitorEntersCountryNameWithFirstLowercaseLetter_theReturn400AndResponseBody() {

        given().body(
                        """
                {
                    "firstName": "Testas",
                    "lastName": "Testukaitis",
                    "country": "south Korea",
                    "password": "Testukas123*",
                    "displayName": "J3",
                    "roles": [
                        {
                            "id": 1
                        }
                    ],
                    "dateOfBirth": "1800-07-16",
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
                        "country",
                        equalTo(
                                "Must be a valid country name. It should start with an uppercase letter. English only"));
    }

    @Test
    void whenVisitorEntersInvalidCountryName_theReturn400AndResponseBody() {

        given().body(
                        """
                {
                    "firstName": "Testas",
                    "lastName": "Testukaitis",
                    "country": "South",
                    "password": "Testukas123*",
                    "displayName": "J3",
                    "roles": [
                        {
                            "id": 1
                        }
                    ],
                    "dateOfBirth": "1800-07-16",
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
                        "country",
                        equalTo(
                                "Must be a valid country name. It should start with an uppercase letter. English only"));
    }

    @Test
    void whenCountryFieldDoesNotExist_theReturn400AndResponseBody() {

        given().body(
                        """
                {
                    "firstName": "Testas",
                    "lastName": "Testukaitis",
                    "password": "Testukas123*",
                    "displayName": "J3",
                    "roles": [
                        {
                            "id": 1
                        }
                    ],
                    "dateOfBirth": "1800-07-16",
                    "email": "jukava@testas.lt"
                }
                """)
                .contentType(ContentType.JSON)
                .when()
                .request("POST", "/register")
                .then()
                .assertThat()
                .statusCode(400)
                .body("size()", is(1), "country", equalTo("Cannot be null or empty"));
    }

    @Test
    void whenVisitorEntersInvalidFormatEmailWithoutDot_theReturn400AndResponseBody() {

        given().body(
                        """
                    {
                        "firstName": "Testas",
                        "lastName": "Testukaitis",
                        "country": "Lithuania",
                        "password": "Testukas123*",
                        "displayName": "J3",
                        "gender": "Male",
                        "roles": [
                            {
                                "id": 1
                            }
                        ],
                        "dateOfBirth": "1906-07-16",
                        "email": "jukava@testaslt"
                    }
                    """)
                .contentType(ContentType.JSON)
                .when()
                .request("POST", "/register")
                .then()
                .assertThat()
                .statusCode(400)
                .body("size()", is(1), "email", equalTo("Does not match correct email format"));
    }

    @Test
    void whenVisitorEntersInvalidFormatEmailWithoutEta_theReturn400AndResponseBody() {

        given().body(
                        """
                    {
                        "firstName": "Testas",
                        "lastName": "Testukaitis",
                        "country": "Lithuania",
                        "password": "Testukas123*",
                        "displayName": "J3",
                        "gender": "Male",
                        "roles": [
                            {
                                "id": 1
                            }
                        ],
                        "dateOfBirth": "1906-07-16",
                        "email": "jukavatestas.lt"
                    }
                    """)
                .contentType(ContentType.JSON)
                .when()
                .request("POST", "/register")
                .then()
                .assertThat()
                .statusCode(400)
                .body("size()", is(1), "email", equalTo("Does not match correct email format"));
    }

    @Test
    void whenEmailFieldDoesNotExists_theReturn400AndResponseBody() {

        given().body(
                        """
                    {
                        "firstName": "Testas",
                        "lastName": "Testukaitis",
                        "country": "Lithuania",
                        "password": "Testukas123*",
                        "displayName": "J3",
                        "gender": "Male",
                        "roles": [
                            {
                                "id": 1
                            }
                        ],
                        "dateOfBirth": "1906-07-16"
                    }
                    """)
                .contentType(ContentType.JSON)
                .when()
                .request("POST", "/register")
                .then()
                .assertThat()
                .statusCode(400)
                .body("size()", is(1), "email", equalTo("Cannot be null or empty"));
    }

    @Test
    void whenEmailLengthIsLessThan5Characters_theReturn400AndResponseBody() {

        given().body(
                        """
                    {
                        "firstName": "Testas",
                        "lastName": "Testukaitis",
                        "country": "Lithuania",
                        "password": "Testukas123*",
                        "displayName": "Jeva",
                        "gender": "Male",
                        "roles": [
                            {
                                "id": 1
                            }
                        ],
                        "dateOfBirth": "1906-07-16",
                         "email": "a@s."
                    }
                    """)
                .contentType(ContentType.JSON)
                .when()
                .request("POST", "/register")
                .then()
                .assertThat()
                .statusCode(400)
                .body("size()", is(1), "email", equalTo("Minimum length 5 characters, maximum length 200 characters"));
    }

    @Test
    void whenEmailLengthIsMoreThan200Characters_theReturn400AndResponseBody() {

        given().body(
                        """
                    {
                        "firstName": "Testas",
                        "lastName": "Testukaitis",
                        "country": "Lithuania",
                        "password": "Testukas123*",
                        "displayName": "J3",
                        "gender": "Male",
                        "roles": [
                            {
                                "id": 1
                            }
                        ],
                        "dateOfBirth": "1906-07-16",
                         "email": "gdtrsfdrtshgsgsrdjkfhgfgdtrsfdgdtgdgdgdtrsfdghdtrsfdrtshgsgsrdjkfhgfrtshgsgsrdjkfhgftrsfdrtshgsgsrdjkfhgftrsfdrtshgsgsrdjkfhgfrsfdrtshgsgsrdjkfhgfrtshggdtrsfdrtshgsgsrdjkfhgfgdtrsfdrtshgsgsrdjkfhgfgdtrsfdrtshgsgsrdjkfhgfsgsrdjkfhgf@s.lt"
                    }
                    """)
                .contentType(ContentType.JSON)
                .when()
                .request("POST", "/register")
                .then()
                .assertThat()
                .statusCode(400)
                .body("size()", is(1), "email", equalTo("Minimum length 5 characters, maximum length 200 characters"));
    }

    @Test
    void whenVisitorEntersEmailWithLithuanianLetters_theReturn400AndResponseBody() {

        given().body(
                        """
                    {
                        "firstName": "Testas",
                        "lastName": "Testukaitis",
                        "country": "Lithuania",
                        "password": "Testukas123*",
                        "displayName": "J3",
                        "gender": "Male",
                        "roles": [
                            {
                                "id": 1
                            }
                        ],
                        "dateOfBirth": "1906-07-16",
                        "email": "jukavašė@testas.lt"
                    }
                    """)
                .contentType(ContentType.JSON)
                .when()
                .request("POST", "/register")
                .then()
                .assertThat()
                .statusCode(400)
                .body("size()", is(1), "email", equalTo("Does not match correct email format"));
    }

    @Test
    void whenVisitorEntersEmailWithCapitalsLetters_theReturn400AndResponseBody() {

        given().body(
                        """
                    {
                        "firstName": "Testas",
                        "lastName": "Testukaitis",
                        "country": "Lithuania",
                        "password": "Testukas123*",
                        "displayName": "J3",
                        "gender": "Male",
                        "roles": [
                            {
                                "id": 1
                            }
                        ],
                        "dateOfBirth": "1906-07-16",
                        "email": "JUKOVA@testas.lt"
                    }
                    """)
                .contentType(ContentType.JSON)
                .when()
                .request("POST", "/register")
                .then()
                .assertThat()
                .statusCode(400)
                .body("size()", is(1), "email", equalTo("Does not match correct email format"));
    }

    @Test
    void testEmailIsUnique_theReturn400AndResponseBody() {
        given().body(
                        """
                        {
                            "firstName": "Testas",
                            "lastName": "Testukaitis",
                            "country": "Lithuania",
                            "password": "Testukas123*",
                            "displayName": "Jonasass",
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
                .post("/register")
                .then()
                .assertThat()
                .statusCode(201);

        Response response = given().contentType(ContentType.JSON)
                .body(
                        """
                        {
                            "firstName": "Testas",
                            "lastName": "Testukaitis",
                            "country": "Lithuania",
                            "password": "Testukas123*",
                            "displayName": "Jonasas",
                            "roles": [
                                {
                                    "id": 1
                                }
                            ],
                            "dateOfBirth": "1903-01-01",
                            "email": "jukava@testas.lt"
                        }
                        """)
                .when()
                .post("/register");

        response.then().statusCode(400).body("email", equalTo("Already exists"));
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
