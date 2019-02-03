package lab4_MultiThreading;

import java.io.FileNotFoundException;

public interface Occurenciesable {
    void getOccurencies(String[] sources, String[] words, String res) throws FileNotFoundException;
}
