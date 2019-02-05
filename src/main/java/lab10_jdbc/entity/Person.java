package lab10_jdbc.entity;

import java.util.Date;
import java.util.Objects;

public class Person {
    public Person() {
    }

    /**
     * Инициализирует поля класса переданными параметрами.
     * @param id
     * @param name
     * @param birthDate Дата рождения в формате long.
     */
    public Person(int id, String name, long birthDate) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
    }

    private int id;
    private String name;
    private long birthDate;

    /**
     * Получить id персоны.
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Установить id персоны.
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Получить имя персоны.
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Установить имя персоны.
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Получить дату рождения персоны.
     * @return Дата в формате long.
     */
    public long getBirthDate() {
        return birthDate;
    }

    /**
     * Установить дату рождения персоны.
     * @param birthDate Дата в формате long.
     */
    public void setBirthDate(long birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + new Date(birthDate) +
                '}';
    }
}
