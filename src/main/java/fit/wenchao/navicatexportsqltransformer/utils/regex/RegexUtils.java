package fit.wenchao.navicatexportsqltransformer.utils.regex;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {

    public static RegexGroups regexGroup(String src, String pat) {
        Pattern compile = Pattern.compile(pat);
        Matcher matcher = compile.matcher(src);

        if (matcher.find()) {
            RegexGroups regexGroups = new RegexGroups();
            for (int i = 0; i < matcher.groupCount() +1; i++) {
                regexGroups.addGroupValue(matcher.group(i));
            }
            return regexGroups;
        }

        return new RegexGroups();
    }

    public static String regexFirstWord(String src, String pat) {
        Pattern compile = Pattern.compile(pat);
        Matcher matcher = compile.matcher(src);
        boolean matchFound = matcher.find();
        if (!matchFound) {
            return null;
        }
        return matcher.group();
    }

    public static Long regexFirstInt(String src) {
        return regexFirstNumber(src, "([-|\\+]?[1-9]\\d*|0)");
    }

    public static Long regexFirstPosInt(String src) {
        return regexFirstNumber(src, "([1-9]\\d*|0)");
    }

    private static Long regexFirstNumber(String src, String pat) {
        String value = regexFirstWord(src, pat);
        if (value == null) {
            return null;
        }
        try {
            return Long.valueOf(value);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public void testRegexFirstInt() {
        Long aLong = regexFirstInt("-12");
        System.out.println(aLong);
        aLong = regexFirstPosInt("0");
        System.out.println(aLong);
    }

    public void testRegexFirstPosInt() {
        Long aLong = regexFirstPosInt("-12");
        System.out.println(aLong);
        aLong = regexFirstPosInt("0");
        System.out.println(aLong);
    }

}