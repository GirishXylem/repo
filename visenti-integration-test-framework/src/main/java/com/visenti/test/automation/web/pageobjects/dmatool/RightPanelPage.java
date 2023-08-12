package com.visenti.test.automation.web.pageobjects.dmatool;

import com.visenti.test.automation.utils.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class RightPanelPage {

    private WebDriver driver;

    public RightPanelPage(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    public String getStationOrGISTitle (String keyword){
        /*keyword = ConfigFileReader.getConfigProperty(keyword)!= null ?
                    ConfigFileReader.getConfigProperty(keyword) : keyword ;*/

        String title = SeleniumUtils.getTextOfAnElement(driver, keyword);
        return title;
    }
}
