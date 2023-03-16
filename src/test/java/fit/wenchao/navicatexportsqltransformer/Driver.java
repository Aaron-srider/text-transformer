package fit.wenchao.navicatexportsqltransformer;

import fit.wenchao.navicatexportsqltransformer.utils.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class Driver {
    @Test
    public void test() {
        byte[] bytes1 = IOUtils.readAllBytes("chi.txt");
        byte[] bytes2 = IOUtils.readAllBytes("chi-copy.txt");

        reportBytes(bytes1);
        reportBytes(bytes2);

        System.out.println((int)'\r');
        System.out.println((int)'\n');
        System.out.println((char)12);
        System.out.println((char)0);

    }

    @Test
    public void test1(){

        try
        {
            RandomAccessFile randomAccessFile = new RandomAccessFile(new File("test.txt"), "rw");
            randomAccessFile.write("hello".getBytes(StandardCharsets.UTF_8));
            randomAccessFile.close();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void reportBytes(byte[] bytes) {
        for (byte aByte : bytes) {
            System.out.print(aByte);
            System.out.print(" ");
        }
        System.out.println();
    }
}
