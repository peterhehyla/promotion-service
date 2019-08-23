package com.hylamobile.promotion.config;

import com.github.springtestdbunit.bean.DatabaseConfigBean;
import com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean;
import com.hylamobile.promotion.dbunit.HylaExtLegacyDataBaseConfigBean;
import com.hylamobile.promotion.dbunit.HylaPostgresqlDataTypeFactory;
import org.dbunit.dataset.datatype.IDataTypeFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DbUnitConfig {
    @Bean
    public IDataTypeFactory dbUnitDataTypeFactory(){
        return new HylaPostgresqlDataTypeFactory();
    }
    @Bean
    public DatabaseConfigBean dbUnitDatabaseConfig(IDataTypeFactory dbUnitDataTypeFactory){
        HylaExtLegacyDataBaseConfigBean dbUnitDatabaseConfig = new HylaExtLegacyDataBaseConfigBean();
        dbUnitDatabaseConfig.setDatatypeFactory(dbUnitDataTypeFactory);
        dbUnitDatabaseConfig.setAllowEmptyFields(true);
        return dbUnitDatabaseConfig;
    }
    @Bean
    public DatabaseDataSourceConnectionFactoryBean dbUnitDatabaseConnection(DatabaseConfigBean dbUnitFlipswapDatabaseConfig, DataSource flipswapDataSource){
        DatabaseDataSourceConnectionFactoryBean databaseDataSourceConnectionFactoryBean = new DatabaseDataSourceConnectionFactoryBean();
        databaseDataSourceConnectionFactoryBean.setDataSource(flipswapDataSource);
        databaseDataSourceConnectionFactoryBean.setDatabaseConfig(dbUnitFlipswapDatabaseConfig);
        databaseDataSourceConnectionFactoryBean.setSchema("public");
        return databaseDataSourceConnectionFactoryBean;
    }

}
