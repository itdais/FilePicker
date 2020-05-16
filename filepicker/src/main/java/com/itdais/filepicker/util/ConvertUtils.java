package com.itdais.filepicker.util;

import java.text.DecimalFormat;

/**
 * 数据类型转换、单位转换
 *
 * @author 李玉江[QQ:1023694760]
 * @since 2014-4-18
 */
public class ConvertUtils {
    public static final long GB = 1073741824;
    public static final long MB = 1048576;
    public static final long KB = 1024;

    public static String toFileSizeString(long fileSize) {
        DecimalFormat df = new DecimalFormat("0.00");
        String fileSizeString;
        if (fileSize < KB) {
            fileSizeString = fileSize + "B";
        } else if (fileSize < MB) {
            fileSizeString = df.format((double) fileSize / KB) + "K";
        } else if (fileSize < GB) {
            fileSizeString = df.format((double) fileSize / MB) + "M";
        } else {
            fileSizeString = df.format((double) fileSize / GB) + "G";
        }
        return fileSizeString;
    }

}
