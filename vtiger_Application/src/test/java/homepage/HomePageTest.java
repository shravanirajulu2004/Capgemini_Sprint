
package homepage;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import genericutility.BaseClass;
import genericutility.ExcelUtility;
import objrepo.HomePage;

public class HomePageTest extends BaseClass {

	@Test(groups = { "Functional", "Positive" })
	public void createNotebookWithExcelData() throws IOException {

		HomePage home = new HomePage(driver);
		ExcelUtility eu = new ExcelUtility();

		String title = eu.getDataFromExcel("NotebookTitles", 3, 1) + "_" + System.currentTimeMillis();

		Assert.assertNotNull(title, "Notebook title is null");
		Assert.assertFalse(title.trim().isEmpty(), "Notebook title is empty");

		home.clickAddWidget();
		home.clickNotebookOption();

		Assert.assertTrue(home.isWindowTitleDisplayed(), "Add Notebook popup was not opened");

		home.enterWindowTitle(title);
		home.clickSave();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		Assert.assertTrue(wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("stufftitle_id"))),
				"Notebook popup did not close after saving");
	}

	@Test(groups = { "Integration", "Positive" })
	public void verifyCalendarModuleNavigation() {
		HomePage home = new HomePage(driver);

		// Open the Home page mini-calendar
		home.clickHomeCalendarIcon();

		// Select October 16
		home.selectMiniCalendarDate("16");

		// Verify Calendar module navigation
		String currentUrl = driver.getCurrentUrl();

		Assert.assertTrue(currentUrl.contains("module=Calendar"), "Calendar module did not open");

	}

	@Test(groups = { "Functional", "Positive" })
	public void createDashboardWidgetWithExcelData() throws Exception {

		ExcelUtility eu = new ExcelUtility();
		HomePage home = new HomePage(driver);

		home.clickAddWidget();
		home.clickDashboardOption();

		String windowTitle = eu.getDataFromExcel("HomeWidget", 3, 0);

		home.enterWindowTitle(windowTitle);
		home.selectDashboardNameByIndex(0);
		home.selectDashboardTypeByIndex(1);
		home.clickSave();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		Assert.assertTrue(wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("stufftitle_id"))),
				"FAIL: Add Dashboard popup did not close after saving");
	}

	@Test(groups = { "Smoke", "Positive" })
	public void verifyHomePageLoads() {
		HomePage home = new HomePage(driver);

		Assert.assertTrue(home.isHomePageDisplayed(), "Home page did not load successfully");
	}

}
