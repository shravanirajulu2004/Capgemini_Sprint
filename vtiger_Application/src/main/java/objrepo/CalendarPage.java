package objrepo;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CalendarPage {

    WebDriver driver;
    WebDriverWait wait;
    Actions actions;

    public CalendarPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        actions = new Actions(driver);
    }

    // Calendar module
    @FindBy(linkText = "Calendar")
    private WebElement calendarModule;

    // Add menu
    @FindBy(xpath = "//td[normalize-space()='Add']")
    private WebElement addMenu;

    // To Do option
    @FindBy(xpath = "//*[self::a or self::td or self::span][normalize-space()='To Do']")
    private WebElement todoOption;

    // To Do title and description
    @FindBy(name = "task_subject")
    private WebElement todoTitle;

    @FindBy(name = "task_description")
    private WebElement description;

    // Status dropdown
    @FindBy(name = "taskstatus")
    private WebElement statusDropdown;

    // Priority dropdown
    @FindBy(xpath = "(//select[@id='taskpriority'])[2]")
    private WebElement priorityDropdown;

    // Group radio button
    @FindBy(xpath = "//input[@type='radio' and @name='task_assigntype' and @value='T']")
    private WebElement groupRadio;

    // Group dropdown
    @FindBy(name = "task_assigned_group_id")
    private WebElement groupDropdown;

    // Start hour and minute
    @FindBy(xpath = "(//select[@id='starthr'])[2]")
    private WebElement startHour;

    @FindBy(xpath = "(//select[@id='startmin'])[2]")
    private WebElement startMinute;

    // Save button
    @FindBy(xpath = "//input[@value='Save' and @name='todosave']")
    private WebElement saveButton;

    // All Events and Todos tab
    @FindBy(xpath = "//a[normalize-space()='All Events & Todos']")
    private WebElement allEventsAndTodosTab;


    // Open Calendar module
    public void openCalendarModule() {
        wait.until(
            ExpectedConditions.elementToBeClickable(calendarModule)
        ).click();
    }


    // Hover over Add and click To Do
    public void openCreateTodoForm() {
        wait.until(
            ExpectedConditions.visibilityOf(addMenu)
        );

        actions.moveToElement(addMenu).perform();

        wait.until(
            ExpectedConditions.elementToBeClickable(todoOption)
        ).click();

        wait.until(
            ExpectedConditions.visibilityOf(todoTitle)
        );
    }


    // Verify To Do form
    public boolean isTodoFormDisplayed() {
        return wait.until(
            ExpectedConditions.visibilityOf(todoTitle)
        ).isDisplayed();
    }


    // Enter To Do title
    public void enterTodoTitle(String title) {
        WebElement element = wait.until(
            ExpectedConditions.visibilityOf(todoTitle)
        );

        element.clear();
        element.sendKeys(title);
    }


    // Enter description
    public void enterDescription(String text) {
        WebElement element = wait.until(
            ExpectedConditions.visibilityOf(description)
        );

        element.clear();
        element.sendKeys(text);
    }


    // Select Status using Actions
    public void selectStatusByIndex(int index) {
        WebElement status = wait.until(
            ExpectedConditions.elementToBeClickable(statusDropdown)
        );

        status.click();

        actions.sendKeys(Keys.HOME).perform();

        for (int i = 0; i < index; i++) {
            actions.sendKeys(Keys.ARROW_DOWN).perform();
        }

        actions.sendKeys(Keys.ENTER).perform();

        System.out.println(
            "Selected Status: " +
            new Select(statusDropdown).getFirstSelectedOption().getText()
        );
    }


    // Select Priority using Actions
    public void selectPriorityByIndex(int index) {
        WebElement priority = wait.until(
            ExpectedConditions.elementToBeClickable(priorityDropdown)
        );

        priority.click();

        actions.sendKeys(Keys.HOME).perform();

        for (int i = 0; i < index; i++) {
            actions.sendKeys(Keys.ARROW_DOWN).perform();
        }

        actions.sendKeys(Keys.ENTER).perform();

        System.out.println(
            "Selected Priority: " +
            new Select(priorityDropdown).getFirstSelectedOption().getText()
        );
    }


    // Select Group radio button and group using Actions
    public void selectGroupByIndex(int index) {
        WebElement radio = wait.until(
            ExpectedConditions.elementToBeClickable(groupRadio)
        );

        if (!radio.isSelected()) {
            radio.click();
        }

        WebElement group = wait.until(
            ExpectedConditions.elementToBeClickable(groupDropdown)
        );

        group.click();

        actions.sendKeys(Keys.HOME).perform();

        for (int i = 0; i < index; i++) {
            actions.sendKeys(Keys.ARROW_DOWN).perform();
        }

        actions.sendKeys(Keys.ENTER).perform();

        System.out.println(
            "Selected Group: " +
            new Select(groupDropdown).getFirstSelectedOption().getText()
        );
    }


    // Select start time using index
    public void selectStartTimeByIndex(int hourIndex, int minuteIndex) {
        WebElement hour = wait.until(
            ExpectedConditions.elementToBeClickable(startHour)
        );

        new Select(hour).selectByIndex(hourIndex);

        System.out.println(
            "Selected Hour: " +
            new Select(hour).getFirstSelectedOption().getText()
        );

        WebElement minute = wait.until(
            ExpectedConditions.elementToBeClickable(startMinute)
        );

        new Select(minute).selectByIndex(minuteIndex);

        System.out.println(
            "Selected Minute: " +
            new Select(minute).getFirstSelectedOption().getText()
        );
    }


    // Click Save
    public void clickSave() {
        wait.until(
            ExpectedConditions.elementToBeClickable(saveButton)
        ).click();
    }


    // Verify To Do form closes after saving
    public boolean isTodoFormClosed() {
        try {
            wait.until(
                ExpectedConditions.invisibilityOf(todoTitle)
            );
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    // Open All Events and Todos tab
    public void clickAllEventsAndTodos() {
        wait.until(
            ExpectedConditions.elementToBeClickable(allEventsAndTodosTab)
        ).click();
    }


    // Verify that a To Do title is displayed
    public boolean isTodoDisplayed(String title) {
        By todoLocator = By.xpath(
            "//*[normalize-space()='" + title + "']"
        );

        try {
            return wait.until(
                ExpectedConditions.visibilityOfElementLocated(todoLocator)
            ).isDisplayed();
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }
    
}
