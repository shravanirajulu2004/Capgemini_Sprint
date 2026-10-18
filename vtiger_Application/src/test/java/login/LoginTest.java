package login;


import org.testng.Assert;
import org.testng.annotations.Test;

import genericutility.BaseClass;
import genericutility.FileUtility;
import objrepo.LoginPage;



public class LoginTest extends BaseClass {


    
    @Test
    public void verifyInvaildUsername() throws Exception {
        LoginPage l = new LoginPage(driver);
        FileUtility fu = new FileUtility();
        l.login(
                "Admin",
                fu.getDataFromPropertyFile("password")
            );
        // Add an assertion for the expected page here.
        Assert.assertFalse(
                l.getErrorMessage().contains(
                    "Username and password do not match"
                ),
                "You must specify a valid username and password."
            );
        driver.quit();
    }
}