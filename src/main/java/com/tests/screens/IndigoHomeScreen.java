package com.tests.screens;

import com.exceptions.NotImplementedException;
import com.runner.Drivers;
import com.runner.TestRunner;
import com.tests.screens.web.IndigoHomeScreenWeb;

public abstract class IndigoHomeScreen {

    private static final String SCREEN_NAME = IndigoHomeScreen.class.getSimpleName();

    public static IndigoHomeScreen get(){

        String platform = TestRunner.getPlatform();
        switch (platform){
            case "web":
                return new IndigoHomeScreenWeb(Drivers.getInnerDriver());
        }
        throw new NotImplementedException(SCREEN_NAME+" is not implemented for "+platform);
    }

    public abstract IndigoHomeScreen closeIntroPopup();
    public abstract IndigoHomeScreen selectTrip(String trip);
    public abstract IndigoHomeScreen enterFromDestination(String from);
    public abstract IndigoHomeScreen enterToDestination(String to);
    public abstract IndigoHomeScreen searchFlight();
    public abstract String getSearchedDestinations();
}
