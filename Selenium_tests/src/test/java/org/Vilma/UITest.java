package org.Vilma;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UITest extends BaseTest {

    //HAPPY tests
    @Test
    void whenVisitorClickRegisterLink_userIsMovedToPageRegister() {
        HomePage homePage = new HomePage(driver);
        homePage.clickLinkRegister();
        assertEquals("http://localhost:5173/register", driver.getCurrentUrl(), "Current URL is not as expected");
    }

    @Test
    void whenVisitorClickHomeLink_userIsMovedToPageHome() {
        HomePage homePage = new HomePage(driver);
        RegistrationPage registrationPage = new RegistrationPage(driver);
        homePage.clickLinkRegister();
        registrationPage.clickLinkHome();
        assertEquals("http://localhost:5173/", driver.getCurrentUrl(), "Current URL is not as expected");
    }

    @Test
    void whenVisitorEntersValidInputs_userLoggedInSuccessfully_displayedSuccessMessage() throws InterruptedException {
            HomePage homePage = new HomePage(driver);
            RegistrationPage registrationPage = new RegistrationPage(driver);

            homePage.clickLinkRegister();

            String inputFirstName = "Tomas";
            registrationPage.enterFirstName(inputFirstName);
            String inputLastName = "Tomasaitis";
            registrationPage.enterLastName(inputLastName);
            String inputDisplayName = "Tomasas";
            registrationPage.enterDisplayName(inputDisplayName);
            String inputEmail = "tomukas23@gmail.com";
            registrationPage.enterEmail(inputEmail);
            String inputPassword = "Tomasa23*";
            registrationPage.enterPassword(inputPassword);
            String inputRepeatPassword = "Tomasa23*";
            registrationPage.enterRepeatPassword(inputRepeatPassword);
            String inputDateOfBirth = "07/06/2011";
            registrationPage.enterDateOfBirth(inputDateOfBirth);
            registrationPage.clickPickYourGenderMale();
            String inputCountryYouResideIn = "Angola";
            registrationPage.enterCountryYouResideIn(inputCountryYouResideIn);
            registrationPage.clickIAcceptPrivacyPolicy();
            registrationPage.scrollDown();
            Thread.sleep(2000);
            registrationPage.clickSubmitButton();
            Thread.sleep(2000);
            assertTrue(driver.getPageSource().contains("You have registered successfully. You can now log in"), "Success message is not as expected");
            assertEquals("http://localhost:5173/", driver.getCurrentUrl(), "Current URL is not as expected");
        }

    //UNHAPPY tests

    @Test
    void whenVisitorSubmitEmptyRegistrationForm_displayedErrorMessages() throws InterruptedException {
        HomePage homePage = new HomePage(driver);
        RegistrationPage registerPage = new RegistrationPage(driver);
        homePage.clickLinkRegister();
        registerPage.scrollDown();
        Thread.sleep(2000);
        registerPage.clickSubmitButton();
        assertTrue(driver.getPageSource().contains("This field is required"), "Error messages is not as expected");
        assertEquals("http://localhost:5173/register", driver.getCurrentUrl(), "Current URL is not as expected");

    }
    @Test
    void whenVisitorEntersFirstNameWithLithuanianLetters_displayedErrorMessage() throws InterruptedException {
        HomePage homePage = new HomePage(driver);
        RegistrationPage registrationPage = new RegistrationPage(driver);

        homePage.clickLinkRegister();

        String inputFirstName = "Tomaš";
        registrationPage.enterFirstName(inputFirstName);
        String inputLastName = "Tomasaitis";
        registrationPage.enterLastName(inputLastName);
        String inputDisplayName = "Tomasas";
        registrationPage.enterDisplayName(inputDisplayName);
        String inputEmail = "tomukas23@gmail.com";
        registrationPage.enterEmail(inputEmail);
        String inputPassword = "Tomasa23*";
        registrationPage.enterPassword(inputPassword);
        String inputRepeatPassword = "Tomasa23*";
        registrationPage.enterRepeatPassword(inputRepeatPassword);
        String inputDateOfBirth = "07/06/2011";
        registrationPage.enterDateOfBirth(inputDateOfBirth);
        registrationPage.clickPickYourGenderMale();
        String inputCountryYouResideIn = "Angola";
        registrationPage.enterCountryYouResideIn(inputCountryYouResideIn);
        registrationPage.clickIAcceptPrivacyPolicy();
        registrationPage.scrollDown();
        Thread.sleep(2000);
        registrationPage.clickSubmitButton();
        Thread.sleep(2000);

        assertTrue(driver.getPageSource().contains("You can only enter letters. First letter must be capital. At least 2 characters long"), "Error messages is not as expected");
        assertEquals("http://localhost:5173/register", driver.getCurrentUrl(), "Current URL is not as expected");
    }


    }



























//        //ĮVEDAM DUOMENIS
//        String inputFirstName = "Jonas";
//        registrationPage.enterFirstName(inputFirstName);
//        String inputLastName = "Jonaitis";
//        registrationPage.enterLastName(inputLastName);
//
//
//
//        //PATIKRINAM DUONĄ
//        String correctMeal = calorieTrackerPage.checkCorrectMeal();
//        String[] splitCorrectMeal = correctMeal.split(":");
//        String withoutSymbol = splitCorrectMeal[0].trim();
//        System.out.println(withoutSymbol);
//        assertEquals("Bread", withoutSymbol, "The meal should be Bread");
//
//
//        String correctCalories = calorieTrackerPage.checkCorrectCalories();
//        String[] splitCorrectCalories = correctCalories.split(":");
//        String withoutSymbolCalories = splitCorrectCalories[0].trim();
//        assertEquals("150 Calories", withoutSymbolCalories, "The calories should be 150");
//        System.out.println(withoutSymbolCalories);




