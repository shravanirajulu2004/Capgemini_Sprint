package objrepo;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
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

	// =============== ORGANIZATIONS MODULE ===============

	@FindBy(xpath = "//a[normalize-space()='Organizations']")
	private WebElement organizationsLink;

	@FindBy(partialLinkText = "Organizations")
	private WebElement organizationsModule;

	public void clickOrganizations() {

		By dialog = By.cssSelector("div[id^='__vtigerjs_dialogbox_']");

		List<WebElement> dialogs = driver.findElements(dialog);

		for (WebElement element : dialogs) {
			if (element.isDisplayed()) {
				wait.until(ExpectedConditions.invisibilityOf(element));
			}
		}

		wait.until(ExpectedConditions
				.refreshed(ExpectedConditions.elementToBeClickable(By.xpath("//a[normalize-space()='Organizations']"))))
				.click();
	}

	// Supports the older test method name
	public void openOrganizationsModule() {
		clickOrganizations();
	}

	// =============== CREATE ORGANIZATION ===============

	@FindBy(xpath = "//img[@alt='Create Organization...']")
	private WebElement createOrganizationButton;

	@FindBy(xpath = "//img[contains(@src,'btnL3Add.gif')]")
	private WebElement addOrganizationButton;

	public void clickCreate() {
		wait.until(ExpectedConditions.elementToBeClickable(createOrganizationButton)).click();

		wait.until(ExpectedConditions.visibilityOf(organizationName));
	}

	// Supports the older test method name
	public void clickAddOrganization() {
		wait.until(ExpectedConditions.elementToBeClickable(addOrganizationButton)).click();

		wait.until(ExpectedConditions.visibilityOf(organizationName));
	}

	// =============== ORGANIZATION DETAILS ===============

	@FindBy(name = "accountname")
	private WebElement organizationName;

	@FindBy(name = "website")
	private WebElement website;

	@FindBy(name = "phone")
	private WebElement phone;

	@FindBy(id = "email1")
	private WebElement email;

	@FindBy(name = "bill_city")
	private WebElement billingCity;

	@FindBy(name = "bill_country")
	private WebElement billingCountry;

	@FindBy(name = "description")
	private WebElement description;

	public void enterOrganizationName(String name) {
		WebElement field = wait.until(ExpectedConditions.visibilityOf(organizationName));

		field.clear();
		field.sendKeys(name);
	}

	public void enterWebsite(String url) {
		website.clear();
		website.sendKeys(url);
	}

	public void enterPhone(String number) {
		phone.clear();
		phone.sendKeys(number);
	}

	public void enterEmail(String emailAddress) {
		email.clear();
		email.sendKeys(emailAddress);
	}

	public void enterBillingCity(String city) {
		billingCity.clear();
		billingCity.sendKeys(city);
	}

	public void enterBillingCountry(String country) {
		billingCountry.clear();
		billingCountry.sendKeys(country);
	}

	public void addDescription(String text) {
		description.clear();
		description.sendKeys(text);
	}

	// Supports the older test method name
	public void enterOrganizationDetails(String name, String websiteUrl, String phoneNumber, String emailAddress) {

		enterOrganizationName(name);
		enterWebsite(websiteUrl);
		enterPhone(phoneNumber);
		enterEmail(emailAddress);
	}

	// =============== SAVE ORGANIZATION ===============

	@FindBy(xpath = "//input[@class='crmbutton small save']")
	private WebElement saveButton;

	@FindBy(xpath = "//input[@value='  Save  ' and @name='button']")
	private WebElement oldSaveButton;

	public void clickSave() {
		wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
	}

	// =============== ALERT HANDLING ===============

	public boolean isAlertPresent() {
		try {
			new WebDriverWait(driver, Duration.ofSeconds(2)).until(ExpectedConditions.alertIsPresent());
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public String getAlertText() {
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		return alert.getText();
	}

	public void acceptAlert() {
		wait.until(ExpectedConditions.alertIsPresent()).accept();
	}

	// Supports the existing delete method
	public String acceptDeleteAlert() {
		String message = getAlertText();
		acceptAlert();
		return message;
	}

	// =============== SEARCH ORGANIZATION ===============

	@FindBy(name = "search_text")
	private WebElement searchText;

	@FindBy(name = "search_field")
	private WebElement searchField;

	@FindBy(xpath = "//select[option[normalize-space()='Organization Name']]")
	private WebElement searchFieldDropdown;

	@FindBy(name = "submit")
	private WebElement searchButton;

	@FindBy(xpath = "//input[@value=' Search Now ']")
	private WebElement searchNowButton;

	public void searchOrganization(String name) {

		WebElement searchBox = wait.until(ExpectedConditions.visibilityOf(searchText));

		searchBox.clear();
		searchBox.sendKeys(name);

		WebElement dropdown = wait.until(ExpectedConditions.visibilityOf(searchField));

		new Select(dropdown).selectByVisibleText("Organization Name");

		wait.until(ExpectedConditions.elementToBeClickable(searchNowButton)).click();

		// Wait for the exact matching record to appear.
		By organizationLink = By.xpath("//a[normalize-space(.)='" + name + "']");

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(organizationLink));
		} catch (TimeoutException e) {
			// The verification method in the test will report failure.
		}
	}

	// =============== VERIFY ORGANIZATION ===============

	public boolean isOrganizationDisplayed(String name) {

		By organizationLink = By.xpath("//a[normalize-space(.)='" + name + "']");

		try {
			return wait.until(ExpectedConditions.visibilityOfElementLocated(organizationLink)).isDisplayed();
		} catch (TimeoutException e) {
			return false;
		}
	}

	// Supports the older test method name
	public boolean isOrganizationNameDisplayed(String name) {
		return isOrganizationDisplayed(name);
	}

	// =============== OPEN ORGANIZATION ===============

	public void openOrganization(String name) {
		By organizationLink = By.xpath("//a[normalize-space(.)='" + name + "']");

		wait.until(ExpectedConditions.elementToBeClickable(organizationLink)).click();
	}

	// =============== EDIT ORGANIZATION ===============

	@FindBy(xpath = "//a[text()='edit']")
	private WebElement editBtn;

	public void clickEdit() {
		wait.until(ExpectedConditions.elementToBeClickable(editBtn)).click();
	}

	// =============== DELETE ORGANIZATION ===============

	public void clickDelete(String name) {
		By deleteLink = By.xpath("//tr[.//a[normalize-space()='" + name + "']]" + "//a[normalize-space()='del']");

		wait.until(ExpectedConditions.elementToBeClickable(deleteLink)).click();
	}

	// =============== CHECKBOX ===============

	public void selectOrganizationCheckbox(String name) {
		By checkbox = By.xpath("//a[normalize-space()='" + name + "']/ancestor::tr[1]//input[@type='checkbox']");

		wait.until(ExpectedConditions.elementToBeClickable(checkbox)).click();
	}

	// =============== MASS EDIT ===============

	@FindBy(xpath = "//input[@value='Mass Edit']")
	private WebElement massEditButton;

	public void clickMassEdit() {
		wait.until(ExpectedConditions.elementToBeClickable(massEditButton)).click();
	}

	// =============== EXPORT ===============

	@FindBy(xpath = "//input[contains(@value,'Export')]")
	private WebElement exportButton;

	public void clickExport() {
		wait.until(ExpectedConditions.elementToBeClickable(exportButton)).click();
	}

	// =============== FILTERS ===============

	@FindBy(xpath = "//select[option[normalize-space()='All']]")
	private WebElement filtersDropdown;

	public void selectFilterByIndex(int index) {
		new Select(filtersDropdown).selectByIndex(index);
	}
}
