package org.Vilma;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;


import static org.junit.jupiter.api.Assertions.*;

public class RegistrationPageTest extends BasePageTest {
   RegistrationPage registrationPage;

String inputFirstName = "Tomas";
String inputLastName = "Tomasaitis";
String inputDisplayName = "Tomasas";
String inputEmail = "tomukas23@gmail.com";
String inputPassword = "Tomasa23*";
String inputRepeatPassword = "Tomasa23*";
String inputDateOfBirth = "07/06/2011";
String inputCountryYouResideIn = "Angola";


   public void registrationSteps(
       String inputFirstName, String inputLastName, String inputDisplayName, String  inputEmail, String inputPassword, String inputRepeatPassword, String inputDateOfBirth, String inputCountryYouResideIn) {
       registrationPage = new RegistrationPage(driver);

       registrationPage.enterFirstName(inputFirstName);
       registrationPage.enterLastName(inputLastName);
       registrationPage.enterDisplayName(inputDisplayName);
       registrationPage.enterEmail(inputEmail);
       registrationPage.enterPassword(inputPassword);
       registrationPage.enterRepeatPassword(inputRepeatPassword);
       registrationPage.enterDateOfBirth(inputDateOfBirth);
       registrationPage.clickPickYourGenderMale();
       registrationPage.enterCountryYouResideIn(inputCountryYouResideIn);
       registrationPage.clickIAcceptPrivacyPolicy();
       registrationPage.scrollDownToButtonSubmit();
       registrationPage.clickSubmitButton();
   }



//public class RegistrationPageTest extends BasePageTest {


    //HAPPY tests

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
    void whenVisitorSubmitEmptyRegistrationForm_displayedErrorMessagesForAllRequiredFields() {
        HomePage homePage = new HomePage(driver);
        RegistrationPage registrationPage = new RegistrationPage(driver);

        homePage.clickLinkRegisterAndWaitUrl();
        registrationPage.scrollDownToButtonSubmit();
        registrationPage.clickSubmitButton();

        assertEquals(7, driver.findElements(By.xpath("//*[contains(text(),'This field is required')]")).size(), "Number of error messages is not as expected");
        assertTrue(driver.getPageSource().contains("You must check this in order to continue"), "Error message 'You must check this in order to continue' is not as expected");
        assertEquals("http://localhost:5173/register", driver.getCurrentUrl(), "Current URL is not as expected");
    }
    @Test
    void whenVisitorEntersFirstNameWithLithuanianLetters_displayedErrorMessage() {
        registrationPage = new RegistrationPage(driver);
        HomePage homePage = new HomePage(driver);

        homePage.clickLinkRegisterAndWaitUrl();

        registrationSteps( "Tomaš", inputLastName, inputDisplayName, inputEmail, inputPassword, inputRepeatPassword, inputDateOfBirth, inputCountryYouResideIn);

        assertTrue(driver.getPageSource().contains("You can only enter English letters. First letter must be capital. At least 2 characters long."), "Error messages is not as expected");
        assertEquals("http://localhost:5173/register", driver.getCurrentUrl(), "Current URL is not as expected");
    }

