package objrepo;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OrganizationPage {

	WebDriver driver;
	WebDriverWait wait;

	public OrganizationPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	}

	// Organizations module
	@FindBy(partialLinkText = "Organizations")
	private WebElement organizationsModule;

	// Green + icon
	@FindBy(xpath = "//img[contains(@src,'btnL3Add.gif')]")
	private WebElement addOrganizationButton;

	// Organization details
	@FindBy(name = "accountname")
	private WebElement organizationName;

	@FindBy(name = "website")
	private WebElement website;

	@FindBy(name = "phone")
	private WebElement phone;

	@FindBy(id = "email1")
	private WebElement email;

	// Save buttons at the top and bottom
	@FindBy(xpath = "//input[@value='  Save  ' and @name='button']")
	private WebElement saveButton;

	@FindBy(name = "search_text")
	private WebElement searchText;

	@FindBy(name = "search_field")
	private WebElement searchField;

	@FindBy(xpath = "//input[@value=' Search Now ']")
	private WebElement searchNowButton;

	// Open Organizations module

	public void openOrganizationsModule() {
		By organizationsLocator = By.xpath("//a[normalize-space()='Organizations']");

		WebElement organizationsLink = wait
				.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(organizationsLocator)));

		organizationsLink.click();
	}

	// Click green + icon
	public void clickAddOrganization() {
		wait.until(ExpectedConditions.elementToBeClickable(addOrganizationButton)).click();

		wait.until(ExpectedConditions.visibilityOf(organizationName));
	}

	// Enter data read from Excel
	public void enterOrganizationDetails(String name, String websiteUrl, String phoneNumber, String emailAddress) {

		WebElement nameField = wait.until(ExpectedConditions.visibilityOf(organizationName));
		nameField.clear();
		nameField.sendKeys(name);

		WebElement websiteField = wait.until(ExpectedConditions.visibilityOf(website));
		websiteField.clear();
		websiteField.sendKeys(websiteUrl);

		WebElement phoneField = wait.until(ExpectedConditions.visibilityOf(phone));
		phoneField.clear();
		phoneField.sendKeys(phoneNumber);

		WebElement emailField = wait.until(ExpectedConditions.visibilityOf(email));
		emailField.clear();
		emailField.sendKeys(emailAddress);
	}

	// Click the visible Save button
	public void clickSave() {
		wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
	}

	public boolean isAlertPresent() {

		try {
			new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.alertIsPresent());

			return true;

		} catch (TimeoutException e) {
			return false;
		}
	}

	// Get browser alert message
	public String getAlertText() {
		return driver.switchTo().alert().getText();
	}

	// Accept browser alert
	public void acceptAlert() {
		driver.switchTo().alert().accept();
	}

	// Verify the created organization in the list

	public boolean isOrganizationDisplayed(String organizationName) {

		try {
			return wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), organizationName));

		} catch (TimeoutException e) {
			System.out.println("Organization not found on current page: " + organizationName);
			System.out.println("Current URL: " + driver.getCurrentUrl());
			System.out.println("Page title: " + driver.getTitle());
			return false;
		}
	}

	public void searchOrganization(String name) {
		wait.until(ExpectedConditions.visibilityOf(searchText));
		searchText.clear();
		searchText.sendKeys(name);

		new Select(searchField).selectByVisibleText("Organization Name");

		wait.until(ExpectedConditions.elementToBeClickable(searchNowButton)).click();

		// Wait for the requested organization to appear in search results
		By organizationLink = By.xpath("//a[normalize-space()='" + name + "']");

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(organizationLink));
		} catch (TimeoutException e) {
			System.out.println("Organization not found after search: " + name);
			System.out.println("Current URL: " + driver.getCurrentUrl());
		}
	}

	public void selectOrganization(String name) {
		By checkbox = By.xpath("//a[normalize-space()='" + name + "']/ancestor::tr[1]//input[@type='checkbox']");

		wait.until(ExpectedConditions.elementToBeClickable(checkbox)).click();
	}

	public boolean isOrganizationNameDisplayed(String name) {
		By organizationLink = By.xpath("//a[normalize-space()='" + name + "']");

		return !driver.findElements(organizationLink).isEmpty();
	}

}