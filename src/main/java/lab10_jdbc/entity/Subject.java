package lab10_jdbc.entity;

import java.util.Objects;

public class Subject {


    private int id;
    private String description;

    /**
     * Инициализирует поля класса переданными параметрами id и description.
     * @param id
     * @param description
     */
    public Subject(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public Subject() {

    }

    /**
     * Получить id предмета.
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Установить id предмета.
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Получить имя персоны.
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Установить название предмета.
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return id == subject.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
