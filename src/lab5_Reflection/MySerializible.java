package lab5_Reflection;

public interface MySerializible {
    void serialize (Object object, String file);
    Object deSerialize(String file);
}
