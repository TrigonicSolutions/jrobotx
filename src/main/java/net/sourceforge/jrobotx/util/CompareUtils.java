package net.sourceforge.jrobotx.util;

public class CompareUtils {
    public static int compare(int i1, int i2) {
        return i1 - i2;
    }
    
    public static int compare(boolean b1, boolean b2) {
        return compare(toInt(b1), toInt(b2)); 
    }
    
    private static int toInt(boolean b1) {
        return b1 ? 1 : 0;
    }
}
