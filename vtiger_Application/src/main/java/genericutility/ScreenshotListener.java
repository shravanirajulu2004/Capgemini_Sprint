package genericutility;

import java.io.IOException;

import org.testng.ITestListener;
import org.testng.ITestResult;

public class ScreenshotListener implements ITestListener {

	// Screenshot for passed negative tests
	@Override
	public void onTestSuccess(ITestResult result) {

		String[] groups = result.getMethod().getGroups();

		for (String group : groups) {

			if (group.equalsIgnoreCase("negative")) {

				takeScreenshot(result, "PASSED_");
				break;
			}
		}
	}

	// Screenshot for all failed tests
	@Override
	public void onTestFailure(ITestResult result) {

		takeScreenshot(result, "FAILED_");
	}

	// Reusable screenshot method
	private void takeScreenshot(ITestResult result, String status) {

		try {

			BaseClass base = (BaseClass) result.getInstance();

			if (base.driver != null) {

				ScreenshotUtility su = new ScreenshotUtility();

				su.takeScreenshot(base.driver, status + result.getName());

				System.out.println("Screenshot saved: " + status + result.getName());
			}

		} catch (IOException | RuntimeException e) {
			System.err.println("Screenshot capture failed: " + e.getMessage());
		}
	}
}