    @Test
    void whenVisitorEntersFirstNameOneCharacterLong_displayedErrorMessage() {
        registrationPage = new RegistrationPage(driver);
        HomePage homePage = new HomePage(driver);

        homePage.clickLinkRegisterAndWaitUrl();

        registrationSteps( "T", inputLastName, inputDisplayName, inputEmail, inputPassword, inputRepeatPassword, inputDateOfBirth, inputCountryYouResideIn);

        assertTrue(driver.getPageSource().contains("You can only enter English letters. First letter must be capital. At least 2 characters long."), "Error messages is not as expected");
        assertEquals("http://localhost:5173/register", driver.getCurrentUrl(), "Current URL is not as expected");
    }
    @Test
    void whenVisitorEntersFirstNameWithOnlyLowercases_displayedErrorMessage() {
        registrationPage = new RegistrationPage(driver);
        HomePage homePage = new HomePage(driver);

        homePage.clickLinkRegisterAndWaitUrl();

        registrationSteps( "tomukas", inputLastName, inputDisplayName, inputEmail, inputPassword, inputRepeatPassword, inputDateOfBirth, inputCountryYouResideIn);

        assertTrue(driver.getPageSource().contains("You can only enter English letters. First letter must be capital. At least 2 characters long."), "Error messages is not as expected");
        assertEquals("http://localhost:5173/register", driver.getCurrentUrl(), "Current URL is not as expected");
    }
    @Test
    void whenVisitorEntersLastNameWithOnlyLowercases_displayedErrorMessage() {
        registrationPage = new RegistrationPage(driver);
        HomePage homePage = new HomePage(driver);

        homePage.clickLinkRegisterAndWaitUrl();

        registrationSteps( inputFirstName, "tomasaitis", inputDisplayName, inputEmail, inputPassword, inputRepeatPassword, inputDateOfBirth, inputCountryYouResideIn);

        assertTrue(driver.getPageSource().contains("You can only enter English letters. First letter must be capital. At least 2 characters long."), "Error messages is not as expected");
        assertEquals("http://localhost:5173/register", driver.getCurrentUrl(), "Current URL is not as expected");
    }
    @Test
    void whenVisitorEntersLastNameWithLithuanianLetters_displayedErrorMessage() {
        registrationPage = new RegistrationPage(driver);
        HomePage homePage = new HomePage(driver);

        homePage.clickLinkRegisterAndWaitUrl();

        registrationSteps( inputFirstName, "Tomašaitis", inputDisplayName, inputEmail, inputPassword, inputRepeatPassword, inputDateOfBirth, inputCountryYouResideIn);

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
    @ParameterizedTest
    @CsvFileSource(resources = "/swearWords.csv")
    void whenVisitorEntersDisplayNameWithSwearWords_displayedErrorMessage(String input) {
        registrationPage = new RegistrationPage(driver);
        HomePage homePage = new HomePage(driver);

        homePage.clickLinkRegisterAndWaitUrl();

        registrationSteps( inputFirstName, inputLastName, input, inputEmail, inputPassword, inputRepeatPassword, inputDateOfBirth, inputCountryYouResideIn);

        assertTrue(driver.getPageSource().contains("Display name contains inappropriate language"), "Error messages is not as expected");
        assertEquals("http://localhost:5173/register", driver.getCurrentUrl(), "Current URL is not as expected");
    }
    @Test
    void whenVisitorEntersDisplayNameWithLithuanianLetters_displayedErrorMessage() {

        registrationPage = new RegistrationPage(driver);
        HomePage homePage = new HomePage(driver);

        homePage.clickLinkRegisterAndWaitUrl();

        registrationSteps( inputFirstName, inputLastName, "Tomašas", inputEmail, inputPassword, inputRepeatPassword, inputDateOfBirth, inputCountryYouResideIn);

        assertTrue(driver.getPageSource().contains("You can only enter English letters or numbers. At least 1 character long. Cannot begin or end with a space. No more than one space between words"), "Error messages is not as expected");
        assertEquals("http://localhost:5173/register", driver.getCurrentUrl(), "Current URL is not as expected");
    }
    @Test
    void whenVisitorEntersNotUniqueEmail_displayedErrorMessage() {
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
        String inputSecondDisplayName = "Tomasas2";
        registrationPage.enterDisplayName(inputSecondDisplayName);
        String inputSecondEmail = "tomukas23@gmail.com";
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
    @Test
    void whenVisitorEntersEmailWithCapitalLetters_displayedErrorMessage() {
        registrationPage = new RegistrationPage(driver);
        HomePage homePage = new HomePage(driver);

        homePage.clickLinkRegisterAndWaitUrl();

        registrationSteps( inputFirstName, inputLastName, inputDisplayName, "TOMAS23@gmail.com", inputPassword, inputRepeatPassword, inputDateOfBirth, inputCountryYouResideIn);

        assertTrue(driver.getPageSource().contains("May only contain English letters, all lowercase. Can contain numbers, and these symbols ._-"), "Error messages is not as expected");
        assertEquals("http://localhost:5173/register", driver.getCurrentUrl(), "Current URL is not as expected");
    }
    @Test
    void whenVisitorEntersEmailWithLithuanianLetters_displayedErrorMessage() {
        registrationPage = new RegistrationPage(driver);
        HomePage homePage = new HomePage(driver);

        homePage.clickLinkRegisterAndWaitUrl();

        registrationSteps( inputFirstName, inputLastName, inputDisplayName, "jonukaš@gmail.com", inputPassword, inputRepeatPassword, inputDateOfBirth, inputCountryYouResideIn);

        assertTrue(driver.getPageSource().contains("May only contain English letters, all lowercase. Can contain numbers, and these symbols ._-"), "Error messages is not as expected");
        assertEquals("http://localhost:5173/register", driver.getCurrentUrl(), "Current URL is not as expected");
    }
@Test
void whenVisitorEntersDifferentRepeatPassword_displayedErrorMessage() {
    registrationPage = new RegistrationPage(driver);
    HomePage homePage = new HomePage(driver);

    homePage.clickLinkRegisterAndWaitUrl();

    registrationSteps( inputFirstName, inputLastName, inputDisplayName, inputEmail, inputPassword, "Tomas234*", inputDateOfBirth, inputCountryYouResideIn);

    assertTrue(driver.getPageSource().contains("Passwords do not match"), "Error messages is not as expected");
    assertEquals("http://localhost:5173/register", driver.getCurrentUrl(), "Current URL is not as expected");
}
//    @ParameterizedTest
//    @CsvFileSource(resources = "/invalidPassword.csv")
//    void whenVisitorEntersInvalidPassword_displayedErrorMessage(String input1, String input2) {
//        registrationPage = new RegistrationPage(driver);
//        HomePage homePage = new HomePage(driver);
//
//        homePage.clickLinkRegisterAndWaitUrl();
//
//        registrationSteps( inputFirstName, inputLastName, inputDisplayName, inputEmail, input1, input2, inputDateOfBirth, inputCountryYouResideIn);
//
//        assertTrue(driver.getPageSource().contains("Must contain at least one uppercase letter, one lowercase letter, one number, and any one of these special symbols: !@#$%^&*\"."), "Expected error message is not displayed for input password: " + input1);
//        assertEquals("http://localhost:5173/register", driver.getCurrentUrl(), "Current URL is not as expected");
//    }
    @Test
    void whenVisitorEntersDifferentPasswordInRepeatPassword_displayedErrorMessage() {
        registrationPage = new RegistrationPage(driver);
        HomePage homePage = new HomePage(driver);

        homePage.clickLinkRegisterAndWaitUrl();

        registrationSteps( inputFirstName, inputLastName, inputDisplayName, inputEmail, inputPassword, "Jonukas23*", inputDateOfBirth, inputCountryYouResideIn);

        assertTrue(driver.getPageSource().contains("Passwords do not match"), "Error messages is not as expected");
        assertEquals("http://localhost:5173/register", driver.getCurrentUrl(), "Current URL is not as expected");
    }
//    @ParameterizedTest
//    @CsvFileSource(resources = "/invalidDateOfBirth.csv")
//    void whenVisitorEntersInvalidDateOfBirth_displayedErrorMessage(String input) {
//        registrationPage = new RegistrationPage(driver);
//        HomePage homePage = new HomePage(driver);
//
//        homePage.clickLinkRegisterAndWaitUrl();
//
//        registrationSteps( inputFirstName, inputLastName, inputDisplayName, inputEmail, inputPassword, inputRepeatPassword, input, inputCountryYouResideIn);
//
//        assertTrue(driver.getPageSource().contains("Cannot be older than the year 1900, or younger than 13 years old"), "Error messages is not as expected");
//        assertEquals("http://localhost:5173/register", driver.getCurrentUrl(), "Current URL is not as expected");
//    }

    }
































