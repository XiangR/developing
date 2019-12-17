package com.joker.utils;

import java.io.*;

/**
 * Created by xiangrui on 2019/4/21.
 *
 * @author xiangrui
 * @date 2019/4/21
 */
public class FileUtil {

    public static void deleteFile(String path) {
        File file = new File(path);
        if (file.exists() && file.isFile()) {
            file.delete();
        }
    }

    /**
     * 插入文件并 newLine
     *
     * @param fileName 文件名
     * @param content  文字
     */
    public static void appendFileTextLine(String fileName, String content) {
        if (isNullOrEmpty(content)) {
            return;
        }
        File file = new File(fileName);
        try {
            if (!file.exists()) {
                file.createNewFile();// 创建新文件,有同名的文件的话直接覆盖
            }
            try (FileWriter writer = new FileWriter(file, true);
                 BufferedWriter out = new BufferedWriter(writer)
            ) {
                out.append(content);
                out.newLine();
                // out.flush(); // 把缓存区内容压入文件
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isNullOrEmpty(String content) {
        return content == null || content.length() == 0;
    }

}
