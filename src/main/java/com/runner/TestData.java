package com.runner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.exceptions.TestExecutionFailedException;
import org.json.*;

public class TestData {

    private static Map<String, Object> testDataAsMap;

    private TestData() {

    }

    static void loadTestData(String testDataFilePath) {
        try {
            String testDataInString = new String(Files.readAllBytes(Paths.get(testDataFilePath)));

            JSONObject jsonObject = new JSONObject(testDataInString);

            testDataAsMap = toMap(jsonObject);

        } catch (IOException e) {
            throw new TestExecutionFailedException("Unable to read Test Data json file from path " + testDataFilePath);
        } catch (JSONException e) {
            throw new TestExecutionFailedException("Unable to read Test Data json file from path " + testDataFilePath);
        }
    }

    private static Map<String, Object> toMap(JSONObject jsonObject) throws JSONException {
        Map<String, Object> map = new HashMap<>();
        for (String key : jsonObject.keySet()) {
            Object value = jsonObject.get(key);
            if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    public static Map<String, Object> getTestDataAsMap() {
        return testDataAsMap;
    }

    public static Object getTestData(Object key) {
        return testDataAsMap.get(key);
    }

    public static String getTestDataAsString(String key) {
        return (String) testDataAsMap.get(key);
    }
}
