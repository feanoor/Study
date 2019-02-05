package lab8_StreamAPI;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class ResourceLoader implements ResourceLoaderable {
//    private String resource;
//
//    public ResourceLoader(String resource) {
//        this.resource = resource;
//    }

    @Override
    public BufferedReader getBufferedReader(String resource) throws IOException {
        typeResource tr = checkResourceType(resource);

        return getReader(tr,resource);
    }

    /**
     * Определяет тип ресурса.
     *
     * @param resource Адрес ресурса.
     */
    public typeResource checkResourceType(String resource) throws IOException {
        if (resource.startsWith("http")) {
            typeResource tr = typeResource.URL;
            //return getReader(tr,resource);
            return tr;
        } else {
            if (resource.startsWith("ftp")) {
                typeResource tr = typeResource.FTP;
                //return getReader(tr,resource);
                return tr;
            } else {
                typeResource tr = typeResource.FILE;
                //return getReader(tr,resource);
                return tr;
            }
        }
    }

    /**
     * Типы ресурсов
     */
    public enum typeResource {
        URL,
        FILE,
        FTP
    }


    /**
     * Метод проверяет доступность ftp или http ресурса.
     *
     * @param resource Адрес типа String.
     * @return Если ресурс доступен возвращает false, иначе true.
     */
    boolean resAvailable(String resource) {
        try {
            new URL(resource).openConnection().getInputStream();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private BufferedReader getReader(typeResource typeR, String fi) throws IOException {
        BufferedReader br = null;
        if (typeR.equals(typeResource.FILE)) {
            br = new BufferedReader(new FileReader(fi));
        }
        if (typeR.equals(typeResource.URL) | typeR.equals(typeResource.FTP)) {
            if (!resAvailable(fi)) {
                return null;
            }
            br = new BufferedReader(new InputStreamReader(new URL(fi).openConnection().getInputStream()));
        }
        return br;
    }

}
