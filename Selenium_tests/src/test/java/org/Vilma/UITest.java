package org.Vilma;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UITest extends BaseTest {

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

    @Test
    void whenVisitorEntersValidInputs_userLoggedInSuccessfully_displayedSuccessMessage() {
            HomePage homePage = new HomePage(driver);
            RegistrationPage registrationPage = new RegistrationPage(driver);

            homePage.clickLinkRegisterAndWaitUrl();

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
            registrationPage.scrollDownToButtonSubmit();
            registrationPage.clickButtonSubmitAndWaitUrl();
            assertTrue(driver.getPageSource().contains("You have registered successfully. You can now log in"), "Success message is not as expected");
            assertEquals("http://localhost:5173/", driver.getCurrentUrl(), "Current URL is not as expected");
        }

    //UNHAPPY tests

    @Test
    void whenVisitorSubmitEmptyRegistrationForm_displayedErrorMessages() {
        HomePage homePage = new HomePage(driver);
        RegistrationPage registerPage = new RegistrationPage(driver);
        homePage.clickLinkRegisterAndWaitUrl();
        registerPage.scrollDownToButtonSubmit();
        registerPage.clickSubmitButton();

        String pageSource = driver.getPageSource();
        int occurrences = countOccurrences(pageSource);

        assertEquals(7, occurrences, "The number of 'Cannot be null or empty' messages is not as expected");
        assertTrue(pageSource.contains("You must check this in order to continue"), "Error message 'You must check this in order to continue' is not as expected");
        assertEquals("http://localhost:5173/register", driver.getCurrentUrl(), "Current URL is not as expected");
    }
    private int countOccurrences(String haystack) {
        int count = 0;
        int idx = 0;
        while ((idx = haystack.indexOf("Cannot be null or empty", idx)) != -1) {
            count++;
            idx += "Cannot be null or empty".length();
        }
        return count;
}
    @Test
    void whenVisitorEntersFirstNameWithLithuanianLetters_displayedErrorMessage() {
        HomePage homePage = new HomePage(driver);
        RegistrationPage registrationPage = new RegistrationPage(driver);

        homePage.clickLinkRegisterAndWaitUrl();

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
        registrationPage.scrollDownToButtonSubmit();
        registrationPage.clickSubmitButton();

        assertTrue(driver.getPageSource().contains("You can only enter English letters. First letter must be capital. At least 2 characters long."), "Error messages is not as expected");
        assertEquals("http://localhost:5173/register", driver.getCurrentUrl(), "Current URL is not as expected");
    }

    @Test
    void whenVisitorEntersFirstNameOneCharacterLong_displayedErrorMessage() {
        HomePage homePage = new HomePage(driver);
        RegistrationPage registrationPage = new RegistrationPage(driver);

        homePage.clickLinkRegisterAndWaitUrl();

        String inputFirstName = "T";
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
        registrationPage.scrollDownToButtonSubmit();
        registrationPage.clickSubmitButton();

        assertTrue(driver.getPageSource().contains("You can only enter English letters. First letter must be capital. At least 2 characters long."), "Error messages is not as expected");
        assertEquals("http://localhost:5173/register", driver.getCurrentUrl(), "Current URL is not as expected");
    }
    @Test
    void whenVisitorEntersFirstNameWithOnlyLowercases_displayedErrorMessage() {
        HomePage homePage = new HomePage(driver);
        RegistrationPage registrationPage = new RegistrationPage(driver);

        homePage.clickLinkRegisterAndWaitUrl();

        String inputFirstName = "tomukas";
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
        registrationPage.scrollDownToButtonSubmit();
        registrationPage.clickSubmitButton();

        assertTrue(driver.getPageSource().contains("You can only enter English letters. First letter must be capital. At least 2 characters long."), "Error messages is not as expected");
        assertEquals("http://localhost:5173/register", driver.getCurrentUrl(), "Current URL is not as expected");
    }
    @Test
    void whenVisitorEntersLastNameWithOnlyLowercases_displayedErrorMessage() {
        HomePage homePage = new HomePage(driver);
        RegistrationPage registrationPage = new RegistrationPage(driver);

        homePage.clickLinkRegisterAndWaitUrl();

        String inputFirstName = "Tomukas";
        registrationPage.enterFirstName(inputFirstName);
        String inputLastName = "tomasaitis";
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
        registrationPage.scrollDownToButtonSubmit();
        registrationPage.clickSubmitButton();

        assertTrue(driver.getPageSource().contains("You can only enter English letters. First letter must be capital. At least 2 characters long."), "Error messages is not as expected");
        assertEquals("http://localhost:5173/register", driver.getCurrentUrl(), "Current URL is not as expected");
    }
    @Test
    void whenVisitorEntersLastNameWithLithuanianLetters_displayedErrorMessage() {
        HomePage homePage = new HomePage(driver);
        RegistrationPage registrationPage = new RegistrationPage(driver);

        homePage.clickLinkRegisterAndWaitUrl();

        String inputFirstName = "Tomukas";
        registrationPage.enterFirstName(inputFirstName);
        String inputLastName = "Tomašaitis";
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
        registrationPage.scrollDownToButtonSubmit();
        registrationPage.clickSubmitButton();

        assertTrue(driver.getPageSource().contains("You can only enter English letters. First letter must be capital. At least 2 characters long."), "Error messages is not as expected");
        assertEquals("http://localhost:5173/register", driver.getCurrentUrl(), "Current URL is not as expected");
    }
    @Test
    void whenVisitorEntersNotUniqueDisplayName_displayedErrorMessage() {
        HomePage homePage = new HomePage(driver);
        RegistrationPage registrationPage = new RegistrationPage(driver);

        homePage.clickLinkRegisterAndWaitUrl();

        String inputFirstName = "Tomukas";
        registrationPage.enterFirstName(inputFirstName);
        String inputLastName = "Tomukaitis";
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
        registrationPage.scrollDownToButtonSubmit();
        registrationPage.clickButtonSubmitAndWaitUrl();

        homePage.clickLinkRegisterAndWaitUrl();

        String inputSecondFirstName = "Tomukas";
        registrationPage.enterFirstName(inputSecondFirstName);
        String inputSecondLastName = "Tomukaitis";
        registrationPage.enterLastName(inputSecondLastName);
        String inputSecondDisplayName = "Tomasas";
        registrationPage.enterDisplayName(inputSecondDisplayName);
        String inputSecondEmail = "tomukas234@gmail.com";
        registrationPage.enterEmail(inputSecondEmail);
        String inputSecondPassword = "Tomasa23*";
        registrationPage.enterPassword(inputSecondPassword);
        String inputSecondRepeatPassword = "Tomasa23*";
        registrationPage.enterRepeatPassword(inputSecondRepeatPassword);
        String inputSecondDateOfBirth = "07/06/2011";
        registrationPage.enterDateOfBirth(inputSecondDateOfBirth);
        registrationPage.clickPickYourGenderMale();
        String inputSecondCountryYouResideIn = "Angola";
        registrationPage.enterCountryYouResideIn(inputSecondCountryYouResideIn);
        registrationPage.clickIAcceptPrivacyPolicy();
        registrationPage.scrollDownToButtonSubmit();
        registrationPage.clickSubmitButton();

        assertTrue(driver.getPageSource().contains("Already exists"), "Error messages is not as expected");
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




