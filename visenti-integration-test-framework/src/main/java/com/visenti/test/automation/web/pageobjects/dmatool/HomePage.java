package com.visenti.test.automation.web.pageobjects.dmatool;

import com.visenti.test.automation.helpers.ConfigFileReader;
import com.visenti.test.automation.helpers.Log;
import com.visenti.test.automation.utils.CommonUtils;
import com.visenti.test.automation.utils.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class HomePage {

	private WebDriver driver;

	public HomePage(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(how = How.CSS, using = "button[title*='Tabs']")
	private WebElement tabsPanelButton;

	@FindBy(how = How.CSS, using = "button[title='Calendar']")
	private WebElement calendarButton;

	@FindBy(how = How.XPATH, using = "//label[contains(text(),'Start Date')]/following-sibling::div/input")
	private WebElement startDateInputTextBox;

	@FindBy(how = How.XPATH, using = "//label[contains(text(),'End Date')]/following-sibling::div/input")
	private WebElement endDateInputTextBox;

	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Apply')]/ancestor::button")
	private WebElement applyButton;

	@FindBy(how = How.CSS, using = "button[title='Themes Panel']")
	private WebElement themesPanelSideMenuButton;

	/*@FindBy(how = How.CSS, using = "button[title='Account']")
	private WebElement accountButton;*/
	
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'ant-dropdown-trigger')]//span/ancestor::button")
	private WebElement accountButton;

	@FindBy(how = How.CSS, using = "ul[role='menu']")
	private WebElement accountMenu;

	@FindBy(how = How.CSS, using = "li[role='menuitem']")
	private List<WebElement> menuItemsList;

	/*@FindBy(how = How.XPATH, using = "//span[contains(text(),'account')]/ancestor::li")
	private WebElement myAccountMenuItem;*/
	
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Logout')]/ancestor::li")
	private WebElement logoutMenuItem;

	@FindBy(xpath = "//input[@placeholder = 'Type to search']")
	private WebElement searchTextbox;

	@FindBy(xpath = "//input[@placeholder = 'Type to search']/following-sibling::button")
	private WebElement searchType;

	@FindBy(xpath = "//button[contains(@class,'ant-btn jss111')]")
	private WebElement appMenuIcon;

	@FindBy(xpath = "//button[@title='Zoom Out']")
	private WebElement zoomOut;

	@FindBy(xpath = "//button[@title='Zoom In']")
	private WebElement zoomIn;

	@FindBy(xpath = "//button[@title='AIMS']")
	private WebElement aimsButton;

	@FindBy(xpath = "//button[2]/span/span")
	private WebElement aimsSensorsButton;
	
	@FindBy(how=How.ID,using="app-header-title")
	private WebElement pageTitleHeader;
	
	@FindBy(how=How.XPATH,using="//a[@aria-label='Open drawer']//*[local-name()='svg']")
	private WebElement logoImg;
	
	@FindBy(how=How.CSS,using="div[title*='Information']")
	private WebElement infoPanelButton;
	
	@FindBy(how=How.CSS,using="div[title*='Plotter']")
	private WebElement plotterOptionsButton;
	
	@FindBy(how=How.XPATH,using="//div[@id='gis_legend']/child::div")
	private WebElement zoneBoundariesDiv;
	
	@FindBy(how=How.CSS,using=".esri-ui")
	private WebElement esriMapContainer;

	@FindBy(xpath = "//div[@title='Simulation']/span[@role='button']")
	private WebElement simulationButton;

	public void clickOnThemesPanelSideMenuButton() {
		// Providing an explicit wait for the Themes panel button to be clickable
		WebElement themesPanelButton = SeleniumUtils.waitUntilElementClickable(themesPanelSideMenuButton, driver, 25L);

		Log.info("Waited for the Side menu Button to be clickable");

		SeleniumUtils.performAJavaScriptClick(driver, themesPanelButton);

		//Log.info("Clicked on Themes panel side menu button");
	}
	
	public List<WebElement> waitForMandatoryElementsToBeVisibleOnThePage(List<WebElement>elements)
	{
		List<WebElement>mandatoryElements=SeleniumUtils.waitForElementsToBeVisible(elements, driver, 20L);
		Log.info("Waited for all mandatory Elements to be visible on the Page");
		return mandatoryElements;
		
	}
	public List<WebElement> getMandatoryElementsOnPageLoad()
	{
		List<WebElement>elements=new ArrayList<WebElement>();
		elements.add(logoImg);
		elements.add(pageTitleHeader);
		elements.add(searchTextbox);
		elements.add(infoPanelButton);
		elements.add(plotterOptionsButton);
		elements.add(accountButton);
		elements.add(esriMapContainer);
		//elements.add(zoneBoundariesDiv);
		
		elements=waitForMandatoryElementsToBeVisibleOnThePage(elements);
		return elements;
		}

	public void searchAndSelectStation(String keyword) throws Exception {
		String[] search = keyword.split("_");
		String type = search[1];
		if(type.equalsIgnoreCase("BulkMeter")){
			type = "BulkMeter";
		}
		String searchKeyword = ConfigFileReader.getConfigProperty(keyword);
		SeleniumUtils.javaScriptClickAndSelect(driver,searchType,type);
		SeleniumUtils.typeAndEnterTheSearch(driver,searchTextbox,searchKeyword);
	}

	public void selectTheAppFromTheApplicationList(String appName) throws Exception {
		SeleniumUtils.javaScriptClickAndSelect(driver, appMenuIcon, appName);
	}

	public void clickOnTheApp(String app){
		WebElement element =
				driver.findElement(By.xpath("//button[@title='" + app+"' and @type='button']"));
		SeleniumUtils.performAJavaScriptClick(driver,element);
		CommonUtils.wait(1);
	}

	public void clickOnAimsSensorsButton(){
		CommonUtils.wait(2);
		SeleniumUtils.waitUntilElementClickable(aimsSensorsButton,driver,2L);
		SeleniumUtils.performAJavaScriptClick(driver,aimsSensorsButton);
	}

	public void clickOnAccountButton() {
		/*WebElement accntButton = SeleniumUtils.waitUntilElementClickable(accountButton, driver, 20L);
		Log.info("Waited for Account Button to be clickable");
*/
	   //SeleniumUtils.clickOnAnElement(accntButton);
		SeleniumUtils.performAJavaScriptClick(driver, accountButton);
		Log.info("Clicked on Account button");

	}

	public void verifyItemsDisplayedInAccountMenuPopup() {
		for (WebElement ele : menuItemsList) {
			assertTrue(SeleniumUtils.isElementDisplayed(ele));
			Log.info("The menu item " + ele.getText() + " is displayed in the popup");
		}
	}

	public void clickOnLogoutInAccountMenu() {
		//WebElement logout = WebCommonUtils.waitUntilElementClickable(logoutMenuItem, driver, 20L);
	//	SeleniumUtils.clickOnAnElement(logoutMenuItem);
		SeleniumUtils.performAJavaScriptClick(driver, logoutMenuItem);
		Log.info("Clicked on Logout menu item");
	}

	public void performLogoutAction() throws InterruptedException {
		clickOnAccountButton();
		//verifyItemsDisplayedInAccountMenuPopup();
		//Wait for Elements to be visible in Account Menu
		
		SeleniumUtils.waitForElementsToBeVisible(menuItemsList, driver, 15L);
		
		//Thread.sleep(5000);
		clickOnLogoutInAccountMenu();
	}

	public void mapZoomOut(){
		SeleniumUtils.performAJavaScriptClick(driver, zoomOut);
	}

	public void mapZoomIn(){
		SeleniumUtils.performAJavaScriptClick(driver, zoomIn);
	}

	public void clickOnZoomButton(String value){
		if(value.equalsIgnoreCase("minus")){
			mapZoomOut();
		}else if(value.equalsIgnoreCase("plus")){
			mapZoomIn();
		}
	}

	public void clickOnSimulationButton(){
		SeleniumUtils.waitUntilElementVisible(simulationButton,driver,1l);
		SeleniumUtils.clickOnAnElement(simulationButton);
	}

}
