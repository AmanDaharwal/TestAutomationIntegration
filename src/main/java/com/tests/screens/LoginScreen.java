package com.tests.screens;

import com.exceptions.NotImplementedException;
import com.runner.Drivers;
import com.runner.TestRunner;
import com.tests.screens.web.LoginScreenWeb;
import org.openqa.selenium.WebDriver;

public abstract class LoginScreen {
    private static final String SCREEN_NAME = LoginScreen.class.getSimpleName();

    public static LoginScreen get() {

        WebDriver driver = Drivers.getInnerDriver(Thread.currentThread().getId());
        String platform = TestRunner.getPlatform();

        switch (platform) {
            case "web":
                return new LoginScreenWeb(driver);
        }
        throw new NotImplementedException(SCREEN_NAME + " is not implemented for " + platform);
    }

    public abstract LoginScreen loginToSwagLab(String username, String password);
}
