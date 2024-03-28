package com.tests.screens;

import com.exceptions.NotImplementedException;
import com.runner.Drivers;
import com.runner.TestRunner;
import com.tests.screens.web.CheckOutScreenWeb;
import org.openqa.selenium.WebDriver;

public class CheckOutScreen {

    private static final String SCREEN_NAME = CheckOutScreen.class.getSimpleName();

    public static CheckOutScreen get() {

        WebDriver driver = Drivers.getInnerDriver();
        String platform = TestRunner.getPlatform();
        switch (platform) {
            case "web":
                return new CheckOutScreenWeb(driver);
        }
        throw new NotImplementedException(SCREEN_NAME + " is not implemented for platform " + platform);
    }
}
