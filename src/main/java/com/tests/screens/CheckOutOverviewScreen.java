package com.tests.screens;

import com.exceptions.NotImplementedException;
import com.runner.Drivers;
import com.runner.TestRunner;
import com.tests.screens.web.CheckOutOverviewScreenWeb;
import org.openqa.selenium.WebDriver;

public abstract class CheckOutOverviewScreen {

    private static final String SCREEN_NAME = CheckOutOverviewScreen.class.getSimpleName();

    public static CheckOutOverviewScreen get() {

        WebDriver driver = Drivers.getInnerDriver();
        String platform = TestRunner.getPlatform();
        switch (platform) {
            case "web":
                return new CheckOutOverviewScreenWeb(driver);
        }
        throw new NotImplementedException(SCREEN_NAME + " is not implemented for platform " + platform);
    }

    public abstract int getNumberOfCartItems();

    public abstract FinishScreen clickFinish();
}
