package com.tutorialspoint.mw;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.tutorialspoint.StudentMarks;

/**
 * Modified from AppTest2
 * http://www.tutorialspoint.com/spring/programmatic_management.htm
 * 
 */
public class AppRollbackTest {
   public static void main(String[] args) {
      ApplicationContext context = 
             new ClassPathXmlApplicationContext("tutContext-2.xml");

      StudentJDBCHandler2 studentJDBCHandler = 
      (StudentJDBCHandler2)context.getBean("studentJDBCHandler2");

      System.out.println("------ Create Student Records / Test Results --------" );
      studentJDBCHandler.createTx("Regina Rosa",    31, 89, 2006, false);
      studentJDBCHandler.createTx("Mark Anthony",    29, 91, 2004, false);
      studentJDBCHandler.createTx("Juash Silveira",   28, 92, 2002, false);
      studentJDBCHandler.createTx("Joe Admas",    27, 93, 2000, true); // will fail to commit to db
      studentJDBCHandler.createTx("Mary Jackson", 26, 94, 1998, false);

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