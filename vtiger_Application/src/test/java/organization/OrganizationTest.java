
package organization;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import java.time.Duration;
import org.openqa.selenium.support.ui.WebDriverWait;
import genericutility.BaseClass;
import genericutility.ExcelUtility;
import objrepo.OrganizationPage;

public class OrganizationTest extends BaseClass {

	@Test(groups = { "System", "Positive" })
	public void verifyOrganizationCreationEndToEnd() throws Exception {

		ExcelUtility excel = new ExcelUtility();

		String name = excel.getDataFromExcel("Organization", 3, 0) + "_" + System.currentTimeMillis();
		String website = excel.getDataFromExcel("Organization", 3, 1);
		String phone = excel.getDataFromExcel("Organization", 3, 2);
		String email = excel.getDataFromExcel("Organization", 3, 3);

		OrganizationPage organizationPage = new OrganizationPage(driver);

		// Step 1: Open Organizations
		organizationPage.openOrganizationsModule();

		// Step 2: Click Add
		organizationPage.clickAddOrganization();

		// Step 3: Enter details
		organizationPage.enterOrganizationDetails(name, website, phone, email);

		// Step 4: Save
		organizationPage.clickSave();

		// Handle any application alert
		if (organizationPage.isAlertPresent()) {
			String alertMessage = organizationPage.getAlertText();
			organizationPage.acceptAlert();

			Assert.fail("Organization creation failed. Alert: " + alertMessage);
		}

		// Search for the exact organization that was just created
		organizationPage.openOrganizationsModule();
		organizationPage.searchOrganization(name);

		// Verify the saved record exists in search results
		Assert.assertTrue(organizationPage.isOrganizationNameDisplayed(name),
				"Saved organization was not found: " + name);

		Reporter.log("PASS: Organization created and verified: " + name, true);

	}

	@Test(groups = { "Negative" })
	public void createOrganizationWithMissingMandatoryName() throws Exception {

		ExcelUtility excel = new ExcelUtility();

		// Read organization name from Excel

		String website = excel.getDataFromExcel("Organization", 3, 1);
		String phone = excel.getDataFromExcel("Organization", 3, 2);
		String email = excel.getDataFromExcel("Organization", 3, 3);

		OrganizationPage organization = new OrganizationPage(driver);

		// Step 1: Open Organizations
		organization.openOrganizationsModule();

		// Step 2: Click the green + icon
		organization.clickAddOrganization();

		// Step 3: Enter other details but leave mandatory name empty
		organization.enterOrganizationDetails("", website, phone, email);

		// Step 4: Click Save
		organization.clickSave();

		// Step 5: Verify the validation alert
		String alertMessage = organization.getAlertText();

		Assert.assertEquals(alertMessage, "Organization Name cannot be empty",
				"Expected mandatory-field validation alert was not displayed.");

		// Step 6: Accept the alert
		organization.acceptAlert();

		Reporter.log("PASS: Organization creation was rejected when the mandatory name was empty.", true);

	}

	@Test(groups = { "Functional", "Positive" })
	public void searchExistingOrganization() throws Exception {

		ExcelUtility excel = new ExcelUtility();
		String name = excel.getDataFromExcel("Organization", 4, 0);

		OrganizationPage organization = new OrganizationPage(driver);

		// Step 1: Open Organizations
		organization.openOrganizationsModule();

		// Step 2: Search for the organization
		organization.searchOrganization(name);

		Assert.assertTrue(
				new WebDriverWait(driver, Duration.ofSeconds(10))
						.until(d -> organization.isOrganizationNameDisplayed(name)),
				"Organization was not found in search results: " + name);

		Reporter.log("PASS: Organization search successful: " + name, true);
	}

	@Test(groups = { "Smoke", "Positive" })
	public void verifyOrganizationsModuleOpens() {

		OrganizationPage organization = new OrganizationPage(driver);

		// Step 1: Open Organizations module
		organization.openOrganizationsModule();

		// Step 2: Verify the Organizations page is displayed
		Assert.assertTrue(driver.getCurrentUrl().contains("module=Accounts"), "Organizations module did not open");

		Reporter.log("PASS: Organizations module opened successfully.", true);
	}

}
