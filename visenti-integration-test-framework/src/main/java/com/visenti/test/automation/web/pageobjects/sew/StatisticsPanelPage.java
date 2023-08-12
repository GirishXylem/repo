package com.visenti.test.automation.web.pageobjects.sew;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class StatisticsPanelPage {

	private WebDriver driver;

	public StatisticsPanelPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(how = How.CSS, using = "span[role='button']")
	private List<WebElement> columnHeaders;

	@FindBy(how = How.XPATH, using = "//input[contains(@placeholder,'Search')]")
	private WebElement searchTextBox;

	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Statistics')]/following::tbody")
	private WebElement statisticsTableBody;

	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Statistics')]/following::tbody/tr")
	private List<WebElement> statisticsTableRows;

	// tbody/tr/td[not(descendant::button)]
	@FindBy(how = How.XPATH, using = "//tbody/tr/td[not(button)]")
	private List<WebElement> columnValuesForARow;

	@FindBy(how = How.XPATH, using = "//button//span[contains(text(),'Alert')]")
	private WebElement alertsTab;

}
