package genericutility;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

public class ScreenshotUtility {

    public String takeScreenshot(WebDriver driver, String testName)
            throws IOException {

        // Create screenshots folder
        String folderPath = "./screenshots";
        File folder = new File(folderPath);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        // Capture screenshot
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);

        // Save screenshot inside folder
        String filePath = folderPath + "/" + testName + ".png";
        File destination = new File(filePath);

        FileHandler.copy(source, destination);

        return filePath;
    }
}