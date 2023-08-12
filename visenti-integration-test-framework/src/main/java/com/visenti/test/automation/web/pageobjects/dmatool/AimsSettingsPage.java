package com.visenti.test.automation.web.pageobjects.dmatool;

import com.visenti.test.automation.utils.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AimsSettingsPage {
    private WebDriver driver;

    public AimsSettingsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[text()='Settings' and @id='rcDialogTitle0']")
    private WebElement aimsSettingPopupTitle;

    @FindBy(xpath = "//button/span[text()='Reset Defaults']")
    private WebElement resetDefaultsButton;

    @FindBy(xpath = "//button/span[text()='Save']")
    private WebElement saveButton;

    @FindBy(xpath = "//label[text()='Attend Filter']/ancestor::div[@class='ant-col ant-form-item-label']/following-sibling::div")
    private WebElement attendFilterDropdown;

    @FindBy(xpath = "//label[text()='Alarm Ringtone']/ancestor::div[@class='ant-col ant-form-item-label']/following-sibling::div")
    private WebElement alarmRingtoneDropdown;

    @FindBy(xpath = "//label[text()='Status']/ancestor::div[@class='ant-col ant-form-item-label']/following-sibling::div")
    private WebElement statusDropdown;

    @FindBy(xpath = "//label[text()='Ongoing']/ancestor::div[@class='ant-col ant-form-item-label']/following-sibling::div")
    private WebElement ongoingDropdown;

    @FindBy(xpath = "//label[text()='Recommendations']/ancestor::div[@class='ant-col ant-form-item-label']/following-sibling::div")
    private WebElement recommendationsDropdown;

    @FindBy(xpath = "//label[text()='Type']/ancestor::div[@class='ant-col ant-form-item-label']/following-sibling::div")
    private WebElement typeDropdown;

    @FindBy(xpath = "//label[text()='Tags']/ancestor::div[@class='ant-col ant-form-item-label']/following-sibling::div")
    private WebElement tagsDropdown;

    public void clickOnResetDefaults() {
        SeleniumUtils.performAJavaScriptClick(driver, resetDefaultsButton);
    }

    public void clickOnSave() {
        SeleniumUtils.performAJavaScriptClick(driver, saveButton);
    }

    /*public void selectDropdownValues(String text) {
        SeleniumUtils.performAJavaScriptClick(driver,);
    }*/

}
