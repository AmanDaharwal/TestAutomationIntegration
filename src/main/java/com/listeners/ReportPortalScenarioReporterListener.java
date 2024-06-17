package com.listeners;

import com.epam.reportportal.cucumber.ScenarioReporter;
import com.epam.ta.reportportal.ws.model.StartTestItemRQ;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.epam.reportportal.utils.MemoizingSupplier;

import java.util.Calendar;

public class ReportPortalScenarioReporterListener extends ScenarioReporter {
    private static final Logger LOGGER = LogManager.getLogger(
            ReportPortalScenarioReporterListener.class.getName());
    private static final String DUMMY_ROOT_SUITE_NAME = "End-2-End Tests";
    private static final String RP_STORY_TYPE = "SUITE";
    public static String launchUUID;

    public ReportPortalScenarioReporterListener() {
        LOGGER.info("ReportPortalScenarioReporterListener");
    }

    @Override
    protected void startRootItem() {
        this.rootSuiteId = new MemoizingSupplier<>(() -> {
            StartTestItemRQ rq = new StartTestItemRQ();
            rq.setName(DUMMY_ROOT_SUITE_NAME);
            rq.setStartTime(Calendar.getInstance().getTime());
            rq.setType(RP_STORY_TYPE);
            launchUUID = this.getItemTree().getLaunchId().blockingGet();
            LOGGER.info("CucumberScenarioReporterListener: launchUUID: " + launchUUID);
            return this.getLaunch().startTestItem(rq);
        });
    }
}
