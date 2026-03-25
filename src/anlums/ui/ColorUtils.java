package anlums.ui;

public class ColorUtils {
    // 颜色前缀
    public static final String RESET = "\u001B[0m";
    public static final String red = "\u001B[31m";
    public static final String green = "\u001B[32m";
    public static final String yellow = "\u001B[33m";
    public static final String blue = "\u001B[34m";
    public static final String purple = "\u001B[35m";
    public static final String cyan = "\u001B[36m";
    public static void printColor(String text, String color) {
        System.out.println(color + text + RESET);
    }
}

