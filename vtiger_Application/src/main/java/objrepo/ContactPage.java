
package objrepo;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ContactPage {

	WebDriver driver;
	WebDriverWait wait;

	public ContactPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	}

	// Contacts module
	@FindBy(linkText = "Contacts")
	private WebElement contactsModule;

	// Green + icon
	@FindBy(xpath = "//img[contains(@src,'btnL3Add.gif')]")
	private WebElement addContactButton;

	// Contact details
	@FindBy(name = "firstname")
	private WebElement firstName;

	@FindBy(name = "lastname")
	private WebElement lastName;

	@FindBy(id = "email")
	private WebElement email;

	@FindBy(name = "phone")
	private WebElement phone;

	// Organization association
	@FindBy(name = "account_name")
	private WebElement organizationName;

	// Save button
	@FindBy(xpath = "//input[@name='button' and contains(@value,'Save')]")
	private WebElement saveButton;

	// Search controls on Contacts list
	@FindBy(name = "search_text")
	private WebElement searchText;

	@FindBy(name = "search_field")
	private WebElement searchField;

	@FindBy(xpath = "//input[contains(@value,'Search Now')]")
	private WebElement searchNowButton;

	// Open Contacts module
	public void openContactsModule() {
		wait.until(ExpectedConditions.elementToBeClickable(contactsModule)).click();
	}

	// Open Create Contact form
	public void clickAddContact() {
		wait.until(ExpectedConditions.elementToBeClickable(addContactButton)).click();

		wait.until(ExpectedConditions.visibilityOf(lastName));
	}

	// Verify Create Contact form
	public boolean isCreateContactFormDisplayed() {
		return wait.until(ExpectedConditions.visibilityOf(lastName)).isDisplayed();
	}

	// Enter Contact details
	public void enterContactDetails(String first, String last, String emailAddress, String phoneNumber) {

		firstName.clear();
		firstName.sendKeys(first);

		lastName.clear();
		lastName.sendKeys(last);

		email.clear();
		email.sendKeys(emailAddress);

		phone.clear();
		phone.sendKeys(phoneNumber);
	}

	// Select an existing Organization using the lookup popup
	public void selectOrganization(String organization) {

		// The lookup icon is typically beside the Organization field.
		By lookupIcon = By.xpath("//input[@name='account_name']/following-sibling::img");

		wait.until(ExpectedConditions.elementToBeClickable(lookupIcon)).click();

		String parentWindow = driver.getWindowHandle();

		wait.until(driver -> driver.getWindowHandles().size() > 1);

		for (String window : driver.getWindowHandles()) {
			if (!window.equals(parentWindow)) {
				driver.switchTo().window(window);
				break;
			}
		}

		By organizationLink = By.xpath("//a[normalize-space()='" + organization + "']");

		wait.until(ExpectedConditions.elementToBeClickable(organizationLink)).click();

		// Return to the Contact form window
		wait.until(driver -> driver.getWindowHandles().contains(parentWindow));

		driver.switchTo().window(parentWindow);

		wait.until(ExpectedConditions.attributeToBe(organizationName, "value", organization));
	}

	// Save Contact
	public void clickSave() {
		wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
	}

	// Search Contact by last name
	public void searchContact(String last) {
		wait.until(ExpectedConditions.visibilityOf(searchText));

		searchText.clear();
		searchText.sendKeys(last);

		new org.openqa.selenium.support.ui.Select(searchField).selectByVisibleText("Last Name");

		wait.until(ExpectedConditions.elementToBeClickable(searchNowButton)).click();
	}

	// Verify Contact exists in current results
	public boolean isContactDisplayed(String last) {
		By contactLink = By.xpath("//a[normalize-space()='" + last + "']");

		return !driver.findElements(contactLink).isEmpty();
	}

	// Verify an Organization association on the saved Contact page
	public boolean isOrganizationDisplayed(String organization) {
		try {
			return wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), organization));
		} catch (org.openqa.selenium.TimeoutException e) {
			return false;
		}
	}

	// Get validation alert message
	public String getAlertText() {
		return wait.until(ExpectedConditions.alertIsPresent()).getText();
	}

	// Accept validation alert
	public void acceptAlert() {
		wait.until(ExpectedConditions.alertIsPresent()).accept();
	}
}
