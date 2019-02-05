package lab8_StreamAPI;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
    class MultiThreadingTests {
        ContainChecker cc;
        ResourceLoader rl = new ResourceLoader();

        @org.junit.jupiter.api.BeforeEach
        void setUp() {
            if(null != ContainChecker.dictHash && !ContainChecker.dictHash.isEmpty()){
                ContainChecker.dictHash = null;
            }

            cc = new ContainChecker();
        }

        @org.junit.jupiter.api.Test
        void getDictHashSet() {
            String [] mas = {"vsvz", "vsv", "fgg"};
            Assertions.assertNull(ContainChecker.dictHash);
            cc.getDictHashSet(mas);
            Assertions.assertEquals(ContainChecker.dictHash.size(),3);
        }

        @org.junit.jupiter.api.Test
        void checkResourceType() throws IOException {
            try {
                Assertions.assertEquals(ResourceLoader.typeResource.FILE, rl.checkResourceType("C:\\docs\\trash\\text-1.txt"));
                Assertions.assertEquals(ResourceLoader.typeResource.URL, rl.checkResourceType("http://www.gutenberg.org/files/48763/48763-0.txt"));
                Assertions.assertEquals(ResourceLoader.typeResource.FTP, rl.checkResourceType("ftp://www.gutenberg.org/files/48763/48763-0.txt"));

            }catch (NullPointerException ex){}
        }

        @org.junit.jupiter.api.Test
        void writeToFile() {
            String filePath = "C:\\Users\\admin\\AppData\\Local\\Temp\\temp_file.tmp";
            String writingString = "Prev him, href html header class the.";
            File file = new File(filePath);
            cc.writeToFile(writingString, filePath);
            Assertions.assertEquals(file.isFile()&& file.length() > 0,true);
            file.delete();
        }

        @org.junit.jupiter.api.Test
        void writeToFileOnIOException(){
            String filePath = "C:\\Users\\Администратор\\filename.txt";
            String writingString = "Prev him, href html header class the.";
            ContainChecker ccMock = Mockito.mock(cc.getClass());
            Mockito.doThrow(IOException.class).when(ccMock).writeToFile(writingString, filePath);
            //Mockito.doThrow(cc.writeToFile(writingString, filePath), IOException.class);



        }

        @org.junit.jupiter.api.Test
        void resAvailable() {
            Assertions.assertEquals(true,rl.resAvailable("http://www.gutenberg.org/files/48763/48763-0.txt"));
            Assertions.assertEquals(false,rl.resAvailable("http://www.gutenberg.org/files/4873/4876.txt"));
        }

        @org.junit.jupiter.api.Test
        void checkEquals() throws IOException {
            ContainChecker spy = Mockito.spy(cc);
            BufferedReader mock = Mockito.mock(BufferedReader.class);
            Mockito.when(mock.readLine()).thenReturn("Prev him, href html header class the.")
                    .thenReturn("Prev  fgg header class the.")
                    .thenReturn(" Harry bookmark, px k copy, html , ")
                    .thenReturn(" street, class highslide j dursley had, because normal, harry?")
                    .thenReturn(null);
            String [] mas = {"html", "fgg"};
            spy.getDictHashSet(mas);
            spy.checkEquals(mock, "someFileName");
            Mockito.verify(spy, Mockito.times(3)).writeToFile(Mockito.anyString(),Mockito.anyString());
        }

        @org.junit.jupiter.api.Test
        void containsInDict() {
            HashSet<String> hs = new HashSet<String>(){{add("href");}};
            String str = "Prev him, href html header class the.";
            Assertions.assertEquals(true,cc.containsInDict(str,hs));
        }

        @org.junit.jupiter.api.Test
        void getOccurencies() throws IOException {
            int j = 3;
            File temp[] = new File[j];
            String sources[] = new String[j];
            for (int i = 0; i<j; i++){
                temp[i] = File.createTempFile("temp_file-" + i,".tmp");
                BufferedWriter bw = new BufferedWriter(new FileWriter(temp[i]));
                bw.write("Prev  fgg header class the.\n");
                bw.write(" Harry bookmark, px k copy, html , \n");
                bw.write(" street, class highslide j dursley had, because normal, harry?");
                bw.close();
                sources[i] = temp[i].getAbsolutePath();
            }
            ContainChecker spy = Mockito.spy(cc);
            String [] mas = {"dursley", "vsv", "fgg"};
            System.out.println(Arrays.toString(mas));
            System.out.println(Arrays.toString(sources));
            spy.getOccurencies(sources, mas, "C:\\Users\\admin\\AppData\\Local\\Temp\\temp_file.tmp");
            Mockito.verify(spy, Mockito.times(6)).writeToFile(Mockito.anyString(),Mockito.anyString());
            for (int i = 0; i<j; i++){
                temp[i].delete();
            }
        }

        @org.junit.jupiter.api.Test
        void getBufferedReader() throws IOException {
            File temp = File.createTempFile("temp_file", ".tmp");
            BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
            bw.write("Prev  fgg header class the.\n");
            bw.close();
            String source = temp.getAbsolutePath();
            rl.getBufferedReader(source);
            Assertions.assertEquals(rl.getBufferedReader(source).readLine() != null, true);
            temp.delete();
        }
    }