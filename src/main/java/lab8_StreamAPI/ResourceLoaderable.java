package lab8_StreamAPI;

import java.io.BufferedReader;
import java.io.IOException;

public interface ResourceLoaderable {
    BufferedReader getBufferedReader(String str) throws IOException;
}
