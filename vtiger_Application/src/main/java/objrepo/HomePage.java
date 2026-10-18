
package objrepo;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

	WebDriver driver;
	WebDriverWait wait;
	Actions actions;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		actions = new Actions(driver);
	}

	// Add Widget button
	@FindBy(xpath = "//img[contains(@onclick,'fnAddWindow')]")
	private WebElement addWidgetButton;

	@FindBy(id = "adddash")
	private WebElement dashboardOption;

	@FindBy(id = "seldashbd_id")
	private WebElement dashboardNameDropdown;

	@FindBy(id = "seldashtype_id")
	private WebElement dashtypeByDropdown;

	@FindBy(xpath = "//a[@class='hdrLink' and normalize-space()='Home']")
	private WebElement homeHeading;

	// Notebook option in Add Widget menu
	@FindBy(id = "addNotebook")
	private WebElement notebookOption;

	// Window Title textbox
	@FindBy(id = "stufftitle_id")
	private WebElement windowTitleTextbox;

	// Save button
	@FindBy(id = "savebtn")
	private WebElement saveButton;

	// Home page mini-calendar icon
	@FindBy(xpath = "//img[@alt='Open Calendar...']")
	private WebElement calendarIcon;

	public void clickAddWidget() {
		addWidgetButton.click();
	}

	public void clickNotebookOption() {
		wait.until(ExpectedConditions.elementToBeClickable(notebookOption)).click();
	}

	public void enterWindowTitle(String title) {
		WebElement textbox = wait.until(ExpectedConditions.visibilityOf(windowTitleTextbox));

		textbox.clear();

		if (title != null && !title.trim().isEmpty()) {
			textbox.sendKeys(title);
		}
	}

	public void clickSave() {
		saveButton.click();
	}

	// Check whether the title field is displayed
	public boolean isWindowTitleDisplayed() {
		try {
			return wait.until(ExpectedConditions.visibilityOf(windowTitleTextbox)).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	// Check whether the title field is empty
	public boolean isWindowTitleEmpty() {
		WebElement textbox = wait.until(ExpectedConditions.visibilityOf(windowTitleTextbox));

		String value = textbox.getAttribute("value");
		return value == null || value.trim().isEmpty();
	}

	public void clickHomeCalendarIcon() {
		wait.until(ExpectedConditions.elementToBeClickable(calendarIcon)).click();
	}

	// Select a date from the mini-calendar
	public void selectMiniCalendarDate(String day) {
		By dateLocator = By.xpath("//div[@id='miniCal']//a[normalize-space()='" + day + "']");

		wait.until(ExpectedConditions.elementToBeClickable(dateLocator)).click();
	}

	public boolean isHomePageDisplayed() {
		return homeHeading.isDisplayed();
	}

	public void clickDashboardOption() {
		wait.until(ExpectedConditions.elementToBeClickable(dashboardOption)).click();
	}

	public void selectDashboardNameByIndex(int index) {
		WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(dashboardNameDropdown));

		new Select(dropdown).selectByIndex(index);
	}

	public void selectDashboardTypeByIndex(int index) {
		WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(dashtypeByDropdown));

		new Select(dropdown).selectByIndex(index);
	}

}