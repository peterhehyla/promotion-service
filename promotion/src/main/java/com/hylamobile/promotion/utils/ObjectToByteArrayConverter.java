package com.hylamobile.promotion.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ObjectToByteArrayConverter {
    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectToByteArrayConverter.class);

    private static final byte[] EMPTY_ARRAY = new byte[0];

    private ObjectToByteArrayConverter() {
        // prevent instantiation
    }

    public static byte[] convert(Object obj) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            LOGGER.error("Error converting object: " + obj.toString());
            return EMPTY_ARRAY;
        }
    }
}
