package org.Vilma;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage extends BasePage {
    public HomePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "li:nth-of-type(2) > .nav-link")
    WebElement linkRegister;


    public void clickLinkRegisterAndWaitUrl() {
        this.linkRegister.click();
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(d -> driver.getCurrentUrl().equals("http://localhost:5173/register"));
    }

}