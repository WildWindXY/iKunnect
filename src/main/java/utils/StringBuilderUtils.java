package utils;

public class StringBuilderUtils {
    public static void trimStartByChar(StringBuilder sb, char ch){
        int count = 0;
        while (count < sb.length() && sb.charAt(count) == ch) {
            count++;
        }
        sb.delete(0, count);
    }
}
