package com.tutorialspoint.mw;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.tutorialspoint.StudentMarks;

/**
 * Modified from com.tutorialspoint.MainApp
 * http://www.tutorialspoint.com/spring/programmatic_management.htm
 */
public class AppTest {
   public static void main(String[] args) {
      ApplicationContext context = 
             new ClassPathXmlApplicationContext("tutContext.xml");

      StudentJDBCHandler studentJDBCHandler = 
      (StudentJDBCHandler)context.getBean("studentJDBCHandler");

      System.out.println("------ Create Student Records / Test Results --------" );
      studentJDBCHandler.createTx("Mark Carr", 11, 99, 2010);
      studentJDBCHandler.createTx("Juash Rosa", 20, 97, 2010);
      studentJDBCHandler.createTx("Joe Smith", 25, 100, 2011);
      studentJDBCHandler.createTx("Mary Johnson", 21, 89, 1998);

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