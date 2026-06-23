package com.tutorialspoint.mw;

import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.tutorialspoint.StudentMarks;
import com.tutorialspoint.StudentMarksMapper;

/**
 * Modified from StudentJDBCHandler2 for rollback test
 *
 * http://www.tutorialspoint.com/spring/programmatic_management.htm
 *
 * Pass dataSource and PlatformTransactionManager (Interface)
 * as parameters to here from tutContext.xml
 *
 * DataSourceTransactionManager implements PlatformTransactionManager
 *
 * PlatformTransactionManager Method Summary
 *     void commit(TransactionStatus status) 
 *     TransactionStatus getTransaction(TransactionDefinition definition) 
 *     void rollback(TransactionStatus status) 
 */
public class StudentJDBCHandler3 {

   private JdbcTemplate jdbcTemplate;
   private PlatformTransactionManager transactionManager;

   public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
      this.jdbcTemplate = jdbcTemplate;
   }

   public void setTransactionManager(PlatformTransactionManager transactionManager) {
      this.transactionManager = transactionManager;
   }

   /**
    * Insert data in Student and Marks in one transaction
    *
    * Let us use PlatformTransactionManager directly to implement programmatic
    * approach to implement transactions. To start a new transaction you need to
    * have a instance of TransactionDefinition with the appropriate transaction
    * attributes. For this example we will simply create an instance of
    * DefaultTransactionDefinition to use the default transaction attributes.
    *
    * Once the TransactionDefinition is created, you can start your transaction
    * by calling getTransaction() method, which returns an instance of TransactionStatus.
    * The TransactionStatus objects helps in tracking the current status of the
    * transaction and finally, if everything goes fine, you can use commit()
    * method of PlatformTransactionManager to commit the transaction, otherwise
    * you can use rollback() to rollback the complete operation.
    *
    * See com.javacodegeeks.springtx.jdbc.examples.SpringTranManagerFundTransferTransaction
    *
    * @param name
    * @param age
    * @param marks
    * @param testYear
    */
   public void createTx(String name, Integer age, Integer marks, Integer testYear, boolean fireException){

      TransactionDefinition txDef = new DefaultTransactionDefinition();
      TransactionStatus txStatus = transactionManager.getTransaction(txDef);

      try {
         String sql1 = "insert into Student (name, age) values (?, ?)";
         jdbcTemplate.update( sql1, name, age);

         // Get the latest student id to be used in Marks table
         String sql2 = "select max(id) from Student";
         int sid = jdbcTemplate.queryForInt( sql2 );

         String sql3 = "insert into Marks(sid, marks, year) values (?, ?, ?)";
         jdbcTemplate.update( sql3, sid, marks, testYear);

         System.out.println("Created Name = " + name + ", Age = " + age);

         if (fireException) {
             throw new Exception();
         }
         transactionManager.commit(txStatus);

      } catch (DataAccessException e) {
         System.out.println("Error in creating record, rolling back");
         transactionManager.rollback(txStatus);
         throw e;
      } catch (Exception e) {
         System.out.println("Error in creating record, rolling back");
         transactionManager.rollback(txStatus);
      }
      return;
   }

   /**
    * Use generic jdbcTemplateObject not start transaction explicitly
    * Search data from Student and Marks tables
    */
   public List<StudentMarks> getStudentInfo() {
      String sql = "select * from Student, Marks where Student.id = Marks.sid";

      List <StudentMarks> studentMarks = jdbcTemplate.query(sql, 
                                         new StudentMarksMapper());
      return studentMarks;
   }
}