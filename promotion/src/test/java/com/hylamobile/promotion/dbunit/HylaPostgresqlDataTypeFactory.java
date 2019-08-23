package com.hylamobile.promotion.dbunit;

import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.DataTypeException;
import org.dbunit.dataset.datatype.DefaultDataTypeFactory;
import org.dbunit.ext.postgresql.GenericEnumType;
import org.dbunit.ext.postgresql.InetType;
import org.dbunit.ext.postgresql.IntervalType;
import org.dbunit.ext.postgresql.UuidType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Types;
import java.util.Arrays;
import java.util.Collection;

public class HylaPostgresqlDataTypeFactory extends DefaultDataTypeFactory {

    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(HylaPostgresqlDataTypeFactory.class);
    /**
     * Database product names supported.
     */
    private static final Collection DATABASE_PRODUCTS = Arrays.asList(new String[] {"PostgreSQL"});

    /**
     * @see org.dbunit.dataset.datatype.IDbProductRelatable#getValidDbProducts()
     */
    public Collection getValidDbProducts()
    {
        return DATABASE_PRODUCTS;
    }
    public DataType createDataType(int sqlType, String sqlTypeName) throws DataTypeException {
        logger.debug("createDataType(sqlType={}, sqlTypeName={})",
                String.valueOf(sqlType), sqlTypeName);

        if (sqlType == Types.OTHER) {

            if ("json".equals(sqlTypeName) ||
                    "jsonb".equals(sqlTypeName)) {
                return new JsonType(); // support PostgreSQL json
            }else
                // Treat Postgresql UUID types as VARCHARS
                if ("uuid".equals(sqlTypeName)) {
                    return new UuidType();
                    // Intervals are custom types
                }else if ("interval".equals(sqlTypeName)) {
                    return new IntervalType();
                }else if ("inet".equals(sqlTypeName)) {
                    return new InetType();
                }else
                {
                    // Finally check whether the user defined a custom datatype
                    if(isEnumType(sqlTypeName))
                    {
                        if(logger.isDebugEnabled())
                            logger.debug("Custom enum type used for sqlTypeName {} (sqlType '{}')",
                                    new Object[] {sqlTypeName, new Integer(sqlType)} );
                        return new GenericEnumType(sqlTypeName);
                    }
                }
        }

        return super.createDataType(sqlType, sqlTypeName);
    }

    /**
     * Returns a data type for the given sql type name if the user wishes one.
     * <b>Designed to be overridden by custom implementations extending this class.</b>
     * Override this method if you have a custom enum type in the database and want
     * to map it via dbunit.
     *
     * @param sqlTypeName The sql type name for which users can specify a custom data type.
     * @return <code>null</code> if the given type name is not a custom
     * type which is the default implementation.
     * @since 2.4.6
     */
    public boolean isEnumType(String sqlTypeName) {
        return false;
    }

}
