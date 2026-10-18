
package contact;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import genericutility.BaseClass;
import genericutility.ExcelUtility;
import objrepo.ContactPage;

public class ContactTest extends BaseClass {

	// CON_SMK_01: Smoke test
	@Test(groups = { "Smoke", "Positive" })
	public void verifyContactModuleAndCreateFormOpens() {

		ContactPage contact = new ContactPage(driver);

		// Step 1: Open Contacts
		contact.openContactsModule();

		// Step 2: Open Create Contact form
		contact.clickAddContact();

		// Step 3: Verify the form
		Assert.assertTrue(contact.isCreateContactFormDisplayed(), "Create Contact form did not open.");

		Reporter.log("PASS: Contacts module and Create Contact form opened.", true);
	}

	@Test(groups = { "Negative" })
	public void createContactWithoutLastName() throws Exception {

		ExcelUtility excel = new ExcelUtility();

		String firstName = excel.getDataFromExcel("Contact", 2, 0);

		String email = excel.getDataFromExcel("Contact", 2, 2);

		String phone = excel.getDataFromExcel("Contact", 2, 3);

		ContactPage contact = new ContactPage(driver);

		// Step 1: Open Contacts
		contact.openContactsModule();

		// Step 2: Open Create Contact form
		contact.clickAddContact();

		// Step 3: Enter details with mandatory Last Name left empty
		contact.enterContactDetails(firstName, "", email, phone);

		// Step 4: Click Save
		contact.clickSave();

		// Step 5: Verify and accept validation alert
		String alertMessage = contact.getAlertText();

		Assert.assertTrue(alertMessage.toLowerCase().contains("last name"),
				"Expected Last Name validation alert, but received: " + alertMessage);

		contact.acceptAlert();

		Reporter.log("PASS: Contact creation was rejected without Last Name.", true);
	}

	@Test(groups = { "Functional", "Positive" })
	public void createContactWithValidDetails() throws Exception {

		ExcelUtility excel = new ExcelUtility();

		String firstName = excel.getDataFromExcel("Contact", 2, 0);

		String lastName = excel.getDataFromExcel("Contact", 2, 1);
//        + System.currentTimeMillis();

		String email = excel.getDataFromExcel("Contact", 2, 2);

		String phone = excel.getDataFromExcel("Contact", 2, 3);

		ContactPage contact = new ContactPage(driver);

		// Step 1: Open Contacts
		contact.openContactsModule();

		// Step 2: Open Create Contact form
		contact.clickAddContact();

		// Step 3: Enter valid details
		contact.enterContactDetails(firstName, lastName, email, phone);

		// Step 4: Save Contact
		contact.clickSave();

		// Step 5: Open Contacts and search for the new Contact
		contact.openContactsModule();
		contact.searchContact(lastName);

		// Step 6: Verify Contact creation
		Assert.assertTrue(contact.isContactDisplayed(lastName), "Contact was not created: " + lastName);

		Reporter.log("PASS: Contact created successfully: " + lastName, true);
	}

}
