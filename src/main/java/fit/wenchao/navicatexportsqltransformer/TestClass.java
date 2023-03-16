package fit.wenchao.navicatexportsqltransformer;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestClass {

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = Files.newBufferedReader(Paths.get("chi.txt"));

        RandomAccessFile randomAccessFile = new RandomAccessFile(new File("chi-copy.txt"),"rw");
        String line;
        while((line = bufferedReader.readLine()) != null) {
            randomAccessFile.writeUTF(line);
        }
    }


}
