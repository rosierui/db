package com.tutorialspoint.mw;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.tutorialspoint.StudentMarks;

/**
 * Modified from AppTest2
 * http://www.tutorialspoint.com/spring/programmatic_management.htm
 *
 * Pass parameters
 *    JdbcTemplate jdbcTemplate;
 *    PlatformTransactionManager transactionManager;
 */
public class AppTest3_Rollback {
   public static void main(String[] args) {
      ApplicationContext context = 
             new ClassPathXmlApplicationContext("tutContext-3.xml");

      StudentJDBCHandler3 studentJDBCHandler = 
      (StudentJDBCHandler3)context.getBean("studentJDBCHandler3");

      System.out.println("------ Create Student Records / Test Results --------" );
      studentJDBCHandler.createTx("Regina Rosa",    31, 89, 2006, false);
      studentJDBCHandler.createTx("Mark Anthony",    29, 91, 2005, false);
      studentJDBCHandler.createTx("Juash Silveira",   28, 92, 2004, false);
      studentJDBCHandler.createTx("Joe Admas",    27, 93, 2003, true); // will fail to commit to db
      studentJDBCHandler.createTx("Mary Jackson", 26, 94, 2002, false);

      System.out.println("------ List student info --------" );
      List<StudentMarks> studentMarks = studentJDBCHandler.getStudentInfo();
      for (StudentMarks record : studentMarks) {
         System.out.print("ID : " + record.getId() );
         System.out.print(", Name : " + record.getName() );
         System.out.print(", Marks : " + record.getMarks());
         System.out.print(", Test Year : " + record.getYear());
         System.out.println(", Age : " + record.getAge());
      }
   }
}