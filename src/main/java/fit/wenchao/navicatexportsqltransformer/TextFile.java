package fit.wenchao.navicatexportsqltransformer;

import fit.wenchao.navicatexportsqltransformer.utils.IOUtils;
import jdk.internal.util.xml.impl.Input;
import org.springframework.boot.context.properties.PropertyMapper;

import java.io.*;
import java.lang.annotation.Target;
import java.nio.Buffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class TextFile implements Iterable<Line> {

    BufferedReader reader;

    byte[] bytes;

    String filePath;

    ArrayList<String> lines;

    public void init(String filePath) {
        this.filePath = filePath;

        ArrayList<String> lines = new ArrayList<>();
        FileInputStream instream = null;
        try {
            // bufferedReader = Files.newBufferedReader(Paths.get(testfile), StandardCharsets.UTF_8);


            instream = new FileInputStream(filePath);
            String line;
            // while ((line =bufferedReader.readLine()) != null) {
            while ((line = readLine(instream, StandardCharsets.UTF_8)) != null) {
                lines.add(line);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                if (instream != null) {
                    instream.close();
                }
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        this.lines = lines;
    }

    public static TextFile from(String filePath) {
        TextFile textFile = new TextFile();
        textFile.init(filePath);
        return textFile;
    }

    private static String readLine(InputStream inputStream, Charset charset) {
        ArrayList<Integer> arrayList = new ArrayList<>();

        try {
            int read = inputStream.read();
            if (read == '\n' || read == '\r') {
                read = inputStream.read();
            }
            if (read == -1) {
                return null;
            }
            while (read != '\n' && read != '\r') {
                arrayList.add(read);
                read = inputStream.read();
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        byte[] bytes1 = new byte[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            bytes1[i] = arrayList.get(i).byteValue();
        }

        return new String(bytes1, charset);
    }

    public Source convert(String srcRegex) {
        return Source.from(this, srcRegex);
    }

    @Override public Iterator<Line> iterator() {
        return new Iterator<Line>() {
            int index = 0;

            @Override public boolean hasNext() {
                if (index > lines.size() - 1) {
                    return false;
                }
                return true;
            }

            @Override public Line next() {
                Line line = Line.of(lines.get(index));
                index++;
                return line;
            }
        };
    }


}
