package com.hylamobile.promotion.dbunit;

import com.github.springtestdbunit.bean.DatabaseConfigBean;
import org.dbunit.database.DatabaseConfig;
import org.springframework.test.util.ReflectionTestUtils;

public class HylaExtLegacyDataBaseConfigBean extends DatabaseConfigBean {
    /**
     * Gets the allow empty fields database config feature.
     * @return the allow empty fields
     * @see DatabaseConfig#FEATURE_ALLOW_EMPTY_FIELDS
     */
    public Boolean getAllowEmptyFields() {
        return (Boolean) getExtProperty("allowEmptyFields", DatabaseConfig.FEATURE_ALLOW_EMPTY_FIELDS);
    }

    /**
     * Sets the allow empty fields database config feature.
     * @param allowEmptyFields allow empty fields
     * @see DatabaseConfig#FEATURE_ALLOW_EMPTY_FIELDS
     */
    public void setAllowEmptyFields(Boolean allowEmptyFields) {
        setExtProperty("allowEmptyFields", DatabaseConfig.FEATURE_ALLOW_EMPTY_FIELDS, allowEmptyFields);
    }
    /**
     * Get a property from the underlying database config.
     * @param propertyName The name of the attribute
     * @param dataConfigPropertyName The data config property name
     * @return the value of the property
     */
    private Object getExtProperty(String propertyName, String dataConfigPropertyName) {
        return ReflectionTestUtils.invokeMethod(this, "getProperty", dataConfigPropertyName);
    }

    /**
     * Set a property to the underlying data config.
     * @param propertyName the name of the property
     * @param dataConfigPropertyName the data config property name
     * @param value the value to set
     */
    private void setExtProperty(String propertyName, String dataConfigPropertyName, Object value) {
        ReflectionTestUtils.invokeMethod(this, "setProperty", new Object[]{propertyName, dataConfigPropertyName, value});
    }
}
