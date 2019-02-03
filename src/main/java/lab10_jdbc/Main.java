package lab10_jdbc;

import lab10_jdbc.dao.*;
import lab10_jdbc.entity.Person;
import lab10_jdbc.entity.Subject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;


public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost:5433/postgres";
        String login = "postgres";
        String pass = "admin";

        Connection connection = DriverManager.getConnection(url, login, pass);
        connection.setAutoCommit(false);
        System.out.println(connection);

        PersonDAO personDAO = new PersonDAOImpl(connection);
        Person person = new Person();
        person.setName("John Cnor");
        person.setBirthDate(95551446911L);
        System.out.println(personDAO.createPerson(person));

//        Person pers1 = new Person();
//        pers1.setName("John Cnorl");
//        person.setBirthDate(478446911L);
//        int x = personDAO.createPerson(pers1);
//        pers1.setName("Terminator");
//        pers1.setId(x);
//        personDAO.updatePerson(pers1);
//        pers1.setId(37);
//        personDAO.deletePerson(pers1);
//
//        Collection<Person> perss = personDAO.getAllPersons();
//
//        perss.stream().map(Person::toString).forEach(System.out::println);

//        SubjectDAO subjectDAO = new SubjectDAOImpl(connection);
//        Subject sub = new Subject("Mathematic");
//        int i = subjectDAO.createSubject(sub);
//        sub.setId(i);
//        sub.setDescription("Chemestry");
//        //subjectDAO.updateSubject(sub);
//        sub.setId(2);
//        subjectDAO.deleteSubject(sub);
//        Collection<Subject> subjects = subjectDAO.getAllSubjects();
//        subjects.stream().map(Subject::toString).forEach(System.out::println);

        CourseDAO courceDAO = new CourseDaoImpl(connection);
//        Person person2 = new Person();
//        person2.setId(6);
//
//        Subject sub2 = new Subject();
//        sub2.setId(6);
////        courceDAO.linkToCourse(112523532363L,person2, sub2);
//
//        Subject sub3 = new Subject();
//        sub3.setId(10);
////        Subject sub4 = new Subject();
////        sub4.setId(9);
        Person person3 = new Person();
        person3.setId(31);
//        Person person4 = new Person();
//        person4.setId(33);
//        Person person5 = new Person();
//        person5.setId(32);
//
//        //courceDAO.linkToCourse(22362352352L, person3, sub3, sub4 );
//        courceDAO.linkToCourse(22362352352L, sub3, person4, person5 );
//        Subject sub3 = new Subject();
//        sub3.setId(10);
//        Collection<Person> perss = courceDAO.getPersonsBySubject(sub3);
//        perss.stream().map(Person::toString).forEach(System.out::println);
        Collection<Subject> subjects = courceDAO.getSubjectsByPerson(person3);
        subjects.stream().map(Subject::toString).forEach(System.out::println);

        connection.close();
    }
}

