package lab10_jdbc.dao;

import lab10_jdbc.entity.Person;
import lab10_jdbc.entity.Subject;

import java.sql.SQLException;
import java.util.Collection;

public interface SubjectDAO {

    Collection<Subject> getAllSubjects();

    int createSubject(Subject person) throws SQLException;

    void updateSubject(Subject person);

    void deleteSubject(Subject person);
}
