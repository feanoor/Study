package lab10_jdbc.dao;

import lab10_jdbc.entity.Person;

import java.sql.SQLException;
import java.util.Collection;

public interface PersonDAO {

    Collection<Person> getAllPersons();

    int createPerson(Person person) throws SQLException;

    void updatePerson(Person person);

    void deletePerson(Person person);

}
