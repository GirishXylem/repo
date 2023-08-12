package com.visenti.test.automation.web.pageobjects.sew;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import com.visenti.test.automation.helpers.Log;
import com.visenti.test.automation.utils.SeleniumUtils;

public class ThemesPanelPopup {

	private WebDriver driver;

	public ThemesPanelPopup(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Themes')]/ancestor::div[1]")
	private WebElement themesPopup;

	@FindBy(how = How.XPATH, using = "//input[contains(@placeholder,'Search')]")
	private WebElement searchTextBox;

	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Themes')]/following::div[7]")
	private WebElement themesScrollableDiv;
	// Acoustic Theme

	// Using the label to get the checkbox
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Last Night Min Energy')]/preceding-sibling::td[1]//input")
	private WebElement acousticLastNightMinEnergyCheckbox;

	// AIMS Theme

	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Acoustic')]/preceding-sibling::td[1]//input")
	private WebElement aimsAcousticCheckbox;

	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Customer Meter Leaks')]/preceding-sibling::td[1]//input")
	private WebElement aimsCustomerMeterLeaksCheckbox;

	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Flow')]/preceding-sibling::td[1]//input")
	private WebElement aimsFlowCheckbox;

	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Pressure')]/preceding-sibling::td[1]//input")
	private WebElement aimsPressureCheckbox;

	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Transient')]/preceding-sibling::td[1]//input")
	private WebElement aimsTransientCheckbox;

	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Wqy')]/preceding-sibling::td[1]//input")
	private WebElement aimsWqyCheckbox;

	// Zone Theme

	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Boundaries Only')]/preceding-sibling::td[1]//input")
	private WebElement zoneBoundariesOnlyCheckbox;

	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Aggregate Consumption Smart')]/preceding-sibling::td[1]//input")
	private WebElement zoneAggregateConsumptionSmartCheckbox;

	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Current Demand')]/preceding-sibling::td[1]//input")
	private WebElement zoneCurrendDemandCheckbox;

	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Mass Balance')]/preceding-sibling::td[1]//input")
	private WebElement zoneMassBalanceCheckbox;

	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Minimum Night Demand')]/preceding-sibling::td[1]//input")
	private WebElement zoneMinimumNightDemandCheckbox;

	// Transient Theme

	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Transient Patterns')]/preceding-sibling::td[1]//input")
	private WebElement transientTransientPatternsCheckbox;

	public void selectingAThemeFromPopUp(String themeName) {

		enterTextInSearchTextBox(themeName);
		clickOnAThemeCheckbox(themeName);

	}

	public void enterTextInSearchTextBox(String themeName) {
		SeleniumUtils.enterTextToAField(searchTextBox, themeName);
		Log.info("Entering the Theme Name " + themeName + " in the Search text box");
	}

	public void clickOnAThemeCheckbox(String themeName) {
		switch (themeName.toLowerCase()) {
		case "transient patterns":
			SeleniumUtils.clickOnAnElement(transientTransientPatternsCheckbox);
			Log.info("Clicking on 'Transient Patterns' checkbox under Transient");
			break;
		case "last night min energy":
			SeleniumUtils.clickOnAnElement(acousticLastNightMinEnergyCheckbox);
			Log.info("Clicking on 'Last Night Min Energy' checkbox under Acoustic");
			break;
		default:
			Log.error("Wrong Theme Name Passed");
			throw new RuntimeException("Wrong Transient theme passed");
		}

	}

}
