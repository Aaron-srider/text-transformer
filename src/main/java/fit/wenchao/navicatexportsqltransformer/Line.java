package fit.wenchao.navicatexportsqltransformer;

import fit.wenchao.navicatexportsqltransformer.utils.regex.RegexGroup;
import fit.wenchao.navicatexportsqltransformer.utils.regex.RegexGroups;
import fit.wenchao.navicatexportsqltransformer.utils.regex.RegexUtils;

import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

public class Line {
    String str;
    String replaced;


    public static Line of(String str) {
        Line line = new Line();
        line.str = str;
        return line;
    }

    public void replace(String srcRegex, String dest) {
        String line = str;

        RegexGroups regexGroups = RegexUtils.regexGroup(line, srcRegex);

        if (regexGroups.getSize() == 0) {
            replaced = line;
            return;
        }

        // replace entire
        if (regexGroups.getSize() == 1) {
            RegexGroup byIndex = regexGroups.getByIndex(0);
            String value = byIndex.getValue();
            replaced = line.replace(value, dest);
            return;
        }

        // only replace first group
        if (regexGroups.getSize() >= 2) {
            RegexGroup byIndex = regexGroups.getByIndex(1);
            String value = byIndex.getValue();
            replaced = line.replace(value, dest);
            return;
        }

    }

    public void appendTo(RandomAccessFile outFile) {
        try {
            long fileLength = outFile.length();
            outFile.seek(fileLength);
            outFile.write((replaced + "\n").getBytes(StandardCharsets.UTF_8));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
