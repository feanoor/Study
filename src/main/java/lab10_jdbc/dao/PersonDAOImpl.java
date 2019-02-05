package lab10_jdbc.dao;

import lab10_jdbc.entity.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class PersonDAOImpl implements PersonDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonDAOImpl.class);

    public static final String INSERT_PERSON_SQL_TEMPLATE =
            "insert into person (name, birth_date) values (?, ?) returning person_id";

    public static final String DELETE_PERSON_SQL_TEMPLATE =
            "delete from person pers where pers.person_id = ?";

    public static final String UPDATE_PERSON_SQL_TEMPLATE =
            "update person pers set name = ?, birth_date = ? where pers.person_id = ?";

    public static final String GET_PERSONS_SQL_TEMPLATE =
            "select pers.person_id, pers.name, pers.birth_date from person pers";

    private final Connection connection;

    /**
     * Получает подключение и сохраняет в данном объекте.
     * @param connection
     */
    public PersonDAOImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * Получить коллекцию всех персон.
     * @return Коллекция персон.
     */
    @Override
    public Collection<Person> getAllPersons() {
        Collection<Person> persons = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs;
            rs = statement.executeQuery(GET_PERSONS_SQL_TEMPLATE);
            while (rs.next()){
                persons.add(new Person(rs.getInt(1),rs.getString(2),rs.getTimestamp(3).getTime()));
            }
            if(persons.size()>0){
                LOGGER.info("Получение списка студентов успешно!");
            }
            else {
                LOGGER.info("Таблица студентов пуста!");
            }
        }catch (Exception ex){
            LOGGER.debug(ex.getMessage());
            LOGGER.error("Ошибка при получении списка студентов!");
        }
        return persons;
    }

    /**
     * Добавить в БД персону.
     * @param person Объект типа Person.
     * @return Если возвращает не -1 значит транзакция успешна.
     * @throws SQLException
     */
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
            LOGGER.info("Студент " + person.getName() + "  " + person.getBirthDate() + " успешно добавлен.");
        }catch (Exception ex){
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            i = -1;
            LOGGER.debug(ex.getMessage());
            LOGGER.error("Ошибка при создании студента!");
        }
        return i;
    }

    /**
     * Обновляет информацию о персоне
     * @param person Объект типа Person. id у объекта должен быть такой же
     * какой у изменяемой записи в БД.
     */
    @Override
    public void updatePerson(Person person) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_PERSON_SQL_TEMPLATE)) {
            statement.setString(1, person.getName());
            statement.setDate(2, new Date(person.getBirthDate()));
            statement.setInt(3, person.getId());
            statement.execute();
            connection.commit();
            LOGGER.info("Студент " + person.getId() + " успешно обновлен!");
        }catch (Exception ex){
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            LOGGER.debug(ex.getMessage());
            LOGGER.error("Ошибка при обновлении записи о студенте!");
        }
    }

    /**
     * Удаляет запись о персоне
     * @param person Объект типа Person. id у объекта должен быть такой же
     * какой у удаляемой записи в БД.
     */
    @Override
    public void deletePerson(Person person) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_PERSON_SQL_TEMPLATE)) {
            statement.setInt(1, person.getId());
            statement.execute();
            System.out.println("Студент " + person.getName() + " с id= "+ person.getId() +" удален.");
            connection.commit();
        }catch (Exception ex){
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            LOGGER.debug(ex.getMessage());
            LOGGER.error("Ошибка при удалении записи студента!");
        }
    }
}
