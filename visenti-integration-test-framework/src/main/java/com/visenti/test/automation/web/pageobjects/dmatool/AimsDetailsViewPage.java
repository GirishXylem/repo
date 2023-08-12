package com.visenti.test.automation.web.pageobjects.dmatool;

import com.cucumber.listener.Reporter;
import com.visenti.test.automation.helpers.Log;
import com.visenti.test.automation.utils.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AimsDetailsViewPage {
    private WebDriver driver;

    public AimsDetailsViewPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//span[text()='State']/following-sibling::div/div/div/div")
    private WebElement stateDropDown;

    @FindBy(xpath = "//span[text()='Status']/following-sibling::div/div/div/div")
    private WebElement statusDropDown;

    @FindBy(xpath = "//span[text()='Assignee']/following-sibling::span/div/button/span")
    private WebElement assigneeButton;

    @FindBy(xpath = "//input[@placeholder='Add a comment...' and @type='text']")
    private WebElement commentTextBox;

    @FindBy(xpath = "//input[@placeholder='Add a comment...']/following-sibling::span/button")
    private WebElement commentButton;

    @FindBy(xpath = "//input[@placeholder='Add a comment...' and @type='text']")
    private WebElement incidentText;

    @FindBy(xpath = "//input[@placeholder='Add a comment...']/ancestor::div[@id='custom-timeline-comment']/following-sibling::div/button[1]")
    private WebElement attachmentButton;

    @FindBy(xpath = "//input[@placeholder='Add a comment...']/ancestor::div[@id='custom-timeline-comment']/following-sibling::div/button[2]")
    private WebElement sortButton;

    @FindBy(xpath = "//input[@placeholder='Add a comment...']/ancestor::div[@id='custom-timeline-comment']/following-sibling::div/button[3]")
    private WebElement collapseButton;

    @FindBy(xpath = "//button/span[text()='Exit']")
    private WebElement exitButton;

    @FindBy(xpath = "//button/span[text()='Exit']/ancestor::div[@class='ant-row-flex ant-row-flex-end']/child::div[1]/button")
    private WebElement notifyButton;

    @FindBy(xpath = "//div[@class='ant-row-flex ant-row-flex-middle']/child::div[@class='ant-col']/button")
    private WebElement aimsViewButton;

    @FindBy(xpath = "//h6[@id='app-header-title']")
    private WebElement incidentHeader;

    @FindBy(xpath = "//span[text()='Details']/following-sibling::span")
    private WebElement incidentDetails;

    @FindBy(xpath = "//div[text()='Notify' and @id='rcDialogTitle1']")
    private WebElement notifyPopUpTitle;

    @FindBy(xpath = "//label[text()='To' and @for='escalate_notify_recipients']")
    private WebElement notifyToText;

    @FindBy(xpath = "//div[text()='Select Recipients' and @unselectable='on']")
    private WebElement selectRecipientTextBox;

    @FindBy(xpath = "//label[text()='Message' and @for='escalate_notify_message']")
    private WebElement notifyMessageText;

    @FindBy(xpath = "//input[@placeholder='Type your message here...' and @id='escalate_notify_message']")
    private WebElement messageTextBox;

    public boolean verifyHeaderAndDetailsMatch(){
        String[] aimsHeader = incidentHeader.getText().split("INCIDENT");
        return incidentDetails.getText().toLowerCase().contains(aimsHeader[0].toLowerCase());
    }

    public boolean verifyElementsOfAimsDetailsPage(){
        boolean present = false;
        if(SeleniumUtils.isElementDisplayed(incidentHeader) && SeleniumUtils.isElementDisplayed(notifyButton)
            && SeleniumUtils.isElementDisplayed(exitButton) && SeleniumUtils.isElementDisplayed(commentTextBox)
                && SeleniumUtils.isElementDisplayed(incidentText)){
            present = true;
            Log.info("Verified the mandate elements of the AIMS details view page");
            Reporter.addStepLog("Verified the mandate elements of the AIMS details view page");
        }
        return present;
    }

    public void clickNotifyButton(){
        SeleniumUtils.performAJavaScriptClick(driver, notifyButton);
    }

    /*public boolean verifyNotifyPopUp(){
        boolean presence = false;
        if(SeleniumUtils.isElementDisplayed(notifyPopUpTitle) && SeleniumUtils.isElementDisplayed())
    }*/

    public void sendTextToTheNotifyFields(){
        SeleniumUtils.enterTextToAField(selectRecipientTextBox, "kishan.ks@xyleminc.com");
        SeleniumUtils.enterTextToAField(messageTextBox, "Test message");
    }
}
