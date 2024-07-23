package org.Vilma;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RegistrationPage extends BasePage {
    public RegistrationPage(WebDriver driver) {
        super(driver);
    }
        @FindBy(xpath = "//a[(@class = 'nav-link')]")
        WebElement linkHome;
        @FindBy(xpath = "//input[(@id = 'first-name')]")
        WebElement firstName;
        @FindBy(xpath = "//input[(@id = 'last-name')]")
        WebElement lastName;
        @FindBy(xpath = "//input[(@id = 'displayName')]")
        WebElement displayName;
        @FindBy(xpath = "//input[(@id = 'email')]")
        WebElement email;
        @FindBy(xpath = "//input[(@id = 'password')]")
        WebElement password;
        @FindBy(xpath = "//input[(@id = 'repeat-password')]")
        WebElement repeatPassword;
        @FindBy(xpath = "//input[(@id = 'dateOfBirth')]")
        WebElement dateOfBirth;
        @FindBy(xpath = "//input[(@id = 'male')]")
        WebElement pickYourGender;
        @FindBy(xpath = "//select[(@id = 'country')]")
        WebElement countryYouResideIn;
        @FindBy(xpath = "//input[(@id = 'privacy-policy')]")
        WebElement privacyPolicy;
        @FindBy(xpath = "//button[(@class = 'btn btn-primary')]")
        WebElement submitButton;

    public void clickLinkHome() {
        linkHome.click();
    }
        public void enterFirstName(String inputFirstName) {
            firstName.sendKeys(inputFirstName);
        }
        public void enterLastName(String inputLastName) {
            lastName.sendKeys(inputLastName);
        }
        public void enterDisplayName(String inputDisplayName) {
            displayName.sendKeys(inputDisplayName);
        }
        public void enterEmail(String inputEmail) {
            email.sendKeys(inputEmail);
        }
        public void enterPassword(String inputPassword) {
            password.sendKeys(inputPassword);
        }
        public void enterRepeatPassword(String inputRepeatPassword) {
            repeatPassword.sendKeys(inputRepeatPassword);
        }
        public void enterDateOfBirth(String inputDateOfBirth) {
            dateOfBirth.sendKeys(inputDateOfBirth);
        }
        public void clickPickYourGenderMale() {
            pickYourGender.click();
        }
        public void enterCountryYouResideIn(String inputCountry) {
            countryYouResideIn.sendKeys(inputCountry);
        }
        public void clickIAcceptPrivacyPolicy() {
            privacyPolicy.click();
        }
        public void scrollDown() {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
        }
        public void clickSubmitButton() {submitButton.click();}




       // public String checkFirstName() {return firstName.getText();}
    }

