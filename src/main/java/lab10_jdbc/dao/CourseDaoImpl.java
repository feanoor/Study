package lab10_jdbc.dao;

import lab10_jdbc.entity.Person;
import lab10_jdbc.entity.Subject;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.*;


public class CourseDaoImpl implements CourseDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseDaoImpl.class);

    public static final String GET_SUBJECT_SQL_TEMPLATE =
            "select subject_id, description from subject";

    public static final String LINK_TO_COURSE_SQL_TEMPLATE =
            "insert into course (subject_id, person_id, start_date) values (?,?,?);";

    public static final String GET_PERSONS_BY_SUBJECT_SQL_TEMPLATE =
            "select pers.person_id, pers.name, pers.birth_date from person pers, course cour where cour.subject_id = ? and cour.person_id = pers.person_id";

    public static final String GET_SUBJECTS_BY_PERSON_SQL_TEMPLATE =
            "select sub.subject_id, sub.description from subject sub, course cour where cour.person_id = ? and cour.subject_id = sub.subject_id";

    private final Connection connection;

    public CourseDaoImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * Создает список персон по субьекту.
     * @param subject Объект типа Subject.
     * @return
     */
    @Override
    public Collection<Person> getPersonsBySubject(Subject subject) {
        Collection<Person> persons = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(GET_PERSONS_BY_SUBJECT_SQL_TEMPLATE)) {
            statement.setInt(1, subject.getId());
            ResultSet rs;
            rs = statement.executeQuery();
            while (rs.next()) {
                persons.add(new Person(rs.getInt(1), rs.getString(2), rs.getTimestamp(3).getTime()));
            }
        } catch (Exception ex) {
            LOGGER.debug(ex.getMessage());
            LOGGER.error("Ошибка при получении списка студентов!");
        }
        return persons;
    }

    /**
     * Создает список предметов по персоне.
     * @param person Объект типа Person.
     * @return
     */
    @Override
    public Collection<Subject> getSubjectsByPerson(Person person) {
        Collection<Subject> subjects = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(GET_SUBJECTS_BY_PERSON_SQL_TEMPLATE)) {
            statement.setInt(1, person.getId());
            ResultSet rs;
            rs = statement.executeQuery();
            while (rs.next()) {
                subjects.add(new Subject(rs.getInt(1), rs.getString(2)));
            }
        } catch (Exception ex) {
            LOGGER.debug(ex.getMessage());
            LOGGER.error("Ошибка при получении списка предметов!");
        }
        return subjects;
    }

    /**
     * Назначить персону на курс по предмету.
     * @param date Дата курса в формате long.
     * @param person Персона.
     * @param subject Предмет.
     */
    @Override
    public void linkToCourse(Long date, Person person, Subject subject) {
        try (PreparedStatement statement = connection.prepareStatement(LINK_TO_COURSE_SQL_TEMPLATE)) {
            statement.setInt(1, subject.getId());
            statement.setInt(2, person.getId());
            statement.setDate(3, new Date(date));
            statement.execute();
            connection.commit();
            LOGGER.info("Курс на дату " + new Date(date).toString() + " создан.");
        } catch (Exception ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            LOGGER.debug(ex.getMessage());
            LOGGER.error("Ошибка при добавлении на курс!");
        }
    }

    /**
     * Назначить персону на курс по предметам.
     * @param date Дата курса в формате long.
     * @param person Персона.
     * @param subject Массив предметов.
     */
    @Override
    public void linkToCourse(Long date, Person person, Subject... subject) {
        try (PreparedStatement statement = connection.prepareStatement(LINK_TO_COURSE_SQL_TEMPLATE)) {
            statement.setInt(2, person.getId());
            statement.setDate(3, new Date(date));
            for (Subject sub : subject) {
                statement.setInt(1, sub.getId());
                statement.addBatch();
            }
            statement.executeBatch();
            connection.commit();
            LOGGER.info("Курсы на дату " + new Date(date).toString() + " созданы.");
        } catch (Exception ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            LOGGER.debug(ex.getMessage());
            LOGGER.error("Ошибка при добавлении на курс!");
        }
    }

    /**
     * Назначить персоны на курс по предмету.
     * @param date Дата курса в формате long.
     * @param person Массив персон.
     * @param subject Предмет.
     */
    @Override
    public void linkToCourse(Long date, Subject subject, Person... person) {
        try (PreparedStatement statement = connection.prepareStatement(LINK_TO_COURSE_SQL_TEMPLATE)) {
            statement.setInt(1, subject.getId());
            statement.setDate(3, new Date(date));
            for (Person per : person) {
                statement.setInt(2, per.getId());
                statement.addBatch();
            }
            statement.executeBatch();
            connection.commit();
            LOGGER.info("Курсы на дату " + new Date(date).toString() + " созданы.");
        } catch (SQLException sqlex) {
            LOGGER.error(sqlex.getMessage());
        } catch (Exception ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            LOGGER.debug(ex.getMessage());
            LOGGER.error("Ошибка при добавлении на курс!");
        }
    }


}
