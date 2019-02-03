package lab10_jdbc.dao;

import lab10_jdbc.entity.Person;
import lab10_jdbc.entity.Subject;

import java.util.Collection;

public interface CourseDAO {


    Collection<Person> getPersonsBySubject(Subject subject);

    Collection<Subject> getSubjectsByPerson(Person person);

    void linkToCourse(Long date, Person person, Subject subject);

    void linkToCourse(Long date, Person person, Subject... subject);

    void linkToCourse(Long date, Subject subject, Person... person);

}
