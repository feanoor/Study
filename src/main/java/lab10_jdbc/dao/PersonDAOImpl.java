package lab10_jdbc.dao;

import lab10_jdbc.entity.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class PersonDAOImpl implements PersonDAO {

    public static final String INSERT_PERSON_SQL_TEMPLATE =
            "insert into person (name, birth_date) values (?, ?) returning person_id";

    public static final String DELETE_PERSON_SQL_TEMPLATE =
            "delete from person pers where pers.person_id = ?";

    public static final String UPDATE_PERSON_SQL_TEMPLATE =
            "update person pers set name = ?, birth_date = ? where pers.person_id = ?";

    public static final String GET_PERSONS_SQL_TEMPLATE =
            "select pers.person_id, pers.name, pers.birth_date from person pers";

    private final Connection connection;

    public PersonDAOImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public Collection<Person> getAllPersons() {
        Collection<Person> persons = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs;
            rs = statement.executeQuery(GET_PERSONS_SQL_TEMPLATE);
            while (rs.next()){
                persons.add(new Person(rs.getInt(1),rs.getString(2),rs.getTimestamp(3).getTime()));
            }
        }catch (Exception ex){
            //ex.printStackTrace();
            System.out.println("Что-то пошло не так...");
        }
        return persons;
    }

    @Override
    public int createPerson(Person person) throws SQLException {
        int i = -1;
        try (PreparedStatement statement = connection.prepareStatement(INSERT_PERSON_SQL_TEMPLATE)) {
            statement.setString(1, person.getName());
            statement.setDate(2, new Date(person.getBirthDate()));
            ResultSet rs;
            rs = statement.executeQuery();
            rs.next();
            i = rs.getInt(1);
            if (i<0) {
                throw new SQLException();
            }
            connection.commit();
            System.out.println("Студент " + person.getName() + "  " + person.getBirthDate() + " добавлен.");
        }catch (Exception ex){
            //ex.printStackTrace();
            i = -1;
            System.out.println("Что-то пошло не так...");
        }
        return i;
    }



    @Override
    public void updatePerson(Person person) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_PERSON_SQL_TEMPLATE)) {
            statement.setString(1, person.getName());
            statement.setDate(2, new Date(person.getBirthDate()));
            statement.setInt(3, person.getId());
            System.out.println("----------"+person.getId()+ " " + person.getName() + " " + person.getBirthDate());
            statement.execute();
            connection.commit();
        }catch (Exception ex){
            ex.printStackTrace();
            System.out.println("Что-то пошло не так...");
        }
    }

    @Override
    public void deletePerson(Person person) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_PERSON_SQL_TEMPLATE)) {
            statement.setInt(1, person.getId());
            statement.execute();
            System.out.println("Студент " + person.getName() + " с id= "+ person.getId() +" удален.");
            connection.commit();
        }catch (Exception ex){
            ex.printStackTrace();
            System.out.println("Что-то пошло не так...");
        }
    }
}
