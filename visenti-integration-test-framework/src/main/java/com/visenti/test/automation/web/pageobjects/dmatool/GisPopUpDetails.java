package com.visenti.test.automation.web.pageobjects.dmatool;

import com.visenti.test.automation.constants.FrameworkConstants;
import com.visenti.test.automation.helpers.Log;
import com.visenti.test.automation.helpers.RuntimeConfigSingleton;
import com.visenti.test.automation.utils.CommonUtils;
import com.visenti.test.automation.utils.SeleniumUtils;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GisPopUpDetails {
    private final WebDriver driver;
    String customerName = RuntimeConfigSingleton.getInstance().getCustomerName();

    public GisPopUpDetails(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@role='tab' and text()='Subzones']")
    private WebElement subZonesTab;

    @FindBy(xpath = "(//div[text()='Clear All' and contains(@class,'jss3')])[last()-1]")
    private WebElement clearAllButton;

    @FindBy(xpath = "//span[text()='Name']")
    private WebElement columnName;

    @FindBy(xpath = "//span[text()='Name']/following-sibling::span/div/i[contains(@aria-label,'caret-up')]")
    private WebElement nameUpSort;

    @FindBy(xpath = "//span[text()='Name']/following-sibling::span/div/i[contains(@aria-label,'caret-down')]")
    private WebElement NameDownSort;

    @FindBy(xpath = "//span[@class='ant-checkbox']")
    private WebElement nameCheckBox;

    @FindBy(xpath = "//span[@class='ant-modal-confirm-title']")
    private WebElement popUpTitle;

    @FindBy(xpath = "//div[text()='Do you want to zoom into an appropriate level?']")
    private WebElement popUpWarningMessage;

    @FindBy(xpath = "//span[text()='OK']")
    private WebElement okButton;

    @FindBy(xpath = "//span[text()='Cancel']")
    private WebElement cancelButton;

    @FindBy(xpath = "//td[text()='WQ 10MLD Subzones']/following-sibling::td/descendant::i[@aria-label='icon: link']//*[name()='path']")
    private WebElement downloadLinkForWQ_10MLD_Subzones;

    @FindBy(xpath = "//div[@id='flow_legend']/descendant::strong[text()='Flow Direction']")
    private WebElement flowDirectionLegend;

    @FindBy(xpath = "//span[text()='Not displaying all possible critical valves']")
    private WebElement valveFilterPopUpTitle;

    @FindBy(xpath = "//div[text()='Do you want to zoom to an appropriate level to see all critical valves?']")
    private WebElement valveFilterPopUpWarningMessage;

    public boolean isCustomerFromTheList(String customer){
        List<String> list = new ArrayList<>();
        list.add("cloud");
        list.add("pub");
        list.add("yw");

        return list.contains(customer.toLowerCase());
    }
    public void isRowHeaderDetailsAvailableUnderGisTab() {
        String name = columnName.getText();
        boolean columnHeaderName = SeleniumUtils.isElementDisplayed(columnName);
        Assert.assertTrue(columnHeaderName, "The column header" + name + " is not available");
        Log.info("The column header is -" + name);

        boolean upSorter = SeleniumUtils.isElementDisplayed(nameUpSort);
        Assert.assertTrue(upSorter, "The up sorter button is not available");
        Log.info("The GIS layers up sorter button is available");

        boolean downSorter = SeleniumUtils.isElementDisplayed(NameDownSort);
        Assert.assertTrue(downSorter, "The down sorter button is not available");
        Log.info("The GIS layers down sorter button is available");

        boolean checkBox = SeleniumUtils.isElementDisplayed(nameCheckBox);
        Assert.assertTrue(checkBox, "The GIS header checkBox is not available");
        Log.info("The GIS header checkBox is available");
    }

    public List<String> getGisLayersList() {
        List<String> list = new ArrayList<>();
        list.add("BulkMeter");
        list.add("Hydrant");
        list.add("Junction");
        list.add("Pipe");
        list.add("Valve");

        if (customerName.equalsIgnoreCase("yw")) {
            list.add("PRV");
            list.add("T and End junction");
        }
        if (customerName.equalsIgnoreCase("cloud") || customerName.equalsIgnoreCase("pub")) {
            list.add("Direct/Indirect Supply");
        }
        return list;
    }

    public void isAllGISTabsPresent() {
        List<WebElement> gisTabList = driver.findElements(By.xpath("//div[@role='tab' and contains(@class,' ant-tabs-tab')]"));
        int count = gisTabList.size();
        Log.info("GIS tabs count is - " + count);
        for (WebElement gisTab : gisTabList) {
            String gisTabName = gisTab.getText();
            boolean availableTab = SeleniumUtils.isElementDisplayed(gisTab);
            Assert.assertTrue(availableTab, "Not available tab is-" + gisTabName);
            Log.info("Available tab is -" + gisTabName);
        }
    }

    public void isGISTabSelectedByDefault() {
        WebElement element = driver.findElement(By.xpath("//div[text()='GIS' and @aria-selected='true']"));
        String attributeValue = element.getAttribute("aria-selected");
        if (attributeValue.contains("true")) {
            Log.info("Default selected tab is - GIS");
        } else {
            Log.info("GIS - tab not selected by default");
        }
    }

    public void isGISTabLayerListAvailable() {
        List<WebElement> gisTabLayerList = driver.findElements(By.xpath("(//tbody[@class='ant-table-tbody'])[1]/tr/td[contains(@class,'ant-table-column-has-actions ant-table-column-has-sorters')]"));
        for (WebElement layer : gisTabLayerList) {
            String name = layer.getText();
            Assert.assertTrue(getGisLayersList().contains(name), "The " + layer + " GIS layers not in specified list");
            Log.info("GIS layer name is -" + name);
        }
    }

    public void isGisLayersSortedInAscendingOrderByDefault() {
        List<WebElement> gisTabLayerList = driver.findElements(By.xpath("(//tbody[@class='ant-table-tbody'])[1]/tr/td[contains(@class,'ant-table-column-has-actions ant-table-column-has-sorters')]"));
        ArrayList<String> al = new ArrayList<>();
        int actualCount = gisTabLayerList.size();
        Log.info("GIS layers count is - " + actualCount);
        Log.info("The ascending order GIS layer list is:");
        for (WebElement layer : gisTabLayerList) {
            String name = layer.getText();
            al.add(name);
        }
        int ExpectedCount = getExpectedGisLayersListInDefaultOrder().size();
        Assert.assertEquals(ExpectedCount, actualCount, "Some of the GIS layer missed out from the list");
        for (int i = 0; i < ExpectedCount; i++) {
            Log.info(i + 1 + ".Actual layer list is - " + al.get(i) + " and from expected layer list is -" + getExpectedGisLayersListInDefaultOrder().get(i));
            Assert.assertEquals(al.get(i), getExpectedGisLayersListInDefaultOrder().get(i));
        }
    }

    public List<String> getExpectedGisLayersListInDefaultOrder() {
        List<String> list = new ArrayList<>();
        list.add(0, "BulkMeter");
        list.add(1, "Hydrant");
        list.add(2, "Junction");
        list.add(3, "Pipe");
        list.add(4, "Valve");

        if (customerName.equalsIgnoreCase("cloud") || customerName.equalsIgnoreCase("pub")) {
            list.add(1, "Direct/Indirect Supply");
        }
        if (customerName.equalsIgnoreCase("yw")) {
            list.add(3, "PRV");
            list.add(5, "T and End junction");
        }

        return list;
    }

    public void isGisLayersSortedInDescendingOrderOnClickOfDownSortButton() {
        CommonUtils.wait(3);
        Actions action = new Actions(driver);
        action.doubleClick(NameDownSort).perform();
        Log.info("Successfully clicked on down sort button");
        List<WebElement> gisTabLayerList = driver.findElements(By.xpath("(//tbody[@class='ant-table-tbody'])[1]/tr/td[contains(@class,'ant-table-column-has-actions ant-table-column-has-sorters')]"));
        ArrayList<String> al = new ArrayList<>();
        int actualCount = gisTabLayerList.size();
        Log.info("GIS layers count is - " + actualCount);
        Log.info("The descending order GIS layer list is:");
        for (WebElement layer : gisTabLayerList) {
            String name = layer.getText();
            al.add(name);
        }
        int ExpectedCount = getExpectedGisLayersListInDescendingOrder().size();
        Assert.assertEquals(ExpectedCount, actualCount, "Some of the GIS layer missed out from the list");
        for (int i = 0; i < ExpectedCount; i++) {
            Log.info(i + 1 + ".Actual layer list is - " + al.get(i) + " and from expected layer list is -" + getExpectedGisLayersListInDescendingOrder().get(i));
            Assert.assertEquals(al.get(i), getExpectedGisLayersListInDescendingOrder().get(i));
        }
    }

    public List<String> getExpectedGisLayersListInDescendingOrder() {
        List<String> list = new ArrayList<>();
        list.add(0, "Valve");
        list.add(1, "Pipe");
        list.add(2, "Junction");
        list.add(3, "Hydrant");
        list.add(4, "BulkMeter");

        if (customerName.equalsIgnoreCase("cloud") || customerName.equalsIgnoreCase("pub")) {
            list.add(4, "Direct/Indirect Supply");
        }
        if (customerName.equalsIgnoreCase("yw")) {
            list.add(3, "PRV");
            list.add(1, "T and End junction");
        }
        return list;
    }

    public void isAllGisLayersToggleButtonPresentUnderGISTab() {
        List<WebElement> gisTabLayerList = driver.findElements(By.xpath("(//tbody[@class='ant-table-tbody'])[1]/tr/td[contains(@class,'ant-table-column-has-actions ant-table-column-has-sorters')]"));
        for (WebElement layer : gisTabLayerList) {
            String name = layer.getText();
            WebElement gisLayerToggleButton = driver.findElement(By.xpath("//td[text()='" + name + "']/following-sibling::td/button"));
            boolean elementDisplayed = SeleniumUtils.isElementDisplayed(gisLayerToggleButton);
            Assert.assertTrue(elementDisplayed, "Gis layer " + name + " does not have a toggle button");
            Log.info("The toggle button present for GIS layer -" + name);
        }
    }

    public void isLayersHavingHeatMapButtonUnderGISTab(String layerName) {
            if(customerName.equalsIgnoreCase("cloud") || customerName.equalsIgnoreCase("pub")){
            WebElement heatMapBtn = driver.findElement(By.xpath("//td[text()='" + layerName + "']/following-sibling::td[last()]/button/i//*[local-name()='path' and @p-id='90289']"));
            boolean elementDisplayed = SeleniumUtils.isElementDisplayed(heatMapBtn);
            Assert.assertTrue(elementDisplayed, "Gis " + layerName + " layer does not have a heat map button");
            Log.info("Heat map button present for GIS layer - " + layerName);
        }
    }

    public void isLayerHavingExpandRowButtonUnderGISTab(String layerName) {
        WebElement layerExpandRowBtn = driver.findElement(By.xpath("//td[text()='" + layerName + "']/preceding-sibling::td/div[@aria-label='Expand row']"));
        boolean elementDisplayed = SeleniumUtils.isElementDisplayed(layerExpandRowBtn);
        Assert.assertTrue(elementDisplayed, "Gis " + layerName + " layer does not have a expand row button");
        Log.info("The expand row button available for GIS layer -" + layerName);
    }

    public List<String> getSubZonesLayers() {
        List<String> list = new ArrayList<>();
        if (customerName.equalsIgnoreCase("cloud") || customerName.equalsIgnoreCase("pub")) {
            list.add("BU Zones");
            list.add("NW Clusters");
            list.add("WQ 10MLD Subzones");
        }
        if (customerName.equalsIgnoreCase("yw")) {
            list.add("PMA");
        }
        return list;
    }

    public void clickOnSubZonesTab() {
        if(isCustomerFromTheList(customerName)){
            SeleniumUtils.waitUntilElementVisible(subZonesTab, driver, 3L);
            SeleniumUtils.performAJavaScriptClick(driver, subZonesTab);
            Log.info("Clicked on SubZones tab");
        }
    }

    public void isAllSubZonesTabLayersAvailable() {
        if(isCustomerFromTheList(customerName)){
            List<WebElement> subZonesLayersList = driver.findElements(By.xpath("(//tbody[@class='ant-table-tbody'])[last()]/descendant::td[contains(@class,'ant-table-column')]"));
            int count = subZonesLayersList.size();
            Log.info("The sub zones layers count is-" + count);
            for (WebElement layer : subZonesLayersList) {
                String name = layer.getText();
                Assert.assertTrue(getSubZonesLayers().contains(name), "The " + layer + " SubZones layer not available");
                Log.info("The Sub zones layer is-" + name);
            }
        }
    }

    public void isAllSubZonesTabLayersToggleButtonAvailable() {
        String name;
        if(isCustomerFromTheList(customerName)){
            List<WebElement> subZonesLayersToggleButtonList = driver.findElements(By.xpath("(//tbody[@class='ant-table-tbody'])[last()]/descendant::td[contains(@class,'ant-table-column')]"));
            for (WebElement layer : subZonesLayersToggleButtonList) {
                name = layer.getText();
                WebElement subZoneLayerToggleButton = driver.findElement(By.xpath("//td[text()='" + name + "']/following-sibling::td/descendant::i//*[local-name()='path' and @p-id='89403']"));
                boolean elementDisplayed = SeleniumUtils.isElementDisplayed(subZoneLayerToggleButton);
                Assert.assertTrue(elementDisplayed, "Sub zones layer " + name + " does not have a toggle button");
                Log.info("The toggle button present for Subzones layer -" + name);
            }
        }
    }

    public void isAllSubZonesTabLayersExpandRowButtonAvailable() {
        String name;
        if (isCustomerFromTheList(customerName)) {
            List<WebElement> subZonesLayersExpandRowButtonList = driver.findElements(By.xpath("(//tbody[@class='ant-table-tbody'])[last()]/descendant::td[contains(@class,'ant-table-column')]"));
            for (WebElement layer : subZonesLayersExpandRowButtonList) {
                name = layer.getText();
                WebElement subZoneLayerExpandRowButton = driver.findElement(By.xpath("//td[text()='" + name + "']/preceding-sibling::td/div[@aria-label='Expand row']"));
                boolean elementDisplayed = SeleniumUtils.isElementDisplayed(subZoneLayerExpandRowButton);
                Assert.assertTrue(elementDisplayed, "Sub zones layer " + name + " does not have a expand row button");
                Log.info("The expand row button present for Subzones layer -" + name);
            }
        }
    }

    public void isDownLoadButtonLinkAvailableForSubZonesLayer() {
        if (customerName.equalsIgnoreCase("cloud") || customerName.equalsIgnoreCase("pub")) {
            boolean subZoneLayerDownloadButtonLink = SeleniumUtils.isElementDisplayed(downloadLinkForWQ_10MLD_Subzones);
            Assert.assertTrue(subZoneLayerDownloadButtonLink, "The Sub zones layer WQ 10MLD Subzones does not have a download link");
            Log.info("The download button link present for Subzones layer - WQ 10MLD Subzones");
        }
    }

    public void isFileDownloadedOnClickOfDownloadButtonLink() throws IOException {
        if (customerName.equalsIgnoreCase("cloud") || customerName.equalsIgnoreCase("pub")) {
            Path path = Paths.get(FrameworkConstants.DOWNLOAD_FOLDER);
            String fileDownloadPath = FrameworkConstants.DOWNLOAD_FOLDER;
            Log.info("File downloaded path - " + fileDownloadPath);
            File dir = new File(fileDownloadPath);
            if (!dir.exists()) {
                Files.createDirectory(path);
            }
            File[] dir_contents = dir.listFiles();
            SeleniumUtils.performAJavaScriptClick(driver, subZonesTab);
            CommonUtils.wait(2);
            WebElement downloadLink = driver.findElement(By.xpath("//td[text()='WQ 10MLD Subzones']/following-sibling::td/descendant::button[@type='button' and @class ='ant-btn ant-btn-link']"));
            SeleniumUtils.performAJavaScriptClick(driver, downloadLink);
            CommonUtils.wait(2);
            Log.info("The file downloaded successfully");
            String fileName = "WQ_10MLD_Subzones.pdf";
            Assert.assertNotNull(dir_contents, "The directory is empty");
            for (File dir_content : dir_contents) {
                String name = dir_content.getName();
                Log.info("Directory contents - " + name);
                if (name.equalsIgnoreCase(fileName)) {
                    Assert.assertTrue(true, "The file not found");
                }
            }
            FileUtils.deleteDirectory(dir);
        }
    }

    public void isToolTipAvailableForSubzonesLayerWQMLDSubzones() {
        if (customerName.equalsIgnoreCase("cloud") || customerName.equalsIgnoreCase("pub")) {
            SeleniumUtils.performAJavaScriptClick(driver, subZonesTab);
            WebElement downloadLink = driver.findElement(By.xpath("//td[text()='WQ 10MLD Subzones']/following-sibling::td/descendant::button[@type='button' and @class ='ant-btn ant-btn-link']"));
            Actions a = new Actions(driver);
            a.moveToElement(downloadLink).perform();
            CommonUtils.wait(2);
            WebElement toolTip = driver.findElement(By.xpath("//div[contains(@class,'ant-tooltip ant-tooltip-placement-top')]/descendant::div[@class='ant-tooltip-content']/div[@class='ant-tooltip-inner']"));
            boolean elementDisplayed = SeleniumUtils.isElementDisplayed(toolTip);
            Assert.assertTrue(elementDisplayed, "The tooltip not displayed for subzones tab layer - Download 'WQ 10MLD Subzones' pdf");
            Log.info("The tooltip displayed for subzones tab layer - Download 'WQ 10MLD Subzones' pdf");
        }
    }

    public void clickOnNameCheckBoxUnderGisTab() {
        SeleniumUtils.waitUntilElementVisible(nameCheckBox, driver, 1L);
        SeleniumUtils.performAJavaScriptClick(driver, nameCheckBox);
    }

    public void clickOnNameCheckBoxAndOkButtonForEnablingAllGisLayers() {
        boolean elementEnabled;
        SeleniumUtils.clickOnAnElement(nameCheckBox);
        Log.info("Clicked on - checkbox");
        WebElement checkBox = driver.findElement(By.xpath("//label[@class='ant-checkbox-wrapper ant-checkbox-wrapper-checked']"));//span[contains(@class,'ant-table-column-title')]/descendant::span[@class='ant-checkbox ant-checkbox-checked']
        String attributeValue = checkBox.getAttribute("class");
        if (attributeValue.contains("ant-checkbox-wrapper ant-checkbox-wrapper-checked")) {
            Log.info("Check box is selected");
        } else {
            Log.info("Checkbox is not selected");
        }
        boolean elementDisplayed = SeleniumUtils.isElementDisplayed(okButton);
        Assert.assertTrue(elementDisplayed, "The OK button not displayed");
        CommonUtils.wait(1);
        Log.info("OK button is available");
        SeleniumUtils.performAJavaScriptClick(driver, okButton);
        Log.info("Successfully clicked on OK button");
        CommonUtils.wait(3);
        WebElement gisLayerToggleButton;
        List<WebElement> gisTabLayerList = driver.findElements(By.xpath("(//tbody[@class='ant-table-tbody'])[1]/tr/td[contains(@class,'ant-table-column-has-actions ant-table-column-has-sorters')]"));
        for (WebElement layer : gisTabLayerList) {
            String name = layer.getText();
            gisLayerToggleButton = driver.findElement(By.xpath("//td[text()='" + name + "']/following-sibling::td/descendant::i//*[local-name()='path' and @p-id='89403']"));
            elementEnabled = SeleniumUtils.isElementEnabled(gisLayerToggleButton);
            Assert.assertTrue(elementEnabled, "Gis layer " + name + " does not enabled a toggle button");
            Log.info("The toggle button is enabled for GIS layer -" + name);
        }
    }

    public void isGisAppTrayDetailsAvailable() {
        boolean elementDisplayed;
        WebElement element;
        elementDisplayed = SeleniumUtils.isElementDisplayed(clearAllButton);
        Assert.assertTrue(elementDisplayed, "The button not displayed in GIS app tray is - Clear All");
        Log.info("The - Clear All - button available in GIS app tray");

        List<WebElement> gisTabLayersList = driver.findElements(By.xpath("(//tbody[@class='ant-table-tbody'])[last()]/descendant::td[contains(@class,'ant-table-column')]"));
        for (int i = 0; i <= 1; i++) {
            String layerName = gisTabLayersList.get(i).getText();
            if (layerName.equalsIgnoreCase("BulkMeter")) {
                layerName = "Bulk Meter";
            }
            if (layerName.equalsIgnoreCase("Direct/Indirect Supply")) {
                layerName = "Direct / Indirect Supply";
            }
            element = driver.findElement(By.xpath("//button[@title='GIS']/descendant::div[text()='" + layerName + "']"));
            elementDisplayed = SeleniumUtils.isElementDisplayed(element);
            Assert.assertTrue(elementDisplayed, "The " + layerName + " button not available in GIS app tray");
            Log.info("The " + layerName + " button available in GIS app tray");

            element = driver.findElement(By.xpath("//div[text()='" + layerName + "']/following-sibling::div[text()='x']"));
            elementDisplayed = SeleniumUtils.isElementDisplayed(element);
            Assert.assertTrue(elementDisplayed, "The " + layerName + " layer close button not available in GIS app tray");
            Log.info("The " + layerName + " layer close button available in GIS app tray");
        }
        WebElement moreBtn = driver.findElement(By.xpath("//button[@title='GIS']/descendant::i[text()='More..']"));
        elementDisplayed = SeleniumUtils.isElementDisplayed(moreBtn);
        Assert.assertTrue(elementDisplayed, "The button not displayed in GIS app tray is - More..");
        Log.info("The - More.. -button available in GIS app tray");
    }

    public void isGisLegendsDetailsAvailable() {
        boolean elementDisplayed;
        String name;
        List<WebElement> gisLegends = driver.findElements(By.xpath("//div[@id='gis_legend']/descendant::div/strong"));
        for (WebElement legend : gisLegends) {
            name = legend.getText();
            elementDisplayed = SeleniumUtils.isElementDisplayed(legend);
            Assert.assertTrue(elementDisplayed, "The GIS legend not available on map is -" + name);
            Log.info("The GIS legend available on map is -" + name);

            if (name.equals("Valve") || name.equals("Direct / Indirect Supply") || name.equals("T and End Junction")) {
                WebElement element = driver.findElement(By.xpath("//div[@id='gis_legend']/descendant::div/strong[text()='" + name + "']"));
                SeleniumUtils.performAJavaScriptClick(driver, element);

                WebElement elementTitle = driver.findElement(By.xpath("//div[text()='" + name + "']"));
                name = elementTitle.getText();
                elementDisplayed = SeleniumUtils.isElementDisplayed(elementTitle);
                Assert.assertTrue(elementDisplayed, "The title is not present for GIS legend -" + name);
                Log.info("The title is present for GIS legend -" + name);

                List<WebElement> legendTypeList = driver.findElements(By.xpath("//div[contains(@class,'legend_popup')]/descendant::div[text()='" + name + "']/following-sibling::div/descendant::div[@style='padding: 0px 4px;']"));
                for (WebElement legendType : legendTypeList) {
                    String legendTypeName = legendType.getText();
                    WebElement legendStatus = driver.findElement(By.xpath("//div[text()='" + legendTypeName + "']"));
                    elementDisplayed = SeleniumUtils.isElementDisplayed(legendStatus);
                    Assert.assertTrue(elementDisplayed, "The GIS legend:" + name + " And legend Type:" + legendTypeName + " is not available");
                    Log.info("The GIS legend : " + name + " And legend Type : " + legendTypeName + " is available");
                }
            }
        }
    }

    public void clickOnToggleButtonForLayer(String layerName) {
        WebElement toggleButton = driver.findElement(By.xpath("//td[text()='" + layerName + "']/following-sibling::td/descendant::button"));
        SeleniumUtils.waitUntilElementClickable(toggleButton, driver, 1L);
        SeleniumUtils.clickOnAnElement(toggleButton);
        Log.info("Clicked on toggle button for the layer -" + layerName);
        CommonUtils.wait(2);
    }


    public void clickOnPopUpButton(String buttonName) {
        boolean elementDisplayed;
        if(SeleniumUtils.isElementDisplayed(popUpTitle)){

            String title = popUpTitle.getText();
            elementDisplayed = SeleniumUtils.isElementDisplayed(popUpTitle);
            Assert.assertTrue(elementDisplayed, "Wrong pop up title displayed is -" + title);
            Log.info("The pop up title is -" + title);

            String message = popUpWarningMessage.getText();
            elementDisplayed = SeleniumUtils.isElementDisplayed(popUpWarningMessage);
            Assert.assertTrue(elementDisplayed, "Wrong pop up warning message displayed is -" + message);
            Log.info("The pop up warning message is -" + message);

            if (buttonName.equalsIgnoreCase("OK")) {
                String btnName = okButton.getText();
                elementDisplayed = SeleniumUtils.isElementDisplayed(okButton);
                Assert.assertTrue(elementDisplayed, "Wrongly displayed button on pop up is-" + btnName);
                Log.info("Displayed button on pop up is -" + btnName);
                SeleniumUtils.performAJavaScriptClick(driver, okButton);
                Log.info("Clicked on - OK - button");
                CommonUtils.wait(2);
            } else {
                String btnName = cancelButton.getText();
                elementDisplayed = SeleniumUtils.isElementDisplayed(cancelButton);
                Assert.assertTrue(elementDisplayed, "Wrongly displayed button on pop up is-" + btnName);
                Log.info("Displayed button on pop up is -" + btnName);
                SeleniumUtils.performAJavaScriptClick(driver, cancelButton);
                Log.info("Clicked on - Cancel -  button");
                CommonUtils.wait(1);
            }
        }
    }

    public void isGisLayerEnabled(String layerName) {
        List<String> list = new ArrayList<>();
        WebElement layerToggleButton = driver.findElement(By.xpath("//td[text()='" + layerName + "']/following-sibling::td/descendant::button[contains(@class,'ant-btn-link')]"));
        String classValue = layerToggleButton.getAttribute("class");
        String[] classArr = classValue.split(" ");
        for(String s : classArr){
            if(s.startsWith("jss")){
                list.add(s);
            }
        }
        int count = list.size();
        Assert.assertEquals(count, 2, "The toggle button not enabled for -" + layerName);
        Log.info("The toggle button enabled for -" + layerName);
    }

    public void isGisAppTrayDisplayedEnabledGisLayer(String layerName) {
        boolean elementDisplayed;
        if (layerName.equalsIgnoreCase("BulkMeter")) {
            layerName = "Bulk Meter";
        }
        if (layerName.equalsIgnoreCase("Direct/Indirect Supply")) {
            layerName = "Direct / Indirect Supply";
        }
        if (layerName.equalsIgnoreCase("T and End junction")) {
            layerName = "T and End Junction";
        }
        WebElement element;
        element = driver.findElement(By.xpath("//div[text()='" + layerName + "']"));
        elementDisplayed = SeleniumUtils.isElementDisplayed(element);
        Assert.assertTrue(elementDisplayed, "The " + layerName + " button not available in GIS app tray");
        Log.info("The - " + layerName + " - button available in GIS app tray");

        element = driver.findElement(By.xpath("//div[text()='" + layerName + "']/following-sibling::div[text()='x']"));
        elementDisplayed = SeleniumUtils.isElementEnabled(element);
        Assert.assertTrue(elementDisplayed, "The " + layerName + " layer close button not available in GIS app tray");
        Log.info("The - " + layerName + " - layer close button available in GIS app tray");
    }

    public void isGisLegendHavingGisLayerEnabled(String layerName) {
        if (layerName.equalsIgnoreCase("BulkMeter")) {
            layerName = "Bulk Meter";
        }
        if (layerName.equalsIgnoreCase("Direct/Indirect Supply")) {
            layerName = "Direct / Indirect Supply";
        }
        boolean elementDisplayed;
        WebElement enabledLayerLegend = driver.findElement(By.xpath("//div[@id='gis_legend']/descendant::div/strong[text()='" + layerName + "']"));
        String legendName = enabledLayerLegend.getText();
        elementDisplayed = SeleniumUtils.isElementDisplayed(enabledLayerLegend);
        Assert.assertTrue(elementDisplayed, "The GIS legend not available on map is - " + legendName);
        Log.info("The GIS legend available on map is - " + legendName);

    }

    public void clickOnLayerExpandRowButtonUnderGISTab(String layerName) {
        WebElement element = driver.findElement(By.xpath("//td[text()='" + layerName + "']/preceding-sibling::td/div[@aria-label='Expand row']"));
        boolean elementDisplayed = SeleniumUtils.isElementDisplayed(element);
        Assert.assertTrue(elementDisplayed, "Gis " + layerName + " layer does not have a expand row button");
        Log.info("The expand row button present for GIS layer - " + layerName);
        SeleniumUtils.performAJavaScriptClick(driver, element);
        CommonUtils.wait(1);
        Log.info("Clicked on expand row button for GIS layer - " + layerName);
    }

    public void isLayerFilterDetailsAvailableUnderGISTab(String layerName) {
        boolean elementDisplayed;
        if (layerName.equalsIgnoreCase("Pipe")) {
            layerName = "pipe";
        }
        if (layerName.equalsIgnoreCase("Valve")) {
            layerName = "valve";
        }
        /*verify title is displayed for layer on expand*/
        WebElement layerTitle = driver.findElement(By.xpath("//tr[@data-row-key='" + layerName + "-extra-row']/descendant::div[text()='Filters']"));
        elementDisplayed = SeleniumUtils.isElementDisplayed(layerTitle);
        Assert.assertTrue(elementDisplayed, "The title is not available after Gis " + layerName + " row expanded");
        Log.info("The Gis " + layerName + " row expanded title is - Filters");

        /*verify radio button displayed for layer on expand*/
        WebElement layerRadioBtn = driver.findElement(By.xpath("//tr[contains(@data-row-key,'" + layerName + "')]/descendant::span[contains(@class,'checked')]"));
        SeleniumUtils.waitUntilElementClickable(layerRadioBtn, driver, 5L);
        elementDisplayed = SeleniumUtils.isElementDisplayed(layerRadioBtn);
        Assert.assertTrue(elementDisplayed, "The radio button not available after " + layerName + " row expanded");
        Log.info("The radio button available after " + layerName + " row expanded");

        /*verify filter apply button displayed for layer on expand*/
        WebElement filterApplyBtn = driver.findElement(By.xpath("//tr[@data-row-key='" + layerName + "-extra-row']/descendant::span[text()='Apply']/ancestor::button"));
        elementDisplayed = SeleniumUtils.isElementDisplayed(filterApplyBtn);
        Assert.assertTrue(elementDisplayed, "The " + layerName + " filter apply button not available");
        Log.info("The " + layerName + " filter apply button available");

        /*verify add new filter button displayed for layer on expand*/
        WebElement addNewFilterBtn = driver.findElement(By.xpath("//tr[@data-row-key='" + layerName + "-extra-row']/descendant::span[text()='Add']/ancestor::button"));
        elementDisplayed = SeleniumUtils.isElementDisplayed(addNewFilterBtn);
        Assert.assertTrue(elementDisplayed, "The add new " + layerName + " filter button not available");
        Log.info("The add new " + layerName + " filter button available");

        /*verify filter type drop-down displayed for layer on expand*/
        CommonUtils.wait(3);
        WebElement filterTypeDropDown = driver.findElement(By.xpath("//tr[@data-row-key='" + layerName + "-extra-row']/descendant::div[contains(@class,'ant-select-sm ant-select ant-select-enabled')]"));
        elementDisplayed = SeleniumUtils.isElementDisplayed(filterTypeDropDown);
        Assert.assertTrue(elementDisplayed, "The " + layerName + " filter type drop down not available");
        Log.info("The " + layerName + " filter type drop down available");

        /*verify filter type drop-down placeholder displayed for layer on expand*/
        WebElement filterTypeDropDownPlaceHolder = driver.findElement(By.xpath("//tr[@data-row-key='" + layerName + "-extra-row']/descendant::div[text()='Filter Type']"));
        elementDisplayed = SeleniumUtils.isElementDisplayed(filterTypeDropDownPlaceHolder);
        Assert.assertTrue(elementDisplayed, "The " + layerName + " filter type drop down place holder not available is - Filter Type");
        Log.info("The " + layerName + " filter type drop down place holder available is - Filter Type");
        SeleniumUtils.performAJavaScriptClick(driver, filterTypeDropDown);
        CommonUtils.wait(2);

        List<WebElement> layerFilterTypeDropDownList = driver.findElements(By.xpath("//li[contains(@class,'ant-select-dropdown-menu-item')]"));
        int count = layerFilterTypeDropDownList.size();
        Log.info("The size of the drop down list is-" + count);
        for (WebElement option : layerFilterTypeDropDownList) {
            String optionName = option.getText();
            elementDisplayed = SeleniumUtils.isElementDisplayed(option);
            Assert.assertTrue(elementDisplayed, "The option is not available from " + layerName + " filter drop down list");
            Log.info("The " + layerName + " drop down option is -" + optionName);
        }
    }

    public void verifyLegendFlowDirectionFunctionality() {
        if (customerName.equalsIgnoreCase("cloud") || customerName.equalsIgnoreCase("pub")) {
            SeleniumUtils.getTextOfAWebElement(flowDirectionLegend);
            SeleniumUtils.waitUntilElementVisible(flowDirectionLegend, driver, 7L);
            boolean elementDisplayed = SeleniumUtils.isElementDisplayed(flowDirectionLegend);
            Assert.assertTrue(elementDisplayed, "The flow direction legend is not available");
            Log.info("The flow direction legend is available");
            WebElement flowDirectionCheckBox = driver.findElement(By.xpath("//span[@class='ant-checkbox ant-checkbox-checked']"));
            String classValue = flowDirectionCheckBox.getAttribute("class");
            if (classValue.contains("ant-checkbox ant-checkbox-checked")) {
                Log.info("The flow direction legend check box is checked");
            } else {
                Log.info("The flow direction legend check box is not checked");
            }
        }
    }

    public void verifyAddNewFilterFuctionality(String layerName) {
        boolean elementDisplayed;
        if (layerName.equalsIgnoreCase("Pipe")) {
            layerName = "pipe";
        }
        if (layerName.equalsIgnoreCase("Valve")) {
            layerName = "valve";
        }
        WebElement addNewLayerFilterBtn = driver.findElement(By.xpath("//tr[@data-row-key='" + layerName + "-extra-row']/descendant::span[text()='Add']/ancestor::button"));
        elementDisplayed = SeleniumUtils.isElementDisplayed(addNewLayerFilterBtn);
        Assert.assertTrue(elementDisplayed, "Add new - " + layerName + " - filter button is not available");
        Log.info("Add new - " + layerName + " - filter button is not available");
        SeleniumUtils.waitUntilElementVisible(addNewLayerFilterBtn, driver, 1L);
        SeleniumUtils.performAJavaScriptClick(driver, addNewLayerFilterBtn);
        Log.info("Clicked on button is - +Add");

        /* verify newly added row radio button available */
        WebElement radioBtnOfAddNewFilterForLayer = driver.findElement(By.xpath("//tr[@data-row-key='" + layerName + "-extra-row']/descendant::span[@class='ant-radio']"));
        elementDisplayed = SeleniumUtils.isElementDisplayed(radioBtnOfAddNewFilterForLayer);
        Assert.assertTrue(elementDisplayed, "Gis - " + layerName + " - layer does not have a newly added row radio Button");
        Log.info("The newly added row radio Button present for GIS layer - " + layerName);

        /*verify newly added row filter type drop down available */
        WebElement filterTypeDropDownOfAddNewFilterForLayer = driver.findElement(By.xpath("//tr[@data-row-key='" + layerName + "-extra-row']/descendant::tr[contains(@data-row-key,'1')]/descendant::div[contains(@class,'ant-select-sm ant-select ant-select-enabled')]"));
        elementDisplayed = SeleniumUtils.isElementDisplayed(filterTypeDropDownOfAddNewFilterForLayer);
        Assert.assertTrue(elementDisplayed, "Gis " + layerName + " layer does not have a newly added row filter type drop down");
        Log.info("The newly added row filter type drop down present for GIS layer - " + layerName);

        /*verify newly added row delete button available */
        WebElement filterDeleteBtnForLayer = driver.findElement(By.xpath("//tr[@data-row-key='" + layerName + "-extra-row']/descendant::tr[contains(@class,'ant-table-row')][last()]/descendant::button"));
        elementDisplayed = SeleniumUtils.isElementDisplayed(filterDeleteBtnForLayer);
        Assert.assertTrue(elementDisplayed, "Gis " + layerName + " layer does not have a newly added row filter delete button");
        Log.info("The newly added row filter delete button present for GIS layer - " + layerName);
    }

    public void isVerifyDeleteFilterFuctionality(String layerName) {
        if (layerName.equalsIgnoreCase("Pipe")) {
            layerName = "pipe";
        }
        if (layerName.equalsIgnoreCase("Valve")) {
            layerName = "valve";
        }
        WebElement filterDeleteBtnForLayer = driver.findElement(By.xpath("//tr[@data-row-key='" + layerName + "-extra-row']/descendant::tr[contains(@class,'ant-table-row')][last()]/descendant::button"));
        SeleniumUtils.waitUntilElementVisible(filterDeleteBtnForLayer, driver, 1L);
        SeleniumUtils.performAJavaScriptClick(driver, filterDeleteBtnForLayer);
        Log.info("Deleted the newly added row filter for GIS layer - " + layerName);
    }

    public void isClickAndSelectFilterTypeAndValueTypeOptionFromDropDownAndClickOnApplyBtn(String filterType, String valueType, String layerName) {
        String name;
        if (layerName.equalsIgnoreCase("Valve")) {
            layerName = "valve";
        }
        WebElement filterTypeDropDown = driver.findElement(By.xpath("//tr[@data-row-key='" + layerName + "-extra-row']/descendant::div[@class='ant-select-selection__placeholder']"));
        SeleniumUtils.performAJavaScriptClick(driver, filterTypeDropDown);
        CommonUtils.wait(1);
        WebElement selectFilterTypeOption = driver.findElement(By.xpath("//li[text()='" + filterType + "' and @role='option']"));
        name = selectFilterTypeOption.getText();
        SeleniumUtils.performAJavaScriptClick(driver, selectFilterTypeOption);
        Log.info("Click and selected option from filter type drop down is -" + name);
        CommonUtils.wait(1);

        WebElement valueDropDown;
        /* For multiple values selection */
        if (!filterType.equals("None")) {
            WebElement valueTypeDropDown = driver.findElement(By.xpath("//tr[@data-row-key='valve-extra-row']/descendant::div[contains(@class,'multiple')]/descendant::div[@class='ant-select-selection__placeholder']"));
            SeleniumUtils.performAJavaScriptClick(driver, valueTypeDropDown);
            CommonUtils.wait(1);
            if (valueType.contains(",")) {
                String[] value = valueType.split(",");
                for (String s : value) {
                    WebElement removeDefaultSelected = driver.findElement(By.xpath("//span[@class='ant-select-selection__choice__remove']"));
                    removeDefaultSelected.click();
                    valueDropDown = driver.findElement(By.xpath("//div[text()='" + getFilterValueType(filterType) + "' and @class='ant-select-selection__placeholder']"));
                    SeleniumUtils.performAJavaScriptClick(driver, valueDropDown);
                    CommonUtils.wait(1);
                    WebElement option = driver.findElement(By.xpath("//li[text()='" + s + "' and @role='option']"));
                    name = option.getText();
                    SeleniumUtils.performAJavaScriptClick(driver, option);
                    Log.info("The selected option from value type drop down is - " + name);
                }
            }
            /* For single value selection*/
            else {
                WebElement removeDefaultSelected = driver.findElement(By.xpath("//span[@class='ant-select-selection__choice__remove']"));
                removeDefaultSelected.click();
                valueDropDown = driver.findElement(By.xpath("//div[text()='" + getFilterValueType(filterType) + "' and @class='ant-select-selection__placeholder']"));
                SeleniumUtils.performAJavaScriptClick(driver, valueDropDown);
                CommonUtils.wait(1);
                WebElement option = driver.findElement(By.xpath("//li[text()='" + valueType + "' and @role='option']"));
                name = option.getText();
                SeleniumUtils.performAJavaScriptClick(driver, option);
                Log.info("The selected option from value type drop down is - " + name);
            }
        }
        WebElement filterApplyButton = driver.findElement(By.xpath("//button[@class='ant-btn ant-btn-primary ant-btn-sm']/span[text()='Apply']"));
        SeleniumUtils.waitUntilElementClickable(filterApplyButton, driver, 1L);
        SeleniumUtils.performAJavaScriptClick(driver, filterApplyButton);
        Log.info("Clicked on filter apply button");
        CommonUtils.wait(2);

        WebElement gisAppTray = driver.findElement(By.xpath("//button[@title='GIS']"));
        SeleniumUtils.performAJavaScriptClick(driver, gisAppTray);
    }

    public String getFilterValueType(String filterType) {
        String valueType = "";
        if (filterType.equals("Status")) {
            valueType = "Status";
        } else if (filterType.equals("Criticality")) {
            valueType = "Criticality Type";
        }

        return valueType;
    }

    public void isClickOnFilterPopUpButtonForFilterTypeAndValueType(String buttonName, String filterType, String valueType) {
        if (filterType.equals("Criticality") && valueType.contains("MEDIUM,HIGH,VERY HIGH")) {
            boolean elementDisplayed;
            String title = valveFilterPopUpTitle.getText();
            elementDisplayed = SeleniumUtils.isElementDisplayed(valveFilterPopUpTitle);
            Assert.assertTrue(elementDisplayed, "Wrong pop up title displayed is -" + title);
            Log.info("The pop up title is -" + title);

            String message = valveFilterPopUpWarningMessage.getText();
            elementDisplayed = SeleniumUtils.isElementDisplayed(valveFilterPopUpWarningMessage);
            Assert.assertTrue(elementDisplayed, "Wrong pop up warning message displayed is -" + message);
            Log.info("The pop up warning message is -" + message);

            if (buttonName.equalsIgnoreCase("OK")) {
                String btnName = okButton.getText();
                elementDisplayed = SeleniumUtils.isElementDisplayed(okButton);
                Assert.assertTrue(elementDisplayed, "Wrongly displayed button on pop up is - " + btnName);
                Log.info("Displayed button on pop up is -" + btnName);
                SeleniumUtils.performAJavaScriptClick(driver, okButton);
                Log.info("Clicked on - OK - button");
                CommonUtils.wait(2);
            } else {
                String btnName = cancelButton.getText();
                elementDisplayed = SeleniumUtils.isElementDisplayed(cancelButton);
                Assert.assertTrue(elementDisplayed, "Wrongly displayed button on pop up is - " + btnName);
                Log.info("Displayed button on pop up is -" + btnName);
                SeleniumUtils.performAJavaScriptClick(driver, cancelButton);
                Log.info("Clicked on - Cancel -  button");
                CommonUtils.wait(1);
            }
        }
    }

    public void isGisAppTrayContainsValveFilterDetails(String filterType, String valueType, String layerName) {
        boolean elementDisplayed;
        String name;

        WebElement gisLayer = driver.findElement(By.xpath("//div[text()='" + layerName + "']"));
        elementDisplayed = SeleniumUtils.isElementDisplayed(gisLayer);
        Assert.assertTrue(elementDisplayed, "The " + layerName + " layer button not available in GIS app tray");
        Log.info("The " + layerName + " layer button available in GIS app tray");

        if (customerName.equalsIgnoreCase("cloud") || customerName.equalsIgnoreCase("pub")) {
            String j = filterType.toLowerCase();
            WebElement filterTypeOption = driver.findElement(By.xpath("//button[@title='GIS']/descendant::div[text()='" + j + "']"));
            name = filterTypeOption.getText();
            elementDisplayed = SeleniumUtils.isElementDisplayed(filterTypeOption);
            Assert.assertTrue(elementDisplayed, "The selected filter type option does not available in GIS app tray");
            Log.info("The selected filter type option available in GIS app tray is - " + name);

            WebElement filterTypeCloseBtn = driver.findElement(By.xpath("//button[@title='GIS']/descendant::div[text()='" + j + "']/following-sibling::div[text()='x']"));
            name = filterTypeCloseBtn.getText();
            elementDisplayed = SeleniumUtils.isElementDisplayed(filterTypeCloseBtn);
            Assert.assertTrue(elementDisplayed, "The filter type close button not available in GIS app tray");
            Log.info("The filter type close button available in GIS app tray is - " + name);

        } else if (customerName.equalsIgnoreCase("yw")) {
            String j = filterType.toLowerCase();
            String k = valueType.toLowerCase();
            if (valueType.contains(",")) {
                String[] value = valueType.split(",");
                for (String s : value) {
                    String l = s.toLowerCase();
                    WebElement filterTypeOption = driver.findElement(By.xpath("//button[@title='GIS']/descendant::div[text()='" + j + ": " + l + "," + l + "']"));
                    name = filterTypeOption.getText();
                    elementDisplayed = SeleniumUtils.isElementDisplayed(filterTypeOption);
                    Assert.assertTrue(elementDisplayed, "The selected filter type option does not available in GIS app tray");
                    Log.info("The selected filter type option available in GIS app tray is - " + name);

                    WebElement filterTypeCloseBtn = driver.findElement(By.xpath("//button[@title='GIS']/descendant::div[text()='" + j + ": " + l + "," + l + "']/following-sibling::div[text()='x']"));
                    name = filterTypeCloseBtn.getText();
                    elementDisplayed = SeleniumUtils.isElementDisplayed(filterTypeCloseBtn);
                    Assert.assertTrue(elementDisplayed, "The filter type close button not available in GIS app tray");
                    Log.info("The filter type close button available in GIS app tray is - " + name);
                }
            } else {
                WebElement filterTypeOption = driver.findElement(By.xpath("//button[@title='GIS']/descendant::div[text()='" + j + ": " + k + "']"));
                name = filterTypeOption.getText();
                elementDisplayed = SeleniumUtils.isElementDisplayed(filterTypeOption);
                Assert.assertTrue(elementDisplayed, "The selected filter type option does not available in GIS app tray");
                Log.info("The selected filter type option available in GIS app tray is - " + name);

                WebElement filterTypeCloseBtn = driver.findElement(By.xpath("//button[@title='GIS']/descendant::div[text()='" + j + ": " + k + "']/following-sibling::div[text()='x']"));
                name = filterTypeCloseBtn.getText();
                elementDisplayed = SeleniumUtils.isElementDisplayed(filterTypeCloseBtn);
                Assert.assertTrue(elementDisplayed, "The filter type close button not available in GIS app tray");
                Log.info("The filter type close button available in GIS app tray is - " + name);
            }
            WebElement filterTypeOption = driver.findElement(By.xpath("//button[@title='GIS']/descendant::div[text()='" + j + "']"));
            name = filterTypeOption.getText();
            elementDisplayed = SeleniumUtils.isElementDisplayed(filterTypeOption);
            Assert.assertTrue(elementDisplayed, "The selected filter type option does not available in GIS app tray");
            Log.info("The selected filter type option available in GIS app tray is - " + name);

            WebElement filterTypeCloseBtn = driver.findElement(By.xpath("//button[@title='GIS']/descendant::div[text()='" + j + "']/following-sibling::div[text()='x']"));
            name = filterTypeCloseBtn.getText();
            elementDisplayed = SeleniumUtils.isElementDisplayed(filterTypeCloseBtn);
            Assert.assertTrue(elementDisplayed, "The filter type close button not available in GIS app tray");
            Log.info("The filter type close button available in GIS app tray is - " + name);
        }

        WebElement gisLayerCloseBtn = driver.findElement(By.xpath("//div[text()='" + layerName + "']/following-sibling::div[text()='x']"));
        elementDisplayed = SeleniumUtils.isElementEnabled(gisLayerCloseBtn);
        Assert.assertTrue(elementDisplayed, "The " + layerName + " layer close button not available in GIS app tray");
        Log.info("The - " + layerName + " - layer close button available in GIS app tray");
    }

    public void isGisLegendContainsValveFilterDetails(String filterType, String layerName) {
        boolean elementDisplayed;
        String name;
        WebElement valveLayer = driver.findElement(By.xpath("//div[@id='gis_legend']/descendant::strong[text()='" + layerName + "']"));
        name = valveLayer.getText();
        elementDisplayed = SeleniumUtils.isElementDisplayed(valveLayer);
        Assert.assertTrue(elementDisplayed, "The filter of the GIS layer not available");
        Log.info("The filter of the GIS layer is - " + name);

        WebElement valveLegendPopUpTitle = driver.findElement(By.xpath("//div[@class='ant-popover-title' and text()='" + layerName + "']"));
        name = valveLegendPopUpTitle.getText();
        elementDisplayed = SeleniumUtils.isElementDisplayed(valveLayer);
        Assert.assertTrue(elementDisplayed, "The legend pop up title not available");
        Log.info("The legend pop up title available is - " + name);

        List<WebElement> valvePopUpInnerContentHeaderList = driver.findElements(By.xpath("//div[@class='ant-popover-inner-content']/descendant::h3"));
        for (WebElement element : valvePopUpInnerContentHeaderList) {
            name = element.getText();
            elementDisplayed = SeleniumUtils.isElementDisplayed(element);
            Assert.assertTrue(elementDisplayed, "The valve pop up inner content header name not available");
            Log.info("The valve pop up inner content header name is - " + name);
            if (name.equals("Criticality")) {
                List<WebElement> valveFilterValueList = driver.findElements(By.xpath("//h3[text()='" + filterType + "']/parent::div/descendant::div/descendant::div[last()]"));
                for (WebElement value : valveFilterValueList) {
                    name = value.getText();
                    elementDisplayed = SeleniumUtils.isElementDisplayed(value);
                    Assert.assertTrue(elementDisplayed, "The valve legend filter value are not available");
                    Log.info("The valve legend filter value are - " + name);
                }
            }
            else{
                List<WebElement> valveFilterValueList = driver.findElements(By.xpath("//h3[text()='Status']/parent::div/descendant::div/descendant::div[last()]"));
                for (WebElement value : valveFilterValueList) {
                    name = value.getText();
                    elementDisplayed = SeleniumUtils.isElementDisplayed(value);
                    Assert.assertTrue(elementDisplayed, "The valve legend filter value are not available");
                    Log.info("The valve legend filter value are - " + name);
                }
            }
        }
    }

    public void verifyPopUpDisplayed() {
        boolean elementDisplayed;
        elementDisplayed = SeleniumUtils.isElementDisplayed(popUpTitle);
        Assert.assertTrue(elementDisplayed, "Wrong popUp title displayed");
        Log.info("The popUp title is - Zoom not enough to load GIS");

        elementDisplayed = SeleniumUtils.isElementDisplayed(popUpWarningMessage);
        Assert.assertTrue(elementDisplayed, "Wrong popUp warning message displayed");
        Log.info("The popUp warning message is - Do you want to zoom into an appropriate level?");

        elementDisplayed = SeleniumUtils.isElementDisplayed(cancelButton);
        Assert.assertTrue(elementDisplayed, "Cancel button not displayed");
        Log.info("The button available is - Cancel");

        elementDisplayed = SeleniumUtils.isElementDisplayed(okButton);
        Assert.assertTrue(elementDisplayed, "OK button not displayed");
        Log.info("The button available is - OK");
    }
}





