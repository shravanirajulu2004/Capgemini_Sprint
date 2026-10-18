package genericutility;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class WebDriverUtility {

    WebDriver driver = null;

    public WebDriver launchBrowser(String browser) {

        if (browser.equalsIgnoreCase("chrome")) {

        	ChromeOptions option = new ChromeOptions();

            Map<String, Object> prefs = new HashMap<>();
            prefs.put( "profile.password_manager_leak_detection",false);

            option.setExperimentalOption("prefs", prefs);

            driver = new ChromeDriver(option);

        } else if (browser.equalsIgnoreCase("edge")) {

            driver = new EdgeDriver();

        } else if (browser.equalsIgnoreCase("firefox")) {

            driver = new FirefoxDriver();

        } else {

            driver = new ChromeDriver();
        }

        driver.manage().window().maximize();
        genericWait(driver);

        return driver;
    }

    public void genericWait(WebDriver d) {
        d.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
    }
}