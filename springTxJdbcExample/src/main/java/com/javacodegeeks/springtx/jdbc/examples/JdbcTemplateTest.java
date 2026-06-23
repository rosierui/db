package com.javacodegeeks.springtx.jdbc.examples;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * http://www.tutorialspoint.com/spring/programmatic_management.htm 
 * http://stackoverflow.com/questions/29533937/jdbctemplate-update-error-when-using-with-parameters
 * https://examples.javacodegeeks.com/enterprise-java/spring/jdbc/update-records-in-database-with-jdbctemplate/ ***
 * 
 */
public class JdbcTemplateTest {

    String sql = "INSERT INTO Contact (NAME, EMAIL, ADDRESS, TELEPHONE)"
            + " VALUES (?, ?, ?, ?)";

    public void test1(ApplicationContext context) {
        DataSource dataSource = (DriverManagerDataSource) context.getBean("dataSource");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        // int JdbcTemplate::update(String sql, Object[] args, int[] argTypes)
        // Issue a single SQL update operation (such as an insert, update or 
        // delete statement) via a prepared statement, binding the given arguments.
        try {
            Object[] params = { "Joe Smith", "joe.smith@zoho.com", "1555 E First ST, Rochester MN", "480-555-0909"};
            int[] types = {Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR};
            int rows = jdbcTemplate.update(sql, params, types);
            System.out.println(rows + " row(s) updated.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // 2nd update option
        try {
              int rows = jdbcTemplate.update(sql, "Eloisa Gurovich", "Eloisa.Gurovich@tets.com", "15725 S 46th St, Phoenix, AZ 85048", "602-567-8900");
              System.out.println(rows + " row(s) updated.");

        } catch (Exception e) {
              System.out.println(e.getMessage());
        }
    }

    public void test2(ApplicationContext context) {

        JdbcTemplate jdbcTemplate = (JdbcTemplate ) context.getBean("jdbcTemplate");

        try {
            Object[] params = { "Wayne Antonson", "Wayne.Antonson@gmail.com", "1234 E Second ST, Rochester NY", "555-333-4444"};
            int[] types = {Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR};
            int rows = jdbcTemplate.update(sql, params, types);
            System.out.println(rows + " row(s) updated.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("appContext.xml");
        JdbcTemplateTest jdbcTemplateTest = new JdbcTemplateTest();
        jdbcTemplateTest.test1(context);
        jdbcTemplateTest.test2(context);
    }
}
