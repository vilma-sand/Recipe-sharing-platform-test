
import io.restassured.RestAssured;

import io.restassured.http.ContentType;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class RegistrationTests {

    @Test

    void whenVisitorRegistersSuccessfully_thenReturn201AndResponseBody() {

        given()

                .body("""

                {

                    "firstName": "Testas",

                    "lastName": "Testas",

                    "country": "Lithuania",

                    "password": "Testas1*",

                    "displayName": "Testuka",

                    "roles": [

                        {

                            "id": 1

                        }

                    ],

                    "dateOfBirth": "1903-01-01",

                    "email": "testas34@testas.lt"

                }

                """)

                .contentType(ContentType.JSON)

                .when()

                .request("POST", "/register")

                .then()

                .assertThat()

                .statusCode(201);

    }
    

}



