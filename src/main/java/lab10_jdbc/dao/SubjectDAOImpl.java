package lab10_jdbc.dao;

import lab10_jdbc.entity.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;


public class SubjectDAOImpl implements SubjectDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubjectDAOImpl.class);

    public static final String INSERT_SUBJECT_SQL_TEMPLATE =
            "insert into subject (description) values (?) returning subject_id";

    public static final String DELETE_SUBJECT_SQL_TEMPLATE =
            "delete from subject where subject.subject_id = ?";

    public static final String UPDATE_SUBJECT_SQL_TEMPLATE =
            "update subject set description = ? where subject_id = ?";

    public static final String GET_SUBJECT_SQL_TEMPLATE =
            "select subject_id, description from subject";

    private final Connection connection;

    /**
     * Получает подключение и сохраняет в данном объекте.
     * @param connection
     */
    public SubjectDAOImpl(Connection connection) {
        this.connection = connection;
    }


    /**
     * Получить коллекцию всех предметов.
     * @return Коллекция предметов.
     */
    @Override
    public Collection<Subject> getAllSubjects() {
        Collection<Subject> subjects = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs;
            rs = statement.executeQuery(GET_SUBJECT_SQL_TEMPLATE);
            while (rs.next()){
                subjects.add(new Subject(rs.getInt(1),rs.getString(2)));
            }
            if(subjects.size()>0){
                LOGGER.info("Получение списка предметов успешно!");
            }
            else {
                LOGGER.info("Таблица предметов пуста!");
            }
        }catch (Exception ex){
            LOGGER.debug(ex.getMessage());
            LOGGER.error("Ошибка при получении списка предметов!");
        }
        return subjects;
    }

    /**
     * Добавить в БД предмет.
     * @param subject Объект типа Subject.
     * @return Если возвращает не -1 значит транзакция успешна.
     * @throws SQLException
     */
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
            LOGGER.info("Предмет " + subject.getDescription() + " добавлен!");
        }catch (Exception ex){
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            i = -1;
            LOGGER.debug(ex.getMessage());
            LOGGER.error("Ошибка при создании предмета!");
        }
        return i;
    }

    /**
     * Обновляет информацию о предмете.
     * @param subject Объект типа Subject. id у объекта должен быть такой же
     * какой у изменяемой записи в БД.
     */
    @Override
    public void updateSubject(Subject subject) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_SUBJECT_SQL_TEMPLATE)) {
            statement.setString(1, subject.getDescription());
            statement.setInt(2, subject.getId());
            statement.execute();
            connection.commit();
            LOGGER.info("subject " + " с id= "+ subject.getId() +" обновлен!");
        }catch (Exception ex){
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            LOGGER.debug(ex.getMessage());
            LOGGER.error("Ошибка при обновлении предмета!");
        }
    }

    /**
     * Удаляет запись о предмете.
     * @param subject Объект типа Subject. id у объекта должен быть такой же
     * какой у удаляемой записи в БД.
     */
    @Override
    public void deleteSubject(Subject subject) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_SUBJECT_SQL_TEMPLATE)) {
            statement.setInt(1, subject.getId());
            statement.execute();
            connection.commit();
            LOGGER.info("subject " + " с id= "+ subject.getId() +" удален!");
        }catch (Exception ex){
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            LOGGER.debug(ex.getMessage());
            LOGGER.error("Ошибка при получении удалении предмета!");
        }
    }
}
