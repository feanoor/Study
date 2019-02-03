package lab10_jdbc.dao;

import lab10_jdbc.entity.Person;
import lab10_jdbc.entity.Subject;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;


public class SubjectDAOImpl implements SubjectDAO {


    public static final String INSERT_SUBJECT_SQL_TEMPLATE =
            "insert into subject (description) values (?) returning subject_id";

    public static final String DELETE_SUBJECT_SQL_TEMPLATE =
            "delete from subject where subject.subject_id = ?";

    public static final String UPDATE_SUBJECT_SQL_TEMPLATE =
            "update subject set description = ? where subject_id = ?";

    public static final String GET_SUBJECT_SQL_TEMPLATE =
            "select subject_id, description from subject";

    private final Connection connection;

    public SubjectDAOImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public Collection<Subject> getAllSubjects() {
        Collection<Subject> subjects = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs;
            rs = statement.executeQuery(GET_SUBJECT_SQL_TEMPLATE);
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
    public int createSubject(Subject subject) throws SQLException {
        int i = -1;
        try (PreparedStatement statement = connection.prepareStatement(INSERT_SUBJECT_SQL_TEMPLATE)) {
            statement.setString(1, subject.getDescription());
            ResultSet rs;
            rs = statement.executeQuery();
            rs.next();
            i = rs.getInt(1);
            if (i<0) {
                throw new SQLException();
            }
            connection.commit();
            System.out.println("Предмет " + subject.getDescription() + " добавлен.");
        }catch (Exception ex){
            //ex.printStackTrace();

            i = -1;
            System.out.println("Что-то пошло не так...");
        }
        return i;
    }

    @Override
    public void updateSubject(Subject subject) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_SUBJECT_SQL_TEMPLATE)) {
            statement.setString(1, subject.getDescription());
            statement.setInt(2, subject.getId());
            statement.execute();
            connection.commit();
        }catch (Exception ex){
            ex.printStackTrace();
            System.out.println("Что-то пошло не так...");
        }
    }

    @Override
    public void deleteSubject(Subject subject) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_SUBJECT_SQL_TEMPLATE)) {
            statement.setInt(1, subject.getId());
            statement.execute();
            System.out.println("subject " + " с id= "+ subject.getId() +" удален.");
            connection.commit();
        }catch (Exception ex){
            ex.printStackTrace();
            System.out.println("Что-то пошло не так...");
        }
    }
}
