package com.tutorialspoint.mw;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.tutorialspoint.StudentMarks;
import com.tutorialspoint.StudentMarksMapper;

/**
 * Modified from com.tutorialspoint.StudentJDBCTemplate
 *
 */
public class StudentJDBCTemplate {

   private DataSource dataSource;
   private JdbcTemplate jdbcTemplateObject;
   private PlatformTransactionManager transactionManager;

   public void setDataSource(DataSource dataSource) {
      this.dataSource = dataSource;
      this.jdbcTemplateObject = new JdbcTemplate(this.dataSource);
   }

   public void setTransactionManager(
      PlatformTransactionManager transactionManager) {
      this.transactionManager = transactionManager;
   }

   /**
    * Insert data in Student and Marks in one transaction 
    * 
    * @param name
    * @param age
    * @param marks
    * @param testYear
    */
   public void createTx(String name, Integer age, Integer marks, Integer testYear){

      TransactionDefinition def = new DefaultTransactionDefinition();
      TransactionStatus status = transactionManager.getTransaction(def);

      try {
         String SQL1 = "insert into Student (name, age) values (?, ?)";
         jdbcTemplateObject.update( SQL1, name, age);

         // Get the latest student id to be used in Marks table
         String SQL2 = "select max(id) from Student";
         int sid = jdbcTemplateObject.queryForInt( SQL2 );

         String SQL3 = "insert into Marks(sid, marks, year) values (?, ?, ?)";
         jdbcTemplateObject.update( SQL3, sid, marks, testYear);

         System.out.println("Created Name = " + name + ", Age = " + age);
         transactionManager.commit(status);
      } catch (DataAccessException e) {
         System.out.println("Error in creating record, rolling back");
         transactionManager.rollback(status);
         throw e;
      }
      return;
   }

   /**
    * Generic jdbcTemplateObject not start transaction explicitly
    */
   public List<StudentMarks> listStudents() {
      String SQL = "select * from Student, Marks where Student.id=Marks.sid";

      List <StudentMarks> studentMarks = jdbcTemplateObject.query(SQL, 
                                         new StudentMarksMapper());
      return studentMarks;
   }
}