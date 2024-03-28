package com.tests.bl;

import com.tests.screens.IndigoHomeScreen;

public class IndigoSearchBL {

    public void searchForFlightTickets(String way, String from, String to, int numberOfPassenger) {

        IndigoHomeScreen.get().closeIntroPopup()
                .selectTrip(way)
                .enterFromDestination(from)
                .enterToDestination(to)
                .searchFlight();
    }
}
