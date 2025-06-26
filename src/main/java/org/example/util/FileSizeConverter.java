package org.example.util;

import java.text.DecimalFormat;

public class FileSizeConverter {
    private static final String[] UNITS = {"B", "KB", "MB", "GB", "TB", "PB"};
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

    /**
     * 转换文件大小单位
     * @param sizeInBytes 文件大小（字节）
     * @param targetUnit 目标单位 (0=自动, 1=B, 2=KB, 3=MB, 4=GB, 5=TB, ...)
     * @return 格式化后的字符串（包含数值和单位）
     */
    public static String convertFileSize(long sizeInBytes, int targetUnit) {
        if (sizeInBytes < 0) {
            throw new IllegalArgumentException("文件大小不能为负数");
        }

        // 处理0字节的特殊情况
        if (sizeInBytes == 0) {
            return "0B";
        }

        // 自动选择合适单位
        if (targetUnit == 0) {
            return autoConvert(sizeInBytes);
        }

        // 手动指定单位转换
        return manualConvert(sizeInBytes, targetUnit);
    }

    private static String autoConvert(long bytes) {
        int unitIndex = 0;
        double size = bytes;

        // 循环找到最适合的单位（数值在1-1024之间）
        while (size >= 1024 && unitIndex < UNITS.length - 1) {
            size /= 1024;
            unitIndex++;
        }
        return formatSize(size, unitIndex);
    }

    private static String manualConvert(long bytes, int targetUnit) {
        int unitIndex = targetUnit - 1;  // 转换为数组索引

        // 处理超出范围的单位
        if (unitIndex >= UNITS.length) {
            unitIndex = UNITS.length - 1;  // 使用最大可用单位
        } else if (unitIndex < 0) {
            unitIndex = 0;  // 使用最小单位（字节）
        }

        double size = bytes;
        // 将字节转换为目标单位
        for (int i = 0; i < unitIndex; i++) {
            size /= 1024;
        }
        return formatSize(size, unitIndex);
    }

    private static String formatSize(double size, int unitIndex) {
        // 格式化数值（整数部分大于10时保留整数，否则保留两位小数）
        if (size >= 10 || size == (long) size) {
            return (long) size + UNITS[unitIndex];
        }
        return DECIMAL_FORMAT.format(size) + UNITS[unitIndex];
    }

    // 测试用例
//    public static void main(String[] args) {
//        System.out.println(convertFileSize(0, 0));         // 0B
//        System.out.println(convertFileSize(1023, 0));      // 1023B
//        System.out.println(convertFileSize(1024, 0));      // 1KB
//        System.out.println(convertFileSize(1536, 0));      // 1.5KB
//        System.out.println(convertFileSize(1048576, 0));  // 1MB
//        System.out.println(convertFileSize(123456789, 0)); // 117.74MB
//
//        System.out.println(convertFileSize(1024, 1));      // 1024B
//        System.out.println(convertFileSize(1024, 2));      // 1KB
//        System.out.println(convertFileSize(1048576, 3));   // 1MB
//        System.out.println(convertFileSize(1073741824, 4));// 1GB
//
//        System.out.println(convertFileSize(999, 3));       // 0KB（实际显示0.95KB，但根据规则显示整数）
//        System.out.println(convertFileSize(2048, 10));     // 使用最大单位PB
//    }
}