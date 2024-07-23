package org.Vilma;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {
    public HomePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "li:nth-of-type(2) > .nav-link")
    WebElement linkRegister;


    public void clickLinkRegister() {
        linkRegister.click();
    }

}