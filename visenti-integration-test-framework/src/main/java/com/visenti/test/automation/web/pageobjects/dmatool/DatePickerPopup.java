package com.visenti.test.automation.web.pageobjects.dmatool;

import com.visenti.test.automation.helpers.Log;
import com.visenti.test.automation.utils.CommonUtils;
import com.visenti.test.automation.utils.SeleniumUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class DatePickerPopup {

	private WebDriver driver;
	public DatePickerPopup(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(how = How.XPATH, using = "//div[@role='dialog']//h6")
	private WebElement yearComponent;

	@FindBy(how = How.XPATH, using = "//div[@role='dialog']//h4")
	private WebElement dateComponent;

	@FindBy(how = How.XPATH, using = "//div[@role='dialog']//h3")
	private List<WebElement> timeComponents;

	@FindBy(how = How.XPATH, using = "//button//span[contains(text(),'arrow_left')]")
	private WebElement leftArrowButton;

	@FindBy(how = How.XPATH, using = "//button//span[contains(text(),'arrow_right')]")
	private WebElement rightArrowButton;

	@FindBy(how = How.XPATH, using = "//div[@role='dialog']//p")
	private WebElement monthYearLabel;

	@FindBy(how = How.XPATH, using = "//div[@role='dialog']//div[@role='presentation']/button[@tabindex='0']")
	private List<WebElement> dateButtons;

	@FindBy(how = How.XPATH, using = "//div[@role='dialog']//div[@role='button']")
	private List<WebElement> yearDropdownOptions;

	@FindBy(how = How.XPATH, using = "//div[@role='dialog']//div[@role='menu']//following-sibling::span")
	private List<WebElement> hourElementsClock;

	@FindBy(how = How.XPATH, using = "//button//span[contains(text(),'access_time')]")
	private WebElement clockIcon;

	@FindBy(how = How.XPATH, using = "//div[@role='dialog']//div[@role='menu']//following-sibling::span")
	private List<WebElement> minuteElementsClock;

	@FindBy(how = How.XPATH, using = "//button//span[contains(text(),'Today')]")
	private WebElement todayButton;

	@FindBy(how = How.XPATH, using = "//div[@role='document']")
	private WebElement documentOverlay;

	@FindBy(how = How.XPATH, using = "//button//span[contains(text(),'Cancel')]")
	private WebElement cancelButton;

	@FindBy(xpath = "//a[text()='Ok' and @role='button']")
	private WebElement okButton;

	@FindBy(xpath = "//a[text()='select time' and @role='button']")
	private WebElement selectTimeButton;

	@FindBy(xpath = "//button[@title='Date Range' and @tabindex=0]")
	private WebElement globalDatePickerButton;

	@FindBy(xpath = "//span[text()='Last 24 Hours']")
	private WebElement last24HoursButton;

	@FindBy(xpath = "//span[text()='This Week']")
	private WebElement thisWeekButton;

	@FindBy(xpath = "//div[@class='ant-calendar-date-input-wrap']/input[@placeholder='Start date']")
	private WebElement startDateTextBox;

	@FindBy(xpath = "//div[@class='ant-calendar-date-input-wrap']/input[@placeholder='End date']")
	private WebElement endDateTextBox;

	public void clickOnGlobalDatePicker(){
		SeleniumUtils.waitUntilElementClickable(globalDatePickerButton,driver,2L);
		SeleniumUtils.clickOnAnElement(globalDatePickerButton);
	}

	public String startDateSelector(String date){
		String[] dateArray = date.split("/");
		int year = Integer.parseInt(dateArray[2]);
		int month = Integer.parseInt(dateArray[1]) - 1;
		int day = Integer.parseInt(dateArray[0]);

		String pattern = "dd/MM/yyyy HH:mm:ss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);

		String startDate = simpleDateFormat.format(calendar.getTime());
		Log.info("Start Date for selection is :" + startDate);

		return startDate;
	}

	public String endDateSelector(String date){
		String[] dateArray = date.split("/");
		int year = Integer.parseInt(dateArray[2]);
		int month = Integer.parseInt(dateArray[1]) - 1;
		int day = Integer.parseInt(dateArray[0]);

		String pattern = "dd/MM/yyyy HH:mm:ss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);
		calendar.add(Calendar.DAY_OF_MONTH,7);

		String endDate = simpleDateFormat.format(calendar.getTime());
		Log.info("End Date for selection is :" + endDate);

		return endDate;
	}

	public void enterDateRange(String date){
		if(!date.equalsIgnoreCase("default")){
			SeleniumUtils.isElementDisplayed(startDateTextBox);
			startDateTextBox.sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
			SeleniumUtils.enterTextToAField(startDateTextBox, startDateSelector(date));

			CommonUtils.wait(1);
			SeleniumUtils.isElementDisplayed(endDateTextBox);
			endDateTextBox.sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
			SeleniumUtils.enterTextToAField(endDateTextBox, endDateSelector(date));

			SeleniumUtils.waitUntilElementClickable(okButton,driver,2L);
			SeleniumUtils.performAJavaScriptClick(driver, okButton);
			Log.info("Clicked on date picker ok button");
		}
	}
}
