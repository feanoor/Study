package lab10_jdbc.dao;

import lab10_jdbc.entity.Person;
import lab10_jdbc.entity.Subject;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import org.slf4j.*;


public class CourseDaoImpl implements CourseDAO {

    public static final String GET_SUBJECT_SQL_TEMPLATE =
            "select subject_id, description from subject";

    public static final String LINK_TO_COURSE_SQL_TEMPLATE =
            "insert into course (subject_id, person_id, start_date) values (?,?,?);";

    public static final String GET_PERSONS_BY_SUBJECT_SQL_TEMPLATE =
            "select pers.person_id, pers.name, pers.birth_date from person pers, course cour where cour.subject_id = ? and cour.person_id = pers.person_id";

    public static final String GET_SUBJECTS_BY_PERSON_SQL_TEMPLATE =
            "select sub.subject_id, sub.description from subject sub, course cour where cour.person_id = ? and cour.subject_id = sub.subject_id";

    private final Connection connection;

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseDaoImpl.class);

    public CourseDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Collection<Person> getPersonsBySubject(Subject subject) {
        Collection<Person> persons = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(GET_PERSONS_BY_SUBJECT_SQL_TEMPLATE)) {
            statement.setInt(1, subject.getId());
            ResultSet rs;
            rs = statement.executeQuery();
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
    public Collection<Subject> getSubjectsByPerson(Person person) {
        Collection<Subject> subjects = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(GET_SUBJECTS_BY_PERSON_SQL_TEMPLATE)) {
            statement.setInt(1, person.getId());
            ResultSet rs;
            rs = statement.executeQuery();
            while (rs.next()){
                subjects.add(new Subject(rs.getInt(1),rs.getString(2)));
            }
        }catch (Exception ex){
            //ex.printStackTrace();
            System.out.println("Что-то пошло не так...");
        }
        return subjects;
    }

    @Override
    public void linkToCourse(Long date, Person person, Subject subject) {
        try (PreparedStatement statement = connection.prepareStatement(LINK_TO_COURSE_SQL_TEMPLATE)) {
            statement.setInt(1, subject.getId());
            statement.setInt(2, person.getId());
            statement.setDate(3, new Date(date));
            statement.execute();
            connection.commit();
            System.out.println("Курс на дату " + new Date(date).toString() + " создан.");
        }
        catch (SQLException sqlex){
            sqlex.printStackTrace();
        }
        catch (Exception ex){
            //ex.printStackTrace();
            System.out.println("Что-то пошло не так...");
        }
    }

    @Override
    public void linkToCourse(Long date, Person person, Subject... subject) {
        try (PreparedStatement statement = connection.prepareStatement(LINK_TO_COURSE_SQL_TEMPLATE)) {
            statement.setInt(2, person.getId());
            statement.setDate(3, new Date(date));
            for(Subject sub : subject){
                statement.setInt(1, sub.getId());
                statement.addBatch();
            }
            statement.executeBatch();
            connection.commit();
            System.out.println("Курсы на дату " + new Date(date).toString() + " созданы.");
        }
        catch (SQLException sqlex){
            sqlex.printStackTrace();
        }
        catch (Exception ex){
            //ex.printStackTrace();
            System.out.println("Что-то пошло не так...");
        }
    }

    @Override
    public void linkToCourse(Long date, Subject subject, Person... person) {
        try (PreparedStatement statement = connection.prepareStatement(LINK_TO_COURSE_SQL_TEMPLATE)) {
            statement.setInt(1, subject.getId());
            statement.setDate(3, new Date(date));
            for(Person per : person){
                statement.setInt(2, per.getId());
                statement.addBatch();
            }
            statement.executeBatch();
            connection.commit();
            System.out.println("Курсы на дату " + new Date(date).toString() + " созданы.");
        }
        catch (SQLException sqlex){
            sqlex.printStackTrace();
        }
        catch (Exception ex){
            //ex.printStackTrace();
            System.out.println("Что-то пошло не так...");
        }
    }


}
