package com.hylamobile.promotion.utils;

import org.apache.commons.lang.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class CommonUtils {
    private final static Logger LOGGER = LoggerFactory.getLogger(CommonUtils.class);


    public static Date getDateInTimezone(Date date, TimeZone timeZone) {
        return new Date(date.getTime() + timeZone.getOffset(date.getTime()));
    }

    public static <T> String marshallObjectToXmlString(T object) {
        try {
            StringWriter stringWriter = new StringWriter();

            JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.marshal(object, stringWriter);

            return stringWriter.toString();
        } catch (JAXBException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    public static <T> T unmarshallObjectFromXmlString(String content, Class<T> clasz) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clasz);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            return unmarshaller.unmarshal(new StreamSource(new StringReader(content)), clasz).getValue();
        } catch (JAXBException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Public method to start the validate of IMEI code.
     *
     * @param imei
     *            code of equipment
     * @return Boolean
     */
    public static boolean validateImeiLuhnAlgorithm(String imei) {
        int resultSum = resultImei(imei);
        int check = 0;
        final int CARACTER_ZERO_ASCII = 48;
        if (resultSum % 10 == 0) {
            if ((imei.charAt(imei.length() - 1) - CARACTER_ZERO_ASCII) == 0) {
                return true;
            }
            return false;
        } else {
            check = resultSum % 10;
        }

        if ((imei.charAt(imei.length() - 1) - CARACTER_ZERO_ASCII) == (10 - check)) {
            return true;
        }
        return false;
    }
    /**
     * Private Method to calculate the code
     *
     * @param imei
     *            code of equipment
     * @return result of the calculate
     */

    private static int resultImei(String imei) {
        int totalResult = 0;
        int aux = 0;
        final int CARACTER_ZERO_ASCII = 48;
        for (int i = imei.length() - 2; i >= 0; i--) {
            if (i % 2 != 0) {
                aux = imei.charAt(i) - CARACTER_ZERO_ASCII;
                aux = aux * 2;
                String aux2 = String.valueOf(aux);
                if (aux > 9) {
                    aux = (aux2.charAt(0) - CARACTER_ZERO_ASCII) + (aux2.charAt(1) - CARACTER_ZERO_ASCII);
                }
                totalResult += aux;
            } else {
                aux = imei.charAt(i) - CARACTER_ZERO_ASCII;
                totalResult += aux;
            }
        }
        return totalResult;
    }

}
