package com.visenti.test.automation.web.stepdefinitions.sew;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import com.visenti.test.automation.helpers.DriverManager;
import com.visenti.test.automation.helpers.Log;
import com.visenti.test.automation.hooks.ServiceHooks;
import com.visenti.test.automation.utils.SeleniumUtils;
import com.visenti.test.automation.web.pageobjects.dmatool.HomePage;
import com.visenti.test.automation.web.pageobjects.sew.ThemesPanelPopup;

import cucumber.api.java.en.When;

public class ThemesCommonStepDefinitions {

	private WebDriver driver;
	HomePage objHomePage;
	ThemesPanelPopup objThemesPanelPopup;

	public ThemesCommonStepDefinitions() {
		//driver = ServiceHooks.driver;
		driver=DriverManager.getDriver();
		objHomePage = new HomePage(driver);
		objThemesPanelPopup=new ThemesPanelPopup(driver);
	}

	@When("^I click on the Themes Panel button on the side menu$")
	public void clickOnThemesPanelButton() {
		objHomePage.clickOnThemesPanelSideMenuButton();
		Log.info("Clicked on Themes panel side menu button");
		//SeleniumUtils.takeScreenshotAndSaveInFile(driver, new File("target/test-screenshot/test1.jpg"));
		Reporter.log("Clicked on Themes panel side menu button");
	}

	@When("^I select \"([^\"]*)\" under \"([^\"]*)\" category$")
	public void selectingThemeUnderACategory(String themeName, String category)
	{
		objThemesPanelPopup.selectingAThemeFromPopUp(themeName);
		Log.info("Successfully Selected "+themeName+" under "+category);
		Reporter.log("Successfully Selected "+themeName+" under "+category);
		
	}
	
}
