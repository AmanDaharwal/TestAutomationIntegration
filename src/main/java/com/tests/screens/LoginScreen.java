package com.tests.screens;

import com.entities.Platform;
import com.exceptions.NotImplementedException;
import com.runner.Driver;
import com.runner.Drivers;
import com.runner.TestRunner;
import com.tests.screens.android.LoginScreenAndroid;
import com.tests.screens.ios.LoginScreenIos;
import com.tests.screens.web.LoginScreenWeb;

public abstract class LoginScreen {
    private static final String SCREEN_NAME = LoginScreen.class.getSimpleName();

    public static LoginScreen get() {

        Driver driver = Drivers.getCurrentDriver(Thread.currentThread().getId());
        Platform platform = TestRunner.getPlatform();

        switch (platform) {
            case web:
                return new LoginScreenWeb(driver);
            case android:
                return new LoginScreenAndroid(driver);
            case ios:
                return new LoginScreenIos(driver);
        }
        throw new NotImplementedException(SCREEN_NAME + " is not implemented for " + platform);
    }

    public abstract LoginScreen loginToSwagLab(String username, String password);
}
