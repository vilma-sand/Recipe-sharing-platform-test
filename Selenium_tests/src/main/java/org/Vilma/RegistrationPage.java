package org.Vilma;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegistrationPage extends BasePage {
    public RegistrationPage(WebDriver driver) {
        super(driver);
    }
        @FindBy(xpath = "//a[(@class = 'nav-link')]")
        WebElement linkHome;
        @FindBy(id = "first-name")
        WebElement firstName;
        @FindBy(id = "last-name")
        WebElement lastName;
        @FindBy(id = "displayName")
        WebElement displayName;
        @FindBy(id = "email")
        WebElement email;
        @FindBy(id = "password")
        WebElement password;
        @FindBy(id = "repeat-password")
        WebElement repeatPassword;
        @FindBy(id = "dateOfBirth")
        WebElement dateOfBirth;
        @FindBy(id = "male")
        WebElement pickYourGender;
        @FindBy(id = "country")
        WebElement countryYouResideIn;
        @FindBy(id = "privacy-policy")
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
//        public void scrollDown() {
//            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
//        }
        public void clickSubmitButton() {submitButton.click();}

        // tik kai pasikeičia adresas
        public void clickButtonSubmitAndWaitUrl() {
        this.submitButton.click();
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(d -> driver.getCurrentUrl().equals("http://localhost:5173/"));
        }

        public void scrollDownToButtonSubmit() {
        int deltaY = submitButton.getRect().y;
        new Actions(driver)
                .scrollByAmount(0, deltaY)
                .perform();
        }



       // public String checkFirstName() {return firstName.getText();}
    }

