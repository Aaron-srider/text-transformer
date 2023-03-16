package fit.wenchao.navicatexportsqltransformer;

import fit.wenchao.navicatexportsqltransformer.utils.IOUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class Source {

    String srcRegex;

    TextFile textFile;

    public static Source from(TextFile textFile, String srcRegex) {
        Source source = new Source();
        source.srcRegex = srcRegex;
        source.textFile = textFile;
        return source;
    }

    public static void clearFile(String fileName) {
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void to(String dest) {
        String out = UUID.randomUUID().toString();
        File outFile = new File(out);
        if (!outFile.exists()) {
            try {
                outFile.createNewFile();
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        clearFile(out);
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(outFile, "rw");
            for (Line line : textFile) {
                line.replace(srcRegex, dest);
                line.appendTo(randomAccessFile);
            }
            randomAccessFile.close();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            IOUtils.copyThenClose(new FileInputStream(out), new FileOutputStream(textFile.filePath));
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        textFile.init(textFile.filePath);
        try {
            Files.deleteIfExists(Paths.get(out));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
