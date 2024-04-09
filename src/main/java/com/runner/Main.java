package com.runner;

import com.context.SessionContext;
import com.context.TestExecutionContext;
import com.exceptions.InvalidTestDataException;
import com.exceptions.TestExecutionFailedException;
import com.utils.DateTime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    private final static String LOG_PROPERTIES_FILE = "./src/main/resources/log4j2.properties";

    private Main() {}

    public static void main(String[] args) {
        String outputDirectoryPath = "";
        if(args.length > 3) {
            outputDirectoryPath = args[3];
        }
        else{
            outputDirectoryPath = "./target/" + DateTime.getMonth() + "/" + DateTime.getCurrentDatestamp() + "/" + DateTime.getCurrentTimestamp();
        }
        createOutputDirectory(outputDirectoryPath);
        setLogPropertiesFile();
        new TestRunner(args);
    }

    static TestExecutionContext getTestExecutionContext(long threadId) {
        return SessionContext.getTestExecutionContext(threadId);
    }

    private static void createOutputDirectory(String outputDirectoryPath) {
        System.setProperty("OUTPUT_DIRECTORY", outputDirectoryPath);
        try {
            Path logDirectory = Paths.get(outputDirectoryPath);
            Files.createDirectories(logDirectory);
        } catch (IOException e) {
            throw new TestExecutionFailedException("Failed to create Output Directory");
        }
    }

    private static void setLogPropertiesFile() {
        File file;
        try {
            LoggerContext context = (LoggerContext) LogManager.getContext(false);
            file = new File(LOG_PROPERTIES_FILE);
            context.setConfigLocation(file.toURI());
        } catch (Exception e) {
            throw new InvalidTestDataException(
                    "There was a problem while setting log properties file");
        }
    }
}
