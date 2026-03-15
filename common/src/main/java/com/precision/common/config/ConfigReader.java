package com.precision.common.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Properties properties = new Properties();
    static {
        try {
            InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties");
            if (input == null) {
                throw new RuntimeException("config.properties file not found");
            }
            properties.load(input);
            } catch (IOException e) {
                throw new RuntimeException("Failed to load config.properties", e);
            }
    }

    // The class method which is used to get the values from the Properties Hashmap.
    public static String get(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Key not found");
        }
        return value;
    }
}