package models;

public class Functions {
    public static Integer longToInt(Long num) {
        if (num < Integer.MAX_VALUE) {
            return num.intValue();
        }
        return Integer.MAX_VALUE;
    }
}
