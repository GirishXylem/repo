package com.visenti.test.automation.helpers;

import com.cucumber.listener.Reporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class DriverManager {

    public static WebDriver driver;

    public static WebDriver getDriver() {
        if (driver == null) {
            driver = createDriver();
        }
        return driver;
    }

    private static WebDriver createDriver() {
        String browserName = ConfigFileReader.getConfigProperty("web.browser.name");
        assert browserName != null;
        switch (browserName.toLowerCase()) {
            case "chrome":
//                System.setProperty("webdriver.chrome.driver",
//                    FileReaderManager.getInstance().getConfigReader().getChromeDriverPath());
                //System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY,"true");
                WebDriverManager.chromedriver().setup();
                String downloadFilepath = System.getProperty("user.dir") + "\\target\\downloads";
                Log.info("Browser download path - " + downloadFilepath);
                HashMap<String, Object> chromePrefs = new HashMap<>();
                chromePrefs.put("profile.default_content_settings.popups", 0);
                chromePrefs.put("download.default_directory", downloadFilepath);
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--start-maximized");
                options.addArguments("--disable-dev-shm-usage");
                options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
                options.setExperimentalOption("prefs", chromePrefs);

                if (Objects.requireNonNull(ConfigFileReader.getConfigProperty("is.chrome.execution.headless"))
                        .equalsIgnoreCase("yes")) {
                    options.addArguments("--headless");
                }

                driver = new ChromeDriver(options);
              	Reporter.setSystemInfo("Chrome Version",((RemoteWebDriver)driver).getCapabilities().getVersion().toString() );

                break;

            case "firefox":
//                System.setProperty("webdriver.gecko.driver",
//                    FileReaderManager.getInstance().getConfigReader().getFirefoxDriverPath());
                WebDriverManager.firefoxdriver().setup();
                String firefoxDownloadFilepath = System.getProperty("user.dir") + "\\target\\downloads";
                Log.info("Browser download path - " + firefoxDownloadFilepath);
                HashMap<String, Object> firefoxPrefs = new HashMap<>();
                firefoxPrefs.put("profile.default_content_settings.popups", 0);
                firefoxPrefs.put("download.default_directory", firefoxDownloadFilepath);

                // Set the profile when creating the FirefoxDriver instance
                FirefoxOptions fireOptions = new FirefoxOptions();
                fireOptions.addArguments("--start-maximized");
                fireOptions.addArguments("--disable-dev-shm-usage");

                // Create a new Firefox profile and set the preferences
                FirefoxProfile profile = new FirefoxProfile();
                fireOptions.setProfile(profile);
                driver = new FirefoxDriver(fireOptions);
                profile.setPreference("excludeSwitches", Collections.singletonList("enable-automation").toString());
                profile.setPreference("prefs", String.valueOf(firefoxPrefs));
                Reporter.setSystemInfo("Firefox Version", ((RemoteWebDriver) driver).getCapabilities().getVersion());
                break;

            default:
                throw new RuntimeException("Wrong browser name -" + browserName + " passed");
        }

        return driver;

    }

    public static void initialDriverSetUp() {
        driver.manage().timeouts().implicitlyWait(
                FileReaderManager.getInstance().getConfigReader().getImplicitlyWait(), TimeUnit.SECONDS);
        driver.manage().deleteAllCookies();
    }

    public static void quitDriver() {
        driver.quit();
        driver = null;
    }

}
