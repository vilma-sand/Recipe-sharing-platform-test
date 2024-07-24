package org.Vilma;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class HomePageTest extends BasePageTest {


    //HAPPY tests
    @Test
    void whenVisitorClickRegisterLink_userIsMovedToPageRegister() {
        HomePage homePage = new HomePage(driver);
        homePage.clickLinkRegisterAndWaitUrl();
        assertEquals("http://localhost:5173/register", driver.getCurrentUrl(), "Current URL is not as expected");
    }

    @Test
    void whenVisitorClickHomeLink_userIsMovedToPageHome() {
        HomePage homePage = new HomePage(driver);
        RegistrationPage registrationPage = new RegistrationPage(driver);
        homePage.clickLinkRegisterAndWaitUrl();
        registrationPage.clickLinkHome();
        assertEquals("http://localhost:5173/", driver.getCurrentUrl(), "Current URL is not as expected");
    }
}
