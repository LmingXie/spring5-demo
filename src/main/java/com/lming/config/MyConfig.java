//package com.lming.config;
//
//import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.EnableAspectJAutoProxy;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.sql.DataSource;
//
//@Configuration
//@ComponentScan("com.lming")
//@EnableTransactionManagement
//@EnableAspectJAutoProxy
//public class MyConfig {
//
//    @Bean
//    public DataSource dataSource() {
//        MysqlDataSource mysqlDataSource = new MysqlDataSource();
//        mysqlDataSource.setUser("exchange");
//        mysqlDataSource.setPassword("WzGn5hzltBAlJ0G9");
//        mysqlDataSource.setURL("jdbc:mysql://182.61.40.106:3306/wallet_app?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8");
//        mysqlDataSource.setDatabaseName("wallet_app");
//        return mysqlDataSource;
//    }
//
//    @Bean
//    public JdbcTemplate jdbcTemplate() {
//        return new JdbcTemplate(dataSource());
//    }
//
//    @Bean
//    public PlatformTransactionManager txManager() {
//        return new DataSourceTransactionManager(dataSource());
//    }
//
//}
