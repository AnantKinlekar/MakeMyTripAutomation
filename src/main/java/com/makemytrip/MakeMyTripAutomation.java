package com.makemytrip;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class MakeMyTripAutomation {
    public static void main(String[] args) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.makemytrip.com/");
        By closeButtonLocator = By.xpath("//span[@data-cy = 'closeModal']");
        WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(closeButtonLocator));
        closeButton.click();

        //sometimes the tooltip is visible. This code is for the same. If tooltip isnt visible, then remove this code and execute
        By tooltipLocator = By.xpath("//div[contains(@class, 'tooltipInfo')]");
        isTooltipVisible(wait, tooltipLocator);
        dismissTooltipIfPresent(driver, wait, tooltipLocator);


        By fromCityLocator = By.id("fromCity");
        WebElement fromCityElement = wait.until(ExpectedConditions.elementToBeClickable(fromCityLocator));
        fromCityElement.click();

        By fromCityTextBoxLocator = By.xpath("//input[@placeholder = 'From' ]");
        WebElement fromCityTextBox = wait.until(ExpectedConditions.elementToBeClickable(fromCityTextBoxLocator));
        fromCityTextBox.sendKeys("Pune");


        By fromCitySuggestionLocator = By.xpath("//p[contains(text(), 'SUGGESTIONS')]/ancestor::div[contains(@class, 'section-container--first')]/ul/li");
        List<WebElement> fromCitySuggestionsList = null;

        boolean state = wait.until(ExpectedConditions.and(ExpectedConditions.visibilityOfAllElementsLocatedBy(fromCitySuggestionLocator),
                ExpectedConditions.numberOfElementsToBeLessThan(fromCitySuggestionLocator, 12)));


        if (state) {
            fromCitySuggestionsList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(fromCitySuggestionLocator));
        }

        System.out.println(fromCitySuggestionsList.size());
        for (WebElement suggestion : fromCitySuggestionsList) {
            System.out.println(suggestion.getText());
        }
        fromCitySuggestionsList.getFirst().click();

        By toCityLocator = By.id("toCity");
        WebElement toCityTextBox = wait.until(ExpectedConditions.elementToBeClickable(toCityLocator));
        toCityTextBox.sendKeys("Mumbai");

        By toCitySuggestionLocator = By.xpath("//div[contains(@id, 'react-autowhatever-1')]//ul/li");
        List<WebElement> toCitySuggestionsList = null;

        boolean toSuggestionState = wait.until(ExpectedConditions.
                and(ExpectedConditions.visibilityOfAllElementsLocatedBy(toCitySuggestionLocator),
                        ExpectedConditions.numberOfElementsToBeMoreThan(toCitySuggestionLocator, 10)));

        if (toSuggestionState) {
            toCitySuggestionsList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(toCitySuggestionLocator));
        }
        toCitySuggestionsList.getFirst().click();

        LocalDate targetDate = LocalDate.now().plusDays(5);
        String month = targetDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        int targetDay = targetDate.getDayOfMonth();
        int targetYear = targetDate.getYear();

        By fromCalenderLocator = By.xpath("//div[contains(text(), '" + month + " " + targetYear + "')]/ancestor::div[@class = 'DayPicker-Month']");
        WebElement fromCalenderElement = wait.until(ExpectedConditions.visibilityOfElementLocated(fromCalenderLocator));
        By dateLocator = By.xpath(".//div[contains(text(), 'February 2026')]/ancestor::div[@class = 'DayPicker-Month']/div[@class = 'DayPicker-Body']//div[@class = 'dateInnerCell']/p[text() = '" + targetDay + "']");
        fromCalenderElement.findElement(dateLocator).click();

    }

    public static void dismissTooltipIfPresent(WebDriver driver, WebDriverWait wait, By tooltipLocator) {
        if (isTooltipVisible(wait, tooltipLocator)) {
            // Click on body or a safe neutral area
            driver.findElement(By.tagName("body")).click();
            System.out.println("Tooltip dismissed");
        } else {
            System.out.println("Tooltip not present, continuing");
        }
    }

    public static boolean isTooltipVisible(WebDriverWait wait, By tooltipLocator) {
        try {
            WebElement tooltip = wait.until(ExpectedConditions.visibilityOfElementLocated(tooltipLocator));
            return tooltip.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
