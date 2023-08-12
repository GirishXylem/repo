package com.visenti.test.automation.web.pageobjects.sew;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class AlertsTabPage {

	private WebDriver driver;

	public AlertsTabPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(how = How.XPATH, using = "//input[contains(@placeholder,'Search')]")
	private WebElement searchTextBox;

	@FindBy(how = How.XPATH, using = "//div[@role='presentation']/following-sibling::div[@class='layout-pane']")
	private WebElement alertsTabLayoutPane;

	@FindBy(how = How.CSS, using = "span[role='button']")
	private List<WebElement> columnHeaders;

	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Alerts')]/following::tbody/tr")
	private List<WebElement> alertsTableRows;

	// Fetching all the elements under <tr> node which are not button and are either
	// div or text
	@FindBy(how = How.XPATH, using = "//tbody/tr[1]/td[not (button) and div|text() ]")
	private List<WebElement> columnCellValuesForFirstRow;

	@FindBy(how = How.XPATH, using = "//button[@aria-label='Previous']")
	private WebElement paginationLeftArrowButton;

	@FindBy(how = How.XPATH, using = "//button[@aria-label='Next']")
	private WebElement paginationRightArrowButton;

	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Alerts')]/following::tbody")
	private WebElement alertsTableBody;

}
