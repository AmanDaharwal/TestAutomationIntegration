package com.runner;

import com.exceptions.InvalidTestDataException;
import com.exceptions.TestExecutionFailedException;
import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public class Setup {
    private static String configFilePath;
    private static Properties properties;
    private static Map<String, Object> configMap = new LinkedHashMap<>();
    private final static String BROWSER = "BROWSER";
    private final static String URL = "URL";
    private Setup(){

    }

    static void load(String providedConfigFilePath) {
        configFilePath = providedConfigFilePath;
        properties = loadProperties(configFilePath);
    }

    @NotNull
    static Properties loadProperties(String configFile) {
        final Properties properties;
        try(InputStream input = new FileInputStream(configFile)) {
            properties = new Properties();
            properties.load(input);
        } catch(IOException ex) {
            throw new InvalidTestDataException("Config file not found, or unable to read it", ex);
        }
        return properties;
    }

    static void setConfigProperties(){
        configMap.put(BROWSER, properties.get(BROWSER));
        configMap.put(URL, properties.get(URL));
    }

    public static String getURL() {
        return (String) configMap.get(URL);
    }

    public static String getConfig(String property) {
        if(configMap.containsKey(property.toUpperCase()))
            return (String) configMap.get(property.toUpperCase());
        throw new TestExecutionFailedException(property+" does not exist in config file");
    }
}
