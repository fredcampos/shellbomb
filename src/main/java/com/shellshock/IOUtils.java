package com.shellshock;

import java.text.DecimalFormat;

public class IOUtils {

    public static String formatSize(long value) {
        double size = new Double(value);
        double BASE = 1024;
        double KB = BASE;
        double MB = KB * BASE;
        double GB = MB * BASE;
        DecimalFormat df = new DecimalFormat("#.##");
        if (size >= GB) {
            return df.format(size / GB) + " GB";
        }
        if (size >= MB) {
            return df.format(size / MB) + " MB";
        }
        if (size >= KB) {
            return df.format(size / KB) + " KB";
        }
        return "" + (int) size + " bytes";
    }
    
}
