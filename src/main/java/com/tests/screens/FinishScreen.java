package com.tests.screens;

import com.exceptions.NotImplementedException;
import com.runner.Drivers;
import com.runner.TestRunner;
import com.tests.screens.web.FinishScreenWeb;
import org.openqa.selenium.WebDriver;

public abstract class FinishScreen {

    private static final String SCREEN_NAME = FinishScreen.class.getSimpleName();

    public static FinishScreen get() {
        String platform = TestRunner.getPlatform();
        WebDriver driver = Drivers.getInnerDriver();

        switch (platform) {
            case "web":
                return new FinishScreenWeb(driver);
        }
        throw new NotImplementedException(SCREEN_NAME + " is not implemented for platform " + platform);
    }

    public abstract boolean isOrderSuccessful();
}
