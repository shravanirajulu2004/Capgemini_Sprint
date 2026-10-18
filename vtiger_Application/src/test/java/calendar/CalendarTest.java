
package calendar;

import java.io.IOException;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import genericutility.BaseClass;
import genericutility.ExcelUtility;
import objrepo.CalendarPage;

public class CalendarTest extends BaseClass {

	// SMOKE TEST
	@Test(groups = { "Smoke" })
	public void verifyToDoFormOpens() {

		CalendarPage calendar = new CalendarPage(driver);

		// Open Calendar module
		calendar.openCalendarModule();

		// Hover Add and click To Do
		calendar.openCreateTodoForm();

		// Verify form opens
		Assert.assertTrue(calendar.isTodoFormDisplayed(), "Create To Do form did not open");
	}

	// FUNCTIONAL TEST
	@Test(groups = { "Functional" })
	public void createToDoEndToEnd() throws IOException {

		CalendarPage calendar = new CalendarPage(driver);
		ExcelUtility eu = new ExcelUtility();

		// Read Excel data
		String title = eu.getDataFromExcel("Calendar", 1, 0);

		String description = eu.getDataFromExcel("Calendar", 1, 1);

		// Validate Excel data
		Assert.assertNotNull(title, "To Do title is missing in Excel");

		Assert.assertFalse(title.trim().isEmpty(), "To Do title cannot be empty");

		Assert.assertNotNull(description, "To Do description is missing in Excel");

		// Open Calendar
		calendar.openCalendarModule();

		// Hover Add and click To Do
		calendar.openCreateTodoForm();

		// Enter Excel data
		calendar.enterTodoTitle(title);
		calendar.enterDescription(description);

		// Select Not Started
		calendar.selectStatusByIndex(1);

		// Select High priority
		calendar.selectPriorityByIndex(1);

		// Assign to Group and select second group option
		calendar.selectGroupByIndex(1);

		calendar.selectStartTimeByIndex(9, 10);

		calendar.clickSave();

		// Navigate to the saved records list
		calendar.openCalendarModule();
		calendar.clickAllEventsAndTodos();

		// Verify that the exact saved To Do is displayed
		Assert.assertTrue(calendar.isTodoDisplayed(title), "Saved To Do was not found: " + title);

	}

	@Test(groups = { "Functional" })
	public void verifyAllEventsAndTodosTabOpens() {

		CalendarPage calendar = new CalendarPage(driver);

		// Step 1: Open Calendar module
		calendar.openCalendarModule();

		// Step 2: Open All Events & Todos
		calendar.clickAllEventsAndTodos();

		Assert.assertTrue(
				driver.getCurrentUrl().contains("action=ListView")
						&& driver.getCurrentUrl().contains("module=Calendar"),
				"All Events & Todos list view did not open");

		Reporter.log("PASS: All Events & Todos section opened successfully.", true);
	}

